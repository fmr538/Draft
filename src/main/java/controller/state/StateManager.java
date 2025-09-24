package draft.controller.state;

import draft.controller.state.concrete.*;
import draft.model.observable.Publisher;
import draft.model.observable.Subscriber;

import java.util.ArrayList;

public class StateManager implements Publisher {
    ArrayList<Subscriber> subscribers = new ArrayList<>();

    private State currentState;

    public void setCurrentState(State state) {
        if (state != null) {
            this.currentState = state;
        }
    }

    private final EditRoomState editRoomState;
    private final AddState addState;
    private final SelectState selectState;
    private final ResizeState resizeState;
    private final MoveState moveState;
    private final EditState editState;
    private final DeleteState deleteState;

    public StateManager() {
        editRoomState = new EditRoomState();
        addState = new AddState();
        selectState = new SelectState();
        resizeState = new ResizeState();
        moveState = new MoveState();
        editState = new EditState();
        deleteState = new DeleteState();
    }

    public void setAddState() {
        this.currentState = addState;
    }

    public void setEditRoomState() {
        this.currentState = editRoomState;
        System.out.println("set edit");
    }

    public void setSelectState() {
        this.currentState = selectState;
    }

    public void setResizeState() {
        this.currentState = resizeState;
    }

    public void setMoveState() {
        currentState = moveState;
    }

    public void setEditState() {
        currentState = editState;
    }

    public void setDeleteState() {
        currentState = deleteState;
    }

    @Override
    public void notifySubscribers(Object state) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(state);
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        if (subscriber != null) {
            subscribers.add(subscriber);
        }
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        if (subscriber != null) {
            subscribers.remove(subscriber);
        }
    }

    public void removeSubscribers() {
        subscribers.clear();
    }

    public State getCurrentState() {
        return currentState;
    }

    public SelectState getSelectState() {
        return selectState;
    }
}
