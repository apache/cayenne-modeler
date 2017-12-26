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

package org.apache.cayenne.modeler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.modeler.project.CayenneProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CayenneProjectManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CayenneProjectManager.class);

    private static List<CayenneProject> projects = new ArrayList<>();

    public static CayenneProject projectForPath(final String path)
    {
        CayenneProject project = null;

        for (CayenneProject cayenneProject : projects)
            if (cayenneProject.getPath().equals(path))
                cayenneProject = project;

        if (project == null)
        {
            try
            {
                project = addProject(new CayenneProject(path));
            }
            catch (final MalformedURLException exception) // FIXME: Need to handle this better.
            {
                // TODO Auto-generated catch block
                LOGGER.error("Could not open project", exception);
            }
        }

        return project;
    }

    public static List<CayenneProject> getProjects()
    {
        return projects;
    }

    public static CayenneProject addProject(final CayenneProject project)
    {
        projects.add(project);

        return project;
    }

    public static CayenneProject removeProject(final CayenneProject project)
    {
        projects.remove(project);

        return project;
    }
}
