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

package org.apache.cayenne.modeler.project;

import org.apache.cayenne.modeler.adapters.DataNodeAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

public class DataNodeTreeItem extends TreeItem<String> implements CayenneTreeItem<DataNodeAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(DataNodeTreeItem.class);

    private final DataNodeAdapter dataNodeAdapter;

    public DataNodeTreeItem(final DataNodeAdapter dataNodeAdapter, final TreeItem<String> parent)
    {
        this.dataNodeAdapter = dataNodeAdapter;

        valueProperty().bindBidirectional(dataNodeAdapter.nameProperty());
        setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SERVER, TREE_ICON_SIZE));
        parent.getChildren().add(this);
        setExpanded(true);
    }

    @Override
    public DataNodeAdapter getPropertyAdapter()
    {
        return dataNodeAdapter;
    }

    @Override
    public ContextMenu getContextMenu()
    {
      MenuItem addInbox = new MenuItem("Data Node Menu");
      addInbox.setOnAction(event -> LOGGER.debug("Data Node Context Menu Event: " + event));
      return new ContextMenu(addInbox);
    }
}
