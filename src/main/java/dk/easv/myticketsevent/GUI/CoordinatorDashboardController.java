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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CoordinatorDashboardController implements Initializable {

    @FXML
    private GridPane gridPaneCoord; // Важливо: переконайся, що у FXML цей ID правильний

    @FXML
    private MFXButton btnCancel;

    @FXML
    private AnchorPane anchorPaneCoord;


    private Coordinator coordinator;
    private List<Event> eventList = new ArrayList<>(); // Список подій без BLL/DAL

    @FXML
    private MFXButton btnCreateEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateTestEvents(); // Створюємо тестові події
        populateGridPane(); // Завантажуємо картки у GridPane
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
        populateGridPane(); // Оновлення подій після встановлення координатора
    }

    // Додаємо тестові події (без бази даних)
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

                // Отримуємо контролер картки
                EventCardController controller = fxmlLoader.getController();
                controller.setEvent(event);
                controller.setParentContainer(anchorPaneCoord); // Передаємо контейнер для заміни вмісту

                // Додаємо картку у GridPane
                gridPaneCoord.add(eventCard, col, row);
                col++;
                if (col == numColumns) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("❌ Error loading EventCard.fxml");
            }
        }
    }



    @FXML
    private void openCreateEvent(ActionEvent event) {
        try {
            // Завантажуємо CreateEvent.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/CreateEvent.fxml"));
            Node createEventView = loader.load();

            // Очищаємо anchorPaneCoord і додаємо CreateEvent.fxml
            anchorPaneCoord.getChildren().clear();
            anchorPaneCoord.getChildren().add(createEventView);

            // Прив’язуємо CreateEvent.fxml до розмірів anchorPaneCoord
            AnchorPane.setTopAnchor(createEventView, 0.0);
            AnchorPane.setBottomAnchor(createEventView, 0.0);
            AnchorPane.setLeftAnchor(createEventView, 0.0);
            AnchorPane.setRightAnchor(createEventView, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error loading CreateEvent.fxml");
        }
    }


    @FXML
    public void cancelAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/CoordinatorDashboard.fxml"));
            Node dashboardContent = loader.load();

            // Отримуємо контролер CoordinatorDashboardController
            CoordinatorDashboardController controller = loader.getController();
            controller.populateGridPane(); // Оновлення списку подій

            // Отримуємо батьківський контейнер (gridPaneCoord)
            AnchorPane root = (AnchorPane) btnCancel.getParent().getParent(); // btnCancel всередині VBox → HBox → AnchorPane
            root.getChildren().clear();
            root.getChildren().add(dashboardContent);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error loading CoordinatorDashboard.fxml");
        }
    }
}

