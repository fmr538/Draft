package draft.view.tree;


import draft.core.ApplicationFramework;
import draft.core.DraftRepository;
import draft.model.draftRepository.nodes.*;
import draft.view.swing.MainFrame;
import draft.view.tree.controller.DraftTreeItemMouseListener;
import draft.view.tree.model.DraftTreeItem;
import draft.view.tree.view.DraftTreeView;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.DraftNodeComposite;

import javax.swing.*;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class DraftTreeImplementation implements DraftTree {

    private DraftTreeView treeView;
    private DefaultTreeModel treeModel;

    @Override
    public DraftTreeView generateTree(ProjectExplorer projectExplorer) {
        DraftTreeItem root = new DraftTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new DraftTreeView(treeModel);
        return treeView;
    }

    @Override
    public void addChild(DraftTreeItem parent) {
        if (!(parent.getDraftNode() instanceof DraftNodeComposite))
            return;

        DraftRepository repository = ApplicationFramework.getInstance().getDraftRepository();
        DraftNode child = repository.createNode(parent.getDraftNode());
        // ako zatvorimo dijalog sa X (u slucaju kreiranja deteta na projekat) child ce biti null
        if (child != null) {
            parent.add(new DraftTreeItem(child));
            ((DraftNodeComposite) parent.getDraftNode()).addChild(child);
            treeView.expandPath(treeView.getSelectionPath());
            treeView.addMouseListener(new DraftTreeItemMouseListener());
            SwingUtilities.updateComponentTreeUI(treeView);

            Project selectedProject = repository.getSelectedProject();
            repository.notifySubscribers(selectedProject);
        }
    }

    @Override
    public void deleteTreeNode(DraftTreeItem node) {
        if (node.getDraftNode() instanceof ProjectExplorer){
            ApplicationFramework.getInstance().getMessageGenerator().deleteProjExp();
            return;
        }

        DraftRepository repository = ApplicationFramework.getInstance().getDraftRepository();
        if (node.getDraftNode().equals(repository.getSelectedProject())) {
            repository.setSelectedProject(null);
        }

        Queue<DraftTreeItem> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            DraftTreeItem item = queue.remove();

            for (int i = 0; i < item.getChildCount(); i++) {
                queue.add((DraftTreeItem) item.getChildAt(i));
            }

            item.getDraftNode().deleteDraftNode();
            item.removeFromParent();
        }

        SwingUtilities.updateComponentTreeUI(treeView);

        repository.notifySubscribers(repository.getSelectedProject());
    }

    @Override
    public void changeName(DraftTreeItem node, String name) {
        if (!Objects.equals(name, node.getDraftNode().getName()) && exists(name, (DraftTreeItem) node.getParent())){
            ApplicationFramework.getInstance().getMessageGenerator().nodeExists(node.getDraftNode().getClass().getSimpleName());
            return;
        }
        node.setName(name);
        SwingUtilities.updateComponentTreeUI(treeView);

        DraftRepository repository = ApplicationFramework.getInstance().getDraftRepository();
        repository.notifySubscribers(repository.getSelectedProject());
    }

    public static boolean exists(String nodeName, DraftTreeItem parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);
            if (child.toString().equals(nodeName)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public DraftTreeItem getSelectedNode() {
        return (DraftTreeItem) treeView.getLastSelectedPathComponent();
    }

    public DraftTreeView getTreeView() {
        return treeView;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    @Override
    public DraftTreeItem findNodeByName(String name) {
        TreePath treePath = treeView.getNextMatch(name, 0, Position.Bias.Forward);
        if (treePath != null) {
            return (DraftTreeItem) treePath.getLastPathComponent();
        }
        return null;
    }

    @Override
    public boolean removeElementFromTree(RoomElement element) {
        if (element != null) {
            DraftTreeItem selectedItem = findNodeByName(element.getName());
            if (selectedItem != null) {
                deleteTreeNode(selectedItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNode(DraftTreeItem parent, DraftNode child) {
        if (!(parent.getDraftNode() instanceof DraftNodeComposite))
            return;

        DraftRepository repository = ApplicationFramework.getInstance().getDraftRepository();
        if (child != null) {
            parent.add(new DraftTreeItem(child));
            ((DraftNodeComposite) parent.getDraftNode()).addChild(child);
            treeView.expandPath(treeView.getSelectionPath());
            treeView.addMouseListener(new DraftTreeItemMouseListener());
            SwingUtilities.updateComponentTreeUI(treeView);

            Project selectedProject = repository.getSelectedProject();
            repository.notifySubscribers(selectedProject);
        }
    }

    @Override
    public void loadProject(DraftNodeComposite node) {
        ApplicationFramework.getInstance().getDraftRepository().getProjectExplorer().addChild(node);
        ((DraftTreeItem) treeModel.getRoot()).add(new DraftTreeItem(node));
        for(DraftNode child : node.getChildren())
        {
            DraftTreeItem childItem = new DraftTreeItem(child);
            if(child instanceof Building)
            {
                for(DraftNode rooms : ((Building) child).getChildren())
                {
                    childItem.add(new DraftTreeItem(rooms));
                }
            }
            else
            {
                childItem.add(new DraftTreeItem(child));
            }
        }
        treeView.expandPath(treeView.getSelectionPath());
        treeView.addMouseListener(new DraftTreeItemMouseListener());
        SwingUtilities.updateComponentTreeUI(treeView);

        ApplicationFramework.getInstance().getDraftRepository().notifySubscribers(ApplicationFramework.getInstance().getDraftRepository().getSelectedProject());
    }

    public boolean addLeafElement(String name, RoomElement element) {
        TreePath treePath = treeView.getNextMatch(name, 0, Position.Bias.Forward);
        if (treePath != null) {
            DraftTreeItem selectedItem = (DraftTreeItem) treePath.getLastPathComponent();
            if (selectedItem != null) {
                selectedItem.add(new DraftTreeItem(element));
                ((DraftNodeComposite) selectedItem.getDraftNode()).addChild(element);
                treeView.expandPath(treePath);
                treeView.addMouseListener(new DraftTreeItemMouseListener());
                SwingUtilities.updateComponentTreeUI(treeView);

                return true;
            }
        }

        return false;
    }

    public DraftTreeItem findNode(DraftNode node)
    {
        DraftTreeItem temp = (DraftTreeItem) ((DraftTreeImplementation)MainFrame.getInstance().getDraftTree()).getTreeModel().getRoot();
        Queue<DraftTreeItem> q = new ArrayDeque<>();
        q.add(temp);

        while (!q.isEmpty())
        {
            DraftTreeItem cur = q.poll();

            if (cur.getDraftNode().equals(node))
                return cur;

            for (int i = 0; i < cur.getChildCount(); i++) {
                q.add((DraftTreeItem) cur.getChildAt(i));
            }
        }

        return null;
    }
}
