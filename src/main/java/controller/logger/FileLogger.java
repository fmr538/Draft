package draft.controller.logger;

import draft.core.Logger;
import draft.controller.message.model.Message;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger {
    @Override
    public void log(Message message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dsw-projekat-tim_filipmarcic/src/main/resources/logs/log.txt", true))) {
            writer.write(message.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Object message) {
        if (message instanceof Message) {
            log((Message) message);
        }
    }
}
