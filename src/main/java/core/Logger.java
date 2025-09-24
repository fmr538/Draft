package draft.core;

import draft.controller.message.model.Message;
import draft.model.observable.Subscriber;

public interface Logger extends Subscriber {
    void log(Message message);
}
