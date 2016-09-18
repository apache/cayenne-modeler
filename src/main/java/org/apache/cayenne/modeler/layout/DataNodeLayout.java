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

import org.apache.cayenne.modeler.adapters.DataNodeAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class DataNodeLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DataNodeAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(DataNodeLayout.class);

    @FXML
    private AnchorPane configurationTabAnchorPane, databaseAdapterTabAnchorPane, passwordEncoderTabAnchorPane;

    @FXML
    private Tab passwordEncoderTab;

//    private MainWindowLayout mainWindow;

//    @FXML
//    private TextField dataMapName;

    private DataNodeConfigurationTabLayout dataNodeConfigurationTabLayout;
    private DataNodeDatabaseAdapterTabLayout dataNodeDatabaseAdapterTabLayout;
    private DataNodePasswordEncoderTabLayout dataNodePasswordEncoderTabLayout;

    private DataNodeAdapter dataNodeAdapter;

    public DataNodeLayout(final MainWindowSupport parent) throws IOException
    {
        super(parent.getMainWindow(), "/layouts/DataNodeLayout.fxml");
    }

    @Override
    protected void loadSubViews()
    {
        try
        {
            dataNodeConfigurationTabLayout   = new DataNodeConfigurationTabLayout(this);
            dataNodeDatabaseAdapterTabLayout = new DataNodeDatabaseAdapterTabLayout(this);
            dataNodePasswordEncoderTabLayout = new DataNodePasswordEncoderTabLayout(this);

            loadTab(dataNodeConfigurationTabLayout, configurationTabAnchorPane);
            loadTab(dataNodeDatabaseAdapterTabLayout, databaseAdapterTabAnchorPane);
            loadTab(dataNodePasswordEncoderTabLayout, passwordEncoderTabAnchorPane);
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load subviews", exception);
        }
    }

    @Override
    public void setPropertyAdapter(final DataNodeAdapter dataNodeAdapter)
    {
        this.dataNodeAdapter = dataNodeAdapter;
    }

    @Override
    public void beginEditing()
    {
        LOGGER.debug("begin editing " + this);

//        dataMapName.textProperty().bindBidirectional(dataMapAdapter.getNameProperty());
    }

    @Override
    public void endEditing()
    {
        LOGGER.debug("end editing " + this);

//        dataMapName.textProperty().unbindBidirectional(dataMapAdapter.getNameProperty());
    }

    public void tabChanged(final Event event)
    {
        LOGGER.debug("event: " + event);
        getMainWindow().getCayenneProject().getDataMaps();
    }

    public void disablePasswordEncoderTab()
    {
        passwordEncoderTab.setDisable(true);
    }

    public void enablePasswordEncoderTab()
    {
        passwordEncoderTab.setDisable(false);
    }
}
