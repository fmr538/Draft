package draft.model.draftRepository.nodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.DraftNodeComposite;

@JsonSerialize
public class Project extends DraftNodeComposite {
    @JsonProperty
    private String author;
    @JsonIgnore
    private String filepath;

    public Project(){
        super(null,null);
        this.author = null;
        this.filepath = null;
    }

    public Project(String name, DraftNodeComposite parent) {
        super(name, parent);
    }

    @JsonCreator
    public Project(@JsonProperty("name") String name, @JsonProperty("author")String author, String filepath, DraftNodeComposite parent) {
        super(name, parent);
        this.author = author;
        this.filepath = filepath;
    }

    @Override
    public void addChild(DraftNode node) {
        if (node instanceof Building || node instanceof Room) {this.getChildren().add(node);}
    }

    public String getAuthor() {
        return author;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
