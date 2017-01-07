# Development > Prerequisites

* Java 8
* [Scene Builder](http://gluonhq.com/open-source/scene-builder/) (to edit FXML files)
* An IDE
  * Eclipse
    * e(fx)clipse (install from Eclipse Marketplace)
    * If you get `Access restriction: The type ... is not API (restriction on required library ...` for JavaFX, do the following:
      * Select the project
      * Right-click and choose "Properties"
      * Select "Java Build Path"
      * Select "Libraries" tab
      * Expand Java System Library
      * Select "Access Rules"
      * Click "Edit"
      * Click "Add"
      * Set "Resolution" to "Accessible"
      * Set "Rule Pattern" to "javafx/**"
      * OK/Accept everything
  * IntelliJ
    * TBD
  * Netbeans
    * TBD
