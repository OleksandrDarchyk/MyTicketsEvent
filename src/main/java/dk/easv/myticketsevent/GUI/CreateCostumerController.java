package dk.easv.myticketsevent.GUI;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class CreateCostumerController {

    @FXML
    private MFXTextField costumerNameField;
    @FXML
    private MFXTextField costumerEmailField;

    private Consumer<String> onCustomerAdded;

    // Метод для встановлення callback-функції
    public void setOnCustomerAdded(Consumer<String> callback) {
        this.onCustomerAdded = callback;
    }

    @FXML
    private void createCostumer(ActionEvent actionEvent) {
        String name = costumerNameField.getText().trim();
        String email = costumerEmailField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            System.out.println("❌ Name or email is empty!");
            return;
        }

        System.out.println("✅ New customer added: " + name);

        // Викликаємо callback-функцію, щоб повернути ім'я у ManageEventController
        if (onCustomerAdded != null) {
            onCustomerAdded.accept(name);
        }

        closeWindow();
    }

    @FXML
    private void cancelCostumer(ActionEvent actionEvent) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) costumerNameField.getScene().getWindow();
        stage.close();
    }
}
