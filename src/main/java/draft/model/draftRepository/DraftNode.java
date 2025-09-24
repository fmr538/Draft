package draft.model.draftRepository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public abstract class DraftNode {
    @JsonProperty
    private String name;
    @JsonIgnore
    private DraftNodeComposite parent;

    public DraftNode(String name, DraftNodeComposite parent) {
        this.name = name;
        this.parent = parent;
    }

    public DraftNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DraftNodeComposite getParent() {
        return parent;
    }

    public void deleteDraftNode(){
        if (this instanceof DraftNodeComposite) {
            ((DraftNodeComposite) this).setChildren(new ArrayList<>());
        }

        this.parent.removeChild(this);
    }
}
