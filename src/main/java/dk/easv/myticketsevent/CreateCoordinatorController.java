package dk.easv.myticketsevent;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateCoordinatorController {
    @FXML
    private AnchorPane createCoordinatorAnchorPane;

    @FXML
    private MFXTextField coordinatorNameField;

    @FXML
    private MFXPasswordField passwordField;

    // Метод для створення координатора
    @FXML
    public void createCoordinator() {
        String username = coordinatorNameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in both fields.");
            return;
        }

        System.out.println("✅ Creating coordinator: " + username + " with password: " + password);

        // Тут має бути логіка додавання в базу або список (поки просто друкуємо)

        closeWindow();
    }

    // Метод для скасування створення координатора
    @FXML
    public void cancel() {
        System.out.println("❌ Action cancelled.");
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) createCoordinatorAnchorPane.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
