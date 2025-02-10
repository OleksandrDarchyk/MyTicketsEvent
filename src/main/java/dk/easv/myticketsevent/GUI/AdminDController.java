package dk.easv.myticketsevent.GUI;



import dk.easv.myticketsevent.BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDController implements Initializable {

    @FXML
    private GridPane gridPaneAdmin;

    private List<Event> eventList = new ArrayList<>(); // Список подій для тестування

    @FXML
    private Button createUserButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateTestEvents(); // Створюємо тимчасові події
        populateGridPane(); // Завантажуємо події у GridPane
    }

    // Додаємо тестові події без бази даних
    private void generateTestEvents() {
        eventList.add(new Event("EASV Party", "EASV Bar", "2024-04-10 19:00"));
        eventList.add(new Event("Wine Tasting", "Main Hall", "2024-04-15 18:30"));
        eventList.add(new Event("Coding Workshop", "Room C3", "2024-04-20 14:00"));
    }

    public void populateGridPane() {
        gridPaneAdmin.getChildren().clear(); // Очищаємо GridPane перед оновленням
        int numRows = 4;
        int numColumns = 2;
        int col = 0, row = 0;

        for (Event event : eventList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/EventCard.fxml"));
                Pane eventCard = fxmlLoader.load();

                // Передаємо дані у EventCardController
                EventCardController controller = fxmlLoader.getController();
                controller.setEvent(event);

                // Додаємо в GridPane
                gridPaneAdmin.add(eventCard, col, row);
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

    public void createCoordinator(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/CreateCoordinator.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Діалогове вікно
            stage.setTitle("Create Coordinator");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error loading CreateCoordinator.fxml");
        }
    }
    }

