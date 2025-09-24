package draft.model.draftRepository.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.elements.*;

import draft.model.observable.Publisher;
import draft.model.observable.Subscriber;
import draft.view.painters.RoomPainter;

import java.awt.*;
import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bed.class, name = "bed"),
        @JsonSubTypes.Type(value = Cupboard.class, name = "cupboard"),
        @JsonSubTypes.Type(value = Desk.class, name = "room"),
        @JsonSubTypes.Type(value = Door.class, name = "door"),
        @JsonSubTypes.Type(value = Lasso.class, name = "lasso"),
        @JsonSubTypes.Type(value = Sink.class, name = "sink"),
        @JsonSubTypes.Type(value = Toilet.class, name = "toilet"),
        @JsonSubTypes.Type(value = TubShower.class, name = "tubShower"),
        @JsonSubTypes.Type(value = WashingMachine.class, name = "washingMachine"),
        @JsonSubTypes.Type(value = WaterHeater.class, name = "waterHeater"),
})

@JsonSerialize
public class Room extends DraftNodeComposite implements Publisher, Subscriber {
    @JsonIgnore
    private ArrayList<Subscriber> subscribers;
    @JsonIgnore
    protected RoomPainter roomPainter;
    private Dimension size;
    private double currentZoom;

    public Room(){
        super(null,null);
        subscribers = new ArrayList<>();
        size = new Dimension();
        currentZoom = 1.0;
        roomPainter = new RoomPainter(this);
    }
    public Room(@JsonProperty("name") String name,@JsonProperty("size") Dimension size, DraftNodeComposite parent) {
        super(name, parent);
        subscribers = new ArrayList<>();
        this.size = size;
        currentZoom = 1.0;
        roomPainter = new RoomPainter(this);
    }

    public Room cloneElement() {
        Room newRoom = new Room(super.getName(), (Dimension) this.getSize().clone(),super.getParent());
        for (DraftNode e : getChildren())
            if (e instanceof RoomElement)
                newRoom.addChild(((RoomElement) e).cloneElement());
        return newRoom;
    }

    @Override
    public void addChild(DraftNode node) {
        if (node instanceof RoomElement element) {
            this.getChildren().add(element);
            element.addSubscriber(this);
        }
    }

    @Override
    public void update(Object message) {
        if (message instanceof RoomElement element) {
            notifySubscribers(element);
        }
    }

    @Override
    public void notifySubscribers(Object message) {
        for (Subscriber i : subscribers) i.update(message);
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public Dimension getSize() { return size; }

    public void setSize(Dimension dimension) { this.size = dimension; }

    @JsonIgnore
    public RoomPainter getPainter() {
        return roomPainter;
    }

    public double getCurrentZoom() {
        return currentZoom;
    }

    public void setCurrentZoom(double currentZoom) {
        this.currentZoom = currentZoom;
    }

    @JsonProperty("initialize")
    public void postDeserialize() {
        if (roomPainter == null) {
            roomPainter = new RoomPainter(this);
        }
    }
}
