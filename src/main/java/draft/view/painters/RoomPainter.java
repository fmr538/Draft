package draft.view.painters;

import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.SizeRatio;
import draft.model.draftRepository.nodes.elements.ElementColor;

import java.awt.*;

public class RoomPainter implements ElementPainter {
    private final Room room;

    public RoomPainter(Room room) { this.room = room; }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0,0,room.getSize().width, room.getSize().height);
    }

    @Override
    public void updateTransform() { }

    @Override
    public DraftNode getNode() {
        return room;
    }
}
