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

import org.apache.cayenne.configuration.DataNodeDescriptor;

import javafx.beans.property.StringProperty;

public class DataNodeAdapter extends CayennePropertyAdapter
{
    private StringProperty  nameProperty;

    private StringProperty  dataSourceLocationProperty;
    private StringProperty  dataSourceFactoryProperty;
    private StringProperty  schemaUpdateStrategyNameProperty;

    private final DataNodeDescriptor dataNode;

    public DataNodeAdapter(final DataNodeDescriptor dataNode)
    {
        // Must be assigned before property binding.
        this.dataNode = dataNode;

        try
        {
            nameProperty                 = bindString("name");
//            dataSourceLocationProperty       = JavaBeanStringPropertyBuilder.create().bean(dataNode).name("dataSourceLocation").build();
//            dataSourceFactoryProperty        = JavaBeanStringPropertyBuilder.create().bean(dataNode).name("dataSourceFactory").build();
//            schemaUpdateStrategyNameProperty = JavaBeanStringPropertyBuilder.create().bean(dataNode).name("schemaUpdateStrategyName").build();
        }
        catch (final NoSuchMethodException e)
        {
            throw new RuntimeException("Fix the builder.");
        }
    }

    public StringProperty nameProperty() { return nameProperty; }
    public String getName() { return nameProperty.get(); }
    public void setName(final String value) { nameProperty.set(value); }

    @Override
    public Object getWrappedObject()
    {
        return dataNode;
    }
}
