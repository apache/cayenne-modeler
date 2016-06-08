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

import org.apache.cayenne.map.DataMap;

public class DataMapTreeViewModel extends CayenneTreeViewModel
{
//    public enum CayenneModelItemType
//    {
//        DATA_DOMAIN, DATA_MAP, DATA_NODE, OBJECT_ENTITY, DATABASE_ENTITY;
//    }
//
    private DataMap dataMap;

    public DataMapTreeViewModel(DataMap dataMap)
    {
        this.dataMap = dataMap;
    }

    @Override
    public String toString()
    {
        return dataMap.getName();
    }
}
