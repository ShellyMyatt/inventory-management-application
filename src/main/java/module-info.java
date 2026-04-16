module myatt.Controller {
    requires javafx.controls;
    requires javafx.fxml;


    opens myatt.Controller to javafx.fxml;
    exports myatt.Controller;
    opens myatt.Model to javafx.fxml;
    exports myatt.Model;

}