package draft.view.tree.controller;

import draft.view.tree.model.DraftTreeItem;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class DraftTreeSelectionListener implements TreeSelectionListener {

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        DraftTreeItem treeItemSelected = (DraftTreeItem) path.getLastPathComponent();
//        System.out.println("Selektovan cvor:"+ treeItemSelected.getDraftNode().getName());
//        System.out.println("getPath: "+e.getPath());
    }


}


