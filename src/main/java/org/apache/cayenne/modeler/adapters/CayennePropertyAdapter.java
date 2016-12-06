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
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

/**
 * Provides a common mechanism for creating property adapters. Property adapters
 * map existing Java/Beans classes to JavaFX properties which can be used in
 * bi-directional bindings within the UI.
 */
public abstract class CayennePropertyAdapter
{
    private static final Log LOGGER = LogFactory.getLog(CayennePropertyAdapter.class);

    /**
     * Binds a Java/Bean String property to a JavaFX property. Note: The JavaFX
     * property observes changes and marks the Cayenne project dirty when a
     * change occurs.
     *
     * @param property
     *            The Java/Bean property to bind to.
     * @return A new JavaFX property bound to the Java/Bean property.
     * @throws NoSuchMethodException
     *             If the specified Java/Bean property does not exist (check the
     *             property's spelling).
     */
    public BooleanProperty bindBoolean(String property) throws NoSuchMethodException
    {
        return observePropertyChanges(JavaBeanBooleanPropertyBuilder.create().bean(getWrappedObject()).name(property).build());
    }

    /**
     * Binds a Java/Bean String property to a JavaFX property. Note: The JavaFX
     * property observes changes and marks the Cayenne project dirty when a
     * change occurs.
     *
     * @param property
     *            The Java/Bean property to bind to.
     * @return A new JavaFX property bound to the Java/Bean property.
     * @throws NoSuchMethodException
     *             If the specified Java/Bean property does not exist (check the
     *             property's spelling).
     */
    public IntegerProperty bindInteger(String property) throws NoSuchMethodException
    {
        return observePropertyChanges(JavaBeanIntegerPropertyBuilder.create().bean(getWrappedObject()).name(property).build());
    }

    /**
     * Binds a Java/Bean String property to a JavaFX property. Note: The JavaFX
     * property observes changes and marks the Cayenne project dirty when a
     * change occurs.
     *
     * @param property
     *            The Java/Bean property to bind to.
     * @return A new JavaFX property bound to the Java/Bean property.
     * @throws NoSuchMethodException
     *             If the specified Java/Bean property does not exist (check the
     *             property's spelling).
     */
    public StringProperty bindString(String property) throws NoSuchMethodException
    {
        return observePropertyChanges(JavaBeanStringPropertyBuilder.create().bean(getWrappedObject()).name(property).build());
    }

    /**
     * Observes changes to the given property and marks the project dirty
     * (edited) when the property is changed.
     *
     * @param property
     *            The property to observe changes.
     * @return The observed property.
     */
    private <T extends Property<?>> T observePropertyChanges(T property)
    {
        property.addListener((observable, newValue, oldValue) -> getCayennePropject().setDirty(true));

        return property;
    }

    /**
     * @return The Cayenne project this property adapter is attached to.
     */
    public abstract CayenneProject getCayennePropject();

    /**
     * @return The Cayenne Java/Bean object this property adapter wraps.
     */
    public abstract Object getWrappedObject();
}
