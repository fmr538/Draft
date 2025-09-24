package draft.view.swing;

import draft.controller.actions.ActionManager;
import draft.controller.message.model.Message;
import draft.controller.state.StateManager;
import draft.core.DraftRepository;
import draft.model.observable.Subscriber;
import draft.core.ApplicationFramework;
import draft.view.painters.ElementPainterFactory;
import draft.view.tree.DraftTree;
import draft.view.tree.DraftTreeImplementation;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements Subscriber {
    private static MainFrame instance;
    private ActionManager actionManager;
    private DraftTree draftTree;
    private ProjectView projectView;
    private StateManager stateManager;
    //buduca polja za sve komponente view-a na glavnom prozoru

    public static MainFrame getInstance(){
        if (instance == null) {
            instance = new MainFrame();
            instance.initialize();
        }
        return instance;
    }

    private MainFrame(){}

    private void initialize(){
        draftTree = new DraftTreeImplementation();
        actionManager = new ActionManager();
        stateManager = new StateManager();

        initializeGui();
    }

    private void initializeGui() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DraftRoom");

        JTree tree = draftTree.generateTree(ApplicationFramework.getInstance().getDraftRepository().getProjectExplorer());

        MyMenuBar menu = new MyMenuBar(actionManager.getActions());
        setJMenuBar(menu);

        MyToolBar toolBar = new MyToolBar(actionManager.getActions());
        add(toolBar, BorderLayout.NORTH);

        projectView = new ProjectView();

        JScrollPane sc_pane = new JScrollPane(tree);
        sc_pane.setMinimumSize(new Dimension(200,150));

        JSplitPane sp_pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sc_pane,projectView);

        getContentPane().add(sp_pane,BorderLayout.CENTER);

        sp_pane.setDividerLocation(250);
        sp_pane.setOneTouchExpandable(true);

        DraftRepository repository = ApplicationFramework.getInstance().getDraftRepository();
        repository.addSubscriber(projectView);
    }

    @Override
    public void update(Object message) {
        if (message instanceof Message){
            switch (((Message) message).getType()){
                case INFO -> JOptionPane.showMessageDialog(null, message.toString(), "Info", JOptionPane.INFORMATION_MESSAGE);
                case ERROR -> JOptionPane.showMessageDialog(null, message.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                case WARNING -> JOptionPane.showMessageDialog(null, message.toString(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public DraftTree getDraftTree() {
        return draftTree;
    }



    public ProjectView getProjectView() {
        return projectView;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

}
