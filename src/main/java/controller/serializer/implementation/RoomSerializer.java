package draft.controller.serializer.implementation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;

import java.awt.*;
import java.io.IOException;

public class RoomSerializer extends StdSerializer<Room> {

    public RoomSerializer() {
        this(Room.class);
    }
    protected RoomSerializer(Class<Room> t) { super(t); }

    @Override
    public void serialize(Room room, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "room");
        jsonGenerator.writeStringField("name", room.getName());
        jsonGenerator.writeObjectField("size", room.getSize());
        jsonGenerator.writeNumberField("zoom", room.getCurrentZoom());

        jsonGenerator.writeArrayFieldStart("children");
        for (DraftNode child : room.getChildren()) {
            jsonGenerator.writeStringField("name", child.getName());
            jsonGenerator.writeObjectField("position", ((RoomElement)child).getPosition());
            jsonGenerator.writeObjectField("dimension", ((RoomElement)child).getSize());
            jsonGenerator.writeNumberField("rotation",((RoomElement)child).getRotation());
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
