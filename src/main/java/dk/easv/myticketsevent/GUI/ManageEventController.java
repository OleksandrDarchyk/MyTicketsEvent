package dk.easv.myticketsevent.GUI;

import dk.easv.myticketsevent.BE.Event;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ManageEventController {

    @FXML
    private Label eventTitleLabel;

    @FXML
    private MFXTextField txtLocation;

    @FXML
    private MFXTextField txtDate;

    @FXML
    private MFXComboBox<String> comboAssignCoordinator;

    @FXML
    private ListView<String> participantsList;

    @FXML
    private TextField searchField;

    @FXML
    private MFXButton closeButton;

    @FXML
    private MFXButton btnGetTicket;

    @FXML
    private MFXButton btnGetCoupon;

    private ObservableList<String> participants = FXCollections.observableArrayList();
    private Event event;

    public void setEvent(Event event) {
        this.event = event;
        eventTitleLabel.setText(event.getName());
        txtLocation.setText(event.getLocation());
        txtDate.setText(event.getDateTime());

        comboAssignCoordinator.setItems(FXCollections.observableArrayList("John Doe", "Anna Smith", "Michael Lee"));
        comboAssignCoordinator.getSelectionModel().selectFirst();
    }

    @FXML
    private void addParticipant(ActionEvent event) {
        String participantName = searchField.getText().trim();
        if (!participantName.isEmpty()) {
            participants.add(participantName);
            participantsList.setItems(participants);
            searchField.clear();
        } else {
            showAlert("Error", "Please enter a participant's name.");
        }
    }

    @FXML
    private void deleteParticipant(ActionEvent event) {
        String selectedParticipant = participantsList.getSelectionModel().getSelectedItem();
        if (selectedParticipant != null) {
            participants.remove(selectedParticipant);
        } else {
            showAlert("Error", "Please select a participant to remove.");
        }
    }

    @FXML
    private void saveEvent(ActionEvent event) {
        if (eventTitleLabel.getText().isEmpty() || txtLocation.getText().isEmpty() || txtDate.getText().isEmpty()) {
            showAlert("Error", "Please fill in all event details before saving.");
            return;
        }
        System.out.println("✅ Event saved: " + txtLocation.getText() + " on " + txtDate.getText());
    }

    @FXML
    private void deleteEvent(ActionEvent event) {
        System.out.println("❌ Event deleted: " + eventTitleLabel.getText());
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getTicket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/Ticket.fxml"));
            Parent root = loader.load();

            TicketController controller = loader.getController();

            // Виправлений парсинг дати та часу
            String dateTimeString = txtDate.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime;

            try {
                dateTime = LocalDateTime.parse(dateTimeString, formatter);
            } catch (Exception e) {
                showAlert("Error", "Invalid date format. Please use 'yyyy-MM-dd HH:mm'.");
                return;
            }

            controller.setDetails(eventTitleLabel.getText(), txtLocation.getText(),
                    dateTime.toLocalDate(), dateTime.toLocalTime(), "John Doe");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Ticket");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot open ticket window.");
        }
    }

    @FXML
    private void getCoupon(ActionEvent event) {
        showAlert("🎫 Coupon Generated", "Your discount coupon for " + eventTitleLabel.getText() + " is ready!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
