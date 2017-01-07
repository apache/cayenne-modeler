# Development > Cayenne Modeler Extensions

To support the new features planned for Cayenne Modeler, an extra XML file is more desirable due to the format of the (lots of CDATA) and due to fact that the extensions are only required during class generation and not run-time, therefore there is no need to keep it in the main XML file.

# Extensions File

The suggested filename for the extensions is `cayenne-modeler-extensions.xml`.

# Extensions Format

```xml
<?xml version="1.0" encoding="utf-8"?>
<extensions project-version="integer">
  <db-entities>
    <db-entity name="db_entity_name">
      <comment name="column_name">
Comment, potentially multiple lines, goes here and describes the DB column.
For DBs that support comments, this will be included in the SQL generation.
Even for DBs that don't support comments, this can provide useful documentation
for those editing/using Cayenne Modeler.
      </comment>
    </db-entity>
    ...
  </db-entities>
  <obj-entities>
    <obj-entity name="obj_entity_name">
      <class-javadoc>
JavaDoc comments for this Object Entity/Java Class.
      </class-javadoc>
      <imports>
Custom imports for this Object Entity/Java Class.  Useful for importing annotations.
      </imports>
      <obj-attribute name="obj_attribute_name">
        <attribute-javadoc>
JavaDoc for this attribute.
        </attribute-javadoc>
        <getter-javadoc>
JavaDoc for the getter for this attribute.
        </getter-javadoc>
        <setter-javadoc>
JavaDoc for the setter for this attribute.
        </setter-javadoc>
        <attribute-annotations>
Annotations for this attribute.
        </attribute-annotations>
        <attribute-getter-annotations>
Annotations for the getter for this attribute.
        </attribute-getter-annotations>
        <attribute-setter-annotations>
Annotations for the setter for this attribute.
        </attribute-setter-annotations>
      </obj-attribute>
      ...
      <obj-relationship name="obj_relationship_name">
        <add-to-javadoc>
JavaDoc for the addTo* method for this to-many relationship.
        </add-to-javadoc>
        <remove-from-javadoc>
JavaDoc for the removeFrom* method for this to-many relationship.
        </remove-from-javadoc>
        <getter-javadoc>
JavaDoc for the getter method for this to-many or to-one relationship.
        </getter-javadoc>
        <setter-javadoc>
JavaDoc for the setter method for this to-one relationship.
        </setter-javadoc>
        <add-to-annotations>
Annotations for the addTo* method for this to-many relationship.
        </add-to-annotations>
        <remove-from-annotations>
Annotations for the removeFrom* method for this to-many relationship.
        </remove-from-annotations>
        <getter-annotations>
Annotations for the getter method for this to-many or to-one relationship.
        </getter-annotations>
        <setter-annotations>
Annotations for the setter method for this to-one relationship.
        </setter-annotations>
      </obj-relationship>
    <obj-entity>
    ...
  </obj-entities>
  <templates>
    <template filename="template_filename.vm" active="boolean" type="superclass|subclass"/>
    ...
  </templates>
</extensions>
```

# Java Changes

TBD, but need to augment current Cayenne classes so you can do things like `anObjAttribute.getAnnotations()`
which returns a List<String>.

# Velocity Changes

TBD, but deed to update existing default templates to reference extension data.
