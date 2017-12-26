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

import org.apache.cayenne.modeler.adapters.DataDomainAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

//public class DataDomainTreeItem extends CayenneTreeItem<String>
public class DataDomainTreeItem extends TreeItem<String> implements CayenneTreeItem<DataDomainAdapter>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataDomainTreeItem.class);

    private final DataDomainAdapter dataDomainAdapter;

    public DataDomainTreeItem(final DataDomainAdapter dataDomainAdapter, final TreeItem<String> treeRoot)
//    public DataDomainTreeItem(final DataDomainAdapter dataDomainAdapter, final TreeItem<CayenneTreeItem<String>> treeRoot)
//    public DataDomainTreeItem(final DataDomainAdapter dataDomainAdapter)
    {
        this.dataDomainAdapter = dataDomainAdapter;

        valueProperty().bindBidirectional(dataDomainAdapter.nameProperty());
        setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.DATABASE, TREE_ICON_SIZE));
        treeRoot.getChildren().add(this);
        setExpanded(true);
    }

//    public DataDomainAdapter getDataDomainAdapter()
//    {
//        return dataDomainAdapter;
//    }

    @Override
    public DataDomainAdapter getPropertyAdapter()
    {
        return dataDomainAdapter;
    }

    @Override
    public ContextMenu getContextMenu()
    {
      MenuItem addInbox = new MenuItem("Data Domain Menu");
      addInbox.setOnAction(event -> LOGGER.debug("Data Domain Context Menu Event: " + event));
      return new ContextMenu(addInbox);
    }
}
