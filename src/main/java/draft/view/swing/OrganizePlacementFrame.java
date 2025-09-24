package draft.view.swing;

import draft.core.ApplicationFramework;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.painters.GridPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganizePlacementFrame extends JFrame {

    private JPanel roomPanel;  // Panel to display the room
    private JButton randomizeButton;
    private JButton closeButton;// Button to randomize elements
    private ArrayList<RoomElement> elements;
    private RoomView roomView;
    private GridPainter gridPainter;
    private Room roomCopy;
    private int maxWidth;
    private int maxHeight;
    private int rows;
    private int cols;

    public OrganizePlacementFrame(ArrayList<RoomElement> elements, RoomView roomView) {
        this.elements = elements;
        this.roomView = roomView;
        this.roomCopy = roomView.getRoom().cloneElement();
        roomView.getRoom().getChildren().clear();

        // Calculate maximum dimensions for grid steps
        maxHeight = 0;
        maxWidth = 0;
        for (RoomElement element : elements) {
            maxWidth = (int) Math.max(maxWidth, element.getSize().getWidth());
            maxHeight = (int) Math.max(maxHeight, element.getSize().getHeight());
        }
        System.out.println(maxWidth + " " + maxHeight);
        // Calculate steps based on room dimensions
        cols = (int) (roomView.getRoom().getSize().getWidth() / maxWidth);
        rows = (int) (roomView.getRoom().getSize().getHeight() / maxHeight);

        System.out.println("steps: " + rows + " " + cols);

        if (rows == 0 || cols == 0) {
            JOptionPane.showMessageDialog(this, "Invalid room or element size!");
            return;
        }

        gridPainter = new GridPainter(maxWidth, maxHeight, roomView.getRoom().getSize());
        roomView.getRoomElementsView().setGrid(gridPainter);

        // Setup layout and components
        setLayout(new BorderLayout());

        // Add the room view to the frame
        add(roomView.getRoomElementsView(), BorderLayout.CENTER);

        // Create and add the randomize button
        randomizeButton = new JButton("Randomize");
        randomizeButton.addActionListener(e -> randomizeElements());
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            dispose();
            roomView.getRoomElementsView().setGrid(null);
            ApplicationFramework.getInstance().getDraftRepository().notifySubscribers(
                    ApplicationFramework.getInstance().getDraftRepository().getSelectedProject()
            );
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(randomizeButton);
        controlPanel.add(closeButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Frame settings
        setLocationRelativeTo(null);
        setSize(800, 600);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Restore the original children to the room
                roomView.getRoom().setChildren(roomCopy.getChildren());

                // Repaint the room view to reflect the changes
                roomView.getRoomElementsView().repaint();
                roomView.getRoomElementsView().revalidate();

                // Notify other parts of the application about the changes
                ApplicationFramework.getInstance().getDraftRepository().notifySubscribers(
                        ApplicationFramework.getInstance().getDraftRepository().getSelectedProject()
                );

                dispose(); // Close the window
            }
        });

        System.out.println(roomView.getRoom().getSize());
        // Force repaint

        randomizeElements();
        revalidate();
        repaint();
    }

    // Randomize and place elements
    // Randomize and place elements
    private void randomizeElements() {
        // Shuffle elements
        Collections.shuffle(elements);

        // Clear existing children
        roomView.getRoom().getChildren().clear();

        // Recalculate grid placement for shuffled elements
        placeElementsInGrid(elements);

        // Force repaint
        roomView.getRoomElementsView().repaint();
        roomView.getRoomElementsView().revalidate();
    }



    // Places elements in a spiral order
    private void placeElementsInGrid(List<RoomElement> elements) {
        int n = rows;  // Number of rows
        int m = cols;  // Number of columns

        int rowStart = 0, rowEnd = n - 1, colStart = 0, colEnd = m - 1;
        int index = 0;

        while (rowStart <= rowEnd && colStart <= colEnd && index < elements.size()) {

            // Top row: left to right
            for (int i = colStart; i <= colEnd && index < elements.size(); i++) {
                placeElement(elements.get(index++), rowStart, i);
            }
            rowStart++;

            // Right column: top to bottom
            for (int i = rowStart; i <= rowEnd && index < elements.size(); i++) {
                placeElement(elements.get(index++), i, colEnd);
            }
            colEnd--;

            // Bottom row: right to left
            if (rowStart <= rowEnd) {
                for (int i = colEnd; i >= colStart && index < elements.size(); i--) {
                    placeElement(elements.get(index++), rowEnd, i);
                }
                rowEnd--;
            }

            // Left column: bottom to top
            if (colStart <= colEnd) {
                for (int i = rowEnd; i >= rowStart && index < elements.size(); i--) {
                    placeElement(elements.get(index++), i, colStart);
                }
                colStart++;
            }
        }
    }


    // Places a single element in the specified grid cell
    private void placeElement(RoomElement e, int row, int col) {
        int x, y;

        // Room dimensions
        int roomWidth = roomView.getRoom().getSize().width;
        int roomHeight = roomView.getRoom().getSize().height;

        // Calculate default upper-right corner position in grid box
        x = (int) (col * maxWidth + e.getSize().getWidth()/2);
        y = (int) (row * maxHeight + e.getSize().getHeight()/2);

        // Adjust for corners
        if (row == 0 && col == cols - 1) { // Top-right corner
            x = roomWidth - (int) e.getSize().getWidth()/2;
        } else if (row == rows - 1 && col == 0) { // Bottom-left corner
            x = (int) e.getSize().getWidth() / 2;
            y = roomHeight - (int) e.getSize().getHeight()/2;
        } else if (row == rows - 1 && col == cols - 1) { // Bottom-right corner
            x = roomWidth - (int) e.getSize().getWidth()/2;
            y = roomHeight - (int) e.getSize().getHeight()/2;
        }

        // Set position and add element
        e.setPosition(new Point(x, y));
        roomView.getRoom().addChild(e);
    }

}
