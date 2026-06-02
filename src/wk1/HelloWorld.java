package wk1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloWorld extends Application {
    private Label label;
    private Button button;
    private TextField textField;

    @Override
    public void start(Stage stage) {
        label = new Label("Hello World!");
        label.setFont(new Font(40));
        Pane pane = new VBox();
        Pane pane2 = new FlowPane();
        button = new Button("Middle Middle Middle");
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Pigs don't fly");
            }
        };
        EventHandler<ActionEvent> handler2 = _ -> System.out.println("Pigs don't fly");
        button.setOnAction(event -> handleButton(event));
        button.setOnAction(this::handleButton);
//        button.setOnAction(actionEvent -> {
//            if (button.getText().equals("Middle Middle Middle")) {
//                button.setText("First Second Third");
//            } else {
//                button.setText("Middle Middle Middle");
//            }
//        });
        //button.setOnAction(new ButtonHandler());
        pane2.getChildren().addAll(new Label("Top"), button, new Label("Bottom"));
        pane.getChildren().add(label);
        pane.getChildren().add(pane2);
        textField = new TextField("Hello Universe!");
        pane.getChildren().add(textField);
//        textField.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                label.setText(textField.getText());
//            }
//        });
        textField.setOnAction(actionEvent -> label.setText(textField.getText()));
        Scene scene = new Scene(pane);
        stage.setTitle("Welcome to JavaFX!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }

    private void handleButton(ActionEvent event) {
        if (button.getText().equals("Middle Middle Middle")) {
            button.setText("First Second Third");
        } else {
            button.setText("Middle Middle Middle");
        }
    }
    /*
    private class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (button.getText().equals("Middle Middle Middle")) {
                button.setText("First Second Third");
            } else {
                button.setText("Middle Middle Middle");
            }
        }
    }

    private class TextHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            label.setText(textField.getText());
            button.setText(textField.getText());
        }
    }
     */
}