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

package org.apache.cayenne.modeler.utility;

import org.apache.cayenne.modeler.model.CayenneTreeViewModel;

import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.GlyphsDude;
import javafx.scene.control.TreeItem;

public class TreeViewUtilities
{
    public static final String TREE_ICON_SIZE = "16px";

    public static TreeItem<CayenneTreeViewModel> addNode(TreeItem<CayenneTreeViewModel> item, TreeItem<CayenneTreeViewModel> parent)
    {
        return addNode(item, parent, true, null);
    }

    public static TreeItem<CayenneTreeViewModel> addNode(TreeItem<CayenneTreeViewModel> item, TreeItem<CayenneTreeViewModel> parent, boolean expanded)
    {
        return addNode(item, parent, expanded, null);
    }

    public static TreeItem<CayenneTreeViewModel> addNode(TreeItem<CayenneTreeViewModel> item, TreeItem<CayenneTreeViewModel> parent, GlyphIcons icon)
    {
        return addNode(item, parent, true, icon);
    }

    public static TreeItem<CayenneTreeViewModel> addNode(TreeItem<CayenneTreeViewModel> item, TreeItem<CayenneTreeViewModel> parent, boolean expanded, GlyphIcons icon)
    {
//        TreeItem<String> item = new TreeItem<String>(title);

        item.setExpanded(expanded);

        if (icon != null)
            item.setGraphic(GlyphsDude.createIcon(icon, TREE_ICON_SIZE));

        parent.getChildren().add(item);

        return item;
    }
}
