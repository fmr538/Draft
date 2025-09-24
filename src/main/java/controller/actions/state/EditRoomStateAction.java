package draft.controller.actions.state;

import draft.controller.actions.AbstractRoomAction;
import draft.controller.state.StateManager;
import draft.view.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class EditRoomStateAction extends AbstractRoomAction{
    public EditRoomStateAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Edit Room");
        putValue(SHORT_DESCRIPTION, "Edit Room");

        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StateManager stateManager = MainFrame.getInstance().getStateManager();
        stateManager.setEditRoomState();
    }
}
