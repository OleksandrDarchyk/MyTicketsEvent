package dk.easv.myticketsevent.GUI;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CouponController implements Initializable {

    @FXML
    private Label couponEvent, couponLocation, couponHolder;

    @FXML
    private ImageView couponQrCode;

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
        generateQRCode("CPN-2024-ABCD");

    }

    public void generateQRCode(String data) {
        try {
            int width = 150;
            int height = 150;

            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

            Image qrImage = SwingFXUtils.toFXImage(bufferedImage, null);
            couponQrCode.setImage(qrImage);
        } catch (WriterException  e) {
            e.printStackTrace();
            System.out.println("❌ Помилка генерації QR-коду.");
        }
    }

}
