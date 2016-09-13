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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public abstract class AbstractViewLayout extends AnchorPane implements MainWindowSupport
{
    private static final Log LOGGER = LogFactory.getLog(AbstractViewLayout.class);

    private MainWindowLayout mainWindow;

    protected AbstractViewLayout(MainWindowLayout mainWindow, String layout) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(layout));

        this.mainWindow = mainWindow;

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void initialize()
    {
        LOGGER.info("init " + this.getClass().getCanonicalName());

        loadSubViews();
    }

    protected void loadSubViews()
    {
        // Override in subclasses to load in any necessary sub-views.
    }

    protected void loadTab(AnchorPane source, AnchorPane destination)
    {
        destination.getChildren().removeAll(destination.getChildren());

        // Make the detail view fill the pane.
        AnchorPane.setTopAnchor(source, 0.0);
        AnchorPane.setLeftAnchor(source, 0.0);
        AnchorPane.setRightAnchor(source, 0.0);
        AnchorPane.setBottomAnchor(source, 0.0);

        destination.getChildren().add(source);
    }

    @Override
    public MainWindowLayout getMainWindow()
    {
        return mainWindow;
    }
}
