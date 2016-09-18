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

import org.apache.cayenne.modeler.adapters.DataMapAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DataMapLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DataMapAdapter>
{
    private static final Log LOGGER = LogFactory.getLog(DataMapLayout.class);

//    private MainWindowLayout mainWindow;

    @FXML
    private TextField dataMapName;

    private DataMapAdapter dataMapAdapter;

    public DataMapLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/DataMapLayout.fxml");
    }

    @Override
    public void setPropertyAdapter(final DataMapAdapter dataMapAdapter)
    {
        this.dataMapAdapter = dataMapAdapter;
    }

    @Override
    public void beginEditing()
    {
        LOGGER.debug("begin editing " + this);

        dataMapName.textProperty().bindBidirectional(dataMapAdapter.getNameProperty());
    }

    @Override
    public void endEditing()
    {
        LOGGER.debug("end editing " + this);

        dataMapName.textProperty().unbindBidirectional(dataMapAdapter.getNameProperty());
    }
}
