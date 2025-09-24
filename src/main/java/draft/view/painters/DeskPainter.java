package draft.view.painters;

import draft.model.draftRepository.nodes.elements.Desk;

import java.awt.*;

public class DeskPainter extends RoomElementPainter {

    public DeskPainter(Desk model) {
        super(model);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int x = node.getPosition().x;
        int y = node.getPosition().y;
        double width = node.getSize().getWidth();
        double height = node.getSize().getHeight();

        g.drawRect((int) (x - width/2), (int) (y - height/2), (int)width,
                (int)height);

        int x1 = (int) (x - width/2 + 0.15*width);
        int x2 = (int) (x + width/2 - 0.15*width);
        int y1 = (int) (y - height/2 + 0.15*height);
        int y2 = (int) (y + height/2 - 0.15*height);

        g.drawLine(x1,y1,x1,y1);
        g.drawLine(x2,y2,x2,y2);
        g.drawLine(x1,y2,x1,y2);
        g.drawLine(x2,y1,x2,y1);
    }
}
