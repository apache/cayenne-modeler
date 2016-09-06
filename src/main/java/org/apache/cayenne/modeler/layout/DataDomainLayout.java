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
import java.util.HashMap;
import java.util.Map;

import org.apache.cayenne.modeler.adapters.DataDomainAdapter;
import org.apache.cayenne.modeler.notification.NotificationCenter;
import org.apache.cayenne.modeler.notification.event.DataDomainChangeEvent;
import org.apache.cayenne.modeler.notification.listener.DataDomainListener;
import org.apache.cayenne.modeler.project.CayenneProject;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import jfxtras.labs.scene.control.BeanPathAdapter;
import jfxtras.labs.scene.control.BeanPathAdapter.FieldPathValue;

// org.apache.cayenne.modeler.controller.DataDomainViewController
public class DataDomainLayout
    extends AbstractViewLayout
    implements DataDomainListener,
               DetailEditorSupport
{
//    private MainWindowLayout mainWindow;

    private static Map<CayenneProject, BeanPathAdapter<CayenneProject>> dataDomainPropertyAdapterMap = new HashMap<CayenneProject, BeanPathAdapter<CayenneProject>>();

    @FXML
    private TextField dataDomainNameTextField;

    @FXML
    private CheckBox objectValidationCheckBox;

    public DataDomainLayout(final MainWindowSupport parent) throws IOException
    {
        super(parent.getMainWindow(), "/layouts/DataDomainLayout.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/DataDomainLayout.fxml"));
//
//        this.mainWindow = mainWindow;
//
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//        fxmlLoader.load();
    }

//    @Override
//    public void initialize()
//    {
//        System.out.println("foobar");
//    }

//    @Override
//    public MainWindowLayout getMainWindow()
//    {
//        return mainWindow;
//    }

    private final ChangeListener<FieldPathValue> changeObserver = (observable, oldValue, newValue) ->
        System.out.println("Observable: " + observable + ", oldValue: " + oldValue + ", newValue: " + newValue);

    public void beginEditing(final DataDomainAdapter dataDomainAdapter)
    {
        System.out.println("rocking the adapter");

        dataDomainNameTextField.textProperty().bindBidirectional(dataDomainAdapter.getDomainNameProperty());
        objectValidationCheckBox.selectedProperty().bindBidirectional(dataDomainAdapter.getValidatingObjectsProperty());
//        accessibleHelpProperty()setText(getMainWindow().getCayenneProject().getDataDomainName());
//        dataDomainNameTextField.textProperty().addListener((observable, oldValue, newValue) ->
    }

    @Override
    public void beginEditing()
    {
        System.out.println("begin editing");

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
        NotificationCenter.removeProjectListener(getMainWindow().getCayenneProject(), this);
//        BeanPathAdapter<CayenneModel> dataDomainAdapter = getDataDomainPropertyAdapterMap(getMainWindow().getCayenneModel());
//
//        dataDomainAdapter.fieldPathValueProperty().removeListener(changeObserver);
//
//        dataDomainAdapter.unBindBidirectional("dataDomainName", dataDomainNameTextField.textProperty());
//        dataDomainAdapter.unBindBidirectional("dataDomainValidatingObjects", objectValidationCheckBox.selectedProperty());
    }

    private BeanPathAdapter<CayenneProject> getDataDomainPropertyAdapterMap(final CayenneProject cayenneProject)
    {
        BeanPathAdapter<CayenneProject> dataDomainAdapter = dataDomainPropertyAdapterMap.get(cayenneProject);

        if (dataDomainAdapter == null)
        {
            dataDomainAdapter = new BeanPathAdapter<CayenneProject>(getMainWindow().getCayenneProject());

            dataDomainPropertyAdapterMap.put(cayenneProject, dataDomainAdapter);
        }

        return dataDomainAdapter;
    }

    @Override
    public void handleDataDomainChange(final DataDomainChangeEvent event)
    {
        if (event.getEventSource() != this)
        {
            System.out.println("Handling DD Change in DDLayout");

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
}
