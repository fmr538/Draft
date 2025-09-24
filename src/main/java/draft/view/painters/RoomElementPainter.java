package draft.view.painters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class RoomElementPainter implements ElementPainter {
    @JsonIgnore
    protected RoomElement node;
    protected AffineTransform elementTransform;

    RoomElementPainter(RoomElement element) {
        this.node = element;
        elementTransform = new AffineTransform();
    }

    @Override
    public void updateTransform() {
        // Setujemo rotate u AffineTransform za ovaj element
        double rotationRequired = Math.toRadians(node.getRotation());
        int x = node.getPosition().x;
        int y = node.getPosition().y;
        elementTransform = AffineTransform.getRotateInstance(rotationRequired, x, y);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        ((Graphics2D) g).setPaint(node.getPaint());
        g2d.transform(elementTransform);
//        if (this.isSelected())
//            g2d.setPaint(Color.RED);
    }

    @Override
    public RoomElement getNode() {
        return node;
    }

    boolean isSelected(){
        return MainFrame.getInstance().getProjectView().getCurrentRoomView().getRoomElementsView().getSelection().getItems().contains(this.getNode());
    }
}
