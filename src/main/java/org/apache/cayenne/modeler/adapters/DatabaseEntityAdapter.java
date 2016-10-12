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
import org.apache.cayenne.modeler.project.CayenneProject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class DatabaseEntityAdapter extends CayennePropertyAdapter // implements AdapterSupport<DataMap>
{
    private static final String DATAASE_ENTITY_NAME = "name";

    private final DbEntity databaseEntity;
//    private BeanPathAdapter<DataMap> dataMapAdapter;

    private DataMapAdapter dataMapAdapter;

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
        // Must be assigned before property binding.
        this.databaseEntity = databaseEntity;

        try
        {
            nameProperty = bindString(DATAASE_ENTITY_NAME);
        }
        catch (final NoSuchMethodException e)
        {
            throw new RuntimeException("Fix the builder.", e);
        }
    }

    public DataMapAdapter getDataMapAdapter()
    {
        return dataMapAdapter;
    }

    public void setDataMapAdapter(DataMapAdapter dataMapAdapter)
    {
        this.dataMapAdapter = dataMapAdapter;
    }

    public StringProperty nameProperty() { return nameProperty; }
    public String getName() { return nameProperty.get(); }
    public void setName(final String value) { nameProperty.set(value); }

    public StringProperty locationProperty() { return locationProperty; }
    public String getLocationProperty() { return locationProperty.get(); }
    public void setLocationProperty(final String value) { locationProperty.set(value); }

    @Override
    public CayenneProject getCayennePropject()
    {
        return dataMapAdapter.getCayennePropject();
    }

    @Override
    public Object getWrappedObject()
    {
        return databaseEntity;
    }
}
