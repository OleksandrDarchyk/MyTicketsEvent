    package dk.easv.myticketsevent.GUI;

    import io.github.palexdev.materialfx.controls.MFXPasswordField;
    import io.github.palexdev.materialfx.controls.MFXTextField;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Node;
    import javafx.scene.control.Alert;
    import javafx.scene.layout.AnchorPane;
    import javafx.stage.Stage;

    import java.io.IOException;

    public class CreateCoordinatorController {
        @FXML
        private AnchorPane createCoordinatorAnchorPane;

        @FXML
        private MFXTextField coordinatorNameField;

        @FXML
        private MFXPasswordField passwordField;

        // Метод для створення координатора
        @FXML
        private void createCoordinator() {
            String username = coordinatorNameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please fill in both fields.");
                return;
            }

            System.out.println("✅ Creating coordinator: " + username);

            // Повертаємо `gridPaneAdmin` у початковий стан (AdminDController)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/AdminDashboard.fxml"));
                Node adminContent = loader.load();

                // Отримуємо AdminDController і вставляємо головну панель назад
                AdminDController controller = loader.getController();
                controller.populateGridPane(); // Оновлення подій

                // Отримуємо головний GridPane (потрібен доступ з `CreateCoordinator.fxml`)
                AnchorPane root = (AnchorPane) createCoordinatorAnchorPane.getParent();
                root.getChildren().clear();
                root.getChildren().add(adminContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
