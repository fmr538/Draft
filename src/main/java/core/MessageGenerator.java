package draft.core;

import draft.model.observable.Publisher;

public interface MessageGenerator extends Publisher {
    void notSelected();
    void nullName();
    void deleteProjExp();

    void projectNotSelected();
    void nodeExists(String type);
    void renameProjExp();
    void loadProject();

    void fillForms();

    void noRoomView();

    void tmi();
}
