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

import org.apache.cayenne.modeler.adapters.ObjectEntityAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class ObjectEntityClassTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<ObjectEntityAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(ObjectEntityClassTabLayout.class);

    @FXML
    private Button dbEntitySyncButton;

    @FXML
    private CheckBox abstractClassCheckbox;

//    private MainWindowSupport parent;

    private ObjectEntityAdapter objectEntityAdapter;

    public ObjectEntityClassTabLayout(final MainWindowSupport parent) throws IOException
    {
        super(parent.getMainWindow(), "/layouts/ObjectEntityClassTabLayout.fxml");
    }


    @Override
    public void initialize()
    {
//        getScene().getWindow().getScene();
//        getStage().getScene().getWindow().get
//        System.out.println("mrg: " + getStage().getScene().getRoot());
//        System.out.println("oectv");

        super.initialize();

        dbEntitySyncButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REFRESH, "16px"));
    }

    @Override
    public void setPropertyAdapter(final ObjectEntityAdapter objectEntityAdapter)
    {
        this.objectEntityAdapter = objectEntityAdapter;
    }


    @Override
    public void beginEditing()
    {
        LOGGER.debug("begin editing " + this);

        abstractClassCheckbox.selectedProperty().bindBidirectional(objectEntityAdapter.getAbstractClassProperty());
    }


    @Override
    public void endEditing()
    {
        LOGGER.debug("end editing " + this);

        abstractClassCheckbox.selectedProperty().unbindBidirectional(objectEntityAdapter.getAbstractClassProperty());
    }
}
