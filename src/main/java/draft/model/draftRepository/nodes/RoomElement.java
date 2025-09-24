package draft.model.draftRepository.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import draft.model.draftRepository.DraftNodeComposite;
import draft.model.draftRepository.nodes.elements.RoomElementPrototype;
import draft.model.observable.Publisher;
import draft.model.observable.Subscriber;
import draft.view.painters.ElementPainter;

import java.awt.*;
import java.util.ArrayList;

@JsonSerialize
public abstract class RoomElement extends RoomElementPrototype implements Publisher {
    @JsonIgnore
    private ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();
    @JsonIgnore
    protected Paint paint;
    @JsonProperty
    private Point position;
    @JsonProperty
    private Dimension size;
    @JsonProperty
    private double rotation;

    /**
     * Instanciranje RoomElementPainter-a obavljaju konkretni  elementi prilikom svoje konstrukcije
     */
    @JsonIgnore
    protected ElementPainter elementPainter;

    public RoomElement(@JsonProperty("name")String name,@JsonProperty("position") Point position,@JsonProperty("size") Dimension size, DraftNodeComposite parent,@JsonProperty("rotation") double rotation, Paint paint) {
        super(name, parent);
        this.paint = paint;
        this.position = position;
        this.size = size;
        this.rotation = rotation;
    }

    @Override
    public RoomElement cloneElement() { return null; };

    public Point getPosition() { return position; }
    public Dimension getSize() {
        return size;
    }
    public double getRotation() {
        return rotation;
    }
    @JsonIgnore
    public ElementPainter getPainter() { return elementPainter; }
    @JsonIgnore
    public Paint getPaint() { return paint; }

    @JsonIgnore
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setPosition(Point position) {
        this.position = position;
        elementPainter.updateTransform();
    }
    public void setSize(Dimension dimension) {
        this.size = dimension;
    }
    public void setRotation(double rotation) {
        this.rotation = rotation;
        System.out.println(rotation);
        elementPainter.updateTransform();
    }

    @Override
    public void notifySubscribers(Object message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(message);
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RoomElement element) {
            if (this.getPosition().equals(element.getPosition()) &&
                    this.getSize().equals(element.getSize()) &&
                    this.getRotation() == element.getRotation() &&
                    this.getName().equals(element.getName())
            ) {
                return true;
            }
        }
        return false;
    }
}
