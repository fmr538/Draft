package draft.controller.state.concrete;

import draft.controller.state.State;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.model.draftRepository.nodes.elements.Lasso;
import draft.view.painters.ElementPainter;
import draft.view.painters.LassoPainter;
import draft.view.swing.MainFrame;
import draft.view.swing.RoomView;
import draft.view.tree.model.DraftTreeItem;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class SelectState implements State {
    Lasso lasso;

    public SelectState() {}

    @Override
    public void mouseDrag(int x, int y) {
        lasso.setEnd(new Point(x,y));
        MainFrame.getInstance().getProjectView().getCurrentRoomView().getRoomElementsView().repaint();
    }

    @Override
    public void mousePress(int x, int y, DraftTreeItem node) {
        this.lasso = new Lasso(new Point(x,y));
        RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
        lasso.addSubscriber(roomView);
        roomView.getRoomElementsView().setLasso(new LassoPainter(lasso));
    }

    @Override
    public void mouseRelease(int x, int y) {
        RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
        roomView.getRoomElementsView().getSelection().clear();
        lasso.setEnd(new Point(x,y));
        lasso.normalize();

        Room room = roomView.getRoom();
        for (DraftNode node : room.getChildren()) {
            if (node instanceof RoomElement element) {
                ElementPainter painter = element.getPainter();
                if (lasso.contains(painter)){
                    roomView.getRoomElementsView().getSelection().add(element);
                }
            }
        }

        RoomElement element = roomView.findElementOnMousePosition(new Point(x,y));
        if (!roomView.getRoomElementsView().getSelection().getItems().contains(element))
            roomView.getRoomElementsView().getSelection().add(element);

        roomView.getRoomElementsView().setLasso(null);
        System.out.println(roomView.getRoomElementsView().getSelection().toString());
        roomView.getRoomElementsView().repaint();
    }
}
