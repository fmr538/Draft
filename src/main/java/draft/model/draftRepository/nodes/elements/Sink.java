package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.SinkPainter;

import java.awt.*;

@JsonTypeName("sink")
public class Sink extends RoomElement {
    private static int counter = 0;

    public Sink(Point position, Dimension size, double rotation, DraftNodeComposite parent) {
        super("Sink " + counter++, position, size, parent, rotation, ElementColor.Blue());
        elementPainter = new SinkPainter(this);
    }

    @Override
    public Sink cloneElement() {
        return new Sink((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}
