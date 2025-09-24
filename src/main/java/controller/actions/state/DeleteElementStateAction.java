package draft.controller.actions.state;

import draft.controller.actions.AbstractRoomAction;
import draft.controller.state.StateManager;
import draft.controller.state.concrete.DeleteState;
import draft.core.ApplicationFramework;
import draft.model.observable.Subscriber;
import draft.view.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DeleteElementStateAction extends AbstractRoomAction{
    public DeleteElementStateAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete Selected Room Element");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StateManager stateManager = MainFrame.getInstance().getStateManager();
        stateManager.setDeleteState();
    }
}
