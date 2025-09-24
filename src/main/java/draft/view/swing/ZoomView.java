package draft.view.swing;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ZoomView extends JPanel {
    private final JLabel zoomValueLabel;

    public ZoomView(double zoom) {
        zoomValueLabel = new JLabel();
        update(zoom);
        add(zoomValueLabel);
    }

    public void update(double value) {
        double val = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
        zoomValueLabel.setText("Zoom: " + (int)(val * 100.0) + "%");
    }
}
