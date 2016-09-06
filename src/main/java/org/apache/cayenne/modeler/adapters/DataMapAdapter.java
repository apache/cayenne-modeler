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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class DataMapAdapter // implements AdapterSupport<DataMap>
{
    private final DataMap dataMap;
//    private BeanPathAdapter<DataMap> dataMapAdapter;

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

//            quoteSQLIdentifiersProperty = JavaBeanBooleanPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_QUOTE_SQL_IDENTIFIERS_PROPERTY).build();

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

//    @Override
//    public BeanPathAdapter<DataMap> getBeanPathAdapter()
//    {
//        return dataMapAdapter;
//    }
}
