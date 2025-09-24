package draft.controller.actions.state;

import draft.controller.actions.AbstractRoomAction;
import draft.controller.commands.implementation.AddCommand;
import draft.controller.state.StateManager;
import draft.controller.state.concrete.SelectState;
import draft.core.ApplicationFramework;
import draft.model.draftRepository.NodeSelection;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;
import draft.view.swing.RoomView;
import draft.view.tree.DraftTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class PasteElementAction extends AbstractRoomAction {

    public PasteElementAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Paste");
        putValue(SHORT_DESCRIPTION, "Paste Room Element");

        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
        if (roomView.hasCopiedElements()) {
            Room room = roomView.getRoom();
            for (RoomElement element : roomView.getCopiedElements()) {
                if (element != null) {
                    RoomElement pasteElement = element.cloneElement();
                    Point pos = pasteElement.getPosition();
                    pos.setLocation(pos.x + 20, pos.y);
                    pasteElement.setPosition(pos);
                    ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(
                            new AddCommand(MainFrame.getInstance().getDraftTree().findNode(room),pasteElement));
                    roomView.repaint();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No element was copied", "No copied element", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
