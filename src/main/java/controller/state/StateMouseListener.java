package draft.controller.state;

import draft.view.swing.MainFrame;
import draft.view.swing.ProjectView;
import draft.view.swing.RoomView;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class StateMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        AffineTransform at = getAt(); // Your transformation
        Point2D pt = e.getPoint();
        System.out.println(e.getPoint());
        try {
            at.inverseTransform(pt,pt);
            System.out.println("Transformed mouse coordinates: " + pt.getX() + ", " + pt.getY());
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
        }
        MainFrame.getInstance().getStateManager().getCurrentState().mousePress((int) pt.getX(), (int) pt.getY(), null);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        AffineTransform at = getAt();
        Point2D pt = e.getPoint();
        try {
            at.inverseTransform(pt,pt);
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
        }

        MainFrame.getInstance().getStateManager().getCurrentState().mouseRelease((int)pt.getX(),(int)pt.getY());
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        AffineTransform at = getAt();
        Point2D pt = e.getPoint();
        try {
            at.inverseTransform(pt, pt);
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
            return;
        }

        int transformedX = (int) pt.getX();
        int transformedY = (int) pt.getY();

//        System.out.println("Mouse Dragged - Transformed X: " + transformedX + ", Y: " + transformedY);
        MainFrame.getInstance().getStateManager().getCurrentState().mouseDrag(transformedX, transformedY);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        MainFrame mainFrame = MainFrame.getInstance();
        ProjectView projectView = mainFrame.getProjectView();
        if (projectView != null) {
            RoomView roomView = projectView.getCurrentRoomView();
            if (roomView != null) {
                roomView.handleZoom(e.getWheelRotation());
            }
        }

    }

    private AffineTransform getAt()
    {
        return (AffineTransform) MainFrame.getInstance().getProjectView().getCurrentRoomView().getRoomElementsView().getAffineTransform();
    }
}
