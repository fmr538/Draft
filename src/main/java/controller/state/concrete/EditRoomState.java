package draft.controller.state.concrete;

import draft.controller.state.State;
import draft.view.swing.MainFrame;
import draft.view.swing.ProjectView;
import draft.view.swing.RoomView;
import draft.view.tree.model.DraftTreeItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class EditRoomState implements State {
    public EditRoomState() {
    }


    @Override
    public void mouseDrag(int x, int y) {

    }

    @Override
    public void mousePress(int x, int y, DraftTreeItem node) {
        System.out.println("press in editRoomState");
        Dimension size = showRoomInputDialog();
        if (size != null) {
            RoomView roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
            roomView.getRoom().setSize(size);
            MainFrame.getInstance().getStateManager().notifySubscribers(roomView.getRoom());
            roomView.repaint();
        }
    }

    @Override
    public void mouseRelease(int x, int y) {
    }

    public static Dimension showRoomInputDialog() {
        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Enter Room Width:"));
        panel.add(widthField);
        panel.add(Box.createVerticalStrut(10)); // Spacer
        panel.add(new JLabel("Enter Room Height:"));
        panel.add(heightField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Enter Room Dimensions",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());

                if (width > 0 && height > 0) {
                    return new Dimension(width, height);
                } else {
                    JOptionPane.showMessageDialog(null, "Width and height must be positive integers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid integers for width and height.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }

        return null;
    }
}
