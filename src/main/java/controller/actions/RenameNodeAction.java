package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.model.draftRepository.nodes.ProjectExplorer;
import draft.view.swing.MainFrame;
import draft.view.tree.model.DraftTreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class RenameNodeAction extends AbstractRoomAction {
    public RenameNodeAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Rename");
        putValue(SHORT_DESCRIPTION, "Change name of selected node");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = JOptionPane.showInputDialog(null,
                "Please enter new name:",
                "Name Input",
                JOptionPane.PLAIN_MESSAGE);

        if (Objects.isNull(name)) return;

        if (Objects.equals(name, "")) {
            ApplicationFramework.getInstance().getMessageGenerator().nullName();
            return;
        }

        DraftTreeItem selected = (DraftTreeItem) MainFrame.getInstance().getDraftTree().getSelectedNode();

        if (selected.getDraftNode() instanceof ProjectExplorer) {
            ApplicationFramework.getInstance().getMessageGenerator().renameProjExp();
            return;
        }

        MainFrame.getInstance().getDraftTree().changeName(selected, name);
    }
}
