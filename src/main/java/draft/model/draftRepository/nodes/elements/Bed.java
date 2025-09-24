package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.BedPainter;

import java.awt.*;

@JsonTypeName("bed")
public class Bed extends RoomElement {
    @JsonIgnore
    private static int counter = 0;

    public Bed(@JsonProperty("position") Point position,@JsonProperty("size") Dimension size,@JsonProperty("rotation") double rotation, DraftNodeComposite parent) {
        super("Bed " + counter++, position, size, parent, rotation, ElementColor.DarkBlue());
        elementPainter = new BedPainter(this);
    }

    @Override
    public Bed cloneElement() {
        return new Bed((Point)super.getPosition().clone(), (Dimension) super.getSize().clone(), super.getRotation(), super.getParent());
    }
}
