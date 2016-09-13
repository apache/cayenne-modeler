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
import org.apache.cayenne.modeler.adapters.DatabaseEntityAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class DatabaseEntityLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DatabaseEntityAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(DatabaseEntityLayout.class);

    @FXML
    private AnchorPane tableTabAnchorPane, columnsTabAnchorPane;

//    private MainWindowLayout mainWindow;

    private DatabaseEntityTableTabLayout   databaseEntityTableTabLayout;
    private DatabaseEntityColumnsTabLayout databaseEntityColumnsTabLayout;
//    private ObjectEntityClassTabLayout objectEntityClassTabLayout;
//    private ObjectEntityAttributesTabLayout objectEntityAttributesTabLayout;

    private DatabaseEntityAdapter databaseEntityAdapter;

    public DatabaseEntityLayout(MainWindowSupport parent) throws IOException
    {
        super(parent.getMainWindow(), "/layouts/DatabaseEntityLayout.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/DatabaseEntityLayout.fxml"));
//
//        this.mainWindow = mainWindow;
//
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//        fxmlLoader.load();
    }

//    @Override
//    public void initialize()
//    {
//        super.initialize();
//        System.out.println("done with init DatabaseEntityLayout");
//    }

    @Override
    protected void loadSubViews()
    {
        try
        {
            databaseEntityTableTabLayout   = new DatabaseEntityTableTabLayout(this);
            databaseEntityColumnsTabLayout = new DatabaseEntityColumnsTabLayout(this);

            loadTab(databaseEntityTableTabLayout, tableTabAnchorPane);
            loadTab(databaseEntityColumnsTabLayout, columnsTabAnchorPane);
        }
        catch (Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load subviews", exception);
        }
    }

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
    public void display(DbEntity dbEntity)
    {
        LOGGER.debug("trying to display: " + dbEntity);
//        objectEntityClassTabLayout.display(dbEntity);
//        objectEntityAttributesTabLayout.display(dbEntity);
//        objEntity.getAttributes()
    }

//    public void tabChanged(ActionEvent event)
    public void tabChanged(Event event)
    {
        LOGGER.debug("event: " + event);
        getMainWindow().getCayenneProject().getDataMaps();
    }

    @Override
    public void setPropertyAdapter(DatabaseEntityAdapter databaseEntityAdapter)
    {
        this.databaseEntityAdapter = databaseEntityAdapter;

        databaseEntityTableTabLayout.setPropertyAdapter(databaseEntityAdapter);
        databaseEntityColumnsTabLayout.setPropertyAdapter(databaseEntityAdapter);
    }

    @Override
    public void beginEditing()
    {
        databaseEntityTableTabLayout.beginEditing();
        databaseEntityColumnsTabLayout.beginEditing();
    }

    @Override
    public void endEditing()
    {
        databaseEntityTableTabLayout.endEditing();
        databaseEntityColumnsTabLayout.endEditing();
    }
}
