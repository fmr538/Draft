package draft.controller.actions;

import draft.controller.message.MessageGeneratorImplementation;
import draft.core.ApplicationFramework;
import draft.view.swing.MainFrame;
import draft.view.swing.OrganizeInputFrame;
import draft.view.swing.RoomView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OrganizeRoomAction extends AbstractRoomAction{
    public OrganizeRoomAction(){
//        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/organize.png"));
        putValue(NAME, "Organize");
        putValue(SHORT_DESCRIPTION, "Organize my room");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
        if (roomView != null && roomView.getRoom().getSize().getHeight() != 0) {
            new OrganizeInputFrame(roomView);
        }
        else
            ApplicationFramework.getInstance().getMessageGenerator().noRoomView();
    }
}
