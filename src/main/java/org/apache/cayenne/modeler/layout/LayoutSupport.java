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
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public interface LayoutSupport
{
    static final Log LOGGER = LogFactory.getLog(LayoutSupport.class);

    default FXMLLoader loadFXML(final String fxmlPath) throws IOException
    {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        // Note: Must manually initialize the layout because JavaFX will not
        //       automatically call the "initialize" method when the FXML is
        //       loaded from an interface's default method.  To avoid confusion
        //       with the JavaFX "initialize" name, a different method name is
        //       used for the same purpose.
        initializeLayout();

        return fxmlLoader;
    }

    default void initializeLayout()
    {
        LOGGER.info("init " + this.getClass().getCanonicalName());

        loadChildLayouts();
    }

    default void loadChildLayouts()
    {
        // Override in subclasses to load in any necessary child layouts.
    }

    default void displayView(final AnchorPane anchorPane, final Node view)
    {
        // Remove anything already there.
        anchorPane.getChildren().removeAll(anchorPane.getChildren());

        // Make the view fill the anchor pane.
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);

        // Add the view into the anchor pane.
        anchorPane.getChildren().add(view);
    }

    default void disable(final Node node)
    {
        node.setDisable(true);
    }

    default void enable(final Node node)
    {
        node.setDisable(false);
    }
}
