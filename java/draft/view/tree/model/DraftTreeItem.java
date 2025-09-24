package draft.view.tree.model;


import draft.model.draftRepository.DraftNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class DraftTreeItem extends DefaultMutableTreeNode {

    private DraftNode draftNode;

    public DraftTreeItem(DraftNode nodeModel) {
        this.draftNode = nodeModel;
    }

    @Override
    public String toString() {
        return draftNode.getName();
    }

    public void setName(String name) {
        this.draftNode.setName(name);
    }

    public DraftNode getDraftNode() {
        return draftNode;
    }

    public void setDraftNode(DraftNode draftNode) {
        this.draftNode = draftNode;
    }
}
