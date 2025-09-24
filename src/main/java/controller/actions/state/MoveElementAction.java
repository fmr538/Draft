package draft.controller.actions.state;

import draft.controller.actions.AbstractRoomAction;
import draft.controller.state.StateManager;
import draft.view.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MoveElementAction extends AbstractRoomAction {

    public MoveElementAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Move");
        putValue(SHORT_DESCRIPTION, "Move Room Element");

        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StateManager stateManager = MainFrame.getInstance().getStateManager();
        stateManager.setMoveState();
    }
}
