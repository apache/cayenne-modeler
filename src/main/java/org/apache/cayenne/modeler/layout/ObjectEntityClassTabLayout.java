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
import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.modeler.adapters.ObjectEntityAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class ObjectEntityClassTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<ObjectEntityAdapter>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectEntityClassTabLayout.class);

    @FXML
    private Button synchronizeWithDatabaseEntityButton, viewRelatedDatabaseEntityButton;

    @FXML
    private CheckBox abstractClassCheckbox;

//    private MainWindowSupport parent;

    private ObjectEntityAdapter objectEntityAdapter;

    private List<Binding<?>> bindings;

    public ObjectEntityClassTabLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/ObjectEntityClassTabLayout.fxml");
    }


    @Override
    public void initializeLayout()
    {
        super.initializeLayout();

        synchronizeWithDatabaseEntityButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REFRESH, "16px"));
        synchronizeWithDatabaseEntityButton.setText(null);
        viewRelatedDatabaseEntityButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE, "16px"));
        viewRelatedDatabaseEntityButton.setText(null);
    }

    @Override
    public void setPropertyAdapter(final ObjectEntityAdapter objectEntityAdapter)
    {
        this.objectEntityAdapter = objectEntityAdapter;
    }

    @Override
    public void initializeBindings()
    {
        bindings = new ArrayList<>();

        bindings.add(new Binding<>(abstractClassCheckbox.selectedProperty(), objectEntityAdapter.abstractClassProperty()));
    }

    @Override
    public List<Binding<?>> getBindings()
    {
        return bindings;
    }

//    @Override
//    public void beginEditing()
//    {
//        LOGGER.debug("begin editing " + this);
//
//        abstractClassCheckbox.selectedProperty().bindBidirectional(objectEntityAdapter.abstractClassProperty());
//    }


//    @Override
//    public void endEditing()
//    {
//        LOGGER.debug("end editing " + this);
//
//        abstractClassCheckbox.selectedProperty().unbindBidirectional(objectEntityAdapter.abstractClassProperty());
//    }
}
