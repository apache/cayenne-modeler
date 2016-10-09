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

import org.apache.cayenne.access.DataRowStore;
import org.apache.cayenne.modeler.adapters.DataDomainAdapter;
import org.apache.cayenne.modeler.notification.NotificationCenter;
import org.apache.cayenne.modeler.notification.event.DataDomainChangeEvent;
import org.apache.cayenne.modeler.notification.listener.DataDomainListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

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
    private TextField dataDomainNameTextField;

    @FXML
    private CheckBox objectValidationCheckBox;

    @FXML
    private Spinner<Integer> objectCacheSizeSpinner;

    @FXML
    private CheckBox useSharedCacheCheckBox, remoteChangeNotificationsCheckBox;

    @FXML
    private Button remoteChangeConfigurationButton;

    private DataDomainAdapter dataDomainAdapter;

    public DataDomainLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/DataDomainLayout.fxml");
    }

//    private final ChangeListener<FieldPathValue> changeObserver = (observable, oldValue, newValue) ->
//        {
//            LOGGER.debug("Observable: " + observable + ", oldValue: " + oldValue + ", newValue: " + newValue);
//        };

    @Override
    public void initializeLayout()
    {
        super.initializeLayout();

        objectCacheSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, DataRowStore.SNAPSHOT_CACHE_SIZE_DEFAULT, 100));

        useSharedCacheCheckBox.selectedProperty().addListener((obs, oldValue, newValue) ->
            {
                configureRemoteNotifications(newValue);
            });
    }

    @Override
    public void setPropertyAdapter(final DataDomainAdapter dataDomainAdapter)
    {
        this.dataDomainAdapter = dataDomainAdapter;
    }

    @Override
    public void beginEditing()
    {
        LOGGER.debug("begin editing " + this);

        dataDomainNameTextField.textProperty().bindBidirectional(dataDomainAdapter.nameProperty());
        objectValidationCheckBox.selectedProperty().bindBidirectional(dataDomainAdapter.validatingObjectsProperty());

        objectCacheSizeSpinner.getValueFactory().valueProperty().bindBidirectional(dataDomainAdapter.sizeOfObjectCacheProperty().asObject());
        useSharedCacheCheckBox.selectedProperty().bindBidirectional(dataDomainAdapter.useSharedCacheProperty());
        remoteChangeNotificationsCheckBox.selectedProperty().bindBidirectional(dataDomainAdapter.remoteChangeNotificationsProperty());

        configureRemoteNotifications(dataDomainAdapter.getUseSharedCache());
    }

    @Deprecated
    private void beginEditingNotCalled()
    {
        LOGGER.debug("begin editing");

        dataDomainNameTextField.setText(getMainWindow().getCayenneProject().getDataDomainName());
        dataDomainNameTextField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                getMainWindow().getCayenneProject().setDataDomainName(newValue);
                final DataDomainChangeEvent ddce = new DataDomainChangeEvent(getMainWindow().getCayenneProject(), this, DataDomainChangeEvent.Type.NAME, oldValue, newValue);
                NotificationCenter.broadcastProjectEvent(getMainWindow().getCayenneProject(), ddce);
//            System.out.println("DataDomain Name Text Changed (newValue: " + newValue + ")");
            });

        objectValidationCheckBox.setSelected(getMainWindow().getCayenneProject().isDataDomainValidatingObjects());
        objectValidationCheckBox.setOnAction((event) ->
        {
            final Boolean selected = objectValidationCheckBox.isSelected();
            getMainWindow().getCayenneProject().setDataDomainValidatingObjects(selected);
            final DataDomainChangeEvent ddce = new DataDomainChangeEvent(getMainWindow().getCayenneProject(), this, DataDomainChangeEvent.Type.VALIDATION, !selected, selected);
            NotificationCenter.broadcastProjectEvent(getMainWindow().getCayenneProject(), ddce);
        });


//        BeanPathAdapter<CayenneModel> dataDomainAdapter = getDataDomainPropertyAdapterMap(getMainWindow().getCayenneModel());
//
//        dataDomainAdapter.bindBidirectional("dataDomainName", dataDomainNameTextField.textProperty());
//        dataDomainAdapter.bindBidirectional("dataDomainValidatingObjects", objectValidationCheckBox.selectedProperty());
//
////        new ChangeListener<FieldPathValue>() {
////            @Override
////            public void changed(
////                            ObservableValue<? extends FieldPathValue> observable,
////                            FieldPathValue oldValue,
////                            FieldPathValue newValue) {
////                    dumpPojo(oldValue, newValue, personPA);
////            }
////    });
//
//        dataDomainAdapter.fieldPathValueProperty().addListener(changeObserver);

        // Register for notifications.
        NotificationCenter.addProjectListener(getMainWindow().getCayenneProject(), this);
    }

    @Override
    public void endEditing()
    {
        LOGGER.debug("end editing " + this);

        dataDomainNameTextField.textProperty().unbindBidirectional(dataDomainAdapter.nameProperty());
        objectValidationCheckBox.selectedProperty().unbindBidirectional(dataDomainAdapter.validatingObjectsProperty());

        objectCacheSizeSpinner.getValueFactory().valueProperty().unbindBidirectional(dataDomainAdapter.sizeOfObjectCacheProperty().asObject());
        useSharedCacheCheckBox.selectedProperty().unbindBidirectional(dataDomainAdapter.useSharedCacheProperty());
        remoteChangeNotificationsCheckBox.selectedProperty().unbindBidirectional(dataDomainAdapter.remoteChangeNotificationsProperty());

//        NotificationCenter.removeProjectListener(getMainWindow().getCayenneProject(), this);
////        BeanPathAdapter<CayenneModel> dataDomainAdapter = getDataDomainPropertyAdapterMap(getMainWindow().getCayenneModel());
////
////        dataDomainAdapter.fieldPathValueProperty().removeListener(changeObserver);
////
////        dataDomainAdapter.unBindBidirectional("dataDomainName", dataDomainNameTextField.textProperty());
////        dataDomainAdapter.unBindBidirectional("dataDomainValidatingObjects", objectValidationCheckBox.selectedProperty());
    }

//    private BeanPathAdapter<CayenneProject> getDataDomainPropertyAdapterMap(final CayenneProject cayenneProject)
//    {
//        BeanPathAdapter<CayenneProject> dataDomainAdapter = dataDomainPropertyAdapterMap.get(cayenneProject);
//
//        if (dataDomainAdapter == null)
//        {
//            dataDomainAdapter = new BeanPathAdapter<CayenneProject>(getMainWindow().getCayenneProject());
//
//            dataDomainPropertyAdapterMap.put(cayenneProject, dataDomainAdapter);
//        }
//
//        return dataDomainAdapter;
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
                    dataDomainNameTextField.setText(getMainWindow().getCayenneProject().getDataDomainName());
                    break;
                case VALIDATION: // Object Validation Changed
                    objectValidationCheckBox.setSelected((boolean) event.getNewValue());
                    break;
            }
//            dataDomainNameTextField.setText(value);
        }
        // TODO Auto-generated method stub
    }

    private void configureRemoteNotifications(boolean enabled)
    {
        if (enabled)
        {
            enable(remoteChangeNotificationsCheckBox);
            enable(remoteChangeConfigurationButton);
        }
        else
        {
            disable(remoteChangeNotificationsCheckBox);
            disable(remoteChangeConfigurationButton);
            dataDomainAdapter.setRemoteChangeNotifications(false);
        }
    }
}
