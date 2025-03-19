package dk.easv.myticketsevent.GUI;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginViewController {
    @FXML
    private MFXTextField usernameField;

    @FXML
    private MFXPasswordField passwordField;

    @FXML
    private MFXButton loginButton;

    @FXML
    public void login(javafx.event.ActionEvent actionEvent) {
        String username = usernameField.getText().trim();  // Видаляємо зайві пробіли
        String password = passwordField.getText().trim();

        System.out.println("DEBUG: Entered username = [" + username + "]");
        System.out.println("DEBUG: Entered password = [" + password + "]");

        if ("a".equals(username) && "123".equals(password)) {
            openDashboard("/dk/easv/myticketsevent/view/AdminDashboard.fxml", "Admin Dashboard");
        } else if ("c".equals(username) && "123".equals(password)) {
            openDashboard("/dk/easv/myticketsevent/view/CoordinatorDashboard.fxml", "Coordinator Dashboard");
        } else {
            System.out.println("Wrong username or password123456");
        }
    }


    private void openDashboard(String fxmlPath, String title) {
        try {
            System.out.println("DEBUG: Loading FXML -> " + fxmlPath); // Console output

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                throw new IOException(" FXML is not find " + fxmlPath);
            }

            Parent root = loader.load();  // Load FXML

            // Create a new scene
            Scene scene = new Scene(root);

            // Add CSS to the new scene
            scene.getStylesheets().add(getClass().getResource("/dk/easv/myticketsevent/view/css/styles.css").toExternalForm());

            // Create and open a new window
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

            // Close the login window
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading: " + fxmlPath);
        }
    }


}
