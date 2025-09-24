package draft.model.draftRepository;

import draft.model.draftRepository.nodes.RoomElement;
import java.util.ArrayList;
import java.util.List;

public class NodeSelection{
    private List<RoomElement> selection;

    public NodeSelection() {
        this.selection = new ArrayList<>();
    }

    public void add(RoomElement elem) {if (!selection.contains(elem)) selection.add(elem);}

    public void clear() {selection = new ArrayList<>();}

    @Override
    public String toString() {
        return "NodeSelection{" +
                "selection=" + selection +
                '}';
    }

    public List<RoomElement> getItems() {
        return selection;
    }
}

