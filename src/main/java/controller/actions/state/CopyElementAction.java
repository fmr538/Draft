package draft.controller.actions.state;

import draft.controller.actions.AbstractRoomAction;
import draft.controller.state.StateManager;
import draft.controller.state.concrete.SelectState;
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

public class CopyElementAction extends AbstractRoomAction {

    public CopyElementAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Copy");
        putValue(SHORT_DESCRIPTION, "Copyt Room Element");

        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StateManager stateManager = MainFrame.getInstance().getStateManager();
        if (stateManager.getCurrentState() instanceof SelectState) {
            RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
            NodeSelection nodeSelection = roomView.getRoomElementsView().getSelection();
            roomView.removeAllCopiedElements();
            for (RoomElement element : nodeSelection.getItems()) {
                if (element != null) {
                    roomView.addCopiedElement(element);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "You did not select any element!", "No Selected element", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
