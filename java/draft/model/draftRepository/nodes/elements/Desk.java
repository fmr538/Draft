package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.DeskPainter;

import java.awt.*;

@JsonTypeName("desk")
public class Desk extends RoomElement {
    private static int counter = 0;

    public Desk(Point position, Dimension size, double rotation, DraftNodeComposite parent) {
        super("Desk " + counter++, position, size, parent, rotation, ElementColor.Orange());
        elementPainter = new DeskPainter(this);
    }

    @Override
    public Desk cloneElement() {
        return new Desk((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}