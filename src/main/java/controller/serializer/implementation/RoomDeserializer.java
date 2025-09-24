package draft.controller.serializer.implementation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class RoomDeserializer extends StdDeserializer<Room> {

    public RoomDeserializer() { super(Room.class);
    }
    protected RoomDeserializer(Class<Room> t) {
        super(t);
    }

    @Override
    public Room deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        var node = jsonParser.getCodec().readTree(jsonParser);

        Room room = new Room();

        room.setName(node.get("name").toString());
        room.setCurrentZoom(Double.parseDouble(node.get("zoom").toString()));
        var size = node.get("size");
        room.setSize(new Dimension(Integer.parseInt(size.get("width").toString()),Integer.parseInt(size.get("height").toString())));
        var children = node.get("children");
        if(children != null && children.isArray())
        {
            for(var child : (ArrayList<JsonNode>)children){
                room.addChild(jsonParser.getCodec().treeToValue(child, RoomElement.class));
            }
        }

        return room;
    }
}
