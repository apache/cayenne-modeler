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

import org.apache.cayenne.modeler.project.CayenneProject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public abstract class CayennePropertyAdapter
{
    private static final Log LOGGER = LogFactory.getLog(CayennePropertyAdapter.class);

    public BooleanProperty bindBoolean(String property) throws NoSuchMethodException
    {
        BooleanProperty boundProperty = JavaBeanBooleanPropertyBuilder.create().bean(getWrappedObject()).name(property).build();

        boundProperty.addListener((observable, newValue, oldValue) -> markProjectDirty());

        return boundProperty;
    }

    public IntegerProperty bindInteger(String property) throws NoSuchMethodException
    {
        IntegerProperty boundProperty = JavaBeanIntegerPropertyBuilder.create().bean(getWrappedObject()).name(property).build();

        boundProperty.addListener((observable, newValue, oldValue) -> markProjectDirty());

        return boundProperty;
    }

    public StringProperty bindString(String property) throws NoSuchMethodException
    {
        StringProperty boundProperty = JavaBeanStringPropertyBuilder.create().bean(getWrappedObject()).name(property).build();

        boundProperty.addListener((observable, newValue, oldValue) -> markProjectDirty());

        return boundProperty;
    }

    private void markProjectDirty()
    {
        getCayennePropject().setDirty(true);
    }

    public abstract CayenneProject getCayennePropject();
    public abstract Object getWrappedObject();
}
