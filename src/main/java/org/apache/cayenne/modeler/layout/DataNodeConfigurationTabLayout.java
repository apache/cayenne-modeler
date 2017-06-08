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

import org.apache.cayenne.access.dbsync.CreateIfNoSchemaStrategy;
import org.apache.cayenne.access.dbsync.SkipSchemaUpdateStrategy;
import org.apache.cayenne.access.dbsync.ThrowOnPartialOrCreateSchemaStrategy;
import org.apache.cayenne.access.dbsync.ThrowOnPartialSchemaStrategy;
import org.apache.cayenne.configuration.server.JNDIDataSourceFactory;
import org.apache.cayenne.configuration.server.XMLPoolingDataSourceFactory;
import org.apache.cayenne.modeler.adapters.DataNodeAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
    private ComboBox<String> dataSourceFactoryComboBox, schemaUpdateStrategyComboBox;

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

    private List<Binding<?>> bindings;

    private static final String[] standardSchemaUpdateStrategies =
        {
            SkipSchemaUpdateStrategy.class.getName(),
            CreateIfNoSchemaStrategy.class.getName(),
            ThrowOnPartialSchemaStrategy.class.getName(),
            ThrowOnPartialOrCreateSchemaStrategy.class.getName()
        };

    private static final String DBCP_DATA_SOURCE_FACTORY = "org.apache.cayenne.configuration.server.DBCPDataSourceFactory";

    private static final String[] standardDataSourceFactories =
        {
            XMLPoolingDataSourceFactory.class.getName(),
            JNDIDataSourceFactory.class.getName(),
            DBCP_DATA_SOURCE_FACTORY
        };

    public DataNodeConfigurationTabLayout(final DataNodeLayout parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/DataNodeConfigurationTabLayout.fxml");
    }

    @Override
    public void initializeLayout()
    {
        super.initializeLayout();

        configureDevelopmentDataSourceButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.COGS, "16px"));
        configureDevelopmentDataSourceButton.setText("");

        schemaUpdateStrategyComboBox.getItems().addAll(standardSchemaUpdateStrategies);
        schemaUpdateStrategyComboBox.getSelectionModel().select(0);

        dataSourceFactoryComboBox.getItems().addAll(standardDataSourceFactories);

        dataSourceFactoryComboBox.valueProperty().addListener((obs, oldValue, newValue) ->
            {
                final boolean jdbc = newValue.equals(XMLPoolingDataSourceFactory.class.getName());
                final boolean jndi = newValue.equals(JNDIDataSourceFactory.class.getName());
                final boolean dbcp = newValue.equals(DBCP_DATA_SOURCE_FACTORY);

                jdbcConfigurationGrid.setVisible(jdbc);
                jdbcConfigurationGrid.setManaged(jdbc);

                jndiConfigurationGrid.setVisible(jndi);
                jndiConfigurationGrid.setManaged(jndi);

                dbcpConfigurationGrid.setVisible(dbcp);
                dbcpConfigurationGrid.setManaged(dbcp);

                final DataNodeLayout dataNodeLayout = (DataNodeLayout) getParentLayout();

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

    @Override
    public void initializeBindings()
    {
        bindings = new ArrayList<>();

        bindings.add(new Binding<>(nameTextField.textProperty(), dataNodeAdapter.nameProperty()));
    }

    @Override
    public List<Binding<?>> getBindings()
    {
        return bindings;
    }

//    @Override
//    public void beginEditing()
//    {
//        nameTextField.textProperty().bindBidirectional(dataNodeAdapter.nameProperty());
//    }
//
//    @Override
//    public void endEditing()
//    {
//        nameTextField.textProperty().unbindBidirectional(dataNodeAdapter.nameProperty());
//    }
}
