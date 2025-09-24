package draft.view.swing;

import draft.controller.state.StateManager;
import draft.model.observable.Subscriber;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Building;
import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.Room;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class ProjectView extends JPanel implements Subscriber {
    private Project project;
    private JTabbedPane tabs;
    private JLabel currentPath;
    private JLabel author;
    private HashMap<Building, Color> buildingColors;

    public ProjectView() {
        if(this.getParent()!=null)
            this.setSize(this.getParent().getSize());
        this.setLayout(new BorderLayout());

        buildingColors = new HashMap<>();
        tabs = new JTabbedPane();
        author = new JLabel("Author: /");
        currentPath = new JLabel("No project selected");
        currentPath.setHorizontalAlignment(SwingConstants.LEFT);
        JPanel labels = new JPanel();
        labels.add(currentPath);
        labels.add(author);
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));

        this.add(labels, BorderLayout.NORTH);

        tabs.setSize(this.getSize());
        tabs.addChangeListener(e -> handleTabChange());

        this.add(tabs, BorderLayout.CENTER);
    }

    private void handleTabChange() {
        if (project == null){
            currentPath.setText("No project selected");
            author.setText("Author: /");
            tabs.removeAll();
            return;
        }

        StateManager stateManager = MainFrame.getInstance().getStateManager();
        stateManager.removeSubscribers();

        String buildingName = "/";
        String roomName = "/";

        RoomView selectedRoom = (RoomView) tabs.getSelectedComponent();
        if (selectedRoom != null) {
            stateManager.addSubscriber(selectedRoom);
            buildingName = selectedRoom.getBuilding() == null ? "/" : ((RoomView) tabs.getSelectedComponent()).getBuilding().getName();
            roomName = selectedRoom.getRoom().getName();

            Room room = selectedRoom.getRoom();
            if (room != null) {
                Dimension size = selectedRoom.getRoom().getSize();
                if (size.width == 0 || size.height == 0) {
                    stateManager.setEditRoomState();
                }
            }
        }

        if (project.getAuthor() == null || project.getAuthor().isEmpty())
            author.setText("Author: /");
        else
            author.setText("Author: " + project.getAuthor());

        currentPath.setText("Project: " + project.getName() + " Building: " + buildingName + " Room: " + roomName);
    }

    private void refresh(){
        handleTabChange();
        if (project == null)return;

        int index = 0;
        RoomView currentRoom = (RoomView) tabs.getSelectedComponent();
        boolean flag = false;
        tabs.removeAll();

        for (DraftNode i : project.getChildren()){
            if (i instanceof Room) {
                if (currentRoom != null && currentRoom.getRoom().equals(i)){
                    flag = true;
                    index = tabs.getTabCount();
                }
                tabs.addTab(i.getName(), new RoomView((Room) i));
            } else if (i instanceof Building) {
                for (DraftNode j : ((Building) i).getChildren()){
                    Random rand = new Random();
                    if (currentRoom != null && currentRoom.getRoom().equals(j)){
                        flag = true;
                        index = tabs.getTabCount();
                    }

                    if (!(buildingColors.containsKey(i)))
                        buildingColors.put((Building) i,(new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256))));
                    if (j instanceof Room) {
                        tabs.addTab(j.getName(), new RoomView((Room) j));
                    }
                }
            }
        }
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (((RoomView)tabs.getComponentAt(i)).getBuilding() != null)
                tabs.setBackgroundAt(i, buildingColors.get(((RoomView)tabs.getComponentAt(i)).getBuilding()));
        }
        if (flag) tabs.setSelectedIndex(index);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public RoomView getCurrentRoomView() {
        return (RoomView) tabs.getSelectedComponent();
    }

    @Override
    public void update(Object message) {
        if (message instanceof Project || message == null) {
            this.project = (Project) message;
            refresh();
        }
    }
}
