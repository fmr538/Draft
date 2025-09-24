package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.WashingMachinePainter;

import java.awt.*;

@JsonTypeName("washingMachine")
public class WashingMachine extends RoomElement {
    private static int counter = 0;

    public WashingMachine(Point position, Dimension size, double rotation, DraftNodeComposite parent) {
        super("WashingMachine " + counter++, position, size, parent, rotation, ElementColor.DarkOrange());
        elementPainter = new WashingMachinePainter(this);
    }

    @Override
    public WashingMachine cloneElement() {
        return new WashingMachine((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}
