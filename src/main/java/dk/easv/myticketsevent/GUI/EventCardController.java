package dk.easv.myticketsevent.GUI;

import dk.easv.myticketsevent.BE.Event;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class EventCardController {

    @FXML
    private MFXButton deleteBtn;

    @FXML
    private MFXButton btnManageEvents;


    @FXML
    private Label eventLocationlbl, eventNamelbl, eventTimelbl;

    @FXML
    private Pane gridPane;

    private Event event;
    private AnchorPane parentContainer; // Контейнер для заміни вмісту

    private String userRole;

    public void setEvent(Event event) {
        this.event = event;
        eventNamelbl.setText(event.getName());
        eventLocationlbl.setText(event.getLocation());
        eventTimelbl.setText(event.getDateTime());
    }

    public void setUserRole(String role) {
        this.userRole = role;
        if ("Admin".equalsIgnoreCase(role)) {
            btnManageEvents.setVisible(false); // Приховуємо кнопку для Admin
        }
    }


    // Передаємо головний контейнер (AnchorPane) з CoordinatorDashboard або AdminDashboard
    public void setParentContainer(AnchorPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void openManageEvents(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/myticketsevent/view/ManageEvents.fxml"));
            Node manageEventsView = loader.load();

            // Отримуємо контролер ManageEventController
            ManageEventController controller = loader.getController();
            if (event != null) {
                controller.setEvent(this.event);
            }

            // Замінюємо вміст `parentContainer` (CoordinatorDashboard або AdminDashboard)
            if (parentContainer != null) {
                parentContainer.getChildren().clear();
                parentContainer.getChildren().add(manageEventsView);

                // Прив’язуємо до розмірів AnchorPane
                AnchorPane.setTopAnchor(manageEventsView, 0.0);
                AnchorPane.setBottomAnchor(manageEventsView, 0.0);
                AnchorPane.setLeftAnchor(manageEventsView, 0.0);
                AnchorPane.setRightAnchor(manageEventsView, 0.0);
            } else {
                System.out.println("❌ Parent container is null.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error loading ManageEvents.fxml");
        }
    }

    public void deleteEvent(ActionEvent actionEvent) {
    }
}
