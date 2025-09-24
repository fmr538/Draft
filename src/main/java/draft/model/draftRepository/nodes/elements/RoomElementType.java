package draft.model.draftRepository.nodes.elements;

import draft.model.draftRepository.DraftNode;

public enum RoomElementType {
    BED("Bed"),
    DOOR("Door"),
    CUPBOARD("Cupboard"),
    DESK("Desk"),
    SINK("Sink"),
    TOILET("Toilet"),
    WATERHEATER("Water Heater"),
    TUBSHOWER("Tub Shower"),
    WASHINGMACHINE("Washing Machine");

    private final String element;

    RoomElementType(String element) {
        this.element = element;
    }

    public static RoomElementType getElementFromString(String typeStr) {
        return switch (typeStr) {
            case "Bed" -> BED;
            case "Door" -> DOOR;
            case "Cupboard" -> CUPBOARD;
            case "Desk" -> DESK;
            case "Sink" -> SINK;
            case "Toilet" -> TOILET;
            case "WaterHeater", "Water Heater" -> WATERHEATER;
            case "TubShower", "Tub Shower" -> TUBSHOWER;
            case "WashingMachine", "Washing Machine" -> WASHINGMACHINE;
            default -> null;
        };
    }

    public static String getElementFromNode(DraftNode e) {
        return getElementFromString(e.getClass().getSimpleName()).toString();
    }

    @Override
    public String toString() {
        return element;
    }
}
