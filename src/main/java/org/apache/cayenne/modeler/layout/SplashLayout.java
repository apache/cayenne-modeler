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

import java.io.File;
import java.io.IOException;

import org.apache.cayenne.modeler.CayenneModeler;
import org.apache.cayenne.modeler.preferences.ModelerPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The "splash" page for the application. Shown on modeler launch and gives the
 * choice of opening recent/existing projects or creating a new project.
 */
public class SplashLayout extends AbstractWindowLayout
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SplashLayout.class);

    /**
     * Outlet to the recent projects ListView.
     */
    @FXML
    private ListView<String> recentProjectsListView;

    /**
     * Outlet to the New Project Button.
     */
    @FXML
    private Button newProjectButton;

    /**
     * Outlet to the OpenProject Button.
     */
    @FXML
    private Button openProjectButton;


    /**
     * Create a new splash layout.
     *
     * @param initialStage
     * @throws IOException
     */
    public SplashLayout(final Stage initialStage) throws IOException
    {
        super(initialStage, "/layouts/SplashLayout.fxml");

        initializeStyle(StageStyle.DECORATED);
        setResizable(false);
    }

    /**
     * Initialize the layout with a list of recent projects.
     */
    public void initialize()
    {
        // Load the recent projects.
        final ObservableList<String> projects =
            FXCollections.observableArrayList(ModelerPreferences.getLastProjFiles());

        // Set the recent projects and pre-select the first entry.
        recentProjectsListView.setItems(projects);
        recentProjectsListView.getSelectionModel().select(0);

        // Set the graphics for the new/open button.
        newProjectButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS_SQUARE, "16px"));
        openProjectButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FOLDER_OPEN, "16px"));
    }

    /**
     * Callback handler for when the Open Project button is clicked.
     */
    public void onOpenProjectClicked()
    {
        try
        {
            final FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Open Cayenne Model");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            fileChooser.getExtensionFilters().addAll
                (
                    new FileChooser.ExtensionFilter("Cayenne Projects", "cayenne*.xml"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
                );

            final File file = fileChooser.showOpenDialog(getStage());

            if (file != null)
            {
                CayenneModeler.openProject(file.getAbsolutePath());
                hide();
            }
            else
            {
                LOGGER.info("Open canceled");
            }
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load Cayenne model", exception);
        }
    }

    /**
     * Callback handler for when the New Project button is clicked.
     */
    public void onNewProjectClicked()
    {
        LOGGER.debug("new project clicked -- but not implemented");
    }

    /**
     * Callback handler for when a recent project is double-clicked.
     */
    public void onOpenRecentProject(final MouseEvent event)
    {
        if (event.getClickCount() == 2 && recentProjectsListView.getItems().size() > 0)
            openSelectedProject();
    }

    /**
     * Callback handler for when return/enter is pressed (to open the recent
     * project).
     */
    public void onKeyTyped(final KeyEvent event)
    {
        if (event.getCharacter().equals("\r") && recentProjectsListView.getItems().size() > 0)
            openSelectedProject();
    }

    /**
     * Opens the selected recent project.
     */
    private void openSelectedProject()
    {
        try
        {
            CayenneModeler.openProject(recentProjectsListView.getSelectionModel().getSelectedItem());
            hide();
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load Cayenne model", exception);
        }
    }
}
