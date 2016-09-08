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

import org.apache.cayenne.map.DbEntity;
import org.apache.cayenne.modeler.CayenneModeler;
import org.apache.cayenne.modeler.adapters.DataMapAdapter;
import org.apache.cayenne.modeler.adapters.ObjectEntityAdapter;
import org.apache.cayenne.modeler.notification.NotificationCenter;
import org.apache.cayenne.modeler.notification.event.DataDomainChangeEvent;
import org.apache.cayenne.modeler.notification.event.DataDomainChangeEvent.Type;
import org.apache.cayenne.modeler.notification.listener.DataDomainListener;
import org.apache.cayenne.modeler.project.CayenneProject;
import org.apache.cayenne.modeler.project.DataDomainTreeItem;
import org.apache.cayenne.modeler.project.DataMapTreeItem;
import org.apache.cayenne.modeler.project.DatabaseEntityTreeViewModel;
import org.apache.cayenne.modeler.project.ObjectEntityTreeItem;
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

public class MainWindowLayout
    extends AbstractWindowLayout
    implements DataDomainListener,
               MainWindowSupport
{
    @FXML
    private TreeView<String> treeView;
//    private TreeView<CayenneTreeItem<String>> treeView;

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

//    private final TreeItem<CayenneTreeItem<String>> treeRoot = new CayenneTreeItem<>(); // = new TreeItem<>();
//    private final TreeItem<CayenneTreeItem<String>> treeRoot = new TreeItem<CayenneTreeItem<String>>(); // = new TreeItem<>();
    private final TreeItem<String> treeRoot = new TreeItem<>(); // = new TreeItem<>();

    private CayenneProject cayenneProject;

    private boolean dirty;

    public MainWindowLayout() throws IOException
    {
        super(new Stage(), "/layouts/MainWindowLayout.fxml");

        setMinimumWindowSize(900, 700);
    }

    public CayenneProject getCayenneProject()
    {
        return cayenneProject;
    }

    public void setTitle()
    {
        final String edited = isDirty() ? "[edited] " : "";
        String title = edited + "Cayenne Modeler";

        if (cayenneProject != null)
            title += " - " + cayenneProject.getPath();

        super.setTitle(title);
    }

//    private DataDomainAdapter dataDomainAdapter;

    private DetailEditorSupport<?> getDetailEditor(TreeItem<String> treeItem)
    {
        if (treeItem instanceof DataDomainTreeItem)
            return dataDomainDetail;
        else if (treeItem instanceof DataMapTreeItem)
            return dataMapDetail;
        else if (treeItem instanceof ObjectEntityTreeItem)
            return objectEntityDetail;

        return null;
    }

    public void displayCayenneProject(final CayenneProject cayenneProject)
    {
        this.cayenneProject    = cayenneProject;
//        this.dataDomainAdapter = new DataDomainAdapter(cayenneProject);

      treeRoot.setExpanded(true);
      treeView.setRoot(treeRoot);
      treeView.setShowRoot(false);

        // addDataDomain(CayenneModelManager.getModels().get(0));
        // System.out.println(CayenneModelManager.getModels().size());

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (oldValue != null)
            {
                DetailEditorSupport<?> detailEditor = getDetailEditor(oldValue);

                if (detailEditor != null)
                    detailEditor.endEditing();
            }

            if (newValue != null)
            {
                observable.getValue().getParent();
//                System.out.println("observable: " + observable.getValue() + ", new: " + newValue.getValue() + ", old: " + oldValue.getValue());
                System.out.println("observable: " + observable + ", new: " + newValue + ", old: " + oldValue);

                System.out.println(observable.getValue().getValue().getClass());
                System.out.println(newValue.getValue().getClass());

                if (newValue instanceof DataDomainTreeItem)
                    displayDataDomain((DataDomainTreeItem) newValue);
                else if (newValue instanceof DataMapTreeItem)
                    displayDataMap((DataMapTreeItem) newValue);
                else if (newValue instanceof ObjectEntityTreeItem)
                    displayObjectEntity((ObjectEntityTreeItem) newValue);
//                if (newValue.getValue() instanceof DataDomainTreeViewModel)
//                    displayDataDomain((DataDomainTreeViewModel) newValue.getValue());
//                else if (newValue.getValue() instanceof DataMapTreeViewModel)
//                    displayDataMap(((DataMapTreeViewModel) newValue.getValue()).getDataMap());
//                // else if (newValue.getValue() instanceof
//                // DataNodeTreeViewModel)
//                // displayDataNode();
//                else if (newValue.getValue() instanceof ObjectEntityTreeViewModel)
//                    displayObjectEntity((ObjectEntityTreeViewModel) newValue.getValue());
//                else if (newValue.getValue() instanceof DatabaseEntityTreeViewModel)
//                    displayDatabaseEntity((DatabaseEntityTreeViewModel) newValue.getValue());
            }
        });

        setTitle();
