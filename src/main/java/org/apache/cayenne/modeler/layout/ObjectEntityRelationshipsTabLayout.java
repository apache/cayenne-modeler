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

import org.apache.cayenne.modeler.adapters.ObjectAttributeAdapter;
import org.apache.cayenne.modeler.adapters.ObjectEntityAdapter;
import org.apache.cayenne.modeler.utility.ObjectEntityUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ObjectEntityRelationshipsTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<ObjectEntityAdapter>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectEntityRelationshipsTabLayout.class);

    @FXML
    private Button newRelationshipButton;

    @FXML
    private Button synchronizeWithDatabaseEntityButton, viewRelatedDatabaseEntityButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cutButton, copyButton, pasteButton;

    @FXML
    private TextField javaAttributeNameTextField;

    @FXML
    private ComboBox<String> javaTypeComboBox;

    @FXML
    private CheckBox optimisticLockingCheckBox;

    @FXML
    private TableView<ObjectAttributeAdapter> relationshipsTableView;

    @FXML
    private TableColumn<ObjectAttributeAdapter,String> relationshipNameColumn;

    @FXML
    private TableColumn<ObjectAttributeAdapter,String> relationshipTargetColumn;
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

    public ObjectEntityRelationshipsTabLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/ObjectEntityRelationshipsTabLayout.fxml");
    }

//    private static ObservableList javaTypes = FXCollections.observableArrayList(ObjectEntityUtilities.getRegisteredTypeNames());

//    @FXML
//    private Button newAttributeButton;
//
//    @FXML
//    private Button synchronizeWithDatabaseEntityButton, viewRelatedDatabaseEntityButton;
//
//    @FXML
//    private Button deleteButton;
//
//    @FXML
//    private Button cutButton, copyButton, pasteButton;

    @Override
    public void initializeLayout()
    {
        super.initializeLayout();

        newRelationshipButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS, "16px"));
        newRelationshipButton.setText(null);

        synchronizeWithDatabaseEntityButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REFRESH, "16px"));
        synchronizeWithDatabaseEntityButton.setText(null);

        viewRelatedDatabaseEntityButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE, "16px"));
        viewRelatedDatabaseEntityButton.setText(null);

        deleteButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TRASH, "16px"));
        deleteButton.setText(null);

        cutButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.CUT, "16px"));
        cutButton.setText(null);

        copyButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.COPY, "16px"));
        copyButton.setText(null);

        pasteButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PASTE, "16px"));
        pasteButton.setText(null);

        attributeUsedForLockingColumn.setText(null);
        attributeIsInheritedColumn.setText(null);

        attributeUsedForLockingColumn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, "16px"));
        attributeIsInheritedColumn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LEVEL_UP, "16px"));

        relationshipNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        relationshipTargetColumn.setCellValueFactory(cellData -> cellData.getValue().javaTypeProperty());
        attributeDatabasePathColumn.setCellValueFactory(cellData -> cellData.getValue().databaseAttributePathProperty());
        // FIXME: See if there is a way of doing this without using the string "databaseType"...
        attributeDatabaseTypeColumn.setCellValueFactory(new PropertyValueFactory<ObjectAttributeAdapter,String>("databaseType"));

        attributeUsedForLockingColumn.setCellValueFactory(cellData -> cellData.getValue().usedForLockingProperty());
        attributeUsedForLockingColumn.setCellFactory((column) ->
            {
                return new TableCell<ObjectAttributeAdapter,Boolean>()
                    {
                        @Override
                        protected void updateItem(final Boolean item, final boolean empty)
                        {
                            super.updateItem(item, empty);

                            setAlignment(Pos.CENTER);
                            setStyle("-fx-padding: 0;");
                            setText("");

                            if (item == null || empty || item == false)
                                setGraphic(null);
                            else
                                setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, "16px"));
                        }
                     };
            });
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

    private List<Binding<?>> getSelectedAttributeBindings(ObjectAttributeAdapter adapter)
    {
        List<Binding<?>> bindings = new ArrayList<>();

        bindings.add(new Binding<>(javaAttributeNameTextField.textProperty(), adapter.nameProperty()));
        bindings.add(new Binding<>(javaTypeComboBox.valueProperty(), adapter.javaTypeProperty()));
        bindings.add(new Binding<>(optimisticLockingCheckBox.selectedProperty(), adapter.usedForLockingProperty()));

        return bindings;
    }

    private final ChangeListener<ObjectAttributeAdapter> attributesTableViewSelectionListener = (observable, oldSelection, newSelection) ->
        {
            final String[] javaTypes = ObjectEntityUtilities.getRegisteredTypeNames();

            if (oldSelection != null)
            {
                unbind(getSelectedAttributeBindings(oldSelection));

//                javaAttributeNameTextField.textProperty().unbindBidirectional(oldSelection.nameProperty());
//                javaTypeComboBox.valueProperty().unbindBidirectional(oldSelection.javaTypeProperty());
//                optimisticLockingCheckBox.selectedProperty().unbindBidirectional(oldSelection.usedForLockingProperty());
            }

            javaTypeComboBox.getItems().clear();

            if (newSelection != null)
            {
                javaTypeComboBox.getItems().addAll(javaTypes);

                bind(getSelectedAttributeBindings(newSelection));

//                javaAttributeNameTextField.textProperty().bindBidirectional(newSelection.nameProperty());
//                javaTypeComboBox.valueProperty().bindBidirectional(newSelection.javaTypeProperty());
//                optimisticLockingCheckBox.selectedProperty().bindBidirectional(newSelection.usedForLockingProperty());
                databaseTypeLabel.setText(newSelection.getDatabaseType());
            }

            javaAttributeNameTextField.setDisable(newSelection == null);
            javaTypeComboBox.setDisable(newSelection == null);
            optimisticLockingCheckBox.setDisable(newSelection == null);
        };

    @Override
    public void beginEditing()
    {
        DetailEditorSupport.super.beginEditing();

        disable(javaAttributeNameTextField);
        javaAttributeNameTextField.setText(null);

        disable(javaTypeComboBox);
        javaTypeComboBox.getItems().clear();
        javaTypeComboBox.setValue(null);

        disable(optimisticLockingCheckBox);
        optimisticLockingCheckBox.setSelected(false);

        databaseTypeLabel.setText("N/A");

        relationshipsTableView.setItems(objectEntityAdapter.getAttributes());
        relationshipsTableView.getSelectionModel().selectedItemProperty().addListener(attributesTableViewSelectionListener);

        // Automatically select the first item, if available.
        if (relationshipsTableView.getItems().size() > 0)
            relationshipsTableView.getSelectionModel().select(0);
    }

    @Override
    public void endEditing()
    {
        DetailEditorSupport.super.endEditing();

        final ObjectAttributeAdapter currentObjectAttributeAdapter = relationshipsTableView.getSelectionModel().getSelectedItem();

        if (currentObjectAttributeAdapter != null)
        {
            unbind(getSelectedAttributeBindings(currentObjectAttributeAdapter));

//            javaAttributeNameTextField.textProperty().unbindBidirectional(currentObjectAttributeAdapter.nameProperty());
//            javaTypeComboBox.valueProperty().unbindBidirectional(currentObjectAttributeAdapter.javaTypeProperty());
//            optimisticLockingCheckBox.selectedProperty().unbindBidirectional(currentObjectAttributeAdapter.usedForLockingProperty());
        }

        relationshipsTableView.getSelectionModel().selectedItemProperty().removeListener(attributesTableViewSelectionListener);
    }
}
