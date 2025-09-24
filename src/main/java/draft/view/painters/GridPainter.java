package draft.view.painters;

import java.awt.*;

public class GridPainter {
    private int width;
    private int height;
    private int xStep;
    private int yStep;

    public GridPainter(int xStep, int yStep, Dimension size) {
        this.xStep = xStep;
        this.yStep = yStep;
        this.width = size.width;
        this.height = size.height;
    }

    public void paint(Graphics g){
        g.setColor(Color.BLACK);

        for (int y = 0; y + yStep <= height; y+=yStep)
            g.drawLine(0,y,width,y);

        for (int x = 0; x + xStep <= width; x+=xStep)
            g.drawLine(x,0,x,height);

    }
}
