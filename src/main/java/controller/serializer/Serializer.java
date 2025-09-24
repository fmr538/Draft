package draft.controller.serializer;

import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.Room;

import java.io.File;

public interface Serializer {
    Project loadProject(File projectFile);

    void saveProject(Project project);

    Room loadTemplate(File templateFile);

    void saveTemplate(Room template);
}
