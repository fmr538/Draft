package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.WaterHeaterPainter;

import java.awt.*;

@JsonTypeName("waterHeater")
public class WaterHeater extends RoomElement {
    private static int counter = 0;

    public WaterHeater(Point position, Dimension size, double rotation, DraftNodeComposite parent) {
        super("WaterHeater " + counter++, position, size, parent, rotation, ElementColor.Red());
        elementPainter = new WaterHeaterPainter(this);
    }

    @Override
    public WaterHeater cloneElement() {
        return new WaterHeater((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}
