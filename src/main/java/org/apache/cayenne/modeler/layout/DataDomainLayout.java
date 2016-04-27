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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import jfxtras.labs.scene.control.BeanPathAdapter;

// org.apache.cayenne.modeler.controller.DataDomainViewController
public class DataDomainLayout extends AnchorPane implements DetailEditorSupport, MainWindowSupport
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

    @Override
    public void beginEditing()
    {
        System.out.println("begin editing");

        BeanPathAdapter<CayenneModel> dataDomainAdapter = getDataDomainPropertyAdapterMap(getMainWindow().getCayenneModel());

        dataDomainAdapter.bindBidirectional("dataDomainName", dataDomainNameTextField.textProperty());
        dataDomainAdapter.bindBidirectional("dataDomainValidatingObjects", objectValidationCheckBox.selectedProperty());
    }

    @Override
    public void endEditing()
    {
        BeanPathAdapter<CayenneModel> dataDomainAdapter = getDataDomainPropertyAdapterMap(getMainWindow().getCayenneModel());

        dataDomainAdapter.unBindBidirectional("dataDomainName", dataDomainNameTextField.textProperty());
        dataDomainAdapter.unBindBidirectional("dataDomainValidatingObjects", objectValidationCheckBox.selectedProperty());
    }

    private BeanPathAdapter<CayenneModel> getDataDomainPropertyAdapterMap(CayenneModel cayenneModel)
    {
        BeanPathAdapter<CayenneModel> dataDomainAdapter = dataDomainPropertyAdapterMap.get(cayenneModel);

        if (dataDomainAdapter == null)
        {
            dataDomainAdapter = new BeanPathAdapter<CayenneModel>(getMainWindow().getCayenneModel());

            dataDomainAdapter.fieldPathValueProperty().addListener((observable, oldValue, newValue) ->
                System.out.println("Observable: " + observable + ", oldValue: " + oldValue + ", newValue: " + newValue));

            dataDomainPropertyAdapterMap.put(cayenneModel, dataDomainAdapter);
        }

        return dataDomainAdapter;
    }
}
