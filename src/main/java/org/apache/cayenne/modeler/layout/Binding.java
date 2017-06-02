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

package org.apache.cayenne.modeler.layout;

import javafx.beans.property.Property;

/**
 * Associates a binding between a Property in the UI and a Property in the model.
 *
 * @param <T>
 */
public class Binding<T>
{
    private Property<T> uiProperty, modelProperty;

    public Binding(Property<T> uiProperty, Property<T> modelProperty)
    {
        this.uiProperty    = uiProperty;
        this.modelProperty = modelProperty;
    }

    /**
     * Bi-directionally binds the UI Property to the model Property.
     */
    public void bind()
    {
        uiProperty.bindBidirectional(modelProperty);
    }

    /**
     * Bi-directionally unbinds the UI Property from the model Property.
     */
    public void unbind()
    {
        uiProperty.unbindBidirectional(modelProperty);
    }
}
