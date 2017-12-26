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

import org.apache.cayenne.map.ObjEntity;
import org.apache.cayenne.modeler.adapters.ObjectEntityAdapter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

public class ObjectEntityTreeItem extends TreeItem<String> implements CayenneTreeItem<ObjectEntityAdapter>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataDomainTreeItem.class);

    private final ObjectEntityAdapter objectEntityAdapter;

    public ObjectEntityTreeItem(final ObjectEntityAdapter objectEntityAdapter, final TreeItem<String> parent)
    {
        this.objectEntityAdapter = objectEntityAdapter;

        valueProperty().bindBidirectional(objectEntityAdapter.nameProperty());
        setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FILE_TEXT, TREE_ICON_SIZE));
        parent.getChildren().add(this);
        setExpanded(true);
    }

    @Override
    public ObjectEntityAdapter getPropertyAdapter()
    {
        return objectEntityAdapter;
    }

    @Override
    public ContextMenu getContextMenu()
    {
        ContextMenu contextMenu  = new ContextMenu();
        ObjEntity   objectEntity = (ObjEntity) getPropertyAdapter().getWrappedObject();

        getPropertyAdapter().getDataMapAdapter().getDatabaseEntityAdapters().stream().forEach(databaseEntity ->
            {
                if (StringUtils.equals(databaseEntity.getName(), objectEntity.getDbEntityName()))
                {
                    MenuItem jumpTo = new MenuItem("Jump To Database Entity: " + databaseEntity.getName());

                    jumpTo.setMnemonicParsing(false);
                    jumpTo.setOnAction(event ->
                        {
                            LOGGER.debug("Jumping to DB Entity: " + databaseEntity.getName());
                        });

                    contextMenu.getItems().add(jumpTo);
                }
            });

      return contextMenu;
    }
}
