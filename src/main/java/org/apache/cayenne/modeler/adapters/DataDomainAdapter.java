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

import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.modeler.project.CayenneProject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class DataDomainAdapter extends CayennePropertyAdapter // implements AdapterSupport<CayenneProject>
{
    private static final String DATA_DOMAIN_NAME   = "dataDomainName";
    private static final String VALIDATING_OBJECTS = "dataDomainValidatingObjects";
    private static final String OBJECT_CACHE_SIZE  = "sizeOfObjectCache";

    private final CayenneProject cayenneProject;

    private final List<DataMapAdapter> dataMapAdapters = new ArrayList<>(); //FXCollections.emptyObservableList();
    private final List<DataNodeAdapter> dataNodeAdapters = new ArrayList<>(); //FXCollections.emptyObservableList();

    private StringProperty  nameProperty;
    private BooleanProperty validatingObjectsProperty;

    private IntegerProperty sizeOfObjectCache;
    private BooleanProperty useSharedCache;
    private BooleanProperty remoteChangeNotifications;

    public DataDomainAdapter(final CayenneProject cayenneProject)
    {
        this.cayenneProject = cayenneProject;

        cayenneProject.getDataMaps().stream().forEach(dataMap -> dataMapAdapters.add(new DataMapAdapter(dataMap)));
        cayenneProject.getDataNodes().stream().forEach(dataNode -> dataNodeAdapters.add(new DataNodeAdapter(dataNode)));

        try
        {
            nameProperty              = JavaBeanStringPropertyBuilder.create().bean(cayenneProject).name(DATA_DOMAIN_NAME).build();
            validatingObjectsProperty = JavaBeanBooleanPropertyBuilder.create().bean(cayenneProject).name(VALIDATING_OBJECTS).build();
            sizeOfObjectCache         = JavaBeanIntegerPropertyBuilder.create().bean(cayenneProject).name(OBJECT_CACHE_SIZE).build();
        }
        catch (final NoSuchMethodException e)
        {
            throw new RuntimeException("Fix the builder.");
        }
    }

    public StringProperty nameProperty() { return nameProperty; }
    public String getName() { return nameProperty.get(); }
    public void setName(final String value) { nameProperty.set(value); }

    public BooleanProperty validatingObjectsProperty() { return validatingObjectsProperty; }
    public Boolean getValidatingObjectsProperty() { return validatingObjectsProperty.get(); }
    public void setValidatingObjectsProperty(final Boolean value) { validatingObjectsProperty.set(value); }

    public IntegerProperty sizeOfObjectCacheProperty() { return sizeOfObjectCache; }
    public Integer getSizeOfObjectCache() { return sizeOfObjectCache.get(); }
    public void setSizeOfObjectCache(final Integer value) { sizeOfObjectCache.set(value); }

    public List<DataMapAdapter> getDataMapAdapters()
    {
        return dataMapAdapters;
    }

    public List<DataNodeAdapter> getDataNodeAdapters()
    {
        return dataNodeAdapters;
    }
}
