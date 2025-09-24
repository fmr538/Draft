package draft.view.painters;

import draft.model.draftRepository.nodes.elements.WaterHeater;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class WaterHeaterPainter extends RoomElementPainter {

    public WaterHeaterPainter(WaterHeater model) {
        super(model);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int x = node.getPosition().x;
        int y = node.getPosition().y;
        double width = node.getSize().getWidth();
        double height = node.getSize().getHeight();

        g.drawOval((int)(x - width/2),(int)(y - height/2), (int) width, (int) height);

        GeneralPath xShape = new GeneralPath();
        xShape.moveTo(x - width/4, y - height/4);
        xShape.lineTo(x + width/4, y + height/4);
        xShape.moveTo(x + width/4, y - height/4);
        xShape.lineTo(x - width/4, y + height/4);
        xShape.closePath();

        ((Graphics2D)(g)).draw(xShape);
    }
}
