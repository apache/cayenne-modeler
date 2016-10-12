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

package org.apache.cayenne.modeler.project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import org.apache.cayenne.access.DataDomain;
import org.apache.cayenne.access.DataRowStore;
import org.apache.cayenne.configuration.DataChannelDescriptor;
import org.apache.cayenne.configuration.DataNodeDescriptor;
import org.apache.cayenne.map.DataMap;
import org.apache.cayenne.modeler.CayenneModeler;
import org.apache.cayenne.modeler.adapters.DataDomainAdapter;
import org.apache.cayenne.project.Project;
import org.apache.cayenne.project.ProjectLoader;
import org.apache.cayenne.project.upgrade.ProjectUpgrader;
import org.apache.cayenne.project.upgrade.UpgradeHandler;
import org.apache.cayenne.project.upgrade.UpgradeMetaData;
import org.apache.cayenne.project.upgrade.UpgradeType;
import org.apache.cayenne.resource.Resource;
import org.apache.cayenne.resource.URLResource;
import org.apache.cayenne.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CayenneProject
{
    private static final Log LOGGER = LogFactory.getLog(CayenneProject.class);

    private final String path;
    private final DataDomainAdapter dataDomainAdapter;

    private final BooleanProperty dirtyProperty = new SimpleBooleanProperty(false);

    public BooleanProperty dirtyProperty() { return dirtyProperty; };
    public boolean isDirty() { return dirtyProperty.get(); }
    public void setDirty(boolean value) { dirtyProperty.set(value); }

    public String getPath()
    {
        return path;
    }

//    public void setPath(String path)
//    {
//        this.path = path;
//    }

    private Project project;
    private DataChannelDescriptor root;

    // TODO: Handle this exception.
    public CayenneProject(final String path) throws MalformedURLException
    {
//        this.path = path;
//        URL url = CayenneModeler.class.getResource(path);
        final URL url = new File(path).toURI().toURL();
        final Resource rootSource = new URLResource(url);
        final ProjectUpgrader upgrader = CayenneModeler.getInjector().getInstance(ProjectUpgrader.class);
        final UpgradeHandler handler = upgrader.getUpgradeHandler(rootSource);
        final UpgradeMetaData md = handler.getUpgradeMetaData();

        this.path = url.getPath();

        // FIXME: These should be handled better.
        if (UpgradeType.DOWNGRADE_NEEDED == md.getUpgradeType())
        {
            LOGGER.fatal("Can't open project - it was created using a newer version of Cayenne Modeler");
        }
        else if (UpgradeType.INTERMEDIATE_UPGRADE_NEEDED == md.getUpgradeType())
        {
            LOGGER.fatal("Can't open project - it was created using an older version of Cayenne Modeler");
        }
        else if (UpgradeType.UPGRADE_NEEDED == md.getUpgradeType())
        {
            LOGGER.fatal("Can't open project - it was created using an older version of Cayenne Modeler");
        }
        else
        {
            // openProjectResourse(rootSource, controller);
            project = openProjectResourse(rootSource);
            root    = (DataChannelDescriptor) project.getRootNode();

//            System.out.println(root.getName());
//
//            for (DataNodeDescriptor dataNodeDescriptor : root.getNodeDescriptors())
//                System.out.println("Node: " + dataNodeDescriptor.getName());
//
//            for (DataMap dataMap : root.getDataMaps())
//                for (DbEntity dbEntity : dataMap.getDbEntities())
//                    System.out.println("DbEntity: " + dbEntity.getName());
        }

        dataDomainAdapter = new DataDomainAdapter(this);
    }

    // private Project openProjectResourse(Resource resource, CayenneModelerController controller)
    private Project openProjectResourse(final Resource resource)
    {
        final Project project = CayenneModeler.getInjector().getInstance(ProjectLoader.class).loadProject(resource);

        // controller.projectOpenedAction(project);

        return project;
    }

    public DataChannelDescriptor getDataDomain()
    {
        return root;
    }

    public String getDataDomainName()
    {
        return root.getName();
    }

    public void setDataDomainName(final String name)
    {
        root.setName(name);
    }

    public boolean isDataDomainValidatingObjects()
    {
        return getDomainBooleanProperty(DataDomain.VALIDATING_OBJECTS_ON_COMMIT_PROPERTY, DataDomain.VALIDATING_OBJECTS_ON_COMMIT_DEFAULT);
    }

    public void setDataDomainValidatingObjects(final boolean validatingObjects)
    {
        setDomainBooleanProperty(DataDomain.VALIDATING_OBJECTS_ON_COMMIT_PROPERTY, validatingObjects, DataDomain.VALIDATING_OBJECTS_ON_COMMIT_DEFAULT);
    }

    public Integer getSizeOfObjectCache()
    {
        return getDomainIntegerProperty(DataRowStore.SNAPSHOT_CACHE_SIZE_PROPERTY, DataRowStore.SNAPSHOT_CACHE_SIZE_DEFAULT);
    }

    public void setSizeOfObjectCache(Integer cacheSize)
    {
        setDomainStringProperty(DataRowStore.SNAPSHOT_CACHE_SIZE_PROPERTY, String.valueOf(cacheSize), DataRowStore.SNAPSHOT_CACHE_SIZE_PROPERTY);
    }

    public boolean isUsingSharedCache()
    {
        return getDomainBooleanProperty(DataDomain.SHARED_CACHE_ENABLED_PROPERTY, DataDomain.SHARED_CACHE_ENABLED_DEFAULT);
    }

    public void setUsingSharedCache(final boolean usingSharedCache)
    {
        setDomainBooleanProperty(DataDomain.SHARED_CACHE_ENABLED_PROPERTY, usingSharedCache, DataDomain.SHARED_CACHE_ENABLED_DEFAULT);
    }

    public boolean isRemoteChangeNotificationsEnabled()
    {
        return getDomainBooleanProperty(DataRowStore.REMOTE_NOTIFICATION_PROPERTY, DataRowStore.REMOTE_NOTIFICATION_DEFAULT);
    }

    public void setRemoteChangeNotificationsEnabled(final boolean remoteChangeNotificationsEnabled)
    {
        setDomainBooleanProperty(DataRowStore.REMOTE_NOTIFICATION_PROPERTY, remoteChangeNotificationsEnabled, DataRowStore.REMOTE_NOTIFICATION_DEFAULT);
    }

    public Collection<DataMap> getDataMaps()
    {
        return root.getDataMaps();
    }

    public Collection<DataNodeDescriptor> getDataNodes()
    {
        return root.getNodeDescriptors();
    }

    /**
     * Helper method that updates domain properties. If a value equals to
     * default, null value is used instead.
     */
    private void setDomainStringProperty(final String property, String value, final String defaultValue)
    {
        if (getDataDomain() == null)
            return;

        // no empty strings
        if ("".equals(value))
            value = null;

        // use NULL for defaults
        if (value != null && value.equals(defaultValue))
            value = null;

        final Map<String, String> properties = getDataDomain().getProperties();

        final Object oldValue = properties.get(property);

        if (!Util.nullSafeEquals(value, oldValue))
        {
            properties.put(property, value);

//            DomainEvent e = new DomainEvent(this, domain);
//            projectController.fireDomainEvent(e);
        }
    }

    private String getDomainProperty(final String property, final String defaultValue)
    {
        if (getDataDomain() == null)
            return null;

        final String value = getDataDomain().getProperties().get(property);
        return value != null ? value : defaultValue;
    }

    private boolean getDomainBooleanProperty(final String property, final boolean defaultValue)
    {
        return "true".equalsIgnoreCase(getDomainProperty(property, Boolean.toString(defaultValue)));
    }

    private void setDomainBooleanProperty(final String property, final boolean value, final boolean defaultValue)
    {
        setDomainStringProperty(property, Boolean.toString(value), Boolean.toString(defaultValue));
    }

    private int getDomainIntegerProperty(final String property, final int defaultValue)
    {
        try
        {
            return Integer.valueOf(getDomainProperty(property, String.valueOf(defaultValue)));
        }
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }

    public DataDomainAdapter getDataDomainAdapter()
    {
        return dataDomainAdapter;
    }
}
