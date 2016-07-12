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
* [Scene Builder](http://gluonhq.com/open-source/scene-builder/)
    (to edit FXML files)
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

The project is Maven-based and has a typical Maven layout.  That said, you can find some high-level things under:

* `src/main/resources/layouts` contains the FXML files for the UI (edit with Scene Builder).
* `src/main/java/org/apache/cayenne/modeler/layout` contains the Java controllers for the FXML layouts (names should match up).
* `src/main/resources/cayenne-analytic.xml` contains the sample model currently being used for testing purposes.
* More to come as the prototype solidifies...

### To-Do

There are so many things needing to be done.  See the [issues](https://github.com/mrg/CMP/issues) for some of them.

## Thanks

The [FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx) library is used to provide icons from [FontAwesome](https://fortawesome.github.io/Font-Awesome/).

The [JFXtras Labs](http://jfxtras.org/) library is used for data synchronization.

## License

[Apache Software Foundation](http://www.apache.org/licenses/LICENSE-2.0)
