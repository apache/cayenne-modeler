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

import org.apache.cayenne.modeler.CayenneModeler;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    @Override
    public void initializeLayout()
    {
        super.initializeLayout();
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

    private DetailEditorSupport<?> getDetailEditor(final TreeItem<String> treeItem)
    {
        if (treeItem instanceof DataDomainTreeItem)
            return dataDomainDetail;
        else if (treeItem instanceof DataMapTreeItem)
            return dataMapDetail;
        else if (treeItem instanceof ObjectEntityTreeItem)
            return objectEntityDetail;
        else if (treeItem instanceof DatabaseEntityTreeItem)
            return databaseEntityDetail;
        else if (treeItem instanceof DataNodeTreeItem)
            return dataNodeDetail;

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
                final DetailEditorSupport<?> detailEditor = getDetailEditor(oldValue);

                if (detailEditor != null)
                    detailEditor.endEditing();
            }

            if (newValue != null)
            {
                observable.getValue().getParent();
//                System.out.println("observable: " + observable.getValue() + ", new: " + newValue.getValue() + ", old: " + oldValue.getValue());
                LOGGER.debug("observable: " + observable + ", new: " + newValue + ", old: " + oldValue);

                LOGGER.debug(observable.getValue().getValue().getClass());
                LOGGER.debug(newValue.getValue().getClass());

                if (newValue instanceof DataDomainTreeItem)
                    displayDataDomain((DataDomainTreeItem) newValue);
                else if (newValue instanceof DataMapTreeItem)
                    displayDataMap((DataMapTreeItem) newValue);
                else if (newValue instanceof ObjectEntityTreeItem)
                    displayObjectEntity((ObjectEntityTreeItem) newValue);
                else if (newValue instanceof DatabaseEntityTreeItem)
                    displayDatabaseEntity((DatabaseEntityTreeItem) newValue);
                else if (newValue instanceof DataNodeTreeItem)
                    displayDataNode((DataNodeTreeItem) newValue);
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

        treeView.getSelectionModel().select(0);
        treeView.requestFocus();
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

        treeView.getSelectionModel().select(dataDomainBranch);
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
//    private void displayDataDomain(final DataDomainTreeViewModel domain)
    private void displayDataDomain(final DataDomainTreeItem dataDomainTreeItem)
    {
        displayDetailView(dataDomainDetail);
        dataDomainDetail.setPropertyAdapter(dataDomainTreeItem.getPropertyAdapter());
        dataDomainDetail.beginEditing();
    }

    private void displayDataMap(final DataMapTreeItem dataMapTreeItem)
    {
        displayDetailView(dataMapDetail);
        dataMapDetail.setPropertyAdapter(dataMapTreeItem.getPropertyAdapter());
        dataMapDetail.beginEditing();
    }

    private void displayDataNode(final DataNodeTreeItem dataNodeTreeItem)
    {
        displayDetailView(dataNodeDetail);
        dataNodeDetail.setPropertyAdapter(dataNodeTreeItem.getPropertyAdapter());
        dataNodeDetail.beginEditing();
    }

    private void displayObjectEntity(final ObjectEntityTreeItem objectEntityTreeItem)
    {
        displayDetailView(objectEntityDetail);
        objectEntityDetail.setPropertyAdapter(objectEntityTreeItem.getPropertyAdapter());
        objectEntityDetail.beginEditing();
    }

    private void displayDatabaseEntity(final DatabaseEntityTreeItem databaseEntityTreeItem)
    {
        displayDetailView(databaseEntityDetail);
        databaseEntityDetail.setPropertyAdapter(databaseEntityTreeItem.getPropertyAdapter());
        databaseEntityDetail.beginEditing();
    }

    private void displayDetailView(final Node detailView)
    {
        displayView(detailAnchorPane, detailView);
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
    private DataNodeLayout dataNodeDetail;

    private MainToolBarLayout mainToolBarLayout;

    @Override
    public void loadChildLayouts()
    {
        try
        {
            dataDomainDetail = new DataDomainLayout(this);
            dataMapDetail = new DataMapLayout(this);
            objectEntityDetail = new ObjectEntityLayout(this);
            databaseEntityDetail = new DatabaseEntityLayout(this);
            dataNodeDetail = new DataNodeLayout(this);

            mainToolBarLayout = new MainToolBarLayout(this);

            displayView(mainToolBarAnchorPane, mainToolBarLayout);
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load subviews", exception);
        }
    }

    public void openPreferences() throws Exception
    {
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
        LOGGER.debug("Handling DataDomain Chain Event (Main Window)");
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
