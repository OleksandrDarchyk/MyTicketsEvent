package dk.easv.myticketsevent.GUI;

import dk.easv.myticketsevent.BE.Coordinator;
import dk.easv.myticketsevent.BE.Event;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CoordinatorDashboardController implements Initializable {

    @FXML
    private GridPane gridPaneCoord;
    @FXML
    private AnchorPane contentContainer; // Головний контейнер для вмісту
    @FXML
    private MFXButton btnCreateEvent;

    private Coordinator coordinator;
    private List<Event> eventList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateTestEvents();
        showHome(); // Ініціалізація головного екрана
    }

    // Показати головний екран з картками подій
    private void showHome() {
        contentContainer.getChildren().clear();
        contentContainer.getChildren().add(gridPaneCoord);
        populateGridPane();
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
        populateGridPane();
    }

    private void generateTestEvents() {
        eventList.add(new Event("Live Music Night", "EASV Bar", "2024-04-12 20:00"));
        eventList.add(new Event("Movie Night", "Auditorium", "2024-04-18 19:30"));
        eventList.add(new Event("Board Games Evening", "Cafeteria", "2024-04-25 18:00"));
    }

    public void populateGridPane() {
        gridPaneCoord.getChildren().clear();
        int col = 0, row = 0;
        int numColumns = 2;

        for (Event event : eventList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/EventCard.fxml"));
                Pane eventCard = fxmlLoader.load();

                EventCardController controller = fxmlLoader.getController();
                controller.setEvent(event);
                controller.setParentContainer(contentContainer);
                controller.setUserRole("Coordinator"); // Передаємо роль

                gridPaneCoord.add(eventCard, col, row);
                col++;
                if (col == numColumns) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("❌ Помилка завантаження EventCard.fxml");
            }
        }
    }


    @FXML
    private void openCreateEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/CreateEvent.fxml"));
            Node newContent = loader.load();
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Помилка завантаження CreateEvent.fxml");
        }
    }

    @FXML
    public void cancelAction() {
        showHome(); // Повернення на головний екран
    }

    @FXML
    private void goToHome(ActionEvent event) {
        showHome();
        System.out.println("✅ Головний екран координатора оновлено");
    }
    @FXML
    private void openTicket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/TicketView.fxml"));
            Parent root = loader.load();

            TicketController controller = loader.getController();
            controller.setDetails("EASV Party", "EASV Bar", LocalDate.of(2024, 4, 10), LocalTime.of(19, 0), "John Doe");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ticket");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logOut(ActionEvent actionEvent) {
        try {
            // Завантажуємо екран входу
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/LoginView.fxml"));
            Parent root = loader.load();

            // Отримуємо поточне вікно
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Створюємо нову сцену та додаємо стиль
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/dk/easv/myticketsevent/view/css/styles.css").toExternalForm());

            // Встановлюємо сцену та показуємо вікно
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();

            System.out.println("✅ Координатор вийшов з системи.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Помилка завантаження LoginView.fxml");
        }
    }

}