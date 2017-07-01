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
import java.util.Optional;

import org.apache.cayenne.modeler.CayenneModeler;
import org.apache.cayenne.modeler.adapters.CayennePropertyAdapter;
import org.apache.cayenne.modeler.adapters.DataMapAdapter;
import org.apache.cayenne.modeler.adapters.DataNodeAdapter;
import org.apache.cayenne.modeler.adapters.DatabaseEntityAdapter;
import org.apache.cayenne.modeler.adapters.ObjectEntityAdapter;
import org.apache.cayenne.modeler.notification.NotificationCenter;
import org.apache.cayenne.modeler.notification.event.DataDomainChangeEvent;
import org.apache.cayenne.modeler.notification.event.DataDomainChangeEvent.Type;
import org.apache.cayenne.modeler.notification.listener.DataDomainListener;
import org.apache.cayenne.modeler.project.CayenneProject;
import org.apache.cayenne.modeler.project.DataDomainTreeItem;
import org.apache.cayenne.modeler.project.DataMapTreeItem;
import org.apache.cayenne.modeler.project.DataNodeTreeItem;
import org.apache.cayenne.modeler.project.DatabaseEntityTreeItem;
import org.apache.cayenne.modeler.project.ObjectEntityTreeItem;
import org.apache.cayenne.modeler.project.ProjectTreeCell;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainWindowLayout
    extends AbstractWindowLayout
    implements DataDomainListener,
               MainWindowSupport
{
    private static final Log LOGGER = LogFactory.getLog(MainWindowLayout.class);

    @FXML
    private TreeView<String> treeView;
//    private TreeView<CayenneTreeItem<String>> treeView;

    @FXML
    private AnchorPane detailAnchorPane, mainToolBarAnchorPane;

    private DatabaseEntityLayout databaseEntityDetail; // TabPane
    private DataDomainLayout dataDomainDetail;
    private DataMapLayout dataMapDetail;
    private DataNodeLayout dataNodeDetail;
    private ObjectEntityLayout objectEntityDetail; // TabPane

    private MainToolBarLayout mainToolBarLayout;


//    private final TreeItem<CayenneTreeItem<String>> treeRoot = new CayenneTreeItem<>(); // = new TreeItem<>();
//    private final TreeItem<CayenneTreeItem<String>> treeRoot = new TreeItem<CayenneTreeItem<String>>(); // = new TreeItem<>();
    private final TreeItem<String> treeRoot = new TreeItem<>(); // = new TreeItem<>();

    private CayenneProject cayenneProject;

    private final StringProperty titleProperty = new SimpleStringProperty();

    public MainWindowLayout() throws IOException
    {
        super(new Stage(), "/layouts/MainWindowLayout.fxml");

        setMinimumWindowSize(900, 700);

        getStage().setOnCloseRequest(event ->
            {
                LOGGER.debug("Window is closing!");
                // ideas for checking if window should save before closing or cancel, etc:
                // event.consume();  <- Prevents window from closing
                // http://stackoverflow.com/questions/31540500/alert-box-for-when-user-attempts-to-close-application-using-setoncloserequest-in
                // http://stackoverflow.com/questions/23160573/javafx-stage-setoncloserequest-without-function

                if (cayenneProject.isDirty())
                {
                    final Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Close Window");
                    alert.setHeaderText("Unsaved Changes");
                    alert.setContentText("Are you sure you want to close this window and lose your changes?");

                    final Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK)
                    {
                        // ... user chose OK
                    }
                    else
                    {
                        // ... user chose CANCEL or closed the dialog
                        event.consume();
                    }
                }
            });
    }

    @Override
    public void initializeLayout()
    {
        super.initializeLayout();
    }

    public CayenneProject getCayenneProject()
    {
        return cayenneProject;
    }

    private void setTitle()
    {
        final String edited = cayenneProject.isDirty() ? "[edited] " : "";
        String title = edited + "Cayenne Modeler";

        if (cayenneProject != null)
            title += " - " + cayenneProject.getPath();

        titleProperty.set(title);
    }

