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

package org.apache.cayenne.modeler.adapters;

import org.apache.cayenne.map.DataMap;
import org.apache.cayenne.modeler.model.CayenneModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class DataDomainAdapter implements AdapterSupport<CayenneModel>
{
    public static final String NAME               = "dataDomainName";
    public static final String VALIDATING_OBJECTS = "dataDomainValidatingObjects";

    private CayenneModel cayenneModel;
    private BeanPathAdapter<CayenneModel> dataDomainAdapter;
    private ObservableList<DataMapAdapter> dataMapAdapters = FXCollections.emptyObservableList();

    public DataDomainAdapter(CayenneModel cayenneModel)
    {
        this.cayenneModel      = cayenneModel;
        this.dataDomainAdapter = new BeanPathAdapter<CayenneModel>(cayenneModel);
    }

    @Override
    public BeanPathAdapter<CayenneModel> getBeanPathAdapter()
    {
        return dataDomainAdapter;
    }

    public ObservableList<DataMapAdapter> getDataMapAdapters()
    {
        if (dataMapAdapters.size() != cayenneModel.getDataMaps().size())
        {
            for (DataMap dataMap : cayenneModel.getDataMaps())
            {
                dataMapAdapters.add(new DataMapAdapter(dataMap));
            }
        }

        return null;
    }
}
