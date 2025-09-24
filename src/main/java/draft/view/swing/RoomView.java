package draft.view.swing;

import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Building;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.model.observable.Subscriber;
import draft.view.painters.ElementPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class RoomView extends JPanel implements Subscriber {
    private Room room;
    private Building building;
    private RoomElementsView roomElementsView;
    private boolean editable;
    private final ArrayList<RoomElement> listOfCopiedElements;
    private ZoomView zoomStateView;

    public RoomView(Room room) {
        super(new GridBagLayout());

        this.room = room;
        this.room.addSubscriber(this);
        zoomStateView = new ZoomView(room.getCurrentZoom());

        listOfCopiedElements = new ArrayList<>();

        for (DraftNode node : room.getChildren()) {
            if (node instanceof RoomElement roomElement) {
                ElementPainter painter = roomElement.getPainter();
                painter.updateTransform();
            }
        }

        if (room.getParent() instanceof Building)
            building = (Building) room.getParent();
        else
            building = null;

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        JLabel label = new JLabel(room.getName());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        StateToolbar stateToolbar = new StateToolbar(MainFrame.getInstance().getActionManager().getStateActions());
        stateToolbar.add(new JToolBar.Separator(), 1);
        stateToolbar.add(new JToolBar.Separator(), 6);
        stateToolbar.add(new JToolBar.Separator(), 9);
        add(stateToolbar, gc);
        stateToolbar.add(zoomStateView);


        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        roomElementsView = new RoomElementsView(room);
        add(roomElementsView, gc);
    }

    public Building getBuilding() {
        return building;
    }

    public Room getRoom() {
        return room;
    }

    public RoomElementsView getRoomElementsView() {
        return roomElementsView;
    }

    @Override
    public void update(Object message) {
        roomElementsView.paintComponent(this.getGraphics());
        System.out.println("room update");
    }

    public RoomElement findElementOnMousePosition(Point mousePosition) {
        RoomElement roomElement = null;
        for (DraftNode node : room.getChildren()) {
            if (node instanceof RoomElement) {
                RoomElement element = (RoomElement) node;
                int width;
                int height;

                if (element.getRotation() == 270 || element.getRotation() == 90) {
                    width = element.getSize().height;
                    height = element.getSize().width;
                }else{
                    width = element.getSize().width;
                    height = element.getSize().height;
                }

                Rectangle rect = new Rectangle(element.getPosition().x - width/2,
                                                element.getPosition().y - height/2,
                                                width,height);
                if (rect.contains(mousePosition)) {
                    roomElement = element;
                    break;
                }
            }
        }

        return roomElement;
    }

    public void addCopiedElement(RoomElement element) {
        if (element != null) {
            if (!listOfCopiedElements.contains(element)) {
                listOfCopiedElements.add(element);
            }
        }
    }

    public void removeCopiedElement(RoomElement element) {
        if (element != null) {
            listOfCopiedElements.remove(element);
        }
    }

    public void removeAllCopiedElements() {
        listOfCopiedElements.clear();
    }

    public boolean hasCopiedElements() {
        return !listOfCopiedElements.isEmpty();
    }

    public ArrayList<RoomElement> getCopiedElements() {
        return listOfCopiedElements;
    }

    public void handleZoom(int wheelRotation) {
        double zoom = room.getCurrentZoom();;
        if (wheelRotation < 0) {
            // Zoom in
            if(zoom < 2.0) {
                zoom += .10;
            }
        } else {
            // Zoom out
            if(zoom > 0.20) {
                zoom -= .10;
            }
        }
        room.setCurrentZoom(zoom);
        zoomStateView.update(zoom);
        repaint();
    }

//    private void handleMouseEvent(MouseEvent event) {
////        if (stateManager.getCurrent() instanceof EditRoomState) {
////            stateManager.getCurrent().handleRequest(this);
////        } else {
////            stateManager.getCurrent().handleRequest(event.getLocationOnScreen());
////        }
////            RoomDimensionsView roomDimensionView = new RoomDimensionsView(room);
////            int result = JOptionPane.showConfirmDialog(
////                    null,
////                    roomDimensionView,
////                    "Edit Room dimensions",
////                    JOptionPane.OK_CANCEL_OPTION,
////                    JOptionPane.PLAIN_MESSAGE
////            );
////            if (result == JOptionPane.OK_OPTION) {
////                try {
////                    int width = Integer.parseInt(roomDimensionView.getRoomWidth());
////                    int height = Integer.parseInt(roomDimensionView.getRoomHeight());
////                    Dimension size = new Dimension(width, height);
////                    room.setSize(size);
////                    DraftRepository repository = ApplicationFramework.getInstance().getDraftRepository();
////                    repository.notifySubscribers(repository.getSelectedProject());
////                } catch (NumberFormatException e) {
////                    System.out.println(e.getMessage());
////                }
////            }
//    }
}
