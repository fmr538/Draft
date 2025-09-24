package draft.model.draftRepository.nodes.elements;

import com.fasterxml.jackson.annotation.JsonTypeName;
import draft.model.draftRepository.nodes.RoomElement;
import draft.model.observable.Publisher;
import draft.model.observable.Subscriber;
import draft.view.painters.ElementPainter;

import java.awt.*;

@JsonTypeName("lasso")
public class Lasso implements Publisher {
    private Point start;
    private Point end;
    private Subscriber room;

    public Lasso(Point start) {
        this.start = start;
        this.end = start;
    }

    public boolean contains(ElementPainter painter) {
        RoomElement e = (RoomElement) painter.getNode();
        Point p = e.getPosition();
        Dimension s = e.getSize();

        int leftX = p.x - s.width/2;
        int rightX = p.x + s.width/2;
        int upY = p.y - s.height/2;
        int downY = p.y + s.height/2;

        return ((leftX > start.x && leftX < end.x || rightX > start.x && rightX < end.x)
                && (upY > start.y && upY < end.y || downY > start.y && downY < end.y));
    }

    @Override
    public void notifySubscribers(Object message) {
        room.update(message);
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        room = subscriber;
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        room = null;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public void normalize() {
        int temp;
        if (start.x > end.x) {
            temp = start.x;
            start.x = end.x;
            end.x = temp;
        }
        if (start.y > end.y) {
            temp = start.y;
            start.y = end.y;
            end.y = temp;
        }
    }

    public Rectangle getBounds() {
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);
        return new Rectangle(x, y, width, height);
    }
}

