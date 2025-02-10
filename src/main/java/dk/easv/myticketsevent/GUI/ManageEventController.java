package dk.easv.myticketsevent.GUI;

import dk.easv.myticketsevent.BE.Event;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        showAlert("🎟 Ticket Generated", "Your ticket for " + eventTitleLabel.getText() + " has been generated!");
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
