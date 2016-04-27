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

import org.apache.cayenne.map.DataMap;
import org.apache.cayenne.map.DbEntity;
import org.apache.cayenne.map.ObjEntity;
import org.apache.cayenne.modeler.CayenneModeler;
import org.apache.cayenne.modeler.model.CayenneModel;
import org.apache.cayenne.modeler.model.CayenneTreeViewModel;
import org.apache.cayenne.modeler.model.DataDomainTreeViewModel;
import org.apache.cayenne.modeler.model.DataMapTreeViewModel;
import org.apache.cayenne.modeler.model.DatabaseEntityTreeViewModel;
import org.apache.cayenne.modeler.model.ObjectEntityTreeViewModel;
import org.apache.cayenne.modeler.utility.TreeViewUtilities;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainWindowLayout extends WindowLayout
{
    @FXML
    private TreeView<CayenneTreeViewModel> treeView;

    @FXML
    private AnchorPane detailAnchorPane;

    @FXML
    private Button newButton, openButton, saveButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button cutButton, copyButton, pasteButton;

    @FXML
    private Button undoButton, redoButton;

    @FXML
    private Button dataMapButton, dataNodeButton;

    private TreeItem<CayenneTreeViewModel> treeRoot = new TreeItem<>();

    private CayenneModel cayenneModel;

    private boolean dirty;

    public MainWindowLayout() throws IOException
    {
        super(new Stage(), "/layouts/MainWindowLayout.fxml");

        setMinimumWindowSize(900, 700);
    }

    public CayenneModel getCayenneModel()
    {
        return cayenneModel;
    }

    public void setTitle()
    {
        String edited = isDirty() ? "[edited] " : "";
        String title = edited + "Cayenne Modeler";

        if (cayenneModel != null)
            title += " - " + cayenneModel.getPath();

        super.setTitle(title);
    }

    public void displayCayenneModel(CayenneModel cayenneModel)
    {
        addDataDomain(this.cayenneModel = cayenneModel);
        // addDataDomain(CayenneModelManager.getModels().get(0));
        // System.out.println(CayenneModelManager.getModels().size());

        treeRoot.setExpanded(true);
        treeView.setRoot(treeRoot);
        treeView.setShowRoot(false);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                observable.getValue().getParent();
                System.out.println(observable.getValue() + " / " + newValue.getValue());

                System.out.println(observable.getValue().getValue().getClass());
                System.out.println(newValue.getValue().getClass());

                if (newValue.getValue() instanceof DataDomainTreeViewModel)
                    displayDataDomain((DataDomainTreeViewModel) newValue.getValue());
                else if (newValue.getValue() instanceof DataMapTreeViewModel)
                    displayDataMap();
                // else if (newValue.getValue() instanceof
                // DataNodeTreeViewModel)
                // displayDataNode();
                else if (newValue.getValue() instanceof ObjectEntityTreeViewModel)
                    displayObjectEntity((ObjectEntityTreeViewModel) newValue.getValue());
                else if (newValue.getValue() instanceof DatabaseEntityTreeViewModel)
                    displayDatabaseEntity((DatabaseEntityTreeViewModel) newValue.getValue());
            }
        });

        setTitle();
    }

    public void initialize()
    {
        configureMainToolbar();

        loadComponents();
    }

