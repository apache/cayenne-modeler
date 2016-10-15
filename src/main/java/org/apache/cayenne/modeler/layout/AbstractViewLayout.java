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
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public abstract class AbstractViewLayout
    extends AnchorPane
    implements LayoutSupport, MainWindowSupport
{
    private static final Log LOGGER = LogFactory.getLog(AbstractViewLayout.class);

    private final MainWindowSupport parentLayout;

    protected AbstractViewLayout(final MainWindowSupport parentLayout, final String fxmlPath) throws IOException
    {
        // Note: This assignment must precede the FXML load.
        this.parentLayout = parentLayout;

        loadFXML(fxmlPath);
    }

    @Deprecated // TODO: This can likely go away.
    protected void loadTab(final AnchorPane source, final AnchorPane destination)
    {
        displayView(destination, source);
    }

    public void setVisibility(final Node item, final boolean state)
    {
        item.setVisible(state);
        item.setManaged(state);
    }

    public void setVisibility(final Node[] items, final boolean state)
    {
        Arrays.stream(items).forEach(item -> setVisibility(item, state));
    }

    public void show(final Node... items)
    {
        setVisibility(items, true);
    }

    public void hide(final Node... items)
    {
        setVisibility(items, false);
    }

    @Override
    public MainWindowLayout getMainWindow()
    {
        return parentLayout.getMainWindow();
    }

    public MainWindowSupport getParentLayout()
    {
        return parentLayout;
    }
}
