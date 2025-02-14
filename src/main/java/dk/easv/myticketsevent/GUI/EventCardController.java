package dk.easv.myticketsevent.GUI;

import dk.easv.myticketsevent.BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EventCardController {
    @FXML
    private Label eventLocationlbl, eventNamelbl, eventTimelbl;
    @FXML
    private ImageView eventImage;

    @FXML
    private Button btnManageEvents;

    private Event event;
    private AnchorPane parentContainer;
    private String userRole;

    public void setUserRole(String role) {
        this.userRole = role;
        if ("Admin".equalsIgnoreCase(role)) {
            btnManageEvents.setDisable(true); // Disable the button instead of hiding it
        }
    }

    public void setEvent(Event event) {
        this.event = event;
        eventNamelbl.setText(event.getName());
        eventLocationlbl.setText(event.getLocation());
        eventTimelbl.setText(event.getDateTime());

        // Get the event index (simulate unique images)
        int imageIndex = (event.hashCode() % 8 + 8) % 8 + 1;
        String imagePath = "/dk/easv/myticketsevent/image/" + imageIndex + ".png";

        if (eventImage != null) {
            eventImage.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        } else {
            System.out.println(" eventImage not initialized!");
        }
    }

    public void setParentContainer(AnchorPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void openManageEvents(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/ManageEvents.fxml"));
            Node manageEventsView = loader.load();

            // Get the ManageEventController
            ManageEventController controller = loader.getController();
            if (event != null) {
                controller.setEvent(this.event);
            }

            // Replace the content of `parentContainer`
            if (parentContainer != null) {
                parentContainer.getChildren().clear();
                parentContainer.getChildren().add(manageEventsView);
                AnchorPane.setTopAnchor(manageEventsView, 0.0);
                AnchorPane.setBottomAnchor(manageEventsView, 0.0);
                AnchorPane.setLeftAnchor(manageEventsView, 0.0);
                AnchorPane.setRightAnchor(manageEventsView, 0.0);
            } else {
                System.out.println(" Parent container is null.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Error loading ManageEvents.fxml");
        }
    }

    public void deleteEvent(ActionEvent actionEvent) {
        System.out.println("🗑 Deleting event: " + event.getName());
    }

    @FXML
    private void showExtraInformation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Extra Information");
        alert.setHeaderText(eventNamelbl.getText());
        alert.setContentText("📍 Location: " + eventLocationlbl.getText() + "\n" +
                "🕒 Date & Time: " + eventTimelbl.getText() + "\n" +
                "ℹ️ Additional details about this event will be added later.");

        // Get the dialog window (DialogPane)
        DialogPane dialogPane = alert.getDialogPane();

        // Add styles
        dialogPane.getStylesheets().add(getClass().getResource("/dk/easv/myticketsevent/view/css/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }
}
