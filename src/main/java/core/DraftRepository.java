package draft.core;

import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.ProjectExplorer;
import draft.model.observable.Publisher;

public interface DraftRepository extends Publisher {
    ProjectExplorer getProjectExplorer();
    Project getSelectedProject();
    void setSelectedProject(Project selectedProject);
    DraftNode createNode(DraftNode parent);
}
