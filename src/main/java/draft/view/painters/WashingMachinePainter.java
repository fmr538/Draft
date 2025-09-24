package draft.view.painters;

import draft.model.draftRepository.nodes.elements.WashingMachine;

import java.awt.*;

public class WashingMachinePainter extends RoomElementPainter {

    public WashingMachinePainter(WashingMachine model) {
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

        g.drawOval((int) (x - width/2 + 0.15*width), (int) (y - height/2 + 0.15*height),
                (int) (0.7*width) , (int) (0.7*height));
    }
}
