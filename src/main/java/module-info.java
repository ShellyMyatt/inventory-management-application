module inventory.Controller {
    requires javafx.controls;
    requires javafx.fxml;


    opens inventory.Controller to javafx.fxml;
    exports inventory.Controller;
    opens inventory.Model to javafx.fxml;
    exports inventory.Model;

}