package draft.view.swing;

import draft.controller.actions.AbstractRoomAction;

import javax.swing.*;
import java.util.ArrayList;

public class StateToolbar extends JToolBar {
    public StateToolbar(ArrayList<AbstractRoomAction> actions) {
        super("Room View", HORIZONTAL);
        setFloatable(false);
        for (AbstractRoomAction action : actions) {
            add(action);
        }
    }
}
