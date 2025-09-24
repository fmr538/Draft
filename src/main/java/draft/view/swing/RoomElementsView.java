package draft.view.swing;

import draft.controller.state.StateMouseListener;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.NodeSelection;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.ElementPainter;
import draft.view.painters.GridPainter;
import draft.view.painters.LassoPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RoomElementsView extends JPanel {
    private final Room room;
    private LassoPainter lasso;
    private GridPainter grid;
    private AffineTransform affineTransform;
    private NodeSelection selection;
    private Point translate;

    RoomElementsView(Room room) {
        this.room = room;
        StateMouseListener mouseListener = new StateMouseListener();
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addMouseWheelListener(mouseListener);
        affineTransform = new AffineTransform();
        selection = new NodeSelection();
    }

    void calculateScalingFactor() {
        double viewWidth = getWidth();
        double viewHeight = getHeight();
        double roomWidth = room.getSize().width;
        double roomHeight = room.getSize().height;

        double scaleX = viewWidth / roomWidth;
        double scaleY = viewHeight / roomHeight;
        double scalingFactor = Math.min(scaleX, scaleY);

        double translateX = (viewWidth - roomWidth * scalingFactor) / 2.0;
        double translateY = (viewHeight - roomHeight * scalingFactor) / 2.0;

        double currentZoom = room.getCurrentZoom();
        affineTransform = new AffineTransform();

        affineTransform.translate(currentZoom * translateX, currentZoom * translateY);
        affineTransform.scale(currentZoom * scalingFactor, currentZoom * scalingFactor);

        Point translate = getTranslate();
        if (translate != null) {
            affineTransform.translate(-translate.x*0.4, -translate.y*0.4);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        calculateScalingFactor();
        g2d.transform(affineTransform);

        room.getPainter().paint(g2d);

        for (DraftNode node : room.getChildren()) {
            if (node instanceof RoomElement element) {
                ElementPainter painter = element.getPainter();
                // Moramo da kreiramo kopiju Graphics
                // Jer na nju cemo da setujemo transforms od objekta koji crtamo
                // i posle unistavamo kreirani Graphics sto ne afektuje
                // glavni graphics za crtanje od paintComponent a samim tim i sledecih elemenata
                Graphics2D elementGraphics = (Graphics2D) g2d.create();
                painter.paint(elementGraphics);
                elementGraphics.dispose();
            }
        }

        if (lasso != null) {lasso.paint(g2d);}
        if (grid != null) {grid.paint(g2d);}
    }

    public void repaintElement(RoomElement element) {
        Graphics g = this.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        calculateScalingFactor();
        g2d.setTransform(affineTransform);
        element.getPainter().paint(g2d);
    }

    public Object getAffineTransform() {
        return affineTransform;
    }

    public void setLasso(LassoPainter lassoPainter) {
        lasso = lassoPainter;
        repaint();
    }

    public void setGrid(GridPainter grid) {
        this.grid = grid;
        repaint();
    }

    public NodeSelection getSelection() {
        return selection;
    }

    public void setTranslate(Point translate) {
        this.translate = translate;
    }

    public Point getTranslate() {
        return translate;
    }
}
