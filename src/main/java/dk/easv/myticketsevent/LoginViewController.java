package dk.easv.myticketsevent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginViewController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}