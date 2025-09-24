package draft.view.painters;

import draft.model.draftRepository.nodes.SizeRatio;
import draft.model.draftRepository.nodes.elements.Toilet;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;

public class ToiletPainter extends RoomElementPainter {

    public ToiletPainter(Toilet model) {
        super(model);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int x = node.getPosition().x;
        int y = node.getPosition().y;
        double width = node.getSize().getWidth();
        double height = node.getSize().getHeight();

        Graphics2D g2d = (Graphics2D) g;

        double arcX = x - width / 2;
        double arcY = y - height*0.8;
        double arcWidth = width;
        double arcHeight = height*0.8;

        Arc2D outerCircle = new Arc2D.Double(arcX, arcY, arcWidth, arcHeight, 0, -180, Arc2D.CHORD);
        Arc2D innerCircle = new Arc2D.Double(arcX + arcWidth/4, arcY + arcHeight/3, arcWidth/2, arcHeight/2, 0, -180, Arc2D.CHORD);

        g2d.draw(outerCircle);
        g2d.draw(innerCircle);
        g2d.drawRect((int) (x - width/2), (int) (y - 6*height/10),(int)width, (int) (height*0.2));


    }
}