//        displayDataDomain(domain);

        // Register for notifications.
        NotificationCenter.addProjectListener(cayenneProject, this);

        addDataDomain();
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

    private void addDataDomain()
    {
        final DataDomainTreeItem dataDomainBranch = new DataDomainTreeItem(cayenneProject.getDataDomainAdapter(), treeRoot);


//        final TreeItem<Object> dataDomainBranch =
//            TreeViewUtilities.addNode(new TreeItem<>(new DataDomainTreeViewModel(dataDomainAdapter.getDataDomain().getName())),
//                                      treeRoot,
//                                      FontAwesomeIcon.DATABASE);

        for (final DataMapAdapter dataMapAdapter : cayenneProject.getDataDomainAdapter().getDataMapAdapters())
            addDataMap(dataMapAdapter, dataDomainBranch);

        treeView.getSelectionModel().select(dataDomainBranch);
    }

    private void addDataMap(final DataMapAdapter dataMapAdapter, final DataDomainTreeItem dataDomainBranch)
    {
        final DataMapTreeItem dataMapBranch = new DataMapTreeItem(dataMapAdapter, dataDomainBranch);

//        final TreeItem<Object> dataMapBranch =
//            TreeViewUtilities.addNode(new TreeItem<>(new DataMapTreeViewModel(dataMap)),
//                                      dataDomainBranch,
//                                      FontAwesomeIcon.CUBES);

        for (ObjectEntityAdapter objectEntityAdapter : dataMapAdapter.getObjectEntityAdapters())
            addObjEntity(objectEntityAdapter, dataMapBranch);
//        for (final ObjEntity objEntity : dataMap.getObjEntities())
//            addObjEntity(objEntity, dataMapBranch);
//
//        for (final DbEntity dbEntity : dataMap.getDbEntities())
//            addDbEntity(dbEntity, dataMapBranch);
    }

//    private void addObjEntity(final ObjEntity objEntity, final TreeItem<Object> dataMapBranch)
    private void addObjEntity(final ObjectEntityAdapter objectEntityAdapter, final DataMapTreeItem dataMapBranch)
    {
        ObjectEntityTreeItem objectEntityLeaf = new ObjectEntityTreeItem(objectEntityAdapter, dataMapBranch);
//        final TreeItem<Object> objEntityLeaf =
//            TreeViewUtilities.addNode(new TreeItem<>(new ObjectEntityTreeViewModel(objEntity)),
//                                      dataMapBranch,
//                                      FontAwesomeIcon.FILE_TEXT);
//        TreeItem<String> objEntityLeaf = TreeViewUtilities.addNode(objEntity.getName(), dataMapBranch, FontAwesomeIcon.FILE_TEXT);
    }

    private void addDbEntity(final DbEntity dbEntity, final TreeItem<Object> dataMapBranch)
    {
        final TreeItem<Object> dbEntityLeaf =
            TreeViewUtilities.addNode(new TreeItem<>(new DatabaseEntityTreeViewModel(dbEntity)),
                                      dataMapBranch,
                                      FontAwesomeIcon.TABLE);
//        TreeItem<String> dbEntityLeaf = TreeViewUtilities.addNode(dbEntity.getName(), dataMapBranch, FontAwesomeIcon.TABLE);
    }


