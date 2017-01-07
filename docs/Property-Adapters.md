# Development > Property Adapters

JavaFX utilizes **properties** and **bindings** instead of the traditional JavaBean convention which allows the UI to be kept synchroized with the underlying data model.  Cayenne's underlying model, however, is not written to the JavaFX property model, therefore it is necessary to bridge the JavaFX property model to the Cayenne model.  This is done with property adapters, which are found in `src/main/java/org/apache/cayenne/modeler/adapters`.  This is the only place where direct access to the underlying Cayenne data model should be performed.
