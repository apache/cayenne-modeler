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

import java.util.List;

import org.apache.cayenne.map.DataMap;
import org.apache.cayenne.map.DbEntity;
import org.apache.cayenne.map.ObjEntity;
import org.apache.commons.lang3.ObjectUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataMapAdapter extends CayennePropertyAdapter // implements AdapterSupport<DataMap>
{
    private final DataMap dataMap;
//    private BeanPathAdapter<DataMap> dataMapAdapter;

//    private final List<ObjectEntityAdapter>   objectEntityAdapters   = new ArrayList<>();
//    private final List<DatabaseEntityAdapter> databaseEntityAdapters = new ArrayList<>();
    private final ObservableList<ObjectEntityAdapter>   objectEntityAdapters   = FXCollections.observableArrayList();
    private final ObservableList<DatabaseEntityAdapter> databaseEntityAdapters = FXCollections.observableArrayList();

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

    public DataMapAdapter(final DataMap dataMap)
    {
        this.dataMap = dataMap;

        try
        {
            nameProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name("name").build();
//            locationProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name("map").build();

            // TODO: Fix Cayenne?  The DEFAULT_QUOTE_SQL_IDENTIFIERS_PROPERTY constant is incorrect.
            quoteSQLIdentifiersProperty = JavaBeanBooleanPropertyBuilder.create().bean(dataMap).name("quotingSQLIdentifiers").build();

            defaultCatalogProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_CATALOG_PROPERTY).build();
            defaultSchemaProperty  = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_SCHEMA_PROPERTY).build();
            defaultPackageProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_PACKAGE_PROPERTY).build();

            defaultSuperclassProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_SUPERCLASS_PROPERTY).build();
            defaultLockTypeProperty   = JavaBeanIntegerPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_LOCK_TYPE_PROPERTY).build();

            clientSupportedProperty         = JavaBeanBooleanPropertyBuilder.create().bean(dataMap).name(DataMap.CLIENT_SUPPORTED_PROPERTY).build();
            defaultClientPackageProperty    = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_CLIENT_PACKAGE_PROPERTY).build();
            defaultClientSuperclassProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_CLIENT_SUPERCLASS_PROPERTY).build();
        }
        catch (final NoSuchMethodException e)
        {
            throw new RuntimeException("Fix the builder.", e);
        }

        // Create ObjectEntityAdapters for all object entities.
        for (final ObjEntity objEntity : dataMap.getObjEntities())
            objectEntityAdapters.add(new ObjectEntityAdapter(objEntity));

        // Sort the ObjectEntityAdapters (by their name).
        sortObjectEntities();

        // Add change listeners for all ObjectEntityAdapter name changes and automatically re-sort.
        for (final ObjectEntityAdapter objectEntityAdapter : objectEntityAdapters)
            objectEntityAdapter.getNameProperty().addListener((observable, oldValue, newValue) -> sortObjectEntities());

        // Create DatabaseEntityAdapters for all database entities.
        for (final DbEntity dbEntity : dataMap.getDbEntities())
            databaseEntityAdapters.add(new DatabaseEntityAdapter(dbEntity));


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

    public BooleanProperty getQuoteSQLIdentifiersProperty()
    {
        return quoteSQLIdentifiersProperty;
    }

    public StringProperty getDefaultCatalogProperty()
    {
        return defaultCatalogProperty;
    }

    public StringProperty getDefaultSchemaProperty()
    {
        return defaultSchemaProperty;
    }

    public StringProperty getDefaultPackageProperty()
    {
        return defaultPackageProperty;
    }

    public StringProperty getDefaultSuperclassProperty()
    {
        return defaultSuperclassProperty;
    }

    public IntegerProperty getDefaultLockTypeProperty()
    {
        return defaultLockTypeProperty;
    }

    public BooleanProperty getClientSupportedProperty()
    {
        return clientSupportedProperty;
    }

    public StringProperty getDefaultClientPackageProperty()
    {
        return defaultClientPackageProperty;
    }

    public StringProperty getDefaultClientSuperclassProperty()
    {
        return defaultClientSuperclassProperty;
    }

    public List<ObjectEntityAdapter> getObjectEntityAdapters()
    {
        return objectEntityAdapters;
    }

    public List<DatabaseEntityAdapter> getDatabaseEntityAdapters()
    {
        return databaseEntityAdapters;
    }

    public void sortObjectEntities()
    {
        objectEntityAdapters.sort((entity1, entity2) ->
            {
                return ObjectUtils.compare(entity1.getNameProperty().get(), entity2.getNameProperty().get());
            });
    }
}
