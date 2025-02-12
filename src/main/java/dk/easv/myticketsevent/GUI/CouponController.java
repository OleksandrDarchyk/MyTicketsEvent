package dk.easv.myticketsevent.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CouponController implements Initializable {

    @FXML
    private Label couponEvent, couponLocation, couponHolder;

    public void setDetails(String eventName, String location, String participantName) {
        System.out.println("🔍 Setting details: " + eventName + ", " + location + ", " + participantName);
        System.out.println("couponEvent = " + couponEvent); // Друк для перевірки
        if (couponEvent == null) {
            System.out.println("❌ couponEvent не ініціалізовано!");
            return;
        }
        couponEvent.setText(eventName);
        couponLocation.setText(location);
        couponHolder.setText(participantName);
    }


    @FXML
    private void printCoupon() {
        System.out.println("🖨 Printing coupon for: " + couponEvent.getText());
        Stage stage = (Stage) couponEvent.getScene().getWindow();
        stage.close();  // Закриваємо вікно після друку
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("✅ CouponController Initialized");
    }

}