//    private DataDomainAdapter dataDomainAdapter;

    private DetailEditorSupport<?> getDetailEditor(final TreeItem<String> treeItem) throws IOException
    {
        if (treeItem instanceof DataDomainTreeItem)
            return getDataDomainDetail();
        else if (treeItem instanceof DataMapTreeItem)
            return getDataMapDetail();
        else if (treeItem instanceof ObjectEntityTreeItem)
            return getObjectEntityDetail();
        else if (treeItem instanceof DatabaseEntityTreeItem)
            return getDatabaseEntityDetail();
        else if (treeItem instanceof DataNodeTreeItem)
            return getDataNodeDetail();

        return null;
    }

    public void displayCayenneProject(final CayenneProject cayenneProject) throws IOException
    {
        this.cayenneProject    = cayenneProject;
//        this.dataDomainAdapter = new DataDomainAdapter(cayenneProject);

        // Wire up the window's title bar to be aware of changes to the dirty indicator.
        getStage().titleProperty().bind(titleProperty);
        cayenneProject.dirtyProperty().addListener((observable, oldValue, newValue) -> setTitle());
        setTitle();

        treeRoot.setExpanded(true);
        treeView.setRoot(treeRoot);
        treeView.setShowRoot(false);

        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>()
        {
            @Override
            public TreeCell<String> call(TreeView<String> p)
            {
                return new ProjectTreeCell();
            }
        });

        // addDataDomain(CayenneModelManager.getModels().get(0));
        // System.out.println(CayenneModelManager.getModels().size());

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
            {
                LOGGER.debug("observable: " + observable + ", new: " + newValue + ", old: " + oldValue);

                try
                {
                    if (oldValue != null)
                    {
                        final DetailEditorSupport<?> detailEditor = getDetailEditor(oldValue);

                        if (detailEditor != null)
                            detailEditor.endEditing();
                    }

                    if (newValue != null)
                    {
                        observable.getValue().getParent();

//                      displayDataDomain((DataDomainTreeItem) newValue);
//                      displayDataMap((DataMapTreeItem) newValue);
//                      displayObjectEntity((ObjectEntityTreeItem) newValue);
//                      displayDatabaseEntity((DatabaseEntityTreeItem) newValue);
//                      displayDataNode((DataNodeTreeItem) newValue);

                        if (newValue instanceof DataDomainTreeItem)
                            displayDetailEditor(getDataDomainDetail(), ((DataDomainTreeItem) newValue).getPropertyAdapter());
                        else if (newValue instanceof DataMapTreeItem)
                            displayDetailEditor(getDataMapDetail(), ((DataMapTreeItem) newValue).getPropertyAdapter());
                        else if (newValue instanceof ObjectEntityTreeItem)
                            displayDetailEditor(getObjectEntityDetail(), ((ObjectEntityTreeItem) newValue).getPropertyAdapter());
                        else if (newValue instanceof DatabaseEntityTreeItem)
                            displayDetailEditor(getDatabaseEntityDetail(), ((DatabaseEntityTreeItem) newValue).getPropertyAdapter());
                        else if (newValue instanceof DataNodeTreeItem)
                            displayDetailEditor(getDataNodeDetail(), ((DataNodeTreeItem) newValue).getPropertyAdapter());
//                    if (newValue.getValue() instanceof DataDomainTreeViewModel)
//                        displayDataDomain((DataDomainTreeViewModel) newValue.getValue());
//                    else if (newValue.getValue() instanceof DataMapTreeViewModel)
//                        displayDataMap(((DataMapTreeViewModel) newValue.getValue()).getDataMap());
//                    // else if (newValue.getValue() instanceof
//                    // DataNodeTreeViewModel)
//                    // displayDataNode();
//                    else if (newValue.getValue() instanceof ObjectEntityTreeViewModel)
//                        displayObjectEntity((ObjectEntityTreeViewModel) newValue.getValue());
//                    else if (newValue.getValue() instanceof DatabaseEntityTreeViewModel)
//                        displayDatabaseEntity((DatabaseEntityTreeViewModel) newValue.getValue());
                    }
                    else
                    {
                        treeView.getSelectionModel().select(0);
                    }
                }
                catch (final IOException e)
                {
                    LOGGER.fatal("Cannot load UI.", e);
                }
            });

        setTitle();
