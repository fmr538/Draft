package draft.controller.commands.implementation;

import draft.controller.commands.AbstractCommand;
import draft.model.draftRepository.DraftNode;
import draft.view.swing.MainFrame;
import draft.view.tree.model.DraftTreeItem;

import java.util.List;

public class DeleteCommand extends AbstractCommand {
    private DraftTreeItem parent;
    private List<DraftNode> children;

    public DeleteCommand(DraftTreeItem parent,  List<DraftNode> children) {
        this.parent = parent;
        this.children = children;
    }
    @Override
    public void doCommand() {
        if(children == null ||  parent==null) return;

        for(DraftNode child:children)
        {
            DraftTreeItem temp = MainFrame.getInstance().getDraftTree().findNode(child);
            if (temp != null)
                MainFrame.getInstance().getDraftTree().deleteTreeNode(temp);
        }
    }

    @Override
    public void undoCommand() {
        if(children == null ||  parent==null) return;

        for(DraftNode child: children)
        {
            if (MainFrame.getInstance().getDraftTree().findNode(child) != null)
                continue;

            MainFrame.getInstance().getDraftTree().addNode(parent, child);
        }
    }
}
