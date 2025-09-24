package draft.controller.actions.state;

import draft.controller.actions.AbstractRoomAction;
import draft.controller.commands.implementation.EditCommand;
import draft.controller.state.StateManager;
import draft.controller.state.concrete.SelectState;
import draft.core.ApplicationFramework;
import draft.model.draftRepository.NodeSelection;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;
import draft.view.swing.RoomElementsView;
import draft.view.swing.RoomView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class RotateElementLeftAction extends AbstractRoomAction {
    public RotateElementLeftAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Rotate Left");
        putValue(SHORT_DESCRIPTION, "Rotate Left Room Element");

        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StateManager stateManager = MainFrame.getInstance().getStateManager();
        if (stateManager.getCurrentState() instanceof SelectState) {
            RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
            RoomElementsView elementsView = roomView.getRoomElementsView();
            NodeSelection nodeSelection = elementsView.getSelection();
            ArrayList<RoomElement> elements = new ArrayList<>();
            ArrayList<RoomElement> oldElements = new ArrayList<>();
            for (RoomElement element : nodeSelection.getItems()) {
                // Ovde proveriti da li kas se rotira izlazi iz okvira rooma i ne dozvoliti rotiranje
                double rotation = element.getRotation() - 90;
                if (rotation < -360) rotation += 360;
                RoomElement copy = element.cloneElement();
                element.setRotation(rotation);
                elements.add(element);
                oldElements.add(copy);
            }
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(
                    new EditCommand(elements,oldElements)
            );
            roomView.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "You did not select any element!", "No Selected element", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
