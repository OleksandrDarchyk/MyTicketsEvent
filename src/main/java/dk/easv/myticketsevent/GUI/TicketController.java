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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

public class TicketController implements Initializable {

    @FXML
    private AnchorPane ticketAnchor;

    @FXML
    private Label ticketEvent, ticketLocation, ticketDate, ticketTime, ticketParticipantName;
    @FXML
    private Button printButton;
    @FXML
    private ImageView imgQRCode, imgBarcode;

    private UUID ticketId;

    public void setDetails(String eventName, String location, LocalDate date, LocalTime time, String participantName) {
        if (ticketEvent == null || ticketLocation == null || ticketDate == null || ticketTime == null || ticketParticipantName == null) {
            System.err.println("❌ FXML elements are not initialized!");
            return;
        }

        ticketEvent.setText(eventName);
        ticketLocation.setText(location);
        ticketDate.setText(date.toString());
        ticketTime.setText(time.toString());
        ticketParticipantName.setText(participantName);
    }


    public void handlePrint(ActionEvent actionEvent) throws IOException {
        printButton.setVisible(false);

        Scene scene = ticketAnchor.getScene();
        float aspectRatio = (float) scene.getWidth() / (float) scene.getHeight();
        float pdfWidth = 595;
        float pdfHeight = pdfWidth / aspectRatio;

        // Створюємо PDF документ
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(pdfWidth, pdfHeight));
        document.addPage(page);

        // Конвертація сцени у BufferedImage
        WritableImage fxImage = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
        scene.snapshot(fxImage);
        BufferedImage image = SwingFXUtils.fromFXImage(fxImage, null);

        // Конвертація у байтовий масив
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        // Додавання зображення у PDF
        PDImageXObject xImage = PDImageXObject.createFromByteArray(document, imageBytes, "ticket");
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(xImage, 0, 0, pdfWidth, pdfHeight);
        contentStream.close();

        // Збереження PDF
        File directory = new File("tickets/");
        if (!directory.exists()) {
            directory.mkdirs();  // Створює папку, якщо її немає
        }

        String event = ticketEvent.getText().replaceAll("\\s+", "_");
        String name = ticketParticipantName.getText().replaceAll("\\s+", "_");
        File outputFile = new File(directory, "Ticket_" + event + "_" + name + ".pdf");
        document.save(outputFile);
        document.close();

        // Автоматично відкриває PDF
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(outputFile);
            }
        }

        // Закриття вікна квитка
        Stage stage = (Stage) ticketAnchor.getScene().getWindow();
        stage.close();
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
            System.out.println("❌ Помилка генерації QR-коду.");
        }
    }


    private Image generateBarcode(String data) throws Exception {
        BufferedImage barcodeImage = new BufferedImage(200, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = barcodeImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 50);
        g.setColor(Color.BLACK);
        g.fillRect(20, 10, 160, 30);
        g.dispose();
        return SwingFXUtils.toFXImage(barcodeImage, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateQRCode("CPN-2024-ABCD");
        System.out.println("✅ TicketController Initialized");
    }

    public void printTicket(ActionEvent actionEvent) {
    }
}