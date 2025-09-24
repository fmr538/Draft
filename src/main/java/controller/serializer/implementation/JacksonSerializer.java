package draft.controller.serializer.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import draft.controller.serializer.Serializer;
import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.Room;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JacksonSerializer implements Serializer {

    private final ObjectMapper objectMapper;

    public JacksonSerializer() {
        objectMapper = new ObjectMapper();
    }
    @Override
    public Project loadProject(File projectFile) {
        try(FileReader fileReader = new FileReader(projectFile)) {
            return objectMapper.readValue(fileReader, Project.class);
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveProject(Project project) {
        try(FileWriter fileWriter = new FileWriter(project.getFilepath())) {
            objectMapper.writeValue(fileWriter, project);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Room loadTemplate(File templateFile) {
        try(FileReader fileReader = new FileReader(templateFile)) {
            return objectMapper.readValue(fileReader, Room.class);
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveTemplate(Room template) {
        File file = new File("src/main/resources/templates/" + template.getName() + ".DraftTemplate");
        try (FileWriter fileWriter = new FileWriter(file)) {
            objectMapper.writeValue(fileWriter, template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
