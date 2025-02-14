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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private AnchorPane contentContainer;


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
        System.out.println("DEBUG: Натиснуто кнопку Add");
        System.out.println("DEBUG: contentContainer = " + contentContainer);

        if (contentContainer == null) {
            showAlert("Error", "contentContainer не ініціалізовано! Перевір FXML.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/CreateCustomer.fxml"));
            Node newContent = loader.load();

            // Отримуємо контролер CreateCustomerController
            CreateCostumerController controller = loader.getController();
            controller.setOnCustomerAdded(this::updateParticipantsList);

            // Замінюємо вміст `contentContainer`
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(newContent);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot open CreateCustomer.fxml");
        }
    }

    @FXML
    public void cancelCostumer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/ManageEvents.fxml"));
            Node manageEventsView = loader.load();

            // Отримуємо контролер ManageEventController, щоб оновити учасників
            ManageEventController controller = loader.getController();
            controller.setEvent(this.event); // Передаємо поточний івент

            // Очищаємо контейнер і повертаємо ManageEvents.fxml
            AnchorPane root = (AnchorPane) contentContainer.getParent();

            root.getChildren().clear();
            root.getChildren().add(manageEventsView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void updateParticipantsList(String customerName) {
        if (customerName != null && !customerName.isEmpty()) {
            participants.add(customerName);
            participantsList.setItems(participants);
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

            // Отримуємо контролер після завантаження
            TicketController controller = loader.getController();

            // Парсимо дату та час
            String dateTimeString = txtDate.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime;

            try {
                dateTime = LocalDateTime.parse(dateTimeString, formatter);
            } catch (Exception e) {
                showAlert("Error", "Invalid date format. Please use 'yyyy-MM-dd HH:mm'.");
                return;
            }

            // Викликаємо setDetails() тільки після ініціалізації
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/Coupon.fxml"));
            Parent root = loader.load();

            // Отримуємо контролер купона
            CouponController controller = loader.getController();
            controller.setDetails(eventTitleLabel.getText(), txtLocation.getText(), "John Doe");

            // Відкриваємо вікно купона
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Discount Coupon");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot open coupon window.");
        }
    }





    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
