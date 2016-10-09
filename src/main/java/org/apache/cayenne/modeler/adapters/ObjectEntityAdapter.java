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

import org.apache.cayenne.map.ObjEntity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObjectEntityAdapter extends CayennePropertyAdapter // implements AdapterSupport<DataMap>
{
    private final ObjEntity objectEntity;

    private final ObservableList<ObjectAttributeAdapter> objectAttributeAdapters = FXCollections.observableArrayList();

    private StringProperty nameProperty;
    private BooleanProperty abstractClassProperty;

//    private StringProperty locationProperty;
//
//    private BooleanProperty quoteSQLIdentifiersProperty;
//
//    private StringProperty defaultCatalogProperty;
//    private StringProperty defaultSchemaProperty;
//    private StringProperty defaultPackageProperty;
//
//    private StringProperty  defaultSuperclassProperty;
//    private IntegerProperty defaultLockTypeProperty;
//
//    private BooleanProperty clientSupportedProperty;
//    private StringProperty  defaultClientPackageProperty;
//    private StringProperty  defaultClientSuperclassProperty;

    public ObjectEntityAdapter(final ObjEntity objectEntity)
    {
        this.objectEntity = objectEntity;

        try
        {
            nameProperty          = bindString(objectEntity, "name");
            abstractClassProperty = bindBoolean(objectEntity, "abstract");


//            locationProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name("map").build();

//            quoteSQLIdentifiersProperty = JavaBeanBooleanPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_QUOTE_SQL_IDENTIFIERS_PROPERTY).build();

//            defaultCatalogProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_CATALOG_PROPERTY).build();
//            defaultSchemaProperty  = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_SCHEMA_PROPERTY).build();
//            defaultPackageProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_PACKAGE_PROPERTY).build();
//
//            defaultSuperclassProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_SUPERCLASS_PROPERTY).build();
//            defaultLockTypeProperty   = JavaBeanIntegerPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_LOCK_TYPE_PROPERTY).build();
//
//            clientSupportedProperty         = JavaBeanBooleanPropertyBuilder.create().bean(dataMap).name(DataMap.CLIENT_SUPPORTED_PROPERTY).build();
//            defaultClientPackageProperty    = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_CLIENT_PACKAGE_PROPERTY).build();
//            defaultClientSuperclassProperty = JavaBeanStringPropertyBuilder.create().bean(dataMap).name(DataMap.DEFAULT_CLIENT_SUPERCLASS_PROPERTY).build();
        }
        catch (final NoSuchMethodException e)
        {
            throw new RuntimeException("Fix the builder.", e);
        }

        objectEntity.getAttributes().stream().forEach(objAttribute -> objectAttributeAdapters.add(new ObjectAttributeAdapter(objAttribute)));
    }

    public StringProperty nameProperty() { return nameProperty; }
    public String getName() { return nameProperty.get(); }
    public void setName(final String value) { nameProperty.set(value); }

    public BooleanProperty abstractClassProperty() { return abstractClassProperty; }
    public Boolean getAbstractClass() { return abstractClassProperty.get(); }
    public void setAbstractClass(final Boolean value) { abstractClassProperty.set(value); }

    public ObservableList<ObjectAttributeAdapter> getAttributes()
    {
        return objectAttributeAdapters;
    }
}
