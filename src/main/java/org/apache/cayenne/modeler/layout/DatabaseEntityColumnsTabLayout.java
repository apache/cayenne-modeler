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

import org.apache.cayenne.map.DbAttribute;
import org.apache.cayenne.map.DbEntity;
import org.apache.cayenne.modeler.adapters.DatabaseEntityAdapter;
import org.apache.cayenne.modeler.utility.ObjectEntityUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class DatabaseEntityColumnsTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DatabaseEntityAdapter>

{
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseEntityColumnsTabLayout.class);

//    @FXML
//    private TableView<ObjAttribute> attributesTableView;
//
//    @FXML
//    private TableColumn<ObjAttribute,String> attributeNameColumn;

//    @FXML
//    private TableColumn<ObjAttribute,String> attributeTypeColumn;
//    private TableColumn<ObjAttribute,ComboBox<String>> attributeTypeColumn;

    @FXML
    private TableColumn<DbAttribute,Boolean> mandatoryColumn;

    @FXML
    private TableColumn<DbAttribute,Boolean> primaryKeyColumn;

//    private MainWindowSupport parent;

//    private ObjectEntityClassTabLayout objectEntityClassTabViewController;

    private DatabaseEntityAdapter databaseEntityAdapter;

    public DatabaseEntityColumnsTabLayout(final MainWindowSupport parent) throws IOException
    {
        super(parent, "/layouts/DatabaseEntityColumnsTabLayout.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/DatabaseEntityColumnsTabLayout.fxml"));
//
//        this.parent = parent;
//
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//        fxmlLoader.load();
    }

    private static ObservableList javaTypes = FXCollections.observableArrayList(ObjectEntityUtilities.getRegisteredTypeNames());

    @Override
    public void initializeLayout()
    {
//        getScene().getWindow().getScene();
//        getStage().getScene().getWindow().get
//        System.out.println("mrg: " + getStage().getScene().getRoot());
//        System.out.println("init DatabaseEntityColumnsTabLayout");
//
//        loadComponents();

        super.initializeLayout();

        primaryKeyColumn.setText(null);
        mandatoryColumn.setText(null);

        primaryKeyColumn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.KEY, "16px"));
        mandatoryColumn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.EXCLAMATION, "16px"));

//        attributeNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
//        attributeTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
//        attributeTypeColumn.setCellValueFactory(new PropertyValueFactory<ObjAttribute, String>("type"));

//        Callback<TableColumn<ObjAttribute, String>, TableCell<ObjAttribute, String>> comboBoxCellFactory
//        = (TableColumn<ObjAttribute, String> param) -> new ComboBoxEditingCell();
//
//        ComboBoxTableCell attributeTypeCell = new ComboBoxTableCell(javaTypes);

//        attributeTypeColumn.setCellFactory(attributeTypeCell);
////        attributeTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(ObjectEntityUtilities.getRegisteredTypeNames()));
//        attributeTypeColumn.setEditable(true);
////        attributeTypeColumn.set
//        attributeTypeColumn.setOnEditCommit(
//                        new EventHandler<CellEditEvent<ObjAttribute, String>>() {
//                            @Override
//                            public void handle(CellEditEvent<ObjAttribute,String> t) {
//                                System.out.println(t);
////                                ((ObjAttribute) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLevel(t.getNewValue());
//                            }
//                        });

//        attributeTypeColumn.setPromptText("Java Type");
//        emailComboBox.setEditable(true);
//        emailComboBox.valueProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue ov, String t, String t1) {
//                address = t1;
//            }
//        });

    }

    @Override
    public void loadChildLayouts()
    {
        try
        {
//            objectEntityClassTabViewController = new ObjectEntityClassTabLayout(this);
//
//            loadTab(objectEntityClassTabViewController, classTabAnchorPane);
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
//            exception.printStackTrace();
        }
    }

//    @Override
//    private void loadTab(AnchorPane source, AnchorPane destination)
//    {
//        destination.getChildren().removeAll(destination.getChildren());
//
//        // Make the detail view fill the pane.
//        AnchorPane.setTopAnchor(source, 0.0);
//        AnchorPane.setLeftAnchor(source, 0.0);
//        AnchorPane.setRightAnchor(source, 0.0);
//        AnchorPane.setBottomAnchor(source, 0.0);
//
//        destination.getChildren().add(source);
//    }

    @Deprecated // Unused?
    public void display(final DbEntity dbEntity)
    {
        LOGGER.debug("trying to display: " + dbEntity);
//        attributesTableView.setItems(FXCollections.observableArrayList(objEntity.getAttributes()));
//        objectEntityClassTabViewController.display(objEntity);
//        objEntity.getAttributes()
    }

//    public void tabChanged(ActionEvent event)
//    public void tabChanged(final Event event)
//    {
//        LOGGER.debug("event: " + event);
//        getMainWindow().getCayenneProject().getDataMaps();
//    }

    @Override
    public void setPropertyAdapter(final DatabaseEntityAdapter databaseEntityAdapter)
    {
        this.databaseEntityAdapter = databaseEntityAdapter;
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
