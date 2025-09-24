package draft.view.painters;

import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;

import java.awt.*;

public interface ElementPainter {
    void paint(Graphics g);
    void updateTransform();
    DraftNode getNode();
}
