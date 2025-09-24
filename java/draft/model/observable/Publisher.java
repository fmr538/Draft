package draft.model.observable;

import java.util.ArrayList;

public interface Publisher {
    void notifySubscribers(Object message);
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);


}
