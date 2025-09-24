package draft.view.swing;

import draft.core.ApplicationFramework;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.model.draftRepository.nodes.elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrganizeInputFrame extends JFrame {
    private JTextField widthField;
    private JTextField heightField;
    private JComboBox<String> elementPicker;
    private JPanel itemPanel;
    private GridBagConstraints gbc;
    private ArrayList<RoomElement> elements;
    private RoomView roomView;
    private Room room;

    public OrganizeInputFrame(RoomView roomView) {
        this.roomView = roomView;
        this.room = roomView.getRoom();
        setTitle("Organize My Room");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        elements = new ArrayList<>();

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Width and Height inputs (Column 1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Width:"), gbc);
        widthField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(widthField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Height:"), gbc);
        heightField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(heightField, gbc);

        // Element picker (Column 2)
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(new JLabel("Element Type:"), gbc);
        String[] types = {RoomElementType.BED.toString(), RoomElementType.TUBSHOWER.toString(), RoomElementType.CUPBOARD.toString(),
                RoomElementType.TOILET.toString(), RoomElementType.WASHINGMACHINE.toString(), RoomElementType.DOOR.toString(),
                RoomElementType.DESK.toString(), RoomElementType.WATERHEATER.toString(), RoomElementType.SINK.toString()};
        elementPicker = new JComboBox<>(types);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(elementPicker, gbc);

        JButton addButton = new JButton("Add");
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(addButton, gbc);

        // Item Panel (Column 4)
        itemPanel = new JPanel();
        itemPanel.setLayout(new GridBagLayout());  // Use GridBagLayout for exact positioning
        JScrollPane listScrollPane = new JScrollPane(itemPanel);
        listScrollPane.setPreferredSize(new Dimension(200, 150));

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        add(listScrollPane, gbc);
        gbc.gridheight = 1;

        // Organize Button
        JButton organizeButton = new JButton("Organize");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        add(organizeButton, gbc);

        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String width = widthField.getText();
                String height = heightField.getText();
                String type = (String) elementPicker.getSelectedItem();
                if (!width.isEmpty() && !height.isEmpty() && type != null) {
                    RoomElement element = RoomElementFactory.createElement(
                            RoomElementType.getElementFromString(type),
                            new Point(0,0),
                            new Dimension(Integer.parseInt(width),Integer.parseInt(height)),
                            room);
                    addItemToPanel(element);
                    elements.add(element);
                }
            }
        });

        // Organize button action
        organizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int maxHeight = 0;
                int maxWidth = 0;
                for (RoomElement element : elements) {
                    maxWidth = (int) Math.max(maxWidth, element.getSize().getWidth());
                    maxHeight = (int) Math.max(maxHeight, element.getSize().getHeight());
                }

                if (!(elements.size() > maxWidth /room.getSize().getWidth()*maxHeight/room.getSize().getHeight())){
                    OrganizePlacementFrame opframe = new OrganizePlacementFrame(elements,roomView);
                    opframe.setVisible(true);
                    setVisible(false);
                }else
                    ApplicationFramework.getInstance().getMessageGenerator().tmi();
            }
        });

        for (DraftNode e : room.getChildren())
            if (e != null & e instanceof RoomElement) {
                addItemToPanel((RoomElement) e);
                elements.add((RoomElement) e);
            }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addItemToPanel(RoomElement element) {
        // Create a panel for each item with a label and a remove button
        JPanel item = new JPanel();
        item.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(RoomElementType.getElementFromNode(element) + " - " + element.getSize().width + "x" + element.getSize().height);
        JButton removeButton = new JButton("X");
        removeButton.setMargin(new Insets(0, 5, 0, 5));

        // Action to remove the item from the panel
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemPanel.remove(item);
                elements.remove(element);
                element.deleteDraftNode();
                itemPanel.revalidate();
                itemPanel.repaint();
//                for (RoomElement i : elements)
//                    System.out.println(i);
//                System.out.println("room - " + room.getChildren());
            }
        });

        item.add(label);
        item.add(removeButton);

        // Add item to the GridBagLayout of itemPanel
        gbc.gridx = 0;
        gbc.gridy = itemPanel.getComponentCount();
        itemPanel.add(item, gbc);

        itemPanel.revalidate();
        itemPanel.repaint();
    }

    public static void main(String[] args) {
        Room room = new Room("Room1",new Dimension(300,400),null);
        Bed bed = new Bed(new Point(0,0),new Dimension(30,30),0,room);
        WaterHeater wh = new WaterHeater(new Point(0,0),new Dimension(20,20),0,room);
        Cupboard cp = new Cupboard(new Point(0,0),new Dimension(100,100),0,room);
        WashingMachine wm = new WashingMachine(new Point(0,0),new Dimension(60,30),0,room);
        Sink s  = new Sink(new Point(0,0),new Dimension(40,40),0,room);
        Sink s2  = new Sink(new Point(0,0),new Dimension(40,40),0,room);
        Sink s3  = new Sink(new Point(0,0),new Dimension(40,40),0,room);
        Sink s4  = new Sink(new Point(0,0),new Dimension(40,40),0,room);
        Sink s5  = new Sink(new Point(0,0),new Dimension(40,40),0,room);
        Sink s6  = new Sink(new Point(0,0),new Dimension(40,40),0,room);
        Sink s7  = new Sink(new Point(0,0),new Dimension(40,40),0,room);

        room.addChild(bed);
        room.addChild(wh);
        room.addChild(cp);
        room.addChild(wm);
        room.addChild(s);
        room.addChild(s2);
        room.addChild(s3);
        room.addChild(s4);
        room.addChild(s5);
        room.addChild(s6);
        room.addChild(s7);
        RoomView roomView = new RoomView(room);

        OrganizeInputFrame frame = new OrganizeInputFrame(roomView);
        frame.setVisible(true);
    }
}
