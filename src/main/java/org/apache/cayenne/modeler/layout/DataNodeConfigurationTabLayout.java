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

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DataNodeConfigurationTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DataNodeAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(DataNodeConfigurationTabLayout.class);

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> dataSourceFactoryComboBox;

    @FXML
    private Spinner<Integer> minimumConnectionsSpinner, maximumConnectionsSpinner;

    @FXML
    private GridPane jdbcConfigurationGrid, jndiConfigurationGrid, dbcpConfigurationGrid;

    @FXML
    private Button configureDevelopmentDataSourceButton;

//    @FXML
//    private TableView<ObjAttribute> attributesTableView;
//
//    @FXML
//    private TableColumn<ObjAttribute,String> attributeNameColumn;

//    @FXML
//    private TableColumn<ObjAttribute,String> attributeTypeColumn;
//    private TableColumn<ObjAttribute,ComboBox<String>> attributeTypeColumn;

//    @FXML
//    private TableColumn<DbAttribute,Boolean> mandatoryColumn;
//
//    @FXML
//    private TableColumn<DbAttribute,Boolean> primaryKeyColumn;

//    private MainWindowSupport parent;

//    private ObjectEntityClassTabLayout objectEntityClassTabViewController;

    private DataNodeAdapter dataNodeAdapter;
//    private final DataNodeLayout parent;

    public DataNodeConfigurationTabLayout(final DataNodeLayout parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/DataNodeConfigurationTabLayout.fxml");
    }

    private final String dataSourceJdbcConfigurationSetting = "org.apache.cayenne.configuration.server.XMLPoolingDataSourceFactory";
    private final String dataSourceJndiConfigurationSetting = "org.apache.cayenne.configuration.server.JNDIDataSourceFactory";
    private final String dataSourceDbcpConfigurationSetting = "org.apache.cayenne.configuration.server.DBCPDataSourceFactory";

    @Override
    public void initialize()
    {
        super.initialize();

        configureDevelopmentDataSourceButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.COGS, "16px"));
        configureDevelopmentDataSourceButton.setText("");

//        dataSourceFactoryComboBox.getItems().removeAll(dataSourceFactoryComboBox.getItems());
        dataSourceFactoryComboBox.getItems().add(dataSourceJdbcConfigurationSetting);
        dataSourceFactoryComboBox.getItems().add(dataSourceJndiConfigurationSetting);
        dataSourceFactoryComboBox.getItems().add(dataSourceDbcpConfigurationSetting);

        dataSourceFactoryComboBox.valueProperty().addListener((obs, oldValue, newValue) ->
            {
                final boolean jdbc = newValue.equals(dataSourceJdbcConfigurationSetting);
                final boolean jndi = newValue.equals(dataSourceJndiConfigurationSetting);
                final boolean dbcp = newValue.equals(dataSourceDbcpConfigurationSetting);

                jdbcConfigurationGrid.setVisible(jdbc);
                jdbcConfigurationGrid.setManaged(jdbc);

                jndiConfigurationGrid.setVisible(jndi);
                jndiConfigurationGrid.setManaged(jndi);

                dbcpConfigurationGrid.setVisible(dbcp);
                dbcpConfigurationGrid.setManaged(dbcp);

                final DataNodeLayout dataNodeLayout = (DataNodeLayout) getParentComponent();

                if (jdbc)
                    dataNodeLayout.enablePasswordEncoderTab();
                else
                    dataNodeLayout.disablePasswordEncoderTab();
            });

        dataSourceFactoryComboBox.getSelectionModel().select(0);

        minimumConnectionsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1, 1));
        maximumConnectionsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1, 1));

        minimumConnectionsSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
            {
                if (newValue.intValue() > maximumConnectionsSpinner.getValue().intValue())
                    maximumConnectionsSpinner.getValueFactory().setValue(newValue);
            });

        maximumConnectionsSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
            {
                if (newValue.intValue() < minimumConnectionsSpinner.getValue().intValue())
                    minimumConnectionsSpinner.getValueFactory().setValue(newValue);
            });
    }

    public void tabChanged(final Event event)
    {
        LOGGER.debug("event: " + event);
        getMainWindow().getCayenneProject().getDataMaps();
    }

    @Override
    public void setPropertyAdapter(final DataNodeAdapter dataNodeAdapter)
    {
        this.dataNodeAdapter = dataNodeAdapter;
    }

    @Override
    public void beginEditing()
    {
        nameTextField.textProperty().bindBidirectional(dataNodeAdapter.getNameProperty());
    }

    @Override
    public void endEditing()
    {
        nameTextField.textProperty().unbindBidirectional(dataNodeAdapter.getNameProperty());
    }
}
