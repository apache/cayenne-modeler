package org.apache.cayenne.modeler.project;

import javafx.scene.control.TreeCell;

public class ProjectTreeCell extends TreeCell<String>
{
    @Override
    public void updateItem(String item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setText(getItem() == null ? "" : getItem().toString());
            setGraphic(getTreeItem().getGraphic());
            setContextMenu(((CayenneTreeItem<?>) getTreeItem()).getContextMenu());
        }
    }
}
