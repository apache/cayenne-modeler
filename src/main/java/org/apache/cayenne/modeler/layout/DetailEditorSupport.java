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

import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.modeler.adapters.CayennePropertyAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DetailEditorSupport<T extends CayennePropertyAdapter>
{
    static final Logger LOGGER = LoggerFactory.getLogger(DetailEditorSupport.class);

    default void showEditor(final T propertyAdapter)
    {
        setPropertyAdapter(propertyAdapter);
        initializeBindings();
        beginEditing();
    }

    default void initializeBindings()
    {
        // Implementors should override.
    }

    default List<Binding<?>> getBindings()
    {
        return new ArrayList<>();
    }

    default void beginEditing()
    {
        LOGGER.debug("begin editing " + this);

        bind(getBindings());
    }

    default void endEditing()
    {
        LOGGER.debug("end editing " + this);

        unbind(getBindings());
    }

    default void bind(List<Binding<?>> bindings)
    {
        bindings.stream().forEach(binding -> binding.bind());
    }

    default void unbind(List<Binding<?>> bindings)
    {
        bindings.stream().forEach(binding -> binding.unbind());
    }

    void setPropertyAdapter(T propertyAdapter);
}
