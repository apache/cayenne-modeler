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

import org.apache.cayenne.modeler.adapters.DataNodeAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DataNodePasswordEncoderTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DataNodeAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(DataNodePasswordEncoderTabLayout.class);

    @FXML
    private ComboBox<String> passwordEncoderComboBox;

    @FXML
    private ChoiceBox<String> passwordLocationChoiceBox;

    @FXML
    private GridPane passwordSourceGridPane, passwordFilenameGridPane, passwordExecutableGridPane, passwordUrlGridPane;

    @FXML
    private TextField passwordSourceTextField, passwordFilenameTextField, passwordExecutableTextField, passwordUrlTextField;

    private DataNodeAdapter dataNodeAdapter;

    private final String passwordEncoderPlainTextSetting = "org.apache.cayenne.configuration.PlainTextPasswordEncoder";
    private final String passwordEncoderRot13Setting     = "org.apache.cayenne.configuration.Rot13PasswordEncoder";
    private final String passwordEncoderRot47Setting     = "org.apache.cayenne.configuration.Rot47PasswordEncoder";

    private final String passwordLocationModelSetting      = "Cayenne Model";
    private final String passwordLocationClasspathSetting  = "Java CLASSPATH Search (File System)";
    private final String passwordLocationExecutableSetting = "Executable Program";
    private final String passwordLocationUrlSetting        = "URL (file:, https:, etc.)";


    public DataNodePasswordEncoderTabLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/DataNodePasswordEncoderTabLayout.fxml");
    }

    @Override
    public void initializeLayout()
    {
        super.initializeLayout();

        passwordEncoderComboBox.getItems().add(passwordEncoderPlainTextSetting);
        passwordEncoderComboBox.getItems().add(passwordEncoderRot13Setting);
        passwordEncoderComboBox.getItems().add(passwordEncoderRot47Setting);

        passwordEncoderComboBox.getSelectionModel().select(0);

        passwordLocationChoiceBox.getItems().add(passwordLocationModelSetting);
        passwordLocationChoiceBox.getItems().add(passwordLocationClasspathSetting);
        passwordLocationChoiceBox.getItems().add(passwordLocationExecutableSetting);
        passwordLocationChoiceBox.getItems().add(passwordLocationUrlSetting);

        passwordLocationChoiceBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            final boolean model      = newValue.equals(passwordLocationModelSetting);
            final boolean classpath  = newValue.equals(passwordLocationClasspathSetting);
            final boolean executable = newValue.equals(passwordLocationExecutableSetting);
            final boolean url        = newValue.equals(passwordLocationUrlSetting);

            setVisibility(passwordSourceGridPane, model);
            setVisibility(passwordFilenameGridPane, classpath);
            setVisibility(passwordExecutableGridPane, executable);
            setVisibility(passwordUrlGridPane, url);
        });

        passwordLocationChoiceBox.getSelectionModel().select(0);
    }

//    public void tabChanged(final Event event)
//    {
//        LOGGER.debug("event: " + event);
//        getMainWindow().getCayenneProject().getDataMaps();
//    }

    @Override
    public void setPropertyAdapter(final DataNodeAdapter dataNodeAdapter)
    {
        this.dataNodeAdapter = dataNodeAdapter;
    }

//    @Override
//    public void beginEditing()
//    {
//    }
//
//    @Override
//    public void endEditing()
//    {
//    }
}
