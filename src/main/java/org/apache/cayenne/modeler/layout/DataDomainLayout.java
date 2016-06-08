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

import org.apache.cayenne.modeler.model.CayenneModel;
import org.apache.cayenne.modeler.notification.NotificationCenter;
import org.apache.cayenne.modeler.notification.event.DataDomainChangeEvent;
import org.apache.cayenne.modeler.notification.listener.DataDomainListener;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import jfxtras.labs.scene.control.BeanPathAdapter;
import jfxtras.labs.scene.control.BeanPathAdapter.FieldPathValue;

// org.apache.cayenne.modeler.controller.DataDomainViewController
public class DataDomainLayout
    extends AnchorPane
    implements DataDomainListener,
               DetailEditorSupport,
               MainWindowSupport
{
    private MainWindowLayout mainWindow;

    private static Map<CayenneModel, BeanPathAdapter<CayenneModel>> dataDomainPropertyAdapterMap = new HashMap<CayenneModel, BeanPathAdapter<CayenneModel>>();

    @FXML
    private TextField dataDomainNameTextField;

    @FXML
    private CheckBox objectValidationCheckBox;

    public DataDomainLayout(MainWindowLayout mainWindow) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/DataDomainLayout.fxml"));

        this.mainWindow = mainWindow;

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void initialize()
    {
        System.out.println("foobar");
    }

    @Override
    public MainWindowLayout getMainWindow()
    {
        return mainWindow;
    }

    private ChangeListener<FieldPathValue> changeObserver = (observable, oldValue, newValue) ->
        System.out.println("Observable: " + observable + ", oldValue: " + oldValue + ", newValue: " + newValue);

    @Override
    public void beginEditing()
    {
        System.out.println("begin editing");

        dataDomainNameTextField.setText(getMainWindow().getCayenneModel().getDataDomainName());
        dataDomainNameTextField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                getMainWindow().getCayenneModel().setDataDomainName(newValue);
                DataDomainChangeEvent ddce = new DataDomainChangeEvent(getMainWindow().getCayenneModel(), this, DataDomainChangeEvent.Type.NAME, oldValue, newValue);
                NotificationCenter.broadcastProjectEvent(getMainWindow().getCayenneModel(), ddce);
//            System.out.println("DataDomain Name Text Changed (newValue: " + newValue + ")");
            });

        objectValidationCheckBox.setSelected(getMainWindow().getCayenneModel().isDataDomainValidatingObjects());
        objectValidationCheckBox.setOnAction((event) ->
        {
            Boolean selected = objectValidationCheckBox.isSelected();
            getMainWindow().getCayenneModel().setDataDomainValidatingObjects(selected);
            DataDomainChangeEvent ddce = new DataDomainChangeEvent(getMainWindow().getCayenneModel(), this, DataDomainChangeEvent.Type.VALIDATION, !selected, selected);
            NotificationCenter.broadcastProjectEvent(getMainWindow().getCayenneModel(), ddce);
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
        NotificationCenter.addProjectListener(getMainWindow().getCayenneModel(), this);
    }

    @Override
    public void endEditing()
    {
        NotificationCenter.removeProjectListener(getMainWindow().getCayenneModel(), this);
//        BeanPathAdapter<CayenneModel> dataDomainAdapter = getDataDomainPropertyAdapterMap(getMainWindow().getCayenneModel());
//
//        dataDomainAdapter.fieldPathValueProperty().removeListener(changeObserver);
//
//        dataDomainAdapter.unBindBidirectional("dataDomainName", dataDomainNameTextField.textProperty());
//        dataDomainAdapter.unBindBidirectional("dataDomainValidatingObjects", objectValidationCheckBox.selectedProperty());
    }

    private BeanPathAdapter<CayenneModel> getDataDomainPropertyAdapterMap(CayenneModel cayenneModel)
    {
        BeanPathAdapter<CayenneModel> dataDomainAdapter = dataDomainPropertyAdapterMap.get(cayenneModel);

        if (dataDomainAdapter == null)
        {
            dataDomainAdapter = new BeanPathAdapter<CayenneModel>(getMainWindow().getCayenneModel());

            dataDomainPropertyAdapterMap.put(cayenneModel, dataDomainAdapter);
        }

        return dataDomainAdapter;
    }

    @Override
    public void handleDataDomainChange(DataDomainChangeEvent event)
    {
        if (event.getEventSource() != this)
        {
            System.out.println("Handling DD Change in DDLayout");

            switch (event.getEventType())
            {
                case CACHE:
                    break;
                case NAME: // Name Changed.
                    dataDomainNameTextField.setText(getMainWindow().getCayenneModel().getDataDomainName());
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
