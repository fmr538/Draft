package draft.controller.actions;

import draft.core.ApplicationFramework;

import java.awt.event.ActionEvent;

public class RedoAction extends AbstractRoomAction {
    public RedoAction()
    {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/redo.png"));
        putValue(NAME,"Redo");
        putValue(SHORT_DESCRIPTION,"Redo action");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationFramework.getInstance().getGui().getCommandManager().doCommand();
    }
}
