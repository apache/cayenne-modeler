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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.cayenne.configuration.server.ServerModule;
import org.apache.cayenne.di.DIBootstrap;
import org.apache.cayenne.di.Inject;
import org.apache.cayenne.di.Injector;
import org.apache.cayenne.di.Module;
import org.apache.cayenne.modeler.di.CayenneModelerModule;
import org.apache.cayenne.modeler.layout.MainWindowLayout;
import org.apache.cayenne.modeler.model.CayenneModel;
import org.apache.cayenne.project.CayenneProjectModule;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CayenneModeler extends Application
{
    private Stage      primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        CayenneModel cayenneModel = new CayenneModel("/cayenne-analytic.xml");
        CayenneModelManager.addModel(cayenneModel);

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScene.fxml"));
//
////        MainWindow mainWindow = new MainWindow(cayenneModel);
////        loader.setController(mainWindow);
//        Parent root = loader.load();
//        MainWindow mainWindow = loader.getController();
//
//        Scene scene = new Scene(root);//, 470, 332);

        Stage stage = new Stage();

//        MainWindowViewController mainWindow = (MainWindowViewController) BaseView.loadFXML(getClass().getResource("/view/MainWindowView.fxml"), primaryStage);
        MainWindowLayout mainWindow = new MainWindowLayout(stage);//(MainWindowViewController) BaseView.loadFXML(getClass().getResource("/view/MainWindowView.fxml"), primaryStage);

//        primaryStage.setScene(mainWindow.getScene());
//        primaryStage.setTitle("Cayenne Modeler");

        Scene scene = new Scene(mainWindow);
        stage.setScene(scene);
        stage.show();

//        mainWindow.setTitle(); // Redundant for now.
        mainWindow.displayCayenneModel(cayenneModel);
}

    /**
     * Initializes the root layout.
     */
//    public void initRootLayout()
//    {
//        try
//        {
//            // Load root layout from fxml file.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainScene.class.getResource("view/MainScene.fxml"));
//            rootLayout = (BorderPane) loader.load();
//
//            // Show the scene containing the root layout.
//            Scene scene = new Scene(rootLayout);
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }

    /**
     * Shows the person overview inside the root layout.
     */
//    public void showPersonOverview()
//    {
//        try
//        {
//            // Load person overview.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainScene.class.getResource("view/PersonOverview.fxml"));
//            AnchorPane personOverview = (AnchorPane) loader.load();
//
//            // Set person overview into the center of root layout.
//            rootLayout.setCenter(personOverview);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }

    /**
     * Returns the main stage.
     *
     * @return
     */
//    public Stage getPrimaryStage()
//    {
//        return primaryStage;
//    }

    @Inject
    protected static Injector injector;

    public static Injector getInjector()
    {
        return injector;
    }


    protected static Collection<Module> appendModules(Collection<Module> modules)
    {
        modules.add(new ServerModule("CayenneModeler"));
        modules.add(new CayenneProjectModule());
        modules.add(new CayenneModelerModule());

        return modules;
    }

    public static void main(String[] args)
    {
//        Font.loadFont(CayenneModeler.class.getResource("/font/fontawesome-webfont.ttf").toExternalForm(), 10);

        injector = DIBootstrap.createInjector(appendModules(new ArrayList<Module>()));

        launch(args);
    }
}
