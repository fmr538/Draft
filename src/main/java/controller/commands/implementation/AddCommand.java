package draft.controller.commands.implementation;

import draft.controller.commands.AbstractCommand;
import draft.model.draftRepository.DraftNode;
import draft.view.swing.MainFrame;
import draft.view.tree.model.DraftTreeItem;

public class AddCommand extends AbstractCommand {

    private DraftTreeItem parent;
    private DraftNode child;

    public AddCommand(DraftTreeItem parent, DraftNode child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public void doCommand() {
        if(child == null ||  parent==null) return;
        MainFrame.getInstance().getDraftTree().addNode(parent,child);
    }

    @Override
    public void undoCommand() {
        if(child == null ||  parent==null) return;
        DraftTreeItem childitem = MainFrame.getInstance().getDraftTree().findNode(child);
        MainFrame.getInstance().getDraftTree().deleteTreeNode(childitem);

    }
}
