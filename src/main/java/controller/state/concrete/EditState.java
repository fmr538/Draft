package draft.controller.state.concrete;

import draft.controller.commands.implementation.DeleteCommand;
import draft.controller.commands.implementation.EditCommand;
import draft.controller.state.State;
import draft.core.ApplicationFramework;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.swing.MainFrame;
import draft.view.swing.RoomView;
import draft.view.tree.model.DraftTreeItem;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EditState implements State {
    RoomElement element;
    RoomView roomView;

    @Override
    public void mouseDrag(int x, int y) {

    }

    @Override
    public void mousePress(int x, int y, DraftTreeItem node) {

    }

    @Override
    public void mouseRelease(int x, int y) {
        roomView = MainFrame.getInstance().getProjectView().getCurrentRoomView();
        if (roomView != null) {
            element = roomView.findElementOnMousePosition(new Point(x, y));
            if (element != null) {
                RoomElement copy = element.cloneElement();
                showRoomEditDialog(element);
                if (checkCollisionAndBounds(element)){
                    element.setPosition(copy.getPosition());
                    element.setSize(copy.getSize());
                    element.setRotation(copy.getRotation());
                }else
                    ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(
                            new EditCommand(new ArrayList<>(Collections.singletonList(copy)),new ArrayList<>(Collections.singletonList(element))));
                roomView.repaint();
                element = null;
            }
            roomView = null;
        }
    }

    private void showRoomEditDialog(RoomElement element) {
        JFormattedTextField widthField = createNumberTextField(element.getSize().width, widthChangeListener);
        JFormattedTextField heightField = createNumberTextField(element.getSize().height, heighChangeListener);
        JFormattedTextField positionXField = createNumberTextField(element.getPosition().x, posXChangeListener);
        JFormattedTextField positionYField = createNumberTextField(element.getPosition().y, posYChangeListener);

        JSpinner rotateField = new JSpinner(new SpinnerNumberModel(element.getRotation(), -360, 360, 90));
        ((JSpinner.DefaultEditor) rotateField.getEditor()).getTextField().setEditable(false);

        rotateField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner spinner = (JSpinner) e.getSource();
                double rotation = (double)spinner.getValue();
                if (rotation == -360 || rotation == 360) {
                    rotation = 0.0;
                    spinner.setValue(rotation);
                }
                element.setRotation(rotation);
                roomView.getRoomElementsView().repaint();
            }
        });
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Width (px):"));
        panel.add(widthField);
        panel.add(Box.createVerticalStrut(10)); // Spacer
        panel.add(new JLabel("Height (px):"));
        panel.add(heightField);
        panel.add(Box.createVerticalStrut(10)); // Spacer
        panel.add(new JLabel("Poasition X (px):"));
        panel.add(positionXField);
        panel.add(Box.createVerticalStrut(10)); // Spacer
        panel.add(new JLabel("Poasition Y (px):"));
        panel.add(positionYField);
        panel.add(Box.createVerticalStrut(10)); // Spacer
        panel.add(new JLabel("Rotation:"));
        panel.add(rotateField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Enter Room Dimensions",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private JFormattedTextField createNumberTextField(Object initialValue, PropertyChangeListener changeListener) {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);//Remove comma from number greater than 4 digit
        NumberFormatter fieldFormatter = new NumberFormatter(format);
        fieldFormatter.setValueClass(Integer.class);
        fieldFormatter.setMinimum(-270);
        fieldFormatter.setMaximum(360);
        fieldFormatter.setAllowsInvalid(true);
        fieldFormatter.setCommitsOnValidEdit(true);
        JFormattedTextField textField = new JFormattedTextField(fieldFormatter);
        textField.setValue(initialValue);
        textField.addPropertyChangeListener("value", changeListener);

        return textField;
    }

    private final PropertyChangeListener widthChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            int width = evt.getNewValue() != null ? (int)evt.getNewValue() : 0;
            element.setSize(new Dimension(width, element.getSize().height));
            roomView.getRoomElementsView().repaint();
        }
    };

    private final PropertyChangeListener heighChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            int height = evt.getNewValue() != null ? (int)evt.getNewValue() : 0;
            element.setSize(new Dimension(element.getSize().width, height));
            roomView.getRoomElementsView().repaint();
        }
    };

    private final PropertyChangeListener posXChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            int posX = evt.getNewValue() != null ? (int)evt.getNewValue() : 0;
            element.setPosition(new Point(posX, element.getPosition().y));
            roomView.getRoomElementsView().repaint();
        }
    };

    private final PropertyChangeListener posYChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            int posY = evt.getNewValue() != null ? (int)evt.getNewValue() : 0;
            element.setPosition(new Point(element.getPosition().x, posY));
            roomView.getRoomElementsView().repaint();
        }
    };
}