//    private boolean toolbarNeedsInitialization = true;

    private void configureMainToolbar()
    {
        newButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS_SQUARE, "16px"));
        openButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FOLDER_OPEN, "16px"));
        saveButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FLOPPY_ALT, "16px"));

        removeButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TRASH, "16px"));

        cutButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SCISSORS, "16px"));
        copyButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.CLONE, "16px"));
        pasteButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.CLIPBOARD, "16px"));

        undoButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.UNDO, "16px"));
        redoButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REPEAT, "16px"));

        dataMapButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.CUBES, "16px"));
        dataNodeButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SERVER, "16px"));

        newButton.setTooltip(new Tooltip("Create a new Cayenne Model project."));
        openButton.setTooltip(new Tooltip("Open an existing Cayenne Model project."));
        saveButton.setTooltip(new Tooltip("Save this Cayenne Model project."));

        removeButton.setTooltip(new Tooltip("Remove this item.")); // FIXME: Should be dynamic.

        cutButton.setTooltip(new Tooltip("Cut this item to the clipboard.")); // FIXME: Should be dynamic.
        copyButton.setTooltip(new Tooltip("Copy this item to the clipboard.")); // FIXME: Should be dynamic.
        pasteButton.setTooltip(new Tooltip("Paste this item from the clipboard.")); // FIXME: Should be dynamic.

        undoButton.setTooltip(new Tooltip("Undo.")); // FIXME: Should be dynamic.
        redoButton.setTooltip(new Tooltip("Redo.")); // FIXME: Should be dynamic.

        dataMapButton.setTooltip(new Tooltip("Create a new Data Map to hold Java and Database definitions."));
        dataNodeButton.setTooltip(new Tooltip("Create a new Data Node to hold database connection settings."));
    }

    private void addDataDomain(CayenneModel model)
    {
        TreeItem<CayenneTreeViewModel> dataDomainBranch =
            TreeViewUtilities.addNode(new TreeItem<>(new DataDomainTreeViewModel(model.getDataDomain().getName())),
                                      treeRoot,
                                      FontAwesomeIcon.DATABASE);

        for (DataMap dataMap : model.getDataMaps())
            addDataMap(dataMap, dataDomainBranch);
    }

    private void addDataMap(DataMap dataMap, TreeItem<CayenneTreeViewModel> dataDomainBranch)
    {
        TreeItem<CayenneTreeViewModel> dataMapBranch =
            TreeViewUtilities.addNode(new TreeItem<>(new DataMapTreeViewModel(dataMap)),
                                      dataDomainBranch,
                                      FontAwesomeIcon.CUBES);

        for (ObjEntity objEntity : dataMap.getObjEntities())
            addObjEntity(objEntity, dataMapBranch);

        for (DbEntity dbEntity : dataMap.getDbEntities())
            addDbEntity(dbEntity, dataMapBranch);
    }

    private void addObjEntity(ObjEntity objEntity, TreeItem<CayenneTreeViewModel> dataMapBranch)
    {
        TreeItem<CayenneTreeViewModel> objEntityLeaf =
            TreeViewUtilities.addNode(new TreeItem<>(new ObjectEntityTreeViewModel(objEntity)),
                                      dataMapBranch,
                                      FontAwesomeIcon.FILE_TEXT);
//        TreeItem<String> objEntityLeaf = TreeViewUtilities.addNode(objEntity.getName(), dataMapBranch, FontAwesomeIcon.FILE_TEXT);
    }

    private void addDbEntity(DbEntity dbEntity, TreeItem<CayenneTreeViewModel> dataMapBranch)
    {
        TreeItem<CayenneTreeViewModel> dbEntityLeaf =
            TreeViewUtilities.addNode(new TreeItem<>(new DatabaseEntityTreeViewModel(dbEntity)),
                                      dataMapBranch,
                                      FontAwesomeIcon.TABLE);
//        TreeItem<String> dbEntityLeaf = TreeViewUtilities.addNode(dbEntity.getName(), dataMapBranch, FontAwesomeIcon.TABLE);
    }


    private void displayDataDomain(DataDomainTreeViewModel domain)
    {
        System.out.println("data domain!!!  " + domain);
        displayDetailView(dataDomainDetail);
    }

    private void displayDataMap()
    {
        System.out.println("data map!!!");
        displayDetailView(dataMapDetail);
    }

    private void displayDataNode()
    {
        System.out.println("data node!!!");
    }

    private void displayObjectEntity(ObjectEntityTreeViewModel objectEntity)
    {
        System.out.println("object entity!!!");
        displayDetailView(objectEntityDetail);
        objectEntityDetail.display(objectEntity.getValue());
    }

    private void displayDatabaseEntity(DatabaseEntityTreeViewModel databaseEntity)
    {
        System.out.println("database entity!!!");
    }

    public void onNewButtonClicked()
    {
        System.out.println("new!");
    }

    private void displayDetailView(Node detailView)
    {
        // TODO: Call endEditing() on children here.
        detailAnchorPane.getChildren().removeAll(detailAnchorPane.getChildren());

        // Make the detail view fill the pane.
        AnchorPane.setTopAnchor(detailView, 0.0);
        AnchorPane.setLeftAnchor(detailView, 0.0);
        AnchorPane.setRightAnchor(detailView, 0.0);
        AnchorPane.setBottomAnchor(detailView, 0.0);

        detailAnchorPane.getChildren().add(detailView);

        if (detailView instanceof DetailEditorSupport)
            ((DetailEditorSupport) detailView).beginEditing();
    }

//    private void displayDetailView(BaseView detailView)
//    {
//        detailAnchorPane.getChildren().removeAll(detailAnchorPane.getChildren());
//        detailAnchorPane.getChildren().add(detailView.getScene().getRoot());
//    }


//    private Node objectEntityDetail; // TabPane
    private ObjectEntityLayout objectEntityDetail; // TabPane
    private Node dataDomainDetail;
    private Node dataMapDetail;

    private void loadComponents()
    {
        try
        {
//          loader = new FXMLLoader();
//          TabPane loader.load();
//          FXMLLoader loader = new FXMLLoader();
//          loader.setLocation(MainWindow.class.getResource("/view/ObjectEntityView.fxml"));
//          TabPane objectEntityTabs = (TabPane) loader.load();

//            dataDomainDetail = FXMLLoader.load(MainWindow.class.getResource("/view/DataDomainView.fxml"));
            dataDomainDetail = new DataDomainLayout(this);
            dataMapDetail = new DataMapLayout(this);
            objectEntityDetail = new ObjectEntityLayout(this);
//            objectEntityDetail = BaseView.loadFXML(getClass().getResource("/view/ObjectEntityView.fxml"), getStage());
//            objectEntityDetail = FXMLLoader.load(MainWindow.class.getResource("/view/ObjectEntityView.fxml"));

            // rootLayout.setCenter(personOverview);
        }
        catch (Exception exception)
        {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }
    }

    public void openPreferences() throws Exception
    {
        System.out.println("opening prefs");
        CayenneModeler.openPreferences();
    }

    public boolean isDirty()
    {
        return dirty;
    }

    public void setDirty(boolean dirty)
    {
        this.dirty = dirty;
    }
}
