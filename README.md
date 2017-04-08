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
![DataDomain](https://people.apache.org/~mgentry/DataDomain.png)

**DataMap Editor**
![DataMap](https://people.apache.org/~mgentry/DataMap.png)

**ObjEntity Class Editor**
![Object Entity Class Tab](https://people.apache.org/~mgentry/ObjectEntity1.png)

**ObjEntity Attributes Editor**
![Object Entity Attributes Tab](https://people.apache.org/~mgentry/ObjectEntity2.png)

**Multiple Window Data Synchronization**
![Multiple Window Data Synchronization](https://people.apache.org/~mgentry/DataSynchronization.gif)

## Development

* [Prerequisites](docs/Prerequisites.md)
* [Project Layout](docs/Project-Layout.md)
* [Running Inside Eclipse](docs/Running-Eclipse.md)
* [Running With Maven](docs/Running-Maven.md)
* [Property Adapters](docs/Property-Adapters.md)
* [UI Layouts](docs/UI-Layouts.md)
* [XML Extensions Format](docs/XML-Extensions.md)
* [Sample Model](docs/Sample-Model.md)
* [To-Do/Issues](docs/To-Do.md)

## Thanks

The [FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx) library is used to provide icons from [FontAwesome](https://fortawesome.github.io/Font-Awesome/).

## License

[Apache Software Foundation](http://www.apache.org/licenses/LICENSE-2.0)

