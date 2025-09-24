package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.ToiletPainter;

import java.awt.*;

@JsonTypeName("toilet")
public class Toilet extends RoomElement {
    private static int counter = 0;

    public Toilet(Point position, Dimension size, double rotation, DraftNodeComposite parent) {
        super("Toilet " + counter++, position, size, parent, rotation, ElementColor.DarkRed());
        elementPainter = new ToiletPainter(this);
    }

    @Override
    public Toilet cloneElement() {
        return new Toilet((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}
