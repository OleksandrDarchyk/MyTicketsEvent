package dk.easv.myticketsevent.GUI;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import dk.easv.myticketsevent.BE.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

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
        eventNamelbl.setText(event.getName());
        eventLocationlbl.setText(event.getLocation());
        eventTimelbl.setText(event.getDateTime());
    }

    public void deleteEvent(ActionEvent actionEvent) {
        System.out.println("Delete Event: " + event.getName());
        // Логіка видалення буде реалізована в BLL (поки що лише вивід у консоль)
    }

    public void openManageEvents(ActionEvent actionEvent) {
        try {
            System.out.println("DEBUG: Відкриваю ManageEvents.fxml...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/ManageEvents.fxml"));
            Parent root = loader.load();

            // Отримуємо контролер ManageEventController
            ManageEventController controller = loader.getController();
            if (event != null) {
                controller.setEvent(this.event);
            }

            Stage stage = new Stage();
            stage.setTitle("Manage Event");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Помилка відкриття ManageEvents.fxml: " + e.getMessage());
        }
    }


}
