package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.view.swing.MainFrame;
import draft.view.tree.model.DraftTreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class CreateNodeAction extends AbstractRoomAction{
    public CreateNodeAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "New");
        putValue(SHORT_DESCRIPTION, "Add new node");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = (DraftTreeItem) MainFrame.getInstance().getDraftTree().getSelectedNode();
        if (selected == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notSelected();
            return;
        }
        MainFrame.getInstance().getDraftTree().addChild(selected);
    }
}
