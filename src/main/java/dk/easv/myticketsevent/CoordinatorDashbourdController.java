package dk.easv.myticketsevent;


import dk.easv.myticketsevent.BE.Coordinator;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CoordinatorDashboardController implements Initializable {

    @FXML
    private GridPane eventPane; // Важливо: переконайся, що у FXML цей ID правильний

    private Coordinator coordinator;
    private List<Event> eventList = new ArrayList<>(); // Список подій без BLL/DAL

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
        eventPane.getChildren().clear(); // Очищуємо GridPane перед завантаженням нових карток
        int numRows = 4;
        int numColumns = 2;
        int col = 0, row = 0;

        for (Event event : eventList) { // Використовуємо локальний список подій
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/view/EventCard.fxml"));
                Pane eventCard = fxmlLoader.load();

                // Передаємо дані у `EventCardController`
                EventCardController controller = fxmlLoader.getController();
                controller.setEvent(event);

                // Додаємо картку в `GridPane`
                eventPane.add(eventCard, col, row);
                col++;
                if (col == numColumns) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
