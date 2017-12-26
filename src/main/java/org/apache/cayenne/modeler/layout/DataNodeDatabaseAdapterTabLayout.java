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

import org.apache.cayenne.modeler.adapters.DataNodeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataNodeDatabaseAdapterTabLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DataNodeAdapter>

{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataNodeDatabaseAdapterTabLayout.class);

    private DataNodeAdapter dataNodeAdapter;

    public DataNodeDatabaseAdapterTabLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent.getMainWindow(), "/layouts/DataNodeDatabaseAdapterTabLayout.fxml");
    }

//    @Override
//    public void initializeView()
//    {
//        super.initializeView();
//    }

//    public void tabChanged(final Event event)
//    {
//        LOGGER.debug("event: " + event);
//        getMainWindow().getCayenneProject().getDataMaps();
//    }

    @Override
    public void setPropertyAdapter(final DataNodeAdapter dataNodeAdapter)
    {
        this.dataNodeAdapter = dataNodeAdapter;
    }

//    @Override
//    public void beginEditing()
//    {
//    }
//
//    @Override
//    public void endEditing()
//    {
//    }
}
