package draft.model.draftRepository.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.DraftNodeComposite;

public class Building extends DraftNodeComposite {
    public Building(@JsonProperty("name") String name, DraftNodeComposite parent) {
        super(name, parent);
    }

    @Override
    public void addChild(DraftNode node) {
        if (node instanceof Room) {this.getChildren().add(node);}
    }
}

