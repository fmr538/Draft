package draft.model.draftRepository;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import draft.model.draftRepository.nodes.Building;
import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.Room;
import draft.model.draftRepository.nodes.RoomElement;
import java.awt.*;
import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Project.class, name = "project"),
        @JsonSubTypes.Type(value = Building.class, name = "building"),
        @JsonSubTypes.Type(value = Room.class, name = "room"),
        @JsonSubTypes.Type(value = RoomElement.class, name = "roomElement")
})
public abstract class DraftNodeComposite extends DraftNode{
    private ArrayList<DraftNode> children;

    public DraftNodeComposite(String name, DraftNodeComposite parent) {
        super(name,parent);
        children = new ArrayList<>();
    }

    public ArrayList<DraftNode> getChildren() {
        return children;
    }

    public abstract void addChild(DraftNode node);

    public void setChildren(ArrayList<DraftNode> children) {
        this.children = children;
    }

    public void removeChild(DraftNode draftNode) {
        this.getChildren().remove(draftNode);
    }

}
