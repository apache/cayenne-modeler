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
import org.apache.cayenne.event.JMSBridge;
import org.apache.cayenne.event.JavaGroupsBridge;
import org.apache.cayenne.map.DataMap;
import org.apache.cayenne.modeler.adapters.DataDomainAdapter;
import org.apache.cayenne.modeler.di.Injection;
import org.apache.cayenne.project.Project;
import org.apache.cayenne.project.ProjectLoader;
import org.apache.cayenne.project.upgrade.ProjectUpgrader;
import org.apache.cayenne.project.upgrade.UpgradeHandler;
import org.apache.cayenne.project.upgrade.UpgradeMetaData;
import org.apache.cayenne.project.upgrade.UpgradeType;
import org.apache.cayenne.resource.Resource;
import org.apache.cayenne.resource.URLResource;
import org.apache.cayenne.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CayenneProject
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CayenneProject.class);

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
    public CayenneProject(String path) throws MalformedURLException
    {
//        this.path = path;
//        URL url = CayenneModeler.class.getResource(path);
        URL url = new File(path).toURI().toURL();
        Resource rootSource = new URLResource(url);
        ProjectUpgrader upgrader = Injection.getInjector().getInstance(ProjectUpgrader.class);
        UpgradeHandler handler = upgrader.getUpgradeHandler(rootSource);
        UpgradeMetaData md = handler.getUpgradeMetaData();

        this.path = url.getPath();

        // FIXME: These should be handled better.
        if (UpgradeType.DOWNGRADE_NEEDED == md.getUpgradeType())
        {
            LOGGER.error("Can't open project - it was created using a newer version of Cayenne Modeler");
        }
        else if (UpgradeType.INTERMEDIATE_UPGRADE_NEEDED == md.getUpgradeType())
        {
            LOGGER.error("Can't open project - it was created using an older version of Cayenne Modeler");
        }
        else if (UpgradeType.UPGRADE_NEEDED == md.getUpgradeType())
        {
            LOGGER.error("Can't open project - it was created using an older version of Cayenne Modeler");
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
    private Project openProjectResourse(Resource resource)
    {
        Project project = Injection.getInjector().getInstance(ProjectLoader.class).loadProject(resource);

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

    public void setDataDomainName(String name)
    {
        root.setName(name);
    }

    public boolean isDataDomainValidatingObjects()
    {
        return getDomainBooleanProperty(DataDomain.VALIDATING_OBJECTS_ON_COMMIT_PROPERTY, DataDomain.VALIDATING_OBJECTS_ON_COMMIT_DEFAULT);
    }

    public void setDataDomainValidatingObjects(boolean validatingObjects)
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

    public void setUsingSharedCache(boolean usingSharedCache)
    {
        setDomainBooleanProperty(DataDomain.SHARED_CACHE_ENABLED_PROPERTY, usingSharedCache, DataDomain.SHARED_CACHE_ENABLED_DEFAULT);
    }

    public boolean isRemoteChangeNotificationsEnabled()
    {
        return getDomainBooleanProperty(DataRowStore.REMOTE_NOTIFICATION_PROPERTY, DataRowStore.REMOTE_NOTIFICATION_DEFAULT);
    }

    public void setRemoteChangeNotificationsEnabled(boolean remoteChangeNotificationsEnabled)
    {
        setDomainBooleanProperty(DataRowStore.REMOTE_NOTIFICATION_PROPERTY, remoteChangeNotificationsEnabled, DataRowStore.REMOTE_NOTIFICATION_DEFAULT);
    }

    public String getEventBridgeFactory()
    {
        return getDomainProperty(DataRowStore.EVENT_BRIDGE_FACTORY_PROPERTY, DataRowStore.EVENT_BRIDGE_FACTORY_DEFAULT);
    }

    public void setEventBridgeFactory(String eventBridgeFactory)
    {
        setDomainStringProperty(DataRowStore.EVENT_BRIDGE_FACTORY_PROPERTY, eventBridgeFactory, DataRowStore.EVENT_BRIDGE_FACTORY_DEFAULT);
    }

    public String getJavaGroupsMulticastAddress()
    {
        return getDomainProperty(JavaGroupsBridge.MCAST_ADDRESS_PROPERTY, JavaGroupsBridge.MCAST_ADDRESS_DEFAULT);
    }

    public void setJavaGroupsMulticastAddress(String multicastAddress)
    {
        setDomainStringProperty(JavaGroupsBridge.MCAST_ADDRESS_PROPERTY, multicastAddress, JavaGroupsBridge.MCAST_ADDRESS_DEFAULT);
    }

    public String getJavaGroupsMulticastPort()
    {
        return getDomainProperty(JavaGroupsBridge.MCAST_PORT_PROPERTY, JavaGroupsBridge.MCAST_PORT_DEFAULT);
    }

    public void setJavaGroupsMulticastPort(String multicastPort)
    {
        setDomainStringProperty(JavaGroupsBridge.MCAST_PORT_PROPERTY, multicastPort, JavaGroupsBridge.MCAST_PORT_DEFAULT);
    }

    public String getJavaGroupsFile()
    {
        return getDomainProperty(JavaGroupsBridge.JGROUPS_CONFIG_URL_PROPERTY, "");
    }

    public void setJavaGroupsFile(String javaGroupsFile)
    {
        setDomainStringProperty(JavaGroupsBridge.JGROUPS_CONFIG_URL_PROPERTY, javaGroupsFile, "");
    }

    public String getJmsConnectionFactory()
    {
        return getDomainProperty(JMSBridge.TOPIC_CONNECTION_FACTORY_PROPERTY, JMSBridge.TOPIC_CONNECTION_FACTORY_DEFAULT);
    }

    public void setJmsConnectionFactory(String jmsConnectionFactory)
    {
        setDomainStringProperty(JMSBridge.TOPIC_CONNECTION_FACTORY_PROPERTY, jmsConnectionFactory, JMSBridge.TOPIC_CONNECTION_FACTORY_DEFAULT);
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
    private void setDomainStringProperty(String property, String value, String defaultValue)
    {
        if (getDataDomain() == null)
            return;

        // no empty strings
        if ("".equals(value))
            value = null;

        // use NULL for defaults
        if (value != null && value.equals(defaultValue))
            value = null;

        Map<String, String> properties = getDataDomain().getProperties();

        Object oldValue = properties.get(property);

        if (!Util.nullSafeEquals(value, oldValue))
        {
            properties.put(property, value);

//            DomainEvent e = new DomainEvent(this, domain);
//            projectController.fireDomainEvent(e);
        }
    }

    private String getDomainProperty(String property, String defaultValue)
    {
        if (getDataDomain() == null)
            return null;

        String value = getDataDomain().getProperties().get(property);
        return value != null ? value : defaultValue;
    }

    private boolean getDomainBooleanProperty(String property, boolean defaultValue)
    {
        return "true".equalsIgnoreCase(getDomainProperty(property, Boolean.toString(defaultValue)));
    }

    private void setDomainBooleanProperty(String property, boolean value, boolean defaultValue)
    {
        setDomainStringProperty(property, Boolean.toString(value), Boolean.toString(defaultValue));
    }

    private int getDomainIntegerProperty(String property, int defaultValue)
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
