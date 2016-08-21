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

import java.io.IOException;

import org.apache.cayenne.map.DbEntity;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class DatabaseEntityTableTabLayout extends AnchorPane implements MainWindowSupport
{
//    @FXML
//    private Button dbEntitySyncButton;
//
//    @FXML
//    private CheckBox abstractClassCheckbox;

    private MainWindowSupport parent;

    public DatabaseEntityTableTabLayout(MainWindowSupport parent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/DatabaseEntityTableTabLayout.fxml"));

        this.parent = parent;

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }


    public void initialize()
    {
//        dbEntitySyncButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REFRESH, "16px"));
    }

//    private ObjEntity objectEntity;
    public void display(DbEntity databaseEntity)
    {
//        this.objectEntity = objectEntity;
//        System.out.println("trying to display: " + objectEntity);

//        abstractClassCheckbox.selectedProperty().bind(objectEntity.isAbstract());
    }

    @Override
    public MainWindowLayout getMainWindow()
    {
        return parent.getMainWindow();
    }
}
