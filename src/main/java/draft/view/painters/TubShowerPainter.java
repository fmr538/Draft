package draft.view.painters;

import draft.model.draftRepository.nodes.elements.TubShower;

import java.awt.*;

public class TubShowerPainter extends RoomElementPainter {

    public TubShowerPainter(TubShower model) {
        super(model);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int x = node.getPosition().x;
        int y = node.getPosition().y;
        double width = node.getSize().getWidth();
        double height = node.getSize().getHeight();

        g.drawRect((int) (x - width/2), (int) (y - height/2), (int) (width),
                (int) (height));

        g.drawRoundRect((int) (x - width/2 + 0.05*width), (int) (y - height/2 + 0.1*height), (int) (0.9*width) ,
                (int) (0.8*height), (int) (0.5*width),(int) (0.6*height));
    }
}
