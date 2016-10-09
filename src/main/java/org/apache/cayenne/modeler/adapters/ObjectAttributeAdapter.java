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

import org.apache.cayenne.dba.TypesMapping;
import org.apache.cayenne.map.ObjAttribute;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class ObjectAttributeAdapter extends CayennePropertyAdapter // implements AdapterSupport<DataMap>
{
    private final ObjAttribute objectAttribute;

    private StringProperty nameProperty;
    private StringProperty javaTypeProperty;
    private StringProperty databaseAttributePathProperty;
    private BooleanProperty usedForLockingProperty;

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

    public ObjectAttributeAdapter(final ObjAttribute objectAttribute)
    {
        // Must be assigned before property binding.
        this.objectAttribute = objectAttribute;

        try
        {
            nameProperty                  = bindString("name");
            javaTypeProperty              = bindString("type");
            databaseAttributePathProperty = bindString("dbAttributePath");
            usedForLockingProperty        = bindBoolean("usedForLocking");


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

//        objectAttribute.getDbAttribute().getName();
    }

    public StringProperty nameProperty() { return nameProperty; }
    public String getName() { return nameProperty.get(); }
    public void setName(final String value) { nameProperty.set(value); }

    public StringProperty javaTypeProperty() { return javaTypeProperty; }
    public String getJavaType() { return javaTypeProperty.get(); }
    public void setJavaType(final String value) { javaTypeProperty.set(value); }

    public StringProperty databaseAttributePathProperty() { return databaseAttributePathProperty; }
    public String getDatabaseAttributePath() { return databaseAttributePathProperty.get(); }
    public void setDatabaseAttributePath(final String value) { databaseAttributePathProperty.set(value); }

    public BooleanProperty usedForLockingProperty() { return usedForLockingProperty; }
    public Boolean getUsedForLocking() { return usedForLockingProperty.get(); }
    public void setUsedForLocking(final Boolean value) { usedForLockingProperty.set(value); }

    public String getDatabaseType()
    {
        return TypesMapping.getSqlNameByType(objectAttribute.getDbAttribute().getType());
    }
    /**
     * @return The underlying ObjAttribute fronted by this property adapter.
     */
    public ObjAttribute getObjAttribute()
    {
        return objectAttribute;
    }

    @Override
    public Object getWrappedObject()
    {
        return objectAttribute;
    }
}
