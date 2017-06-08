/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/

package org.apache.cayenne.modeler.layout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.access.DataRowStore;
import org.apache.cayenne.event.JMSBridgeFactory;
import org.apache.cayenne.event.JavaGroupsBridgeFactory;
import org.apache.cayenne.modeler.adapters.DataDomainAdapter;
import org.apache.cayenne.modeler.notification.event.DataDomainChangeEvent;
import org.apache.cayenne.modeler.notification.listener.DataDomainListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

// org.apache.cayenne.modeler.controller.DataDomainViewController
public class DataDomainLayout
    extends AbstractViewLayout
    implements DataDomainListener,
               DetailEditorSupport<DataDomainAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(DataDomainLayout.class);

//    private MainWindowLayout mainWindow;

//    private static Map<CayenneProject, BeanPathAdapter<CayenneProject>> dataDomainPropertyAdapterMap = new HashMap<CayenneProject, BeanPathAdapter<CayenneProject>>();

    @FXML
    private TextField nameTextField,
                      multicastAddressTextField,
                      multicastPortTextField,
                      jGroupsFileTextField,
                      jmsConnectionFactoryNameTextField,
                      customTransportFactoryClass;

    @FXML
    private CheckBox objectValidationCheckBox;

    @FXML
    private Spinner<Integer> objectCacheSizeSpinner;

    @FXML
    private CheckBox useSharedCacheCheckBox; // , remoteChangeNotificationsCheckBox;

//    @FXML
//    private Button remoteChangeConfigurationButton;

    @FXML
    private VBox javaGroupsConfiguration,
                 jmsConfiguration,
                 customConfiguration;

    @FXML
    private ChoiceBox<String> remoteChangeNotificationsChoiceBox;

    private DataDomainAdapter dataDomainAdapter;

    private List<Binding<?>> bindings;

    // Remote Change Notification groups.
    private static final String RCN_NONE        = "None";
    private static final String RCN_JAVA_GROUPS = "JavaGroups Multicast";
    private static final String RCN_JMS         = "JMS Transport";
    private static final String RCN_CUSTOM      = "Custom Transport";

    private static final String[] remoteChangeNotificationOptions =
        {
            RCN_NONE,
            RCN_JAVA_GROUPS,
            RCN_JMS,
            RCN_CUSTOM
        };

    public DataDomainLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/DataDomainLayout.fxml");
    }

    @Override
    public void initializeLayout()
    {
        super.initializeLayout();

        objectCacheSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, DataRowStore.SNAPSHOT_CACHE_SIZE_DEFAULT, 100));

        useSharedCacheCheckBox.selectedProperty().addListener((obs, oldValue, newValue) ->
            {
                configureRemoteNotifications(newValue);
            });

        hide(javaGroupsConfiguration, jmsConfiguration, customConfiguration);

        remoteChangeNotificationsChoiceBox.getItems().addAll(remoteChangeNotificationOptions);
        remoteChangeNotificationsChoiceBox.getSelectionModel().select(1);
        remoteChangeNotificationsChoiceBox.valueProperty().addListener((observable, oldValue, newValue) ->
            {
                dataDomainAdapter.setRemoteChangeNotifications(newValue != RCN_NONE);

                if (newValue == RCN_NONE)
                {
                    hide(javaGroupsConfiguration, jmsConfiguration, customConfiguration);
                    dataDomainAdapter.setEventBridgeFactoryProperty(null);
                }
                else if (newValue == RCN_JAVA_GROUPS)
                {
                    hide(jmsConfiguration, customConfiguration);
                    show(javaGroupsConfiguration);
                    dataDomainAdapter.setEventBridgeFactoryProperty(null);
                }
                else if (newValue == RCN_JMS)
                {
                    hide(javaGroupsConfiguration, customConfiguration);
                    show(jmsConfiguration);
                    dataDomainAdapter.setEventBridgeFactoryProperty(JMSBridgeFactory.class.getName());
                }
                else if (newValue == RCN_CUSTOM)
                {
                    hide(javaGroupsConfiguration, jmsConfiguration);
                    show(customConfiguration);
                    if (StringUtils.equals(dataDomainAdapter.getEventBridgeFactoryProperty(), JMSBridgeFactory.class.getName()))
                        dataDomainAdapter.setEventBridgeFactoryProperty("use.custom.Class");
                }
            });
    }

    @Override
    public void setPropertyAdapter(final DataDomainAdapter dataDomainAdapter)
    {
        this.dataDomainAdapter = dataDomainAdapter;
    }

    @Override
    public void initializeBindings()
    {
        bindings = new ArrayList<>();

        bindings.add(new Binding<>(nameTextField.textProperty(), dataDomainAdapter.nameProperty()));
        bindings.add(new Binding<>(objectValidationCheckBox.selectedProperty(), dataDomainAdapter.validatingObjectsProperty()));
        bindings.add(new Binding<>(objectCacheSizeSpinner.getValueFactory().valueProperty(), dataDomainAdapter.sizeOfObjectCacheProperty().asObject()));
        bindings.add(new Binding<>(useSharedCacheCheckBox.selectedProperty(), dataDomainAdapter.useSharedCacheProperty()));

        bindings.add(new Binding<>(customTransportFactoryClass.textProperty(), dataDomainAdapter.eventBridgeFactoryProperty()));
        bindings.add(new Binding<>(multicastAddressTextField.textProperty(), dataDomainAdapter.javaGroupsMulticastAddressProperty()));
        bindings.add(new Binding<>(multicastPortTextField.textProperty(), dataDomainAdapter.javaGroupsMulticastPortProperty()));
        bindings.add(new Binding<>(jmsConnectionFactoryNameTextField.textProperty(), dataDomainAdapter.jmsConnectionFactoryProperty()));
    }

    @Override
    public List<Binding<?>> getBindings()
    {
        return bindings;
    }

    @Override
    public void beginEditing()
    {
//        LOGGER.debug("begin editing " + this);
        DetailEditorSupport.super.beginEditing();
//        show(javaGroupsConfiguration, jmsConfiguration, customConfiguration);

//        nameTextField.textProperty().bindBidirectional(dataDomainAdapter.nameProperty());
//        objectValidationCheckBox.selectedProperty().bindBidirectional(dataDomainAdapter.validatingObjectsProperty());

//        objectCacheSizeSpinner.getValueFactory().valueProperty().bindBidirectional(dataDomainAdapter.sizeOfObjectCacheProperty().asObject());
//        useSharedCacheCheckBox.selectedProperty().bindBidirectional(dataDomainAdapter.useSharedCacheProperty());
//        remoteChangeNotificationsCheckBox.selectedProperty().bindBidirectional(dataDomainAdapter.remoteChangeNotificationsProperty());

        configureRemoteNotifications(dataDomainAdapter.getUseSharedCache());

//        customTransportFactoryClass.textProperty().bindBidirectional(dataDomainAdapter.eventBridgeFactoryProperty());
//        multicastAddressTextField.textProperty().bindBidirectional(dataDomainAdapter.javaGroupsMulticastAddressProperty());
//        multicastPortTextField.textProperty().bindBidirectional(dataDomainAdapter.javaGroupsMulticastPortProperty());
//        jmsConnectionFactoryNameTextField.textProperty().bindBidirectional(dataDomainAdapter.jmsConnectionFactoryProperty());
    }

