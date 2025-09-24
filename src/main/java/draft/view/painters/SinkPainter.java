package draft.view.painters;

import draft.model.draftRepository.nodes.elements.Sink;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class SinkPainter extends RoomElementPainter {

    public SinkPainter(Sink model) {
        super(model);
    }

    @Override
    public void updateTransform() {
        double rotationRequired = Math.toRadians(node.getRotation());
        double x = node.getPosition().getX();
        double y = node.getPosition().getY() - ((double) 1 /6) * node.getSize().getHeight();
        elementTransform = AffineTransform.getRotateInstance(rotationRequired, x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int x = node.getPosition().x;
        int y = node.getPosition().y;
        double width = node.getSize().getWidth();
        double height = node.getSize().getHeight();

        int[] xP ={(int)(x-width/2), (int)(x + width/2), x};
        int[] yP ={(int)(y - height/2), (int)(y - height/2), (int)(y+height/2)};
        g.drawPolygon(xP, yP,3);

        g.drawLine(x,(int)(y - ((double) 1 /6)*height),x,(int)(y - ((double) 1 /6)*height));
    }
}
