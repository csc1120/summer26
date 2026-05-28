package wk1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloWorld extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello World!");
        label.setFont(new Font(40));
        Pane pane = new VBox();
        Pane pane2 = new FlowPane();
        pane2.getChildren().addAll(new Label("Top"), new Button("Middle Middle Middle"), new Label("Bottom"));
        pane.getChildren().add(label);
        pane.getChildren().add(pane2);
        pane.getChildren().add(new TextField("Hello Universe!"));
        Scene scene = new Scene(pane);
        stage.setTitle("Welcome to JavaFX!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}