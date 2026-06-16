package wk3;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface ColorTransform {
    Color transform(Color color);
}
