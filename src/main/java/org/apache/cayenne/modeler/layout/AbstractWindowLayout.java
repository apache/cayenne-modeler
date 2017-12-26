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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class AbstractWindowLayout
    extends AnchorPane
    implements LayoutSupport
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWindowLayout.class);

    private final Stage stage;

    public AbstractWindowLayout(final Stage stage, final String fxmlPath) throws IOException
    {
        loadFXML(fxmlPath);

        this.stage = stage;

        stage.setScene(new Scene(this));
    }

    public Stage getStage()
    {
        return stage;
    }

    public void initializeStyle(final StageStyle stageStyle)
    {
        stage.initStyle(stageStyle);
    }

    public void setMinimumWindowSize(final int width, final int height)
    {
        stage.setMinWidth(900);
        stage.setMinHeight(700);
    }

    public void setResizable(final boolean resizable)
    {
        stage.setResizable(resizable);
    }

    public void setTitle(final String title)
    {
        stage.setTitle(title);
    }

    public void show()
    {
        stage.show();
    }

    public void hide()
    {
        stage.hide();
    }
}
