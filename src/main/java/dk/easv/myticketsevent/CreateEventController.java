package dk.easv.myticketsevent;

import javafx.event.ActionEvent;

public class CreateEventController {

    private Runnable refreshCallback;

    public void setRefreshCallback(Runnable refreshCallback) {
        this.refreshCallback = refreshCallback;
    }

    public void createEvent() {
        // ... код створення події ...
        if (refreshCallback != null) {
            refreshCallback.run(); // Виклик оновлення карток
        }
    }
    public void ticketsAction(ActionEvent actionEvent) {
    }

    public void deleteEventAction(ActionEvent actionEvent) {
    }

    public void cancelAction(ActionEvent actionEvent) {
    }

    public void saveAction(ActionEvent actionEvent) {
    }
}
