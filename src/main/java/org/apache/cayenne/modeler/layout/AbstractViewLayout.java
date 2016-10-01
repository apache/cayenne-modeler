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

import javafx.scene.layout.AnchorPane;

public abstract class AbstractViewLayout
    extends AnchorPane
    implements LayoutSupport, MainWindowSupport
{
    private static final Log LOGGER = LogFactory.getLog(AbstractViewLayout.class);

//    private MainWindowLayout  mainWindow;
    private final MainWindowSupport parentComponent;

    protected AbstractViewLayout(final MainWindowSupport parentComponent, final String fxmlPath) throws IOException
    {
        // Note: This assignment must precede the FXML load.
        this.parentComponent = parentComponent;

        loadFXML(fxmlPath);
    }

    @Deprecated // TODO: This can likely go away.
    protected void loadTab(final AnchorPane source, final AnchorPane destination)
    {
        displayView(source, destination);
    }

    @Override
    public MainWindowLayout getMainWindow()
    {
        return parentComponent.getMainWindow(); //mainWindow;
    }

    public MainWindowSupport getParentComponent()
    {
        return parentComponent;
    }
}
