package draft.view.painters;

import draft.model.draftRepository.nodes.elements.Bed;

import java.awt.*;

public class BedPainter extends RoomElementPainter {

    public BedPainter(Bed model) {
        super(model);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int x = node.getPosition().x;
        int y = node.getPosition().y;
        double width = node.getSize().getWidth();
        double height = node.getSize().getHeight();

        g.drawRoundRect((int) (x - width/2), (int) (y - height/2), node.getSize().width,
                node.getSize().height, (int) (0.05*height), (int) (0.05*height));

        g.drawRoundRect((int) (x + 0.175*(width)),(int) (y - height/2 + 0.15*height),
                (int) (0.25*width),(int) (0.7*height),(int)(0.1*height),(int)(0.1*height));
    }
}
