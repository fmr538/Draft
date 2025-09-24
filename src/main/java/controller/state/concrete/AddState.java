package draft.controller.state.concrete;

import draft.controller.commands.implementation.AddCommand;
import draft.controller.state.State;
import draft.core.ApplicationFramework;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.model.draftRepository.nodes.elements.*;
import draft.view.painters.*;
import draft.view.swing.MainFrame;
import draft.view.swing.ProjectView;
import draft.view.swing.RoomView;
import draft.view.tree.DraftTree;
import draft.view.tree.model.DraftTreeItem;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class AddState implements State {

    @Override
    public void mouseDrag(int x, int y) {

    }

    @Override
    public void mousePress(int x, int y, DraftTreeItem node) {
        Point mousePoint = new Point(x, y);
        RoomElementInfo result = showFurnitureDialog();
        if (result.getType() != null) {
            if (node != null) {
                DraftNode draftNode = node.getDraftNode();
                if (draftNode instanceof Room room) {
                    RoomElement element = RoomElementFactory.createElement(
                            result.getType(),
                            mousePoint,
                            new Dimension(result.getWidth(), result.getHeight()),
                            room
                    );
                    if (checkCollisionAndBounds(element))
                        return;
                    ProjectView projectView = MainFrame.getInstance().getProjectView();
                    if (projectView != null) {
                        RoomView roomView = projectView.getCurrentRoomView();
                        if (roomView != null) {
                            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(
                                    new AddCommand(MainFrame.getInstance().getDraftTree().findNode(
                                            roomView.getRoom()),element));
                            roomView.repaint();
                        }
                    }
                }
            } else {
                ProjectView projectView = MainFrame.getInstance().getProjectView();
                if (projectView != null) {
                    RoomView roomView = projectView.getCurrentRoomView();
                    if (roomView != null) {
                        Room room = roomView.getRoom();
                        node = MainFrame.getInstance().getDraftTree().findNodeByName(room.getName());
                        RoomElement element = RoomElementFactory.createElement(
                                result.getType(),
                                mousePoint,
                                new Dimension(result.getWidth(), result.getHeight()),
                                room
                        );
                        if (checkCollisionAndBounds(element))
                            return;
                        ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(
                                new AddCommand(MainFrame.getInstance().getDraftTree().findNode(
                                        roomView.getRoom()),element));
                        roomView.repaint();
                        }
                    }
                }
            }
        }

    @Override
    public void mouseRelease(int x, int y) {

    }

    private RoomElementInfo showFurnitureDialog() {
        JDialog dialog = new JDialog(MainFrame.getInstance(), "Choose Furniture", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout(5, 5));
        dialog.setLocationRelativeTo(null);
        final RoomElementInfo result = new RoomElementInfo();

        // Dropdown for furniture selection
        JComboBox<RoomElementType> furnitureDropdown = new JComboBox<>(RoomElementType.values());

        // Input fields for size
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Select Furniture:"));
        inputPanel.add(furnitureDropdown);

        inputPanel.add(new JLabel("Width (px):"));
        inputPanel.add(widthField);

        inputPanel.add(new JLabel("Height (px):"));
        inputPanel.add(heightField);

        dialog.add(inputPanel, BorderLayout.NORTH);

        // Painter panel to preview the selected furniture
        JPanel painterPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                RoomElementType furnitureType = (RoomElementType) furnitureDropdown.getSelectedItem();
                if (furnitureType != null) {
                    int width = Integer.parseInt(widthField.getText().isEmpty() ? "50" : widthField.getText());
                    int height = Integer.parseInt(heightField.getText().isEmpty() ? "50" : heightField.getText());
                    Point point = new Point(getWidth() / 2, getHeight()/ 2);
                    ElementPainter painter = ElementPainterFactory.createElementPainterFactory(
                            furnitureType,
                            point,
                            width,
                            height,
                            null
                    );
                    painter.paint(g);
                }
            }
        };

        DocumentListener updateListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                painterPanel.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                painterPanel.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                painterPanel.repaint();
            }
        };
        widthField.getDocument().addDocumentListener(updateListener);
        heightField.getDocument().addDocumentListener(updateListener);
        furnitureDropdown.addActionListener(e -> painterPanel.repaint());

        painterPanel.setPreferredSize(new Dimension(400, 150));
        dialog.add(painterPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(e -> {
            try {
                result.setType((RoomElementType) furnitureDropdown.getSelectedItem());
                result.setWidth(Integer.parseInt(widthField.getText()));
                result.setHeight(Integer.parseInt(heightField.getText()));
                dialog.dispose();
            } catch (NumberFormatException ex) {
                ApplicationFramework.getInstance().getMessageGenerator().fillForms();
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        furnitureDropdown.addActionListener(e -> painterPanel.repaint());

        dialog.setVisible(true);
        return result;
    }
}
