package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.view.swing.MainFrame;
import draft.view.tree.model.DraftTreeItem;

import java.awt.event.ActionEvent;

public class RemoveNodeAction extends AbstractRoomAction{
    public RemoveNodeAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Remove");
        putValue(SHORT_DESCRIPTION, "Remove selected node");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = (DraftTreeItem) MainFrame.getInstance().getDraftTree().getSelectedNode();
        if (selected == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notSelected();
            return;
        }
        MainFrame.getInstance().getDraftTree().deleteTreeNode(selected);
    }
}