//        displayDataDomain(domain);

        // Register for notifications.
        NotificationCenter.addProjectListener(cayenneProject, this);

        addDataDomain();

        treeView.requestFocus();
        treeView.getSelectionModel().select(0);
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

        for (final DataNodeAdapter dataNodeAdapter : cayenneProject.getDataDomainAdapter().getDataNodeAdapters())
            addDataNode(dataNodeAdapter, dataDomainBranch);

//        treeView.getSelectionModel().select(dataDomainBranch);
    }

    private void addDataMap(final DataMapAdapter dataMapAdapter, final DataDomainTreeItem dataDomainBranch)
    {
        final DataMapTreeItem dataMapBranch = new DataMapTreeItem(dataMapAdapter, dataDomainBranch);

//        final TreeItem<Object> dataMapBranch =
//            TreeViewUtilities.addNode(new TreeItem<>(new DataMapTreeViewModel(dataMap)),
//                                      dataDomainBranch,
//                                      FontAwesomeIcon.CUBES);

        for (final ObjectEntityAdapter objectEntityAdapter : dataMapAdapter.getObjectEntityAdapters())
            addObjEntity(objectEntityAdapter, dataMapBranch);
//        for (final ObjEntity objEntity : dataMap.getObjEntities())
//            addObjEntity(objEntity, dataMapBranch);

        for (final DatabaseEntityAdapter databaseEntityAdapter : dataMapAdapter.getDatabaseEntityAdapters())
            addDbEntity(databaseEntityAdapter, dataMapBranch);
//        for (final DbEntity dbEntity : dataMap.getDbEntities())
//            addDbEntity(dbEntity, dataMapBranch);
    }

//    private void addObjEntity(final ObjEntity objEntity, final TreeItem<Object> dataMapBranch)
    private void addObjEntity(final ObjectEntityAdapter objectEntityAdapter, final DataMapTreeItem dataMapBranch)
    {
        final ObjectEntityTreeItem objectEntityLeaf = new ObjectEntityTreeItem(objectEntityAdapter, dataMapBranch);
//        final TreeItem<Object> objEntityLeaf =
//            TreeViewUtilities.addNode(new TreeItem<>(new ObjectEntityTreeViewModel(objEntity)),
//                                      dataMapBranch,
//                                      FontAwesomeIcon.FILE_TEXT);
//        TreeItem<String> objEntityLeaf = TreeViewUtilities.addNode(objEntity.getName(), dataMapBranch, FontAwesomeIcon.FILE_TEXT);
    }

    private void addDbEntity(final DatabaseEntityAdapter databaseEntityAdapter, final DataMapTreeItem dataMapBranch)
    {
        final DatabaseEntityTreeItem databaseEntityLeaf = new DatabaseEntityTreeItem(databaseEntityAdapter, dataMapBranch);
//        final TreeItem<Object> dbEntityLeaf =
//            TreeViewUtilities.addNode(new TreeItem<>(new DatabaseEntityTreeViewModel(dbEntity)),
//                                      dataMapBranch,
//                                      FontAwesomeIcon.TABLE);
//        TreeItem<String> dbEntityLeaf = TreeViewUtilities.addNode(dbEntity.getName(), dataMapBranch, FontAwesomeIcon.TABLE);
    }

    private void addDataNode(final DataNodeAdapter dataNodeAdapter, final DataDomainTreeItem dataDomainBranch)
    {
        final DataNodeTreeItem dataMapBranch = new DataNodeTreeItem(dataNodeAdapter, dataDomainBranch);
    }

    private <T extends CayennePropertyAdapter> void displayDetailEditor(DetailEditorSupport<T> detailEditor, T propertyAdapter)
    {
        displayDetailView((Node) detailEditor);
        detailEditor.showEditor(propertyAdapter);
    }
