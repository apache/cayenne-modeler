# Cayenne Modeler Prototype
## A more modern version of Cayenne Modeler

### Project Goals

Why re-write Cayenne Modeler?  While Cayenne Modeler is stable and works well, it is also built upon older technologies that make it harder to augment.  The goal of this prototype project is to preserve everything that is good about Cayenne Modeler feature-wise, while upgrading the underlying technologies and features:

* Use JavaFX and Scene Builder for a more modern core and easier UI development.
* Add JavaDoc support for all generated Java classes, getters/setters, and relationships.
* Add support for interfaces.
* Add custom annotation support for all generated Java classes, getters/setters, and relationships.
* Add custom imports for all generated classes (mainly to support custom annotations).
* Add custom Velocity template editing and management.
* Add multiple window support so multiple projects, including the same project, can be open at the same time.

### Status
This project is about 15-20% finished.

### Appearance

What will it look like?  Well, it has the same basic layout as the current Cayenne Modeler, but with upgrades.

The following screenshots show how the prototype is shaping up.  They'll be updated as work progresses.

**DataDomain Editor**
![DataDomain](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/DataDomain.png)

**DataMap Editor**
![DataMap](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/DataMap.png)

**ObjEntity Class Editor**
![Object Entity Class Tab](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/ObjectEntity1.png)

**ObjEntity Attributes Editor**
![Object Entity Attributes Tab](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/ObjectEntity2.png)

**Multiple Window Data Synchronization**
![Multiple Window Data Synchronization](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/DataSynchronization.gif)

## Development

### Prerequisites

* Java 8
* [Scene Builder](http://gluonhq.com/open-source/scene-builder/) (to edit FXML files)
* Eclipse
    * e(fx)clipse (install from Eclipse Marketplace)
* IntelliJ
    * TBD
* Netbeans
    * TBD

### Running Inside Eclipse

Right-click on the main application,
`src/main/java/org/apache/cayenne/modeler/CayenneModeler.java`,
and choose `Run As` => `Java Application`.

### Running With Maven

From the top-level project, where the `pom.xml` file is located:

`mvn exec:java -Dexec.mainClass=org.apache.cayenne.modeler.CayenneModeler`

### Project Layout

The project is Maven-based and has a typical Maven layout with `src/main/java` containing the Java source code and `src/main/resources` containing project resources.

### Property Adapters

JavaFX utilizes **properties** and **bindings** instead of the traditional JavaBean convention which allows the UI to be kept synchroized with the underlying data model.  Cayenne's underlying model, however, is not written to the JavaFX property model, therefore it is necessary to bridge the JavaFX property model to the Cayenne model.  This is done with property adapters, which are found in `src/main/java/org/apache/cayenne/modeler/adapters`.  This is the only place where direct access to the underlying Cayenne data model should be performed.

### UI Layouts

The term **layout** is used throughout the application to refer to JavaFX FXML files and Java Controllers for either UI windows or components which are composited into windows.  These UI files can be found under:

* `src/main/resources/layouts` -- FXML files for the UI (edit with Scene Builder).
* `src/main/java/org/apache/cayenne/modeler/layout` -- Java controllers for the FXML (names match).

Currently all layouts are in the same respective folder, but this might change to sub-folders as more layouts are added.

UI Starting points:

* `SplashLayout` -- Initial splash window to open existing projects or create a new project.  When a project is opened or created, the main window is then loaded.  Singleton.
* `MainWindowLayout` -- Cayenne Modeler window which loads in many other layouts to create the main UI.
* `PreferencesLayout` -- Application preferences window.  Singleton.

For more detailed information, see [Working With Layouts](https://github.com/mrg/CMP/wiki/Working-With-Layouts).

### Sample Model

A sample model exists at `src/main/resources/cayenne-analytic.xml` and can be opened from the Splash Panel on startup for testing purposes.

### To-Do

There are so many things needing to be done.  See the [issues](https://github.com/mrg/CMP/issues) for some of them.

## Thanks

The [FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx) library is used to provide icons from [FontAwesome](https://fortawesome.github.io/Font-Awesome/).

## License

[Apache Software Foundation](http://www.apache.org/licenses/LICENSE-2.0)

