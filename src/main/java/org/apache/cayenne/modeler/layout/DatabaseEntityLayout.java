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

import org.apache.cayenne.modeler.adapters.DatabaseEntityAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class DatabaseEntityLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DatabaseEntityAdapter>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseEntityLayout.class);

    @FXML
    private AnchorPane tableTabAnchorPane, columnsTabAnchorPane;

//    private MainWindowLayout mainWindow;

    private DatabaseEntityTableTabLayout   databaseEntityTableTabLayout;
    private DatabaseEntityColumnsTabLayout databaseEntityColumnsTabLayout;
//    private ObjectEntityClassTabLayout objectEntityClassTabLayout;
//    private ObjectEntityAttributesTabLayout objectEntityAttributesTabLayout;

    private DatabaseEntityAdapter databaseEntityAdapter;

    public DatabaseEntityLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/DatabaseEntityLayout.fxml");
    }

    @Override
    public void loadChildLayouts()
    {
        try
        {
            databaseEntityTableTabLayout   = new DatabaseEntityTableTabLayout(this);
            databaseEntityColumnsTabLayout = new DatabaseEntityColumnsTabLayout(this);

            loadTab(databaseEntityTableTabLayout, tableTabAnchorPane);
            loadTab(databaseEntityColumnsTabLayout, columnsTabAnchorPane);
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load subviews", exception);
        }
    }

//    public void tabChanged(final Event event)
//    {
//        LOGGER.debug("event: " + event);
//        getMainWindow().getCayenneProject().getDataMaps();
//    }

    @Override
    public void setPropertyAdapter(final DatabaseEntityAdapter databaseEntityAdapter)
    {
        this.databaseEntityAdapter = databaseEntityAdapter;

//        databaseEntityTableTabLayout.setPropertyAdapter(databaseEntityAdapter);
//        databaseEntityColumnsTabLayout.setPropertyAdapter(databaseEntityAdapter);
    }

    @Override
    public void beginEditing()
    {
        DetailEditorSupport.super.beginEditing();

        databaseEntityTableTabLayout.showEditor(databaseEntityAdapter);
        databaseEntityColumnsTabLayout.showEditor(databaseEntityAdapter);

//        databaseEntityTableTabLayout.beginEditing();
//        databaseEntityColumnsTabLayout.beginEditing();
    }

    @Override
    public void endEditing()
    {
        DetailEditorSupport.super.endEditing();

        databaseEntityTableTabLayout.endEditing();
        databaseEntityColumnsTabLayout.endEditing();
    }
}
