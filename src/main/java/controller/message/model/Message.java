package draft.controller.message.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final String content;
    private final MessageType type;
    private final String timestamp;

    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"));
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return " [" + this.getType() + "] " +
                "[ " + this.getTimestamp() + "] " +
                this.getContent();
    }
}
