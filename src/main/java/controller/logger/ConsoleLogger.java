package draft.controller.logger;

import draft.core.Logger;
import draft.controller.message.model.Message;

public class ConsoleLogger implements Logger {
    @Override
    public void log(Message message) {
        System.out.println(message.toString());
    }

    @Override
    public void update(Object message) {
        if (message instanceof Message) {
            log((Message) message);
        }
    }
}
