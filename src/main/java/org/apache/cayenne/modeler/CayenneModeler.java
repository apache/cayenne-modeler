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
import org.apache.cayenne.modeler.layout.PreferencesLayout;
import org.apache.cayenne.modeler.layout.SplashLayout;
import org.apache.cayenne.modeler.project.CayenneProject;
import org.apache.cayenne.project.CayenneProjectModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.application.Application;
import javafx.stage.Stage;

public class CayenneModeler extends Application
{
    private static final Log LOGGER = LogFactory.getLog(CayenneModeler.class);
//    private static final Logger LOGGER = LogManager.getLogger(CayenneModeler.class);

//    private Stage      primaryStage;
    private SplashLayout splashLayout;
//    private BorderPane rootLayout;

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        LOGGER.info("Starting modeler...");
//        this.primaryStage = primaryStage;


//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScene.fxml"));
//
////        MainWindow mainWindow = new MainWindow(cayenneModel);
////        loader.setController(mainWindow);
//        Parent root = loader.load();
//        MainWindow mainWindow = loader.getController();
//
//        Scene scene = new Scene(root);//, 470, 332);


//        MainWindowViewController mainWindow = (MainWindowViewController) BaseView.loadFXML(getClass().getResource("/view/MainWindowView.fxml"), primaryStage);

//        primaryStage.setScene(mainWindow.getScene());
//        primaryStage.setTitle("Cayenne Modeler");


        // Create and display the Splash layout with recent document list.
        splashLayout = new SplashLayout(primaryStage);

        splashLayout.show();
    }

    public static void openProject(final String path) throws Exception
    {
        final CayenneProject cayenneProject = CayenneProjectManager.projectForPath(path);
//        CayenneProject cayenneProject = new CayenneProject(path);

//        CayenneProjectManager.addProject(cayenneProject);

        // TODO: Probably need to save this value off somewhere...
        final MainWindowLayout mainWindow = new MainWindowLayout();

        mainWindow.show();
        mainWindow.displayCayenneProject(cayenneProject);

        // For testing data sync across windows, this creates a second editor window:
        final MainWindowLayout mainWindow2 = new MainWindowLayout();

        mainWindow2.show();
        mainWindow2.displayCayenneProject(cayenneProject);
    }

    private static PreferencesLayout preferencesLayout;

    public static void openPreferences() throws Exception
    {
        if (preferencesLayout == null)
            preferencesLayout = new PreferencesLayout();

        preferencesLayout.show();
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


    protected static Collection<Module> appendModules(final Collection<Module> modules)
    {
        modules.add(new ServerModule("CayenneModeler"));
        modules.add(new CayenneProjectModule());
        modules.add(new CayenneModelerModule());

        return modules;
    }

    public static void main(final String[] args)
    {
//        Font.loadFont(CayenneModeler.class.getResource("/font/fontawesome-webfont.ttf").toExternalForm(), 10);

        injector = DIBootstrap.createInjector(appendModules(new ArrayList<Module>()));

        launch(args);
    }
}
