package draft.controller.state.concrete;

import draft.controller.commands.implementation.EditCommand;
import draft.controller.state.State;
import draft.core.ApplicationFramework;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;
import draft.view.swing.ProjectView;
import draft.view.swing.RoomView;
import draft.view.tree.model.DraftTreeItem;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class MoveState implements State {
    private Point start;
    private Point origin;
    private ArrayList<RoomElement> pointCopy;

    public MoveState() {pointCopy = new ArrayList<>();}


    @Override
    public void mouseDrag(int x, int y) {
        RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
        ArrayList<RoomElement> items = (ArrayList<RoomElement>) roomView.getRoomElementsView().getSelection().getItems();

        Point end = new Point(x,y);
        Point delta = new Point(start.x - end.x, start.y - end.y);

        if (!items.isEmpty()) {
            for (RoomElement node : items) {
                if (node != null) {
                    Point newPoint = new Point(node.getPosition().x - delta.x, node.getPosition().y - delta.y);
                    node.setPosition(applyBoundsAndSnapping(newPoint, roomView, node));
                }
            }
        } else {
            Point currRoomPoint = roomView.getRoomElementsView().getTranslate();
            if (currRoomPoint == null) currRoomPoint = new Point(0, 0);

            Point newRoomPoint = new Point(currRoomPoint.x - delta.x, currRoomPoint.y - delta.y);
            roomView.getRoomElementsView().setTranslate(newRoomPoint);
        }

        start = end;
        roomView.getRoomElementsView().repaint();
    }



    private Point applyBoundsAndSnapping(Point newPoint, RoomView roomView, RoomElement node) {
        if (newPoint == null) {
            return null;
        }
        int roomWidth = (int) roomView.getRoom().getSize().getWidth();
        int roomHeight = (int) roomView.getRoom().getSize().getHeight();
        int adjustedX = newPoint.x;
        int adjustedY = newPoint.y;
        int width;
        int height;

        if(node.getRotation() == 270 || node.getRotation() == 90) {
            width = node.getSize().height;
            height = node.getSize().width;
        }else{
            width = node.getSize().width;
            height = node.getSize().height;
        }

        if (newPoint.x < 10 + width/2) {
            adjustedX = width/2;
        } else if (newPoint.x > roomWidth - 10 - width/2) {
            adjustedX = roomWidth - width/2;
        }

        if (newPoint.y < 10 + height/2) {
            adjustedY = height/2;
        } else if (newPoint.y > roomHeight - 10 - height/2) {
            adjustedY = roomHeight - height/2;
        }
        return new Point(adjustedX, adjustedY);
    }

    @Override
    public void mousePress(int x, int y, DraftTreeItem node) {
        ProjectView projectView = MainFrame.getInstance().getProjectView();
        RoomView roomView = projectView.getCurrentRoomView();
        roomView.getRoomElementsView().setCursor(new Cursor(Cursor.MOVE_CURSOR));
        start = new Point(x, y);
        origin = new Point(x, y);

        if (roomView.getRoomElementsView().getSelection().getItems().isEmpty() &&
                roomView.findElementOnMousePosition(start) != null) {
            roomView.getRoomElementsView().getSelection().add(roomView.findElementOnMousePosition(start));
        }

        if (!roomView.getRoomElementsView().getSelection().getItems().isEmpty()) {
            pointCopy.clear();
            for (RoomElement e : roomView.getRoomElementsView().getSelection().getItems()) {
                if (e != null) {
                    pointCopy.add(e.cloneElement());
                }
            }
        }
    }

    @Override
    public void mouseRelease(int x, int y) {
        ProjectView projectView = MainFrame.getInstance().getProjectView();
        RoomView roomView = projectView.getCurrentRoomView();

        ArrayList<RoomElement> items = (ArrayList<RoomElement>) roomView.getRoomElementsView().getSelection().getItems();
        boolean hasCollision = false;

        if (!items.isEmpty()) {
            for (RoomElement node : items) {
                if (checkCollisionAndBounds(node)) {
                    hasCollision = true;
                    break;
                }
            }
            if (hasCollision) {
//                System.out.println("Collision detected. Reverting to original positions.");
                revertPositions(items);
            }else {
                Point delta = new Point(start.x - origin.x, start.y - origin.y);
                ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(
                        new EditCommand(new ArrayList<>(items), new ArrayList<>(pointCopy)));
            }
        }

        roomView.getRoomElementsView().setCursor(Cursor.getDefaultCursor());
        roomView.getRoomElementsView().getSelection().clear();
        pointCopy.clear();
        roomView.getRoomElementsView().repaint();
    }

    private void revertPositions(ArrayList<RoomElement> items) {
        for (int i = 0; i < items.size(); i++) {
            RoomElement node = items.get(i);
            if (node != null && i < pointCopy.size()) {
                RoomElement originalNode = pointCopy.get(i);
                if (originalNode != null) {
                    node.setPosition(originalNode.getPosition());
                }
            }
        }
    }
}
