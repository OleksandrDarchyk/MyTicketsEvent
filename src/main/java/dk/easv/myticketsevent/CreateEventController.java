package dk.easv.myticketsevent;

import io.github.palexdev.materialfx.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class CreateEventController {

    @FXML
    private MFXTextField txtEventName, txtLocation, numOfTickets, txtNotes, txtLocationGuidance;

    @FXML
    private MFXDatePicker dateStartDate, dateEndDate;

    @FXML
    private MFXComboBox<String> comboStartTime, comboEndTime;

    @FXML
    private MFXFilterComboBox<String> comboAssignCoordinator;

    @FXML
    private MFXButton btnTickets, btnSave, btnCancel, btnDelete;

    @FXML
    public void initialize() {
        // Заповнюємо випадаючі списки часовими слотами
        comboStartTime.getItems().addAll("10:00", "12:00", "14:00", "16:00", "18:00", "20:00");
        comboEndTime.getItems().addAll("11:00", "13:00", "15:00", "17:00", "19:00", "21:00");

        // Додаємо фіктивних координаторів
        comboAssignCoordinator.getItems().addAll("John Doe", "Alice Smith", "Michael Brown");
    }

    @FXML
    public void saveAction() {
        String eventName = txtEventName.getText().trim();
        String location = txtLocation.getText().trim();
        String startDate = (dateStartDate.getValue() != null) ? dateStartDate.getValue().toString() : "";
        String endDate = (dateEndDate.getValue() != null) ? dateEndDate.getValue().toString() : "";
        String startTime = comboStartTime.getValue();
        String endTime = comboEndTime.getValue();
        String tickets = numOfTickets.getText().trim();
        String coordinator = comboAssignCoordinator.getValue();
        String notes = txtNotes.getText().trim();
        String locationGuidance = txtLocationGuidance.getText().trim();

        if (eventName.isEmpty() || location.isEmpty() || startDate.isEmpty() || endDate.isEmpty()
                || startTime == null || endTime == null || tickets.isEmpty() || coordinator == null) {
            showAlert("Error", "Please fill in all required fields.");
            return;
        }

        System.out.println("✅ Event Created:");
        System.out.println("Name: " + eventName);
        System.out.println("Location: " + location);
        System.out.println("Start: " + startDate + " " + startTime);
        System.out.println("End: " + endDate + " " + endTime);
        System.out.println("Tickets: " + tickets);
        System.out.println("Coordinator: " + coordinator);
        System.out.println("Notes: " + notes);
        System.out.println("Location Guidance: " + locationGuidance);

        closeWindow();
    }

    @FXML
    public void cancelAction() {
        System.out.println("❌ Event creation canceled.");
        closeWindow();
    }

    @FXML
    public void deleteEventAction() {
        System.out.println("🗑 Event deleted (dummy function).");
        showAlert("Info", "Event deleted successfully.");
        closeWindow();
    }

    @FXML
    public void ticketsAction() {
        System.out.println("🎟 Open ticket options (dummy function).");
        showAlert("Info", "Ticket options will be implemented later.");
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
