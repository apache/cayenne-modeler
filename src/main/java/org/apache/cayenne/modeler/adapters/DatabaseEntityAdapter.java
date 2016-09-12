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

import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.map.DbEntity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class DatabaseEntityAdapter extends CayennePropertyAdapter // implements AdapterSupport<DataMap>
{
    private final DbEntity databaseEntity;
//    private BeanPathAdapter<DataMap> dataMapAdapter;

    private final List<ObjectEntityAdapter> objectEntityAdapters = new ArrayList<>();

    private StringProperty nameProperty;

    private StringProperty locationProperty;

    private BooleanProperty quoteSQLIdentifiersProperty;

    private StringProperty defaultCatalogProperty;
    private StringProperty defaultSchemaProperty;
    private StringProperty defaultPackageProperty;

    private StringProperty  defaultSuperclassProperty;
    private IntegerProperty defaultLockTypeProperty;

    private BooleanProperty clientSupportedProperty;
    private StringProperty  defaultClientPackageProperty;
    private StringProperty  defaultClientSuperclassProperty;

    public DatabaseEntityAdapter(final DbEntity databaseEntity)
    {
        this.databaseEntity = databaseEntity;

        try
        {
            nameProperty = JavaBeanStringPropertyBuilder.create().bean(databaseEntity).name("name").build();
        }
        catch (final NoSuchMethodException e)
        {
            throw new RuntimeException("Fix the builder.", e);
        }

//        this.dataMapAdapter = new BeanPathAdapter<DataMap>(dataMap);
    }

    public StringProperty getNameProperty()
    {
        return nameProperty;
    }

    public StringProperty getLocationProperty()
    {
        return locationProperty;
    }
}
