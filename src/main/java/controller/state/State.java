package draft.controller.state;

import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;
import draft.view.swing.RoomView;
import draft.view.tree.model.DraftTreeItem;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public interface State {
    void mouseDrag(int x, int y);
    void mousePress(int x, int y, DraftTreeItem node);
    void mouseRelease(int x, int y);
    default boolean checkCollisionAndBounds(RoomElement element) {
        RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
        if (element == null) {
            return false;
        }

        int width;
        int height;
        if (element.getRotation() == 270 || element.getRotation() == 90) {
            width = element.getSize().height;
            height = element.getSize().width;
        }else{
            width = element.getSize().width;
            height = element.getSize().height;
        }

        Rectangle nodeBounds = new Rectangle(
                element.getPosition().x - width / 2,
                element.getPosition().y - height / 2,
                width,
                height
        );

        for (DraftNode e : roomView.getRoom().getChildren()) {
            RoomElement other = (RoomElement) e;
            if (other != element && other != null) {
                int otherWidth;
                int otherHeight;
                if (other.getRotation() == 270 || other.getRotation() == 90) {
                    otherWidth = other.getSize().height;
                    otherHeight = other.getSize().width;
                }else{
                    otherWidth = other.getSize().width;
                    otherHeight = other.getSize().height;
                }

                Rectangle otherBounds = new Rectangle(
                        other.getPosition().x - otherWidth / 2,
                        other.getPosition().y - otherHeight / 2,
                        otherWidth,
                        otherHeight
                );

                if (nodeBounds.intersects(otherBounds)) {
                    return true;
                }
            }
        }

        if (roomView.getSize().width <= width)
            return true;
        if (roomView.getSize().height <= height)
            return true;
        if (element.getPosition().x - width/2 < 0)
            return true;
        else if (element.getPosition().x + width/2 > roomView.getRoom().getSize().width)
            return true;
        if (element.getPosition().y - height/2 < 0)
            return true;
        else return element.getPosition().y + height / 2 > roomView.getRoom().getSize().height;
    }
}
