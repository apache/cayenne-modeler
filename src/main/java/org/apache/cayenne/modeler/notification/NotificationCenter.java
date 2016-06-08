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

package org.apache.cayenne.modeler.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cayenne.modeler.notification.event.CayenneModelerEvent;
import org.apache.cayenne.modeler.notification.listener.CayenneModelerListener;
import org.apache.cayenne.modeler.project.CayenneProject;

public class NotificationCenter
{
    private static Map<CayenneProject, List<? super CayenneModelerListener>> projectListeners = new HashMap<CayenneProject, List<? super CayenneModelerListener>>(2);

    public static <T extends CayenneModelerListener> void addProjectListener(CayenneProject project, T listener)
    {
        if (projectListeners.containsKey(project) == false)
            projectListeners.put(project, new ArrayList<CayenneModelerListener>());

        projectListeners.get(project).add(listener);
    }

//  Class<? extends CayenneModelerListener> listenerClass = event.getListenerClass();

    public static <T extends CayenneModelerEvent, L extends CayenneModelerListener> void broadcastProjectEvent(CayenneProject project, T event)
    {
        if (projectListeners.containsKey(project) == false)
            throw new RuntimeException("Cannot broadcast message -- unknown project.");

        for (Object listener : projectListeners.get(project))
            if (event.getListenerClass().isAssignableFrom(listener.getClass()))
//            if (listener.getClass().isAssignableFrom(event.getListenerClass()))
                fire(event, listener);
//                event.fire(listener);
    }
//  if (listener.getClass() == listenerClass)
//  ;
//    Class<?> clazz = Character.Gorgon.class;
//    Monster.class.isAssignableFrom(clazz);

    @SuppressWarnings("unchecked")
    private static <E extends CayenneModelerEvent, L extends CayenneModelerListener> void fire(E event, Object listener)
    {
        L target = (L) listener;
        event.fire(target);
    }

    public static void removeProjectListener(CayenneProject project, CayenneModelerListener listener)
    {

    }
}
