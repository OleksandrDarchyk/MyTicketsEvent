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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.UUID;

public class TicketController implements Initializable {


    @FXML
    private Label ticketEvent, ticketLocation, ticketDate, ticketTime, ticketParticipantName;
    @FXML
    private Button printButton;
    @FXML
    private ImageView imgQRCode, imgBarcode;


    // If setting details via date and time – generate a barcode
    public void setDetails(String eventName, String location, LocalDate date, LocalTime time, String participantName) {
        if (ticketEvent == null || ticketLocation == null || ticketDate == null || ticketTime == null || ticketParticipantName == null) {
            System.err.println(" FXML elements are not initialized!");
            return;
        }

        ticketEvent.setText(eventName);
        ticketLocation.setText(location);
        ticketDate.setText(date.toString());
        ticketTime.setText(time.toString());
        ticketParticipantName.setText(participantName);

        // Generate a unique ticket code – for example, using UUID
        String ticketCode = UUID.randomUUID().toString();
        generateBarcode(ticketCode);
    }


    public void generateQRCode(String data) {
        try {
            int width = 150;
            int height = 150;

            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

            Image qrImage = SwingFXUtils.toFXImage(bufferedImage, null);
            imgQRCode.setImage(qrImage);
        } catch (WriterException e) {
            e.printStackTrace();
            System.out.println(" Error generating QR code.");
        }
    }

    public void generateBarcode(String data) {
        try {
            int width = 200;
            int height = 50;

            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

            Image barcodeImage = SwingFXUtils.toFXImage(bufferedImage, null);
            imgBarcode.setImage(barcodeImage);
        } catch (WriterException e) {
            e.printStackTrace();
            System.out.println(" Error generating barcode.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateQRCode("CPN-2024-ABCD");
        System.out.println(" TicketController Initialized");
    }

    public void printTicket(ActionEvent actionEvent) {
        // Implement ticket printing if needed
    }
}
