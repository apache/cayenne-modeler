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

package org.apache.cayenne.modeler.utility;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ObjectEntityUtilities
{
    public static String[] getRegisteredTypeNames()
    {
        Set<String> nonPrimitives = new HashSet<String>();

        nonPrimitives.add(String.class.getName());
        nonPrimitives.add(BigDecimal.class.getName());
        nonPrimitives.add(BigInteger.class.getName());
        nonPrimitives.add(Boolean.class.getName());
        nonPrimitives.add(Byte.class.getName());
        nonPrimitives.add(Character.class.getName());
        nonPrimitives.add(Date.class.getName());
        nonPrimitives.add(java.util.Date.class.getName());
        nonPrimitives.add(Double.class.getName());
        nonPrimitives.add(Float.class.getName());
        nonPrimitives.add(Integer.class.getName());
        nonPrimitives.add(Long.class.getName());
        nonPrimitives.add(Short.class.getName());
        nonPrimitives.add(Time.class.getName());
        nonPrimitives.add(Timestamp.class.getName());
        nonPrimitives.add(Date.class.getName());
        nonPrimitives.add(GregorianCalendar.class.getName());
        nonPrimitives.add(Calendar.class.getName());
        nonPrimitives.add(UUID.class.getName());
        nonPrimitives.add(Serializable.class.getName());
        nonPrimitives.add("java.lang.Character[]");
        nonPrimitives.add("java.lang.Byte[]");
        nonPrimitives.add("java.time.LocalDate");
        nonPrimitives.add("java.time.LocalTime");
        nonPrimitives.add("java.time.LocalDateTime");

        String[] nonPrimitivesNames = new String[nonPrimitives.size()];
        nonPrimitives.toArray(nonPrimitivesNames);
        Arrays.sort(nonPrimitivesNames);

        String[] primitivesNames = { "boolean", "byte", "byte[]", "char", "char[]", "double", "float", "int", "long", "short" };

        String[] finalList = new String[primitivesNames.length + nonPrimitivesNames.length + 1];

        finalList[0] = "";
        System.arraycopy(primitivesNames, 0, finalList, 1, primitivesNames.length);
        System.arraycopy(nonPrimitivesNames, 0, finalList, primitivesNames.length + 1, nonPrimitivesNames.length);

        return finalList;
    }
}
