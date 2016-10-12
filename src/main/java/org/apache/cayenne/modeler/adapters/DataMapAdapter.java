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
import org.apache.cayenne.modeler.project.CayenneProject;
import org.apache.commons.lang3.ObjectUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataMapAdapter extends CayennePropertyAdapter // implements AdapterSupport<DataMap>
{
    private static final String DATA_MAP_NAME         = "name";
    private static final String QUOTE_SQL_IDENTIFIERS = "quotingSQLIdentifiers";

    private final DataMap dataMap;

    private final ObservableList<ObjectEntityAdapter>   objectEntityAdapters   = FXCollections.observableArrayList();
    private final ObservableList<DatabaseEntityAdapter> databaseEntityAdapters = FXCollections.observableArrayList();

    private DataDomainAdapter dataDomainAdapter;

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
        // Must be assigned before property binding.
        this.dataMap = dataMap;

        try
        {
            nameProperty = bindString(DATA_MAP_NAME);
//            locationProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name("map").build();

            // TODO: Fix Cayenne?  The DEFAULT_QUOTE_SQL_IDENTIFIERS_PROPERTY constant is incorrect.
            quoteSQLIdentifiersProperty = bindBoolean(QUOTE_SQL_IDENTIFIERS);

            defaultCatalogProperty = bindString(DataMap.DEFAULT_CATALOG_PROPERTY);
            defaultSchemaProperty  = bindString(DataMap.DEFAULT_SCHEMA_PROPERTY);
            defaultPackageProperty = bindString(DataMap.DEFAULT_PACKAGE_PROPERTY);

            defaultSuperclassProperty = bindString(DataMap.DEFAULT_SUPERCLASS_PROPERTY);
            defaultLockTypeProperty   = bindInteger(DataMap.DEFAULT_LOCK_TYPE_PROPERTY);

            clientSupportedProperty         = bindBoolean(DataMap.CLIENT_SUPPORTED_PROPERTY);
            defaultClientPackageProperty    = bindString(DataMap.DEFAULT_CLIENT_PACKAGE_PROPERTY);
            defaultClientSuperclassProperty = bindString(DataMap.DEFAULT_CLIENT_SUPERCLASS_PROPERTY);
        }
        catch (final NoSuchMethodException e)
        {
            throw new RuntimeException("Fix the builder.", e);
        }

        // Create ObjectEntityAdapters for all object entities.
        dataMap.getObjEntities().stream().forEach(objEntity -> objectEntityAdapters.add(new ObjectEntityAdapter(objEntity)));

        objectEntityAdapters.stream().forEach(objectEntityAdapter -> objectEntityAdapter.setDataMapAdapter(this));

        // Sort the ObjectEntityAdapters (by their name).
        sortObjectEntities();

        // Add change listeners for all ObjectEntityAdapter name changes and automatically re-sort.
        objectEntityAdapters.stream().forEach(objectEntityAdapter ->
            objectEntityAdapter.nameProperty().addListener((observable, oldValue, newValue) -> sortObjectEntities()));

        // Create DatabaseEntityAdapters for all database entities.
        dataMap.getDbEntities().stream().forEach(dbEntity -> databaseEntityAdapters.add(new DatabaseEntityAdapter(dbEntity)));

        databaseEntityAdapters.stream().forEach(databaseEntityAdapter -> databaseEntityAdapter.setDataMapAdapter(this));
    }

    public DataDomainAdapter getDataDomainAdapter()
    {
        return dataDomainAdapter;
    }

    public void setDataDomainAdapter(DataDomainAdapter dataDomainAdapter)
    {
        this.dataDomainAdapter = dataDomainAdapter;
    }

    public StringProperty nameProperty() { return nameProperty; }
    public String getName() { return nameProperty.get(); }
    public void setName(final String value) { nameProperty.set(value); }

    public StringProperty locationProperty() { return locationProperty; }
    public String getLocationProperty() { return locationProperty.get(); }
    public void setLocationProperty(final String value) { locationProperty.set(value); }

    public BooleanProperty quoteSQLIdentifiersProperty() { return quoteSQLIdentifiersProperty; }
    public Boolean getQuoteSQLIdentifiersProperty() { return quoteSQLIdentifiersProperty.get(); }
    public void setQuoteSQLIdentifiersProperty(final Boolean value) { quoteSQLIdentifiersProperty.set(value); }

    public StringProperty defaultCatalogProperty() { return defaultCatalogProperty; }
    public String getDefaultCatalogProperty() { return defaultCatalogProperty.get(); }
    public void setDefaultCatalogProperty(final String value) { defaultCatalogProperty.set(value); }

    public StringProperty defaultSchemaProperty() { return defaultSchemaProperty; }
    public String getDefaultSchemaProperty() { return defaultSchemaProperty.get(); }
    public void setDefaultSchemaProperty(final String value) { defaultSchemaProperty.set(value); }

    public StringProperty defaultPackageProperty() { return defaultPackageProperty; }
    public String getDefaultPackageProperty() { return defaultPackageProperty.get(); }
    public void setDefaultPackageProperty(final String value) { defaultPackageProperty.set(value); }

    public StringProperty defaultSuperclassProperty() { return defaultSuperclassProperty; }
    public String getDefaultSuperclassProperty() { return defaultSuperclassProperty.get(); }
    public void setDefaultSuperclassProperty(final String value) { defaultSuperclassProperty.set(value); }

    public IntegerProperty defaultLockTypeProperty() { return defaultLockTypeProperty; }
    public Integer getDefaultLockTypeProperty() { return defaultLockTypeProperty.get(); }
    public void setDefaultLockTypeProperty(final Integer value) { defaultLockTypeProperty.set(value); }

    public BooleanProperty clientSupportedProperty() { return clientSupportedProperty; }
    public Boolean getClientSupportedProperty() { return clientSupportedProperty.get(); }
    public void setClientSupportedProperty(final Boolean value) { clientSupportedProperty.set(value); }

    public StringProperty defaultClientPackageProperty() { return defaultClientPackageProperty; }
    public String getDefaultClientPackageProperty() { return defaultClientPackageProperty.get(); }
    public void setDefaultClientPackageProperty(final String value) { defaultClientPackageProperty.set(value); }

    public StringProperty defaultClientSuperclassProperty() { return defaultClientSuperclassProperty; }
    public String getDefaultClientSuperclassProperty() { return defaultClientSuperclassProperty.get(); }
    public void setDefaultClientSuperclassProperty(final String value) { defaultClientSuperclassProperty.set(value); }

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
                return ObjectUtils.compare(entity1.nameProperty().get(), entity2.nameProperty().get());
            });
    }

    @Override
    public Object getWrappedObject()
    {
        return dataMap;
    }

    @Override
    public CayenneProject getCayennePropject()
    {
        return dataDomainAdapter.getCayennePropject();
    }
}
