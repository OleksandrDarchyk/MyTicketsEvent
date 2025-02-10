package dk.easv.myticketsevent;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import dk.easv.myticketsevent.BE.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;




public class EventCardController {
    @FXML
    private MFXButton deleteBtn;



    @FXML
    private ImageView eventImage;

    @FXML
    private Label eventLocationlbl;

    @FXML
    private Label eventNamelbl;

    @FXML
    private Label eventTimelbl;

    @FXML
    private Pane gridPane;

    private Event event; // Поле для збереження події

    public void setEvent(Event event) {
        this.event = event;
        eventNamelbl.setText(event.getName()); // Встановлюємо назву події
    }

    public void deleteEvent(ActionEvent actionEvent) {
    }
}
