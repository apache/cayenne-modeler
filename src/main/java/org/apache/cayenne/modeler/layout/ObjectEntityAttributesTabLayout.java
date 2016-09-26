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

import org.apache.cayenne.modeler.adapters.ObjectAttributeAdapter;
import org.apache.cayenne.modeler.adapters.ObjectEntityAdapter;
import org.apache.cayenne.modeler.utility.ObjectEntityUtilities;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ObjectEntityAttributesTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<ObjectEntityAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(ObjectEntityAttributesTabLayout.class);

    @FXML
    private TextField javaAttributeNameTextField;
    @FXML
    private ComboBox<String> javaTypeComboBox;

    @FXML
    private TableView<ObjectAttributeAdapter> attributesTableView;

    @FXML
    private TableColumn<ObjectAttributeAdapter,String> attributeNameColumn;

    @FXML
    private TableColumn<ObjectAttributeAdapter,String> attributeTypeColumn;
//    private TableColumn<ObjAttribute,ComboBox<String>> attributeTypeColumn;

    @FXML
    private TableColumn<ObjectAttributeAdapter,String> attributeDatabasePathColumn;

    @FXML
    private TableColumn<ObjectAttributeAdapter,String> attributeDatabaseTypeColumn;

    @FXML
    private TableColumn<ObjectAttributeAdapter,Boolean> attributeUsedForLockingColumn;

    @FXML
    private TableColumn<ObjectAttributeAdapter,Boolean> attributeIsInheritedColumn;

    @FXML
    private Label databaseTypeLabel;

//    private MainWindowSupport parent;

    private ObjectEntityClassTabLayout objectEntityClassTabViewController;

    private ObjectEntityAdapter objectEntityAdapter;

    public ObjectEntityAttributesTabLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/ObjectEntityAttributesTabLayout.fxml");
    }

//    private static ObservableList javaTypes = FXCollections.observableArrayList(ObjectEntityUtilities.getRegisteredTypeNames());

    @Override
    public void initialize()
    {
        super.initialize();

        attributeUsedForLockingColumn.setText(null);
        attributeIsInheritedColumn.setText(null);

        attributeUsedForLockingColumn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, "16px"));
        attributeIsInheritedColumn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LEVEL_UP, "16px"));

        attributeNameColumn.setCellValueFactory(new PropertyValueFactory<ObjectAttributeAdapter,String>("name"));
        attributeTypeColumn.setCellValueFactory(new PropertyValueFactory<ObjectAttributeAdapter,String>("javaType"));
        attributeDatabasePathColumn.setCellValueFactory(new PropertyValueFactory<ObjectAttributeAdapter,String>("databaseAttributePath"));
        attributeDatabaseTypeColumn.setCellValueFactory(new PropertyValueFactory<ObjectAttributeAdapter,String>("databaseType"));
        attributeUsedForLockingColumn.setCellValueFactory(new PropertyValueFactory<ObjectAttributeAdapter,Boolean>("usedForLocking"));

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

//    private void loadComponents()
//    {
//        try
//        {
//            objectEntityClassTabViewController = new ObjectEntityClassTabLayout(this);
//
//            loadTab(objectEntityClassTabViewController, classTabAnchorPane);
//        }
//        catch (Exception exception)
//        {
//            // TODO Auto-generated catch block
//            exception.printStackTrace();
//        }
//    }

//    public void display(final ObjEntity objEntity)
//    {
//        LOGGER.debug("trying to display: " + objEntity);
////        attributesTableView.setItems(FXCollections.observableArrayList(objEntity.getAttributes()));
//
////        objectEntityClassTabViewController.display(objEntity);
////        objEntity.getAttributes()
//    }

    public void tabChanged(final Event event)
    {
        LOGGER.debug("event: " + event);
        getMainWindow().getCayenneProject().getDataMaps();
    }

    @Override
    public void setPropertyAdapter(final ObjectEntityAdapter objectEntityAdapter)
    {
        this.objectEntityAdapter = objectEntityAdapter;
    }

    private ObjectAttributeAdapter currentObjectAttributeAdapter;

    @Override
    public void beginEditing()
    {
//        nameTextField.textProperty().bindBidirectional(objectEntityAdapter.getNameProperty());

        javaAttributeNameTextField.setDisable(true);
        javaAttributeNameTextField.setText(null);
        javaTypeComboBox.setDisable(true);
        javaTypeComboBox.getItems().clear();
        javaTypeComboBox.setValue(null);
        databaseTypeLabel.setText("N/A");
        attributesTableView.setItems(objectEntityAdapter.getAttributes());

        attributesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
            {
                final String[] javaTypes = ObjectEntityUtilities.getRegisteredTypeNames();

                currentObjectAttributeAdapter = newSelection;

                if (oldSelection != null)
                {
                    javaAttributeNameTextField.textProperty().unbindBidirectional(oldSelection.nameProperty());
                    javaTypeComboBox.valueProperty().unbindBidirectional(oldSelection.javaTypeProperty());

//                    javaTypeComboBox.textProperty().unbindBidirectional(oldSelection.javaTypeProperty());
                }

                javaTypeComboBox.getItems().clear();

                if (newSelection != null)
                {
                    javaTypeComboBox.getItems().addAll(javaTypes);

                    javaAttributeNameTextField.textProperty().bindBidirectional(newSelection.nameProperty());
                    javaTypeComboBox.valueProperty().bindBidirectional(newSelection.javaTypeProperty());
//                    javaTypeComboBox.textProperty().bindBidirectional(oldSelection.javaTypeProperty());
                    databaseTypeLabel.setText(newSelection.getDatabaseType());
                }

                javaAttributeNameTextField.setDisable(newSelection == null);
                javaTypeComboBox.setDisable(newSelection == null);
            });
    }

    @Override
    public void endEditing()
    {
//        nameTextField.textProperty().unbindBidirectional(objectEntityAdapter.getNameProperty());

        if (currentObjectAttributeAdapter != null)
        {
            javaAttributeNameTextField.textProperty().unbindBidirectional(currentObjectAttributeAdapter.nameProperty());
            javaTypeComboBox.valueProperty().unbindBidirectional(currentObjectAttributeAdapter.javaTypeProperty());

            currentObjectAttributeAdapter = null;
        }

//        attributesTableView.setItems(null);
    }
}
