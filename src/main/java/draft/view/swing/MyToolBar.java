package draft.view.swing;

import draft.controller.actions.AbstractRoomAction;

import javax.swing.*;
import java.util.ArrayList;

public class MyToolBar extends JToolBar {
    public MyToolBar(ArrayList<AbstractRoomAction> actions){
        super(HORIZONTAL);
        setFloatable(false);

        for (AbstractRoomAction action : actions) {
            add(action);
        }
    }
}
