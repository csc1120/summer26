package wk1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        if (button.getText().equals("Middle Middle Middle")) {
            button.setText("First Second Third");
        } else {
            button.setText("Middle Middle Middle");
        }
    }
}
