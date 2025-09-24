package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.CupboardPainter;

import java.awt.*;

@JsonTypeName("cupboard")
public class Cupboard extends RoomElement {
    private static int counter = 0;

    public Cupboard(Point position, Dimension size, double rotation, DraftNodeComposite parent) {
        super("Cupboard " + counter++, position, size, parent, rotation, ElementColor.DarkGreen());
        elementPainter = new CupboardPainter(this);
    }

    @Override
    public Cupboard cloneElement() {
        return new Cupboard((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}
