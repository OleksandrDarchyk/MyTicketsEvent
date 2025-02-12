package dk.easv.myticketsevent.GUI;

import dk.easv.myticketsevent.BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDController implements Initializable {

    @FXML
    private GridPane gridPaneAdmin;
    @FXML
    private AnchorPane contentContainer; // Головний контейнер для вмісту
    @FXML
    private Button createUserButton;

    private List<Event> eventList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateTestEvents();
        showHome(); // Ініціалізація головного екрана
    }

    // Показати головний екран з картками подій
    private void showHome() {
        contentContainer.getChildren().clear();
        contentContainer.getChildren().add(gridPaneAdmin);
        populateGridPane();
    }

    private void generateTestEvents() {
        eventList.add(new Event("EASV Party", "EASV Bar", "2024-04-10 19:00"));
        eventList.add(new Event("Wine Tasting", "Main Hall", "2024-04-15 18:30"));
        eventList.add(new Event("Coding Workshop", "Room C3", "2024-04-20 14:00"));
    }

    public void populateGridPane() {
        gridPaneAdmin.getChildren().clear();
        int col = 0, row = 0;
        int numColumns = 2;

        for (Event event : eventList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/EventCard.fxml"));
                Pane eventCard = fxmlLoader.load();

                EventCardController controller = fxmlLoader.getController();
                controller.setEvent(event);
                controller.setParentContainer(contentContainer);
                controller.setUserRole("Admin"); // Передаємо роль

                gridPaneAdmin.add(eventCard, col, row);
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


    public void createCoordinator(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/CreateCoordinator.fxml"));
            Node newContent = loader.load();
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Помилка завантаження CreateCoordinator.fxml");
        }
    }

    @FXML
    private void goToHome(ActionEvent event) {
        showHome(); // Просте оновлення без зайвих завантажень
        System.out.println("✅ Головний екран оновлено");
    }
}