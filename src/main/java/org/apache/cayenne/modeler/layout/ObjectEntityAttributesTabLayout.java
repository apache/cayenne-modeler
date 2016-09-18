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

import org.apache.cayenne.map.ObjAttribute;
import org.apache.cayenne.map.ObjEntity;
import org.apache.cayenne.modeler.adapters.ObjectEntityAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ObjectEntityAttributesTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<ObjectEntityAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(ObjectEntityAttributesTabLayout.class);

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

    public void display(final ObjEntity objEntity)
    {
        LOGGER.debug("trying to display: " + objEntity);
        attributesTableView.setItems(FXCollections.observableArrayList(objEntity.getAttributes()));
//        objectEntityClassTabViewController.display(objEntity);
//        objEntity.getAttributes()
    }

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

    @Override
    public void beginEditing()
    {
    }

    @Override
    public void endEditing()
    {
    }
}
