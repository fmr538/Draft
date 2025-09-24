package draft.controller.state.concrete;

import draft.controller.commands.implementation.EditCommand;
import draft.controller.state.State;
import draft.core.ApplicationFramework;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;
import draft.view.swing.ProjectView;
import draft.view.swing.RoomView;
import draft.view.tree.model.DraftTreeItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ResizeState implements State {
    private RoomElement element;
    private RoomElement copy;

    @Override
    public void mouseDrag(int x, int y) {
        if (element != null) {
            Point mousePoint = new Point(x, y);
            Point elementPoint = new Point(
                    element.getPosition().x - element.getSize().width / 2,
                    element.getPosition().y - element.getSize().height / 2
            );
            int width = mousePoint.x - elementPoint.x;
            int height = mousePoint.y - elementPoint.y;
            width = Math.max(width, 2);
            height = Math.max(height, 2);
            Dimension dim = new Dimension(width, height);
            element.setSize(dim);
            RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
            if (roomView != null) {
                roomView.getRoomElementsView().repaint();
            }
        }
    }

    @Override
    public void mousePress(int x, int y, DraftTreeItem node) {
        ProjectView projectView = MainFrame.getInstance().getProjectView();
        RoomView roomView = projectView.getCurrentRoomView();
        roomView.getRoomElementsView().getSelection().clear();
        Point mousePoint = new Point(x, y);
        element = roomView.findElementOnMousePosition(mousePoint);
        if (element != null) {
            copy = element.cloneElement();
            roomView.getRoomElementsView().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    @Override
    public void mouseRelease(int x, int y) {
        if (checkCollisionAndBounds(element)){
            element.setRotation(copy.getRotation());
            element.setPosition(copy.getPosition());
            element.setSize(copy.getSize());
        }else
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(
                    new EditCommand(new ArrayList<>(Collections.singletonList(copy)),new ArrayList<>(Collections.singletonList(element))));
        element = null;
        ProjectView projectView = MainFrame.getInstance().getProjectView();
        RoomView roomView = projectView.getCurrentRoomView();
        roomView.getRoomElementsView().setCursor(Cursor.getDefaultCursor());
        roomView.getRoomElementsView().getSelection().clear();
        roomView.getRoomElementsView().repaint();
    }
}
