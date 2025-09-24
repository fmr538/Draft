package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.RoomElement;

@JsonSerialize
public abstract class RoomElementPrototype extends DraftNode {
    public abstract RoomElement cloneElement();

    public RoomElementPrototype(String name, DraftNodeComposite parent) {
        super(name, parent);
    }
}