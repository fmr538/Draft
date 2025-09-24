package draft.view.painters;

import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;
import draft.model.draftRepository.nodes.elements.*;

import java.awt.*;

public class ElementPainterFactory {

    public static ElementPainter createElementPainterFactory(RoomElementType furnitureType,Point point, int width, int height, DraftNodeComposite parent) {
        return switch (furnitureType) {
            case BED -> new BedPainter(new Bed(point, new Dimension(width, height), 0, parent));
            case DOOR -> new DoorPainter(new Door(point, new Dimension(width, height), 0, parent));
            case CUPBOARD -> new CupboardPainter(new Cupboard(point, new Dimension(width, height), 0, parent));
            case DESK -> new DeskPainter(new Desk(point, new Dimension(width, height), 0, parent));
            case SINK -> new SinkPainter(new Sink(point, new Dimension(width, height), 0, parent));
            case TOILET -> new ToiletPainter(new Toilet(point, new Dimension(width, height), 0, parent));
            case WATERHEATER -> new WaterHeaterPainter(new WaterHeater(point, new Dimension(width, height), 0, parent));
            case TUBSHOWER -> new TubShowerPainter(new TubShower(point, new Dimension(width, height), 0, parent));
            case WASHINGMACHINE -> new WashingMachinePainter(new WashingMachine(point, new Dimension(width, height), 0, parent));
        };
    }

    public static ElementPainter createPainterForElement(RoomElement element) {
        return switch (element) {
            case Bed bed -> new BedPainter(bed);
            case Door door -> new DoorPainter(door);
            case Cupboard cupboard -> new CupboardPainter(cupboard);
            case Desk desk -> new DeskPainter(desk);
            case Sink sink -> new SinkPainter(sink);
            case Toilet toilet -> new ToiletPainter(toilet);
            case WaterHeater waterHeater -> new WaterHeaterPainter(waterHeater);
            case TubShower tubShower -> new TubShowerPainter(tubShower);
            case WashingMachine washingMachine -> new WashingMachinePainter(washingMachine);
            default -> null;
        };
    }
}
