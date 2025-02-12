package dk.easv.myticketsevent.GUI;

import dk.easv.myticketsevent.BE.Event;
import dk.easv.myticketsevent.BE.Ticket;
import io.github.palexdev.materialfx.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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
        LocalDate startDate = dateStartDate.getValue();
        String startTimeStr = comboStartTime.getValue();
        String coordinator = comboAssignCoordinator.getValue();

        if (eventName.isEmpty() || location.isEmpty() || startDate == null || startTimeStr == null || coordinator == null) {
            showAlert("Error", "Please fill in all required fields.");
            return;
        }

        // Конвертуємо строку в LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(startTimeStr, timeFormatter);

        // Створюємо подію
        Event newEvent = new Event(eventName, location, startDate.toString() + " " + startTime);
        System.out.println("🎉 Event Created: " + newEvent);

        // Створюємо квиток автоматично
        Ticket newTicket = new Ticket(UUID.randomUUID(), eventName, location, startDate, startTime, "John Doe");
        System.out.println("🎟 Ticket Created: " + newTicket);

        showAlert("Success", "Event and ticket created successfully!");
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
