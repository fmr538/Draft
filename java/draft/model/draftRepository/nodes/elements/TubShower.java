package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.TubShowerPainter;

import java.awt.*;

@JsonTypeName("tubshower")
public class TubShower extends RoomElement {
    private static int counter = 0;

    public TubShower(Point position, Dimension size, double rotation, DraftNodeComposite parent) {
        super("TubShower " + counter++, position, size, parent, rotation, ElementColor.LightBlue());
        elementPainter = new TubShowerPainter(this);
    }

    @Override
    public TubShower cloneElement() {
        return new TubShower((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}
