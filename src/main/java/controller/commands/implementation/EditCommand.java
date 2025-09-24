package draft.controller.commands.implementation;

import draft.controller.commands.AbstractCommand;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EditCommand extends AbstractCommand {
    private final ArrayList<RoomElement> elements;
    private final HashMap<RoomElement,RoomElement> oldState;
    private boolean init;

    public EditCommand(ArrayList<RoomElement> elements, ArrayList<RoomElement> oldElements) {
        this.elements = elements;
        oldState = new HashMap<>();

        for(int i = 0; i < oldElements.size(); i++)
            oldState.put(elements.get(i), oldElements.get(i));

        init = true;
    }

    @Override
    public void doCommand() {
        if (init){
            init = false;
            return;
        }

        invertElements();
    }

    @Override
    public void undoCommand() {
        invertElements();
    }

    private void invertElements() {
        for (RoomElement element : elements) {
            RoomElement oldState = this.oldState.get(element);

            Paint paint = element.getPaint();
            Point position = element.getPosition();
            Dimension size = element.getSize();
            double rotation = element.getRotation();

            element.setPaint(oldState.getPaint());
            element.setPosition(oldState.getPosition());
            element.setSize(oldState.getSize());
            element.setRotation(oldState.getRotation());

            oldState.setPaint(paint);
            oldState.setPosition(position);
            oldState.setSize(size);
            oldState.setRotation(rotation);
            MainFrame.getInstance().getProjectView().getCurrentRoomView().repaint();
        }
    }
}