//    private void displayDataDomain(final DataDomainTreeViewModel domain)
    private void displayDataDomain(final DataDomainTreeItem dataDomainTreeItem)
    {
        System.out.println("data domain!!!  " + dataDomainTreeItem);
        displayDetailView(dataDomainDetail);

        dataDomainDetail.setPropertyAdapter(dataDomainTreeItem.getPropertyAdapter());
        dataDomainDetail.beginEditing();
    }

    private void displayDataMap(final DataMapTreeItem dataMapTreeItem)
    {
        System.out.println("data map!!!");
        displayDetailView(dataMapDetail);

        dataMapDetail.setPropertyAdapter(dataMapTreeItem.getPropertyAdapter());
        dataMapDetail.beginEditing();
    }

    private void displayDataNode()
    {
        System.out.println("data node!!!");
    }

    private void displayObjectEntity(final ObjectEntityTreeItem objectEntityTreeItem)
    {
        System.out.println("object entity!!!");
        displayDetailView(objectEntityDetail);
        objectEntityDetail.setPropertyAdapter(objectEntityTreeItem.getPropertyAdapter());
        objectEntityDetail.beginEditing();
//        displayDetailView(objectEntityDetail);
//        objectEntityDetail.display(objectEntity.getValue());
    }

    private void displayDatabaseEntity(final DatabaseEntityTreeViewModel databaseEntity)
    {
        System.out.println("database entity!!!");
        displayDetailView(databaseEntityDetail);
        databaseEntityDetail.display(databaseEntity.getValue());
    }

    public void onNewButtonClicked()
    {
        System.out.println("new!");
    }

    private void displayDetailView(final Node detailView)
    {
        // TODO: Call endEditing() on children here.
        detailAnchorPane.getChildren().removeAll(detailAnchorPane.getChildren());

        // Make the detail view fill the pane.
        AnchorPane.setTopAnchor(detailView, 0.0);
        AnchorPane.setLeftAnchor(detailView, 0.0);
        AnchorPane.setRightAnchor(detailView, 0.0);
        AnchorPane.setBottomAnchor(detailView, 0.0);

        detailAnchorPane.getChildren().add(detailView);

//        if (detailView instanceof DetailEditorSupport)
//            ((DetailEditorSupport) detailView).beginEditing();
    }

//    private void displayDetailView(BaseView detailView)
//    {
//        detailAnchorPane.getChildren().removeAll(detailAnchorPane.getChildren());
//        detailAnchorPane.getChildren().add(detailView.getScene().getRoot());
//    }


//    private Node objectEntityDetail; // TabPane
    private ObjectEntityLayout objectEntityDetail; // TabPane
    private DatabaseEntityLayout databaseEntityDetail; // TabPane
    private DataDomainLayout dataDomainDetail;
    private DataMapLayout dataMapDetail;

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
            databaseEntityDetail = new DatabaseEntityLayout(this);
//            objectEntityDetail = BaseView.loadFXML(getClass().getResource("/view/ObjectEntityView.fxml"), getStage());
//            objectEntityDetail = FXMLLoader.load(MainWindow.class.getResource("/view/ObjectEntityView.fxml"));

            // rootLayout.setCenter(personOverview);
        }
        catch (final Exception exception)
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

    public void setDirty(final boolean dirty)
    {
        this.dirty = dirty;
        setTitle();
    }

    @Override
    public void handleDataDomainChange(final DataDomainChangeEvent event)
    {
        System.out.println("Handling DataDomain Chain Event (Main Window)");
        setDirty(true);

        if (event.getEventType() == Type.NAME)
        {
//            for (final TreeItem<Object> dataDomain : treeRoot.getChildren())
//            {
//                final DataDomainTreeViewModel dataDomainTreeViewModel = (DataDomainTreeViewModel) dataDomain.getValue();
//
//                if (dataDomainTreeViewModel.getDataDomain().equals(event.getOldValue()))
//                {
//                    dataDomainTreeViewModel.setDataDomain((String) event.getNewValue());
//                    dataDomain.setValue(null); // This is a hack...let's us reset/redisplay it below.
//                    dataDomain.setValue(dataDomainTreeViewModel);
//                }
//            }
        }
    }

    @Override
    public MainWindowLayout getMainWindow()
    {
        return this;
    }
}
