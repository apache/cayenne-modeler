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

import org.apache.cayenne.modeler.adapters.DatabaseEntityAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

public class DatabaseEntityTreeItem extends TreeItem<String> implements CayenneTreeItem<DatabaseEntityAdapter>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseEntityTreeItem.class);

    private final DatabaseEntityAdapter databaseEntityAdapter;

    public DatabaseEntityTreeItem(final DatabaseEntityAdapter databaseEntityAdapter, final TreeItem<String> parent)
    {
        this.databaseEntityAdapter = databaseEntityAdapter;

        valueProperty().bindBidirectional(databaseEntityAdapter.nameProperty());
        setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE, TREE_ICON_SIZE));
        parent.getChildren().add(this);
        setExpanded(true);
    }

    @Override
    public DatabaseEntityAdapter getPropertyAdapter()
    {
        return databaseEntityAdapter;
    }

    @Override
    public ContextMenu getContextMenu()
    {
      MenuItem addInbox = new MenuItem("Database Entity Menu");
      addInbox.setOnAction(event -> LOGGER.debug("Database Entity Context Menu Event: " + event));
      return new ContextMenu(addInbox);
    }
}
