package draft.model.draftRepository.nodes.elements;

import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;

import java.awt.*;

public final class RoomElementFactory {
    public static RoomElement createElement(RoomElementType elementType, Point position, Dimension size, DraftNodeComposite parent) {
        return switch (elementType) {
            case BED -> new Bed(position, size, 0, parent);
            case DOOR -> new Door(position, size, 0, parent);
            case CUPBOARD -> new Cupboard(position, size, 0, parent);
            case DESK -> new Desk(position, size, 0, parent);
            case SINK -> new Sink(position, size, 0, parent);
            case TOILET -> new Toilet(position, size, 0, parent);
            case WATERHEATER -> new WaterHeater(position, size, 0, parent);
            case TUBSHOWER -> new TubShower(position, size, 0, parent);
            case WASHINGMACHINE -> new WashingMachine(position, size, 0, parent);
        };
    }
}
