package wk3;

import javafx.scene.paint.Color;

public class Grayscaler implements ColorTransform {
    public Color transform(Color color) {
        return color.grayscale();
    }
}
