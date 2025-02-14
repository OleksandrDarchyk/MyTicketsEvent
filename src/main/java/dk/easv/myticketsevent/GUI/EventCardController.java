package dk.easv.myticketsevent.GUI;

import dk.easv.myticketsevent.BE.Event;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dk.easv.myticketsevent.GUI.ManageEventController;

import java.io.IOException;

public class EventCardController {
    @FXML
    private Label eventLocationlbl, eventNamelbl, eventTimelbl;
    @FXML
    private ImageView eventImage;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button btnManageEvents;

    private Event event;
    private AnchorPane parentContainer;
    private String userRole;

    public void setUserRole(String role) {
        this.userRole = role;
        if ("Admin".equalsIgnoreCase(role)) {
            btnManageEvents.setDisable(true); // Блокуємо кнопку, замість приховування
        }
    }

    public void setEvent(Event event) {
        this.event = event;
        eventNamelbl.setText(event.getName());
        eventLocationlbl.setText(event.getLocation());
        eventTimelbl.setText(event.getDateTime());

        // Отримуємо індекс події (імітуємо унікальні зображення)
        int imageIndex = (event.hashCode() % 8 + 8) % 8 + 1;
        String imagePath = "/dk/easv/myticketsevent/image/" + imageIndex + ".png";

        if (eventImage != null) {
            eventImage.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        } else {
            System.out.println("❌ eventImage не ініціалізовано!");
        }
    }

    public void setParentContainer(AnchorPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void openManageEvents(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/ManageEvents.fxml"));
            Node manageEventsView = loader.load();

            // Отримуємо контролер ManageEventController
            ManageEventController controller = loader.getController();
            if (event != null) {
                controller.setEvent(this.event);
            }

            // Замінюємо вміст `parentContainer`
            if (parentContainer != null) {
                parentContainer.getChildren().clear();
                parentContainer.getChildren().add(manageEventsView);
                AnchorPane.setTopAnchor(manageEventsView, 0.0);
                AnchorPane.setBottomAnchor(manageEventsView, 0.0);
                AnchorPane.setLeftAnchor(manageEventsView, 0.0);
                AnchorPane.setRightAnchor(manageEventsView, 0.0);
            } else {
                System.out.println("❌ Parent container is null.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error loading ManageEvents.fxml");
        }
    }

    public void deleteEvent(ActionEvent actionEvent) {
        System.out.println("🗑 Видалення події: " + event.getName());
    }

    @FXML
    private void showExtraInformation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Extra Information");
        alert.setHeaderText(eventNamelbl.getText());
        alert.setContentText("📍 Location: " + eventLocationlbl.getText() + "\n" +
                "🕒 Date & Time: " + eventTimelbl.getText() + "\n" +
                "ℹ️ Additional details about this event will be added later.");

        // Отримуємо діалогове вікно (DialogPane)
        DialogPane dialogPane = alert.getDialogPane();

        // Додаємо стилі
        dialogPane.getStylesheets().add(getClass().getResource("/dk/easv/myticketsevent/view/css/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }


}
