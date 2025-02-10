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

        System.out.println("DEBUG: Введений логін = [" + username + "]");
        System.out.println("DEBUG: Введений пароль = [" + password + "]");

        if ("a".equals(username) && "123".equals(password)) {
            openDashboard("/dk/easv/myticketsevent/AdminDashboard.fxml", "Admin Dashboard");
        } else if ("c".equals(username) && "123".equals(password)) {
            openDashboard("/dk/easv/myticketsevent/CoordinatorDashboard.fxml", "Coordinator Dashboard");
        } else {
            System.out.println("Wrong username or password");
        }
    }


    private void openDashboard(String fxmlPath, String title) {
        try {
            System.out.println("DEBUG: Завантажую FXML -> " + fxmlPath); // Вивід у консоль

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                throw new IOException("❌ FXML-файл не знайдено: " + fxmlPath);
            }

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            // Закриваємо вікно логіну
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Помилка завантаження: " + fxmlPath);
        }
    }


}
