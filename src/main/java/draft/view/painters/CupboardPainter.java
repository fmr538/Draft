package draft.view.painters;

import draft.model.draftRepository.nodes.elements.Cupboard;

import java.awt.*;

public class CupboardPainter extends RoomElementPainter {

    public CupboardPainter(Cupboard model) {
        super(model);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int x = node.getPosition().x;
        int y = node.getPosition().y;
        double width = node.getSize().getWidth();
        double height = node.getSize().getHeight();

        g.drawRect((int) (x - width/2), (int) (y - height/2), (int)(width),
                (int)height);

        g.drawLine(x,(int)(y - height/2),x,(int)(y + height/2));
        g.drawLine((int)(x - width*0.1),y,(int)(x - width*0.1),y);
        g.drawLine((int)(x + width*0.1),y,(int)(x + width*0.1),y);
    }
}
