package wk3;

import javafx.scene.paint.Color;

public class Driver {
    public static void main(String[] args) {
        Color[] colors = { Color.RED, Color.AQUAMARINE, Color.BLUE, Color.CYAN, Color.MAGENTA };
        Color[] transformed = new  Color[colors.length];
        //ColorTransform transformer = new Grayscaler();
        //ColorTransform transformer = new Inverter();
        transform(transformed, pickTransformation(), colors);
        System.out.println(colors[1]);
        System.out.println(transformed[1]);
    }

    private static ColorTransform pickTransformation() {
        ColorTransform transformer = c -> c.grayscale();
        if (Math.random() < 0.5) {
            transformer = c -> c.invert();
        }
        return transformer;
    }

    private static void transform(Color[] transformed, ColorTransform transformer, Color[] colors) {
        for (int i = 0; i < transformed.length; i++) {
            transformed[i] = transformer.transform(colors[i]);
        }
    }

}
