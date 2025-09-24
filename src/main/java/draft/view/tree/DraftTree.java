package draft.view.tree;


import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import draft.view.tree.model.DraftTreeItem;
import draft.view.tree.view.DraftTreeView;
import draft.model.draftRepository.nodes.ProjectExplorer;

public interface DraftTree {

    DraftTreeView generateTree(ProjectExplorer projectExplorer);
    void addChild(DraftTreeItem parent);
    void deleteTreeNode(DraftTreeItem node);
    void changeName(DraftTreeItem node, String name);

    DraftTreeItem getSelectedNode();

    DraftTreeItem findNodeByName(String name);
    boolean addLeafElement(String name, RoomElement element);
    boolean removeElementFromTree(RoomElement element);

    void addNode(DraftTreeItem parent, DraftNode child);
    void loadProject(DraftNodeComposite node);

    DraftTreeItem findNode(DraftNode child);
}
