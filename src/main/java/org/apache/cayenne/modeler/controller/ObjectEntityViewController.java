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

package org.apache.cayenne.modeler.controller;

import java.io.IOException;

import org.apache.cayenne.map.ObjAttribute;
import org.apache.cayenne.map.ObjEntity;
import org.apache.cayenne.modeler.utility.ObjectEntityUtilities;
import org.apache.cayenne.modeler.view.MainWindowViewController;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ObjectEntityViewController extends AnchorPane
{
    @FXML
    private Button dbEntitySyncButton;

    @FXML
    private TableView<ObjAttribute> attributesTableView;

    @FXML
    private TableColumn<ObjAttribute,String> attributeNameColumn;

    @FXML
    private TableColumn<ObjAttribute,String> attributeTypeColumn;
//    private TableColumn<ObjAttribute,ComboBox<String>> attributeTypeColumn;

    @FXML
    private TableColumn<ObjAttribute,Boolean> attributeUsedForLockingColumn;

    @FXML
    private TableColumn<ObjAttribute,Boolean> attributeIsInheritedColumn;

    private MainWindowViewController mainWindow;

    public ObjectEntityViewController(MainWindowViewController mainWindow) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ObjectEntityView.fxml"));

        this.mainWindow = mainWindow;

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    private static ObservableList javaTypes = FXCollections.observableArrayList(ObjectEntityUtilities.getRegisteredTypeNames());

    public void initialize()
    {
//        getScene().getWindow().getScene();
//        getStage().getScene().getWindow().get
//        System.out.println("mrg: " + getStage().getScene().getRoot());
        System.out.println("oev");

        attributeUsedForLockingColumn.setText(null);
        attributeIsInheritedColumn.setText(null);

        dbEntitySyncButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REFRESH, "16px"));
        attributeUsedForLockingColumn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, "16px"));
        attributeIsInheritedColumn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LEVEL_UP, "16px"));

        attributeNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
//        attributeTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
        attributeTypeColumn.setCellValueFactory(new PropertyValueFactory<ObjAttribute, String>("type"));

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

    public void display(ObjEntity objEntity)
    {
        System.out.println("trying to display: " + objEntity);
        attributesTableView.setItems(FXCollections.observableArrayList(objEntity.getAttributes()));
//        objEntity.getAttributes()
    }

//    public void tabChanged(ActionEvent event)
    public void tabChanged(Event event)
    {
        System.out.println(event);
        mainWindow.getCayenneModel().getDataMaps();
    }
}
