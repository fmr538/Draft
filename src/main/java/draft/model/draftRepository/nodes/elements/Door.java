package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.DoorPainter;

import java.awt.*;

@JsonTypeName("door")
public class Door extends RoomElement {
    private static int counter = 0;

    public Door(Point position, Dimension size, double rotation, DraftNodeComposite parent) {
        super("Door " + counter++, position, size, parent, rotation, ElementColor.Brown());
        elementPainter = new DoorPainter(this);
    }

    @Override
    public Door cloneElement() {
        return new Door((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}
