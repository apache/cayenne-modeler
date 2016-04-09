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

import org.apache.cayenne.modeler.CayenneModeler;
import org.apache.cayenne.modeler.preferences.ModelerPreferences;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashLayout extends WindowLayout
{
    @FXML
    private ListView<String> projectListView;

    @FXML
    private Button newProjectButton, openProjectButton;

    public SplashLayout(Stage initialStage) throws IOException
    {
        super(initialStage, "/layouts/SplashLayout.fxml");

        initializeStyle(StageStyle.DECORATED);
        setResizable(false);
    }

    public void initialize()
    {
//        final List<String> arr = ModelerPreferences.getLastProjFiles();

//        URL url = CayenneModeler.class.getResource("/cayenne-analytic.xml");

        ObservableList<String> projects =
            FXCollections.observableArrayList(ModelerPreferences.getLastProjFiles());

        projectListView.setItems(projects);
        projectListView.getSelectionModel().select(0);

        newProjectButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS_SQUARE, "16px"));
        openProjectButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FOLDER_OPEN, "16px"));
    }

    public void onOpenClicked()
    {
        System.out.println("open clicked");
    }

    public void onNewClicked()
    {
        System.out.println("new clicked");
    }

    public void onOpenProjectSelected(MouseEvent event)
    {
        if (event.getClickCount() == 2)
            openSelectedModel();
    }

    public void onKeyTyped(KeyEvent event)
    {
//        System.out.println(event);

        if (event.getCharacter().equals("\r"))
            openSelectedModel();
    }

    private void openSelectedModel()
    {
        try
        {
            CayenneModeler.openProject(projectListView.getSelectionModel().getSelectedItem());
            hide();
        }
        catch (Exception exception)
        {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }
    }
}
