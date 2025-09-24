package draft.model.draftRepository.nodes;

import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.DraftNodeComposite;

public class ProjectExplorer extends DraftNodeComposite {
    public ProjectExplorer(String name) {
        super(name, null);
    }

    @Override
    public void addChild(DraftNode node) {
        if (node instanceof Project) getChildren().add(node);
    }
}
