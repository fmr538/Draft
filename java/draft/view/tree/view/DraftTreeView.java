package draft.view.tree.view;

import draft.view.tree.controller.DraftTreeCellEditor;
import draft.view.tree.controller.DraftTreeSelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class DraftTreeView extends JTree {


    public DraftTreeView(DefaultTreeModel defaultTreeModel) {
        setModel(defaultTreeModel);
        DraftTreeCellRenderer ruTreeCellRenderer = new DraftTreeCellRenderer();
        addTreeSelectionListener(new DraftTreeSelectionListener());
        setCellEditor(new DraftTreeCellEditor(this, ruTreeCellRenderer));
        setCellRenderer(ruTreeCellRenderer);
        setEditable(true);
    }
}
