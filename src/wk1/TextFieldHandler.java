package wk1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class TextFieldHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        TextField textField = (TextField) actionEvent.getSource();
        System.out.println(textField.getText());
    }
}