//    private void displayDataDomain(final DataDomainTreeViewModel domain)
//    private void displayDataDomain(final DataDomainTreeItem dataDomainTreeItem) throws IOException
//    {
//        displayDetailView(getDataDomainDetail());
//        getDataDomainDetail().setPropertyAdapter(dataDomainTreeItem.getPropertyAdapter());
//        getDataDomainDetail().beginEditing();
//    }

//    private void displayDataMap(final DataMapTreeItem dataMapTreeItem) throws IOException
//    {
//        displayDetailView(getDataMapDetail());
//        getDataMapDetail().showEditor(dataMapTreeItem.getPropertyAdapter());
////        getDataMapDetail().setPropertyAdapter(dataMapTreeItem.getPropertyAdapter());
////        getDataMapDetail().beginEditing();
//    }

//    private void displayDataNode(final DataNodeTreeItem dataNodeTreeItem) throws IOException
//    {
//        displayDetailView(getDataNodeDetail());
//        getDataNodeDetail().setPropertyAdapter(dataNodeTreeItem.getPropertyAdapter());
//        getDataNodeDetail().beginEditing();
//    }

//    private void displayObjectEntity(final ObjectEntityTreeItem objectEntityTreeItem) throws IOException
//    {
//        displayDetailView(getObjectEntityDetail());
//        getObjectEntityDetail().setPropertyAdapter(objectEntityTreeItem.getPropertyAdapter());
//        getObjectEntityDetail().beginEditing();
//    }

//    private void displayDatabaseEntity(final DatabaseEntityTreeItem databaseEntityTreeItem) throws IOException
//    {
//        displayDetailView(getDatabaseEntityDetail());
//        getDatabaseEntityDetail().setPropertyAdapter(databaseEntityTreeItem.getPropertyAdapter());
//        getDatabaseEntityDetail().beginEditing();
//    }

    private void displayDetailView(final Node detailView)
    {
        displayView(detailAnchorPane, detailView);
    }

//    private void displayDetailView(BaseView detailView)
//    {
//        detailAnchorPane.getChildren().removeAll(detailAnchorPane.getChildren());
//        detailAnchorPane.getChildren().add(detailView.getScene().getRoot());
//    }


    @Override
    public void loadChildLayouts()
    {
        try
        {
//            databaseEntityDetail = new DatabaseEntityLayout(this);
//            dataDomainDetail = new DataDomainLayout(this);
//            dataMapDetail = new DataMapLayout(this);
//            dataNodeDetail = new DataNodeLayout(this);
//            objectEntityDetail = new ObjectEntityLayout(this);

            mainToolBarLayout = new MainToolBarLayout(this);

            displayView(mainToolBarAnchorPane, mainToolBarLayout);
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load subviews", exception);
        }
    }

    private DatabaseEntityLayout getDatabaseEntityDetail() throws IOException
    {
        return databaseEntityDetail == null ? databaseEntityDetail = new DatabaseEntityLayout(this) : databaseEntityDetail;
    }

    private DataDomainLayout getDataDomainDetail() throws IOException
    {
        return dataDomainDetail == null ? dataDomainDetail = new DataDomainLayout(this) : dataDomainDetail;
    }

    private DataMapLayout getDataMapDetail() throws IOException
    {
        return dataMapDetail == null ? dataMapDetail = new DataMapLayout(this) : dataMapDetail;
    }

    private DataNodeLayout getDataNodeDetail() throws IOException
    {
        return dataNodeDetail == null ? dataNodeDetail = new DataNodeLayout(this) : dataNodeDetail;
    }

    private ObjectEntityLayout getObjectEntityDetail() throws IOException
    {
        return objectEntityDetail == null ? objectEntityDetail = new ObjectEntityLayout(this) : objectEntityDetail;
    }

    public void newWindow() throws Exception
    {
        CayenneModeler.openProject(getCayenneProject());
    }

    public void openPreferences() throws Exception
    {
        CayenneModeler.openPreferences();
    }

    @Override
    public void handleDataDomainChange(final DataDomainChangeEvent event)
    {
        LOGGER.debug("Handling DataDomain Chain Event (Main Window)");
//        setDirty(true);

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
