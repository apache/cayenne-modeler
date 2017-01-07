# Development > UI Layouts

# Introduction
The term **layout** is used throughout the application to refer to JavaFX FXML files and Java Controllers for either UI windows or components which are composited into windows.  These UI files can be found under:

* `src/main/resources/layouts` -- FXML files for the UI (edit with Scene Builder).
* `src/main/java/org/apache/cayenne/modeler/layout` -- Java controllers for the FXML (names match).

Currently all layouts are in the same respective folder, but this might change to sub-folders as more layouts are added.

## UI Starting points:

* `SplashLayout` -- Initial splash window to open existing projects or create a new project.  When a project is opened or created, the main window is then loaded.  Singleton.
* `MainWindowLayout` -- Cayenne Modeler window which loads in many other layouts to create the main UI.
* `PreferencesLayout` -- Application preferences window.  Singleton.

# Scene Builder

## Creating a new Layout

* Create a new FXML file.
* Make sure the `Use fx:root Construct` is selected, but do not enter a controller name.  This setting can be found in the lower left corner of Scene Builder's editor, under the Document/Controller section.
* Create an `AnchorPane` as the top-level element.  This is important due to the expectations of the Java supporting classes in the project.
* Create the UI inside the `AnchorPane`.  Typically a `VBox` or some other container is placed inside the `AnchorPane`.  Look at existing `*Layout.fxml` files for ideas.
* Save the UI as `*Layout.fxml` under `src/main/resources/layouts`.

## Working with an existing FXML file.

* Open the existing FXML file.
* Edit as needed, but do not remove the top-level `AnchorPane` or de-select `Use fx:root Construct`.

# Java

## Creating Java Window Layouts

* Create a new Java UI window controller file in `src/main/java/org/apache/cayenne/modeler/layout` and make sure the Java class name matches the name of the corresponding `*Layout.fxml` file (with `.fxml` being replaced with `.java`, of course).
* The layout should extend `AbstractWindowLayout`.
* The layout's constructor should call the superclass constructor, passing in a `Stage` and the path to the matching `*Layout.fxml`.  See existing component layouts for examples.


## Creating Java Component Layouts

* Create a new Java UI component controller file in `src/main/java/org/apache/cayenne/modeler/layout` and make sure the Java class name matches the name of the corresponding `*Layout.fxml` file (with `.fxml` being replaced with `.java`, of course).
* The layout should extend `AbstractViewLayout` and possibly implement `DetailEditorSupport` if it is a detail editor.  See existing layout controllers for examples.
* The layout's constructor should call the superclass constructor, passing in the parent component (which can be the window) and the path to the matching `*Layout.fxml`.  See existing window layouts for examples.

## Layout Lifecycle

* `initializeLayout()` -- Called after FXML is loaded from the superclass constructor.  Override if the controller needs to initialize settings after FXML is loaded.  The overridden method should call `super.initializeLayout()` which in turn calls `loadChildLayouts()` allowing child layouts to load and initialize before the parent tries to use them.  If the layout does not require initialization, this method can be omitted.
* `loadChildLayouts()` -- Called by `initializeLayout()` to allow layout children to be loaded into the parent layout and subsequently initialized.  Override if the controller has additional FXML components to load.  If the layout does not have children to load, this method can be omitted.  NOTE: The child layouts will go through their own `initializeLayout()` and `loadChildLayouts()` lifecycle.  This allows more complex UIs to be composited together via separate FXML files and Java controllers.

## Detail Lifecycle

If the layout is a detail editor (implements `DetailEditorSupport`), the following lifecycle is expected:

* `setPropertyAdapter(CayennePropertyAdapter propertyAdapter)` -- Called to tell the detail editor/view which property adapter is to be used for editing.
* `beginEditing()` -- Called to tell the detail editor that editing should begin and it should bind UI elements to the property adapter.
* `endEditing()` -- Called to tell the detail editor that editing should end and it should unbind UI elements from the property adapter.

