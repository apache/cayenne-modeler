# Cayenne Modeler Prototype

A more modern version of Cayenne Modeler built upon JavaFX.

![DataDomain](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/DataDomain.png)

![DataMap](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/DataMap.png)

![Object Entity Class Tab](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/ObjectEntity1.png)

![Object Entity Attributes Tab](https://dl.dropboxusercontent.com/u/54311650/CayenneModelerPrototype/ObjectEntity2.png)

## Prerequisites

* Java 8
* [Scene Builder](http://gluonhq.com/open-source/scene-builder/)
    (to edit FXML files)
* Eclipse
    * e(fx)clipse (install from Eclipse Marketplace)
* IntelliJ
    * TBD
* Netbeans
    * TBD

## Getting Started

### Running Inside Eclipse

Right-click on the main application,
`src/main/java/org/apache/cayenne/modeler/CayenneModeler.java`,
and choose `Run As` => `Java Application`.

### Running With Maven

From the top-level project, where the `pom.xml` file is located:

`mvn exec:java -Dexec.mainClass=org.apache.cayenne.modeler.CayenneModeler`

## Thanks

The [FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx) library is used to provide icons from [FontAwesome](https://fortawesome.github.io/Font-Awesome/).

The [JFXtras Labs](http://jfxtras.org/) library is used for data synchronization.

## License

[Apache Software Foundation](http://www.apache.org/licenses/LICENSE-2.0)
