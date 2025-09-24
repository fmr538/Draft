package draft.controller.state.concrete;

import draft.controller.commands.implementation.DeleteCommand;
import draft.controller.state.State;
import draft.core.ApplicationFramework;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;
import draft.view.swing.ProjectView;
import draft.view.swing.RoomView;
import draft.view.tree.DraftTree;
import draft.view.tree.model.DraftTreeItem;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class DeleteState implements State {

    @Override
    public void mouseDrag(int x, int y) {
        MainFrame.getInstance().getStateManager().getSelectState().mouseDrag(x,y);
    }

    @Override
    public void mousePress(int x, int y, DraftTreeItem node) {MainFrame.getInstance().getStateManager().getSelectState().mousePress(x,y,node);}

    @Override
    public void mouseRelease(int x, int y) {
        MainFrame.getInstance().getStateManager().getSelectState().mouseRelease(x,y);
        RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
        DraftTreeItem roomItem = MainFrame.getInstance().getDraftTree().findNode(roomView.getRoom());
        ArrayList<RoomElement> items = (ArrayList<RoomElement>) roomView.getRoomElementsView().getSelection().getItems();

        if (items.isEmpty()) {
            RoomElement element = roomView.findElementOnMousePosition(new Point(x, y));
            if (element != null) {
                items.add(element);
            }
        }

        ArrayList<DraftNode> elements = new ArrayList<>(items);

        ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(
                new DeleteCommand(roomItem,elements));
        roomView.getRoomElementsView().getSelection().clear();
    }
}