//    @Override
//    public void endEditing()
//    {
//        LOGGER.debug("end editing " + this);
//
//        nameTextField.textProperty().unbindBidirectional(dataDomainAdapter.nameProperty());
//        objectValidationCheckBox.selectedProperty().unbindBidirectional(dataDomainAdapter.validatingObjectsProperty());
//
//        objectCacheSizeSpinner.getValueFactory().valueProperty().unbindBidirectional(dataDomainAdapter.sizeOfObjectCacheProperty().asObject());
//        useSharedCacheCheckBox.selectedProperty().unbindBidirectional(dataDomainAdapter.useSharedCacheProperty());
////        remoteChangeNotificationsCheckBox.selectedProperty().unbindBidirectional(dataDomainAdapter.remoteChangeNotificationsProperty());
//
//        customTransportFactoryClass.textProperty().unbindBidirectional(dataDomainAdapter.eventBridgeFactoryProperty());
//        multicastAddressTextField.textProperty().unbindBidirectional(dataDomainAdapter.javaGroupsMulticastAddressProperty());
//        multicastPortTextField.textProperty().unbindBidirectional(dataDomainAdapter.javaGroupsMulticastPortProperty());
//        jmsConnectionFactoryNameTextField.textProperty().unbindBidirectional(dataDomainAdapter.jmsConnectionFactoryProperty());
//
////        NotificationCenter.removeProjectListener(getMainWindow().getCayenneProject(), this);
//////        BeanPathAdapter<CayenneModel> dataDomainAdapter = getDataDomainPropertyAdapterMap(getMainWindow().getCayenneModel());
//////
//////        dataDomainAdapter.fieldPathValueProperty().removeListener(changeObserver);
//////
//////        dataDomainAdapter.unBindBidirectional("dataDomainName", dataDomainNameTextField.textProperty());
//////        dataDomainAdapter.unBindBidirectional("dataDomainValidatingObjects", objectValidationCheckBox.selectedProperty());
//    }


    @Override
    public void handleDataDomainChange(final DataDomainChangeEvent event)
    {
        if (event.getEventSource() != this)
        {
            LOGGER.debug("Handling DD Change in DDLayout");

            switch (event.getEventType())
            {
                case CACHE:
                    break;
                case NAME: // Name Changed.
                    nameTextField.setText(getMainWindow().getCayenneProject().getDataDomainName());
                    break;
                case VALIDATION: // Object Validation Changed
                    objectValidationCheckBox.setSelected((boolean) event.getNewValue());
                    break;
            }
//            dataDomainNameTextField.setText(value);
        }
    }

    private void configureRemoteNotifications(final boolean enabled)
    {
        if (enabled)
        {
            enable(remoteChangeNotificationsChoiceBox);
            String eventBridgefactory = dataDomainAdapter.getEventBridgeFactoryProperty();

            if (StringUtils.equals(eventBridgefactory, JavaGroupsBridgeFactory.class.getName()))
                remoteChangeNotificationsChoiceBox.getSelectionModel().select(1);
            else if (StringUtils.equals(eventBridgefactory, JMSBridgeFactory.class.getName()))
                remoteChangeNotificationsChoiceBox.getSelectionModel().select(2);
            else
                remoteChangeNotificationsChoiceBox.getSelectionModel().select(3);

//            remoteChangeNotificationsChoiceBox.getSelectionModel().select(0);
//            hide(javaGroupsConfiguration, jmsConfiguration, customConfiguration);

//            enable(remoteChangeNotificationsCheckBox);
//            enable(remoteChangeConfigurationButton);
        }
        else
        {
            disable(remoteChangeNotificationsChoiceBox);
            remoteChangeNotificationsChoiceBox.getSelectionModel().select(0);
//            hide(javaGroupsConfiguration, jmsConfiguration, customConfiguration);
//            disable(remoteChangeNotificationsCheckBox);
//            disable(remoteChangeConfigurationButton);
            dataDomainAdapter.setRemoteChangeNotifications(false);
        }
    }
}
