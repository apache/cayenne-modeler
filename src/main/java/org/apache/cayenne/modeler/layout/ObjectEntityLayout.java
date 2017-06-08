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

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ObjectEntityLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<ObjectEntityAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(ObjectEntityLayout.class);

    @FXML
    private AnchorPane classTabAnchorPane, attributesTabAnchorPane, relationshipsTabAnchorPane;

//    private MainWindowLayout mainWindow;

    private ObjectEntityClassTabLayout objectEntityClassTabLayout;
    private ObjectEntityAttributesTabLayout objectEntityAttributesTabLayout;
    private ObjectEntityRelationshipsTabLayout objectEntityRelationshipsTabLayout;

    private ObjectEntityAdapter objectEntityAdapter;

    public ObjectEntityLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/ObjectEntityLayout.fxml");
    }

    @Override
    public void loadChildLayouts()
    {
        try
        {
            objectEntityClassTabLayout         = new ObjectEntityClassTabLayout(this);
            objectEntityAttributesTabLayout    = new ObjectEntityAttributesTabLayout(this);
            objectEntityRelationshipsTabLayout = new ObjectEntityRelationshipsTabLayout(this);

            loadTab(objectEntityClassTabLayout, classTabAnchorPane);
            loadTab(objectEntityAttributesTabLayout, attributesTabAnchorPane);
            loadTab(objectEntityRelationshipsTabLayout, relationshipsTabAnchorPane);
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load subviews", exception);
        }
    }

//    @Deprecated // Unused?
//    public void display(final ObjEntity objEntity)
//    {
//        LOGGER.debug("trying to display: " + objEntity);
//    }

//    public void tabChanged(ActionEvent event)
//    public void tabChanged(final Event event)
//    {
//        LOGGER.debug("event: " + event);
//        getMainWindow().getCayenneProject().getDataMaps();
//    }

    @Override
    public void setPropertyAdapter(final ObjectEntityAdapter objectEntityAdapter)
    {
        this.objectEntityAdapter = objectEntityAdapter;

//        objectEntityClassTabLayout.setPropertyAdapter(objectEntityAdapter);
//        objectEntityAttributesTabLayout.setPropertyAdapter(objectEntityAdapter);
//        objectEntityRelationshipsTabLayout.setPropertyAdapter(objectEntityAdapter);
    }

    @Override
    public void beginEditing()
    {
        DetailEditorSupport.super.beginEditing();

        objectEntityClassTabLayout.showEditor(objectEntityAdapter);
        objectEntityAttributesTabLayout.showEditor(objectEntityAdapter);
        objectEntityRelationshipsTabLayout.showEditor(objectEntityAdapter);

//        objectEntityClassTabLayout.beginEditing();
//        objectEntityAttributesTabLayout.beginEditing();
//        objectEntityRelationshipsTabLayout.beginEditing();
    }

    @Override
    public void endEditing()
    {
        DetailEditorSupport.super.endEditing();

        objectEntityClassTabLayout.endEditing();
        objectEntityAttributesTabLayout.endEditing();
        objectEntityRelationshipsTabLayout.endEditing();
    }
}
