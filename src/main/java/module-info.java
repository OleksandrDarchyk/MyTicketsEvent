module dk.easv.myticketsevent {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.easv.myticketsevent to javafx.fxml;
    exports dk.easv.myticketsevent;
}