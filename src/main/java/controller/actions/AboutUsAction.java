package draft.controller.actions;

import draft.view.swing.AboutUsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AboutUsAction extends AbstractRoomAction{
    private AboutUsFrame aboutUsFrame;

    public AboutUsAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "About");
        putValue(SHORT_DESCRIPTION, "About us");
        this.aboutUsFrame = new AboutUsFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        aboutUsFrame.setVisible(true);
    }
}
