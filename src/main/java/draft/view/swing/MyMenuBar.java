package draft.view.swing;

import draft.controller.actions.AbstractRoomAction;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar(ArrayList<AbstractRoomAction> actions){
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        for (AbstractRoomAction action : actions) {
            fileMenu.add(action);
        }
        add(fileMenu);
    }
}
