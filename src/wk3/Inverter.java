package wk3;

import javafx.scene.paint.Color;

public class Inverter implements ColorTransform {
    @Override
    public Color transform(Color color) {
        return color.invert();
    }
}
