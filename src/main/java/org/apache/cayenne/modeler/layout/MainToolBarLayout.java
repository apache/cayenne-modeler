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

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class MainToolBarLayout
    extends AbstractViewLayout
{
    private static final Log LOGGER = LogFactory.getLog(MainToolBarLayout.class);

    @FXML
    private Button newButton, openButton, saveButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button cutButton, copyButton, pasteButton;

    @FXML
    private Button undoButton, redoButton;

    @FXML
    private Button dataMapButton, dataNodeButton;

    private final boolean showLabels = false;

    public MainToolBarLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/MainToolBarLayout.fxml");
    }

    @Override
    public void initializeLayout()
    {
        super.initializeLayout();

        setToolBarValues();
    }

    private void setToolBarValues()
    {
        setIcons();
        setLabels();
        setToolTips();
        setState();
    }

    private void setIcons()
    {
        newButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS_SQUARE, "16px"));
        openButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FOLDER_OPEN, "16px"));
        saveButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FLOPPY_ALT, "16px"));

        removeButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TRASH, "16px"));

        cutButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.CUT, "16px"));
        copyButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.COPY, "16px"));
        pasteButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PASTE, "16px"));

        undoButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.UNDO, "16px"));
        redoButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REPEAT, "16px"));

        dataMapButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.CUBES, "16px"));
        dataNodeButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SERVER, "16px"));
    }

    private void setToolTips()
    {
        newButton.setTooltip(new Tooltip("Create a new Cayenne Model project."));
        openButton.setTooltip(new Tooltip("Open an existing Cayenne Model project."));
        saveButton.setTooltip(new Tooltip("Save this Cayenne Model project."));

        removeButton.setTooltip(new Tooltip("Remove this item.")); // FIXME: Should be dynamic.

        cutButton.setTooltip(new Tooltip("Cut this item to the clipboard.")); // FIXME: Should be dynamic.
        copyButton.setTooltip(new Tooltip("Copy this item to the clipboard.")); // FIXME: Should be dynamic.
        pasteButton.setTooltip(new Tooltip("Paste this item from the clipboard.")); // FIXME: Should be dynamic.

        undoButton.setTooltip(new Tooltip("Undo.")); // FIXME: Should be dynamic.
        redoButton.setTooltip(new Tooltip("Redo.")); // FIXME: Should be dynamic.

        dataMapButton.setTooltip(new Tooltip("Create a new Data Map to hold Java and Database definitions."));
        dataNodeButton.setTooltip(new Tooltip("Create a new Data Node to hold database connection settings."));
    }

    private void setLabels()
    {
        if (showLabels)
        {
            newButton.setText("New");
            openButton.setText("Open");
            saveButton.setText("Save");

            removeButton.setText("Delete"); // FIXME: Should be dynamic.

            cutButton.setText("Cut"); // FIXME: Should be dynamic.
            copyButton.setText("Copy"); // FIXME: Should be dynamic.
            pasteButton.setText("Paste"); // FIXME: Should be dynamic.

            undoButton.setText("Undo"); // FIXME: Should be dynamic.
            redoButton.setText("Redo"); // FIXME: Should be dynamic.

            dataMapButton.setText("New Data Map");
            dataNodeButton.setText("New Data Node");
        }
        else
        {
            newButton.setText(null);
            openButton.setText(null);
            saveButton.setText(null);

            removeButton.setText(null);

            cutButton.setText(null);
            copyButton.setText(null);
            pasteButton.setText(null);

            undoButton.setText(null);
            redoButton.setText(null);

            dataMapButton.setText(null);
            dataNodeButton.setText(null);
        }
    }

    private void setState()
    {
        newButton.setDisable(false); // Can always create a new Cayenne Project.
        openButton.setDisable(false); // Can always open an existing Cayenne Project.
        saveButton.setDisable(true); // TODO: Bind to main window's dirty state.

        removeButton.setDisable(true);

        cutButton.setDisable(true);
        copyButton.setDisable(true);
        pasteButton.setDisable(true);

        undoButton.setDisable(true);
        redoButton.setDisable(true);

        dataMapButton.setDisable(true);
        dataNodeButton.setDisable(true);
    }

    public void onNewButtonClicked()
    {
        LOGGER.debug("new!");
    }

}
