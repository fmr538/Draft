package draft.controller.actions;

import draft.core.ApplicationFramework;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UndoAction extends AbstractRoomAction {
    public UndoAction()
    {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/undo.png"));
        putValue(NAME,"Undo");
        putValue(SHORT_DESCRIPTION,"Undo action");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationFramework.getInstance().getGui().getCommandManager().undoCommand();

    }
}
