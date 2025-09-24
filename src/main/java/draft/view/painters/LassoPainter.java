package draft.view.painters;

import draft.model.draftRepository.nodes.elements.Lasso;

import java.awt.*;

public class LassoPainter
{
    Lasso lasso;

    public LassoPainter(Lasso lasso)
    {
        this.lasso = lasso;
    }

    public void paint(Graphics2D g)
    {
        Rectangle bounds = lasso.getBounds();
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0));
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }


}
