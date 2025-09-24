package draft.view.painters;

import draft.model.draftRepository.nodes.elements.Door;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

public class DoorPainter extends RoomElementPainter {

    public DoorPainter(Door model) {
        super(model);
    }

    @Override
    public void updateTransform() {
        double rotationRequired = Math.toRadians(node.getRotation());
        double x = node.getPosition().getX() + node.getSize().getWidth() / 2.0;
        double y = node.getPosition().getY(); // + node.getSize().getHeight(); // / 2.0;
        elementTransform = AffineTransform.getRotateInstance(rotationRequired, x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int x = node.getPosition().x;
        int y = node.getPosition().y;
        double width = node.getSize().getWidth();
        double height = node.getSize().getHeight();

        g.drawLine(x, (int) (y-height/2),x, (int) (y+height/2));
        Arc2D quarterCircle = new Arc2D.Double(x-width, y-height/2, width*2, height*2, 90, -90, Arc2D.OPEN);
        ((Graphics2D)g).draw(quarterCircle);
    }
}
