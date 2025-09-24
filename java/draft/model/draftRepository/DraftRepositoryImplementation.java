package draft.model.draftRepository;

import draft.controller.state.concrete.AddState;
import draft.model.observable.Subscriber;
import draft.core.DraftRepository;
import draft.view.swing.MainFrame;
import draft.view.tree.DraftTree;
import draft.view.tree.DraftTreeImplementation;
import draft.model.draftRepository.nodes.Building;
import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.ProjectExplorer;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.elements.Door;
import draft.view.tree.model.DraftTreeItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DraftRepositoryImplementation implements DraftRepository {
    private ArrayList<Subscriber> subscribers;
    private final ProjectExplorer projectExplorer;
    private Project selectedProject;
    private int projCnt = 0;
    private int buildCnt = 0;
    private int roomCnt = 0;

    public DraftRepositoryImplementation() {
        subscribers = new ArrayList<>();
        projectExplorer = new ProjectExplorer("My Project Explorer");
    }

    @Override
    public ProjectExplorer getProjectExplorer() {
        return projectExplorer;
    }

    @Override
    public DraftNode createNode(DraftNode parent){
        if (!(parent instanceof DraftNodeComposite)) return null;

        // Koristimo patter matching for switch (dodato u Java 17)
        // Mnogo sigurnije nego parent.getClass().getSimpleName() zato sto ne garantuje da ce vratiti sam naziv klase.
        if (parent instanceof ProjectExplorer pe) {
            return createProject(pe);
        } else if (parent instanceof Project project) {
            String[] options = {"Room", "Building"};

            int selectedOption = JOptionPane.showOptionDialog(
                    null,
                    "Would you like to add a new Room or Building?",
                    "Select Option",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (selectedOption == 0) {
                return createRoom(project);
            } else if (selectedOption == 1) {
                return createBuilding(project);
            }
        } else if (parent instanceof Building building) {
            return createRoom(building);
        } else if (parent instanceof Room room) {
            return createRoomElement(room);
        } else {
            // No need to check RoomElement compatibility here; handle unexpected cases
            throw new IllegalStateException("Unexpected value: " + parent);
        }
        return null;
    }

    private DraftNode createProject(DraftNodeComposite parent) {
        return new Project("Project " + projCnt++, parent);
    }

    private DraftNode createBuilding(DraftNodeComposite parent) {
        return new Building("Building " + buildCnt++, parent);
    }

    private DraftNode createRoom(DraftNodeComposite parent) {
        return new Room("Room " + roomCnt++, new Dimension(0, 0), parent);
    }

    private DraftNode createRoomElement(Room room) {
        // Here we have show dialog asking which elements to add
        // Then ask for element dimensions and position
        DraftTree tree = MainFrame.getInstance().getDraftTree();
        DraftTreeItem parent = tree.findNodeByName(room.getName());
        if (parent != null) {
            AddState addState = new AddState();
            addState.mousePress(15, 15, parent);
        }
        return  null;
    }

    @Override
    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;

        notifySubscribers(selectedProject);
        SwingUtilities.updateComponentTreeUI(((DraftTreeImplementation)MainFrame.getInstance().getDraftTree()).getTreeView());
    }

    @Override
    public Project getSelectedProject() {
        return selectedProject;
    }

    @Override
    public void notifySubscribers(Object message) {
        // Iz nekog razloga koriscenjem ove petlje se dobija exception na konzoli java.util.ConcurrentModificationException
//        for (Subscriber i : subscribers) {
//            i.update(message);
//        }
        for (int i = 0; i < subscribers.size(); i++) {
            Subscriber subscriber = subscribers.get(i);
            subscriber.update(message);
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }
}
