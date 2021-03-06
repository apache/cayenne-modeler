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

import org.apache.cayenne.modeler.adapters.CayennePropertyAdapter;

import javafx.scene.control.ContextMenu;

//public class CayenneTreeItem<T> extends TreeItem<T>
public interface CayenneTreeItem<T extends CayennePropertyAdapter>
{
//    static final Log LOGGER = LogFactory.getLog(CayenneTreeItem.class);

    public static final String TREE_ICON_SIZE = "16px";

    T getPropertyAdapter();

    ContextMenu getContextMenu();

//    default ContextMenu getContextMenu()
//    {
//        MenuItem addInbox = new MenuItem("add inbox");
//        addInbox.setOnAction(event -> LOGGER.debug("default context menu - event: " + event));
//        return new ContextMenu(addInbox);
//    }
}
