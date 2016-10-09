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

public class DataDomainAdapter extends CayennePropertyAdapter // implements AdapterSupport<CayenneProject>
{
    private static final String DATA_DOMAIN_NAME            = "dataDomainName";
    private static final String VALIDATING_OBJECTS          = "dataDomainValidatingObjects";
    private static final String OBJECT_CACHE_SIZE           = "sizeOfObjectCache";
    private static final String USE_SHARED_CACHE            = "usingSharedCache";
    private static final String REMOTE_CHANGE_NOTIFICATIONS = "remoteChangeNotificationsEnabled";

    private final CayenneProject cayenneProject;

    private final List<DataMapAdapter> dataMapAdapters = new ArrayList<>(); //FXCollections.emptyObservableList();
    private final List<DataNodeAdapter> dataNodeAdapters = new ArrayList<>(); //FXCollections.emptyObservableList();

    private StringProperty  nameProperty;
    private BooleanProperty validatingObjectsProperty;

    private IntegerProperty sizeOfObjectCacheProperty;
    private BooleanProperty useSharedCacheProperty;
    private BooleanProperty remoteChangeNotificationsProperty;

    public DataDomainAdapter(final CayenneProject cayenneProject)
    {
        // Must be assigned before property binding.
        this.cayenneProject = cayenneProject;

        cayenneProject.getDataMaps().stream().forEach(dataMap -> dataMapAdapters.add(new DataMapAdapter(dataMap)));
        cayenneProject.getDataNodes().stream().forEach(dataNode -> dataNodeAdapters.add(new DataNodeAdapter(dataNode)));

        try
        {
            nameProperty                      = bindString(DATA_DOMAIN_NAME);
            validatingObjectsProperty         = bindBoolean(VALIDATING_OBJECTS);
            sizeOfObjectCacheProperty         = bindInteger(OBJECT_CACHE_SIZE);
            useSharedCacheProperty            = bindBoolean(USE_SHARED_CACHE);
            remoteChangeNotificationsProperty = bindBoolean(REMOTE_CHANGE_NOTIFICATIONS);
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
    public Boolean getValidatingObjects() { return validatingObjectsProperty.get(); }
    public void setValidatingObjects(final Boolean value) { validatingObjectsProperty.set(value); }

    public IntegerProperty sizeOfObjectCacheProperty() { return sizeOfObjectCacheProperty; }
    public Integer getSizeOfObjectCache() { return sizeOfObjectCacheProperty.get(); }
    public void setSizeOfObjectCache(final Integer value) { sizeOfObjectCacheProperty.set(value); }

    public BooleanProperty useSharedCacheProperty() { return useSharedCacheProperty; }
    public Boolean getUseSharedCache() { return useSharedCacheProperty.get(); }
    public void setUseSharedCache(final Boolean value) { useSharedCacheProperty.set(value); }

    public BooleanProperty remoteChangeNotificationsProperty() { return remoteChangeNotificationsProperty; }
    public Boolean getRemoteChangeNotifications() { return remoteChangeNotificationsProperty.get(); }
    public void setRemoteChangeNotifications(final Boolean value) { remoteChangeNotificationsProperty.set(value); }

    public List<DataMapAdapter> getDataMapAdapters()
    {
        return dataMapAdapters;
    }

    public List<DataNodeAdapter> getDataNodeAdapters()
    {
        return dataNodeAdapters;
    }

    @Override
    public Object getWrappedObject()
    {
        return cayenneProject;
    }
}
