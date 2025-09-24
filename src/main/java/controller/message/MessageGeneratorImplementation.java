package draft.controller.message;

import draft.controller.message.model.Message;
import draft.controller.message.model.MessageType;
import draft.core.MessageGenerator;
import draft.model.observable.Subscriber;

import java.util.ArrayList;

public class MessageGeneratorImplementation implements MessageGenerator {
    private ArrayList<Subscriber> subscribers;

    public MessageGeneratorImplementation() {
        subscribers = new ArrayList<>();
    }

    private void generateMessage(Message message){
        notifySubscribers(message);
    }

    @Override
    public void notSelected() {
        Message message = new Message("No node selected",MessageType.WARNING);
        generateMessage(message);
    }

    @Override
    public void nullName() {
        Message message = new Message("Name cannot be empty",MessageType.ERROR);
        generateMessage(message);
    }

    @Override
    public void deleteProjExp() {
        Message message = new Message("Cannot delete Project explorer",MessageType.ERROR);
        generateMessage(message);
    }

    @Override
    public void notifySubscribers(Object message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(message);
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void projectNotSelected() {
        Message message = new Message("Project not selected",MessageType.WARNING);
        generateMessage(message);
    }

    @Override
    public void nodeExists(String type) {
        Message message = new Message(type + " already exists",MessageType.ERROR);
        generateMessage(message);
    }

    @Override
    public void renameProjExp() {
        Message message = new Message("Cannot rename Project explorer",MessageType.ERROR);
        generateMessage(message);
    }

    @Override
    public void fillForms(){
        Message message = new Message("Invalid values",MessageType.ERROR);
        generateMessage(message);
    }
  
    @Override
    public void loadProject(){
        Message message = new Message("Invalid file type",MessageType.ERROR);
    }
  
    @Override
    public void noRoomView() {
        Message message = new Message("Room not set",MessageType.ERROR);
        generateMessage(message);
    }
  
    @Override
    public void tmi() {
        Message message = new Message("Number of added elements cannot fit room",MessageType.WARNING);
        generateMessage(message);
    }
}
