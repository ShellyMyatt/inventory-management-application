package myatt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import myatt.Model.InHouse;
import myatt.Model.Inventory;
import myatt.Model.OutSourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class controls the user interface for the Add Part form.
 * It is used to add new parts to the inventory, in either In House or Out Sourced.
 * This class also contains methods to save the part to the inventory and to return to the MainScreen-view.
 *
 * This class implements the {@link Initializable} interface to initialize the controller and its user interface elements.
 *
 * @author Shelly Myatt
 */

public class AddPartController implements Initializable {

    public RadioButton inHouseRadioButton;
    public ToggleGroup RadioButton;
    public javafx.scene.control.RadioButton outsourcedRadioButton;
    public TextField machineIDCompanyNameTf;
    public Label machineIDorCompanyNameLabel;
    public TextField partID;
    public TextField partName;
    public TextField partInv;
    public TextField partPrice;
    public TextField partMax;
    public TextField partMin;
    public static int part;

    /**
     * This method initializes the controller class.
     * This method is called automatically after the fxml file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle to use for localizing the root object or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * THis method is called when the InHouse radio button is selected.
     * This method is called when the user selects In House and updates the label to Machine ID.
     *
     * @param actionEvent The event that is triggered when the InHouse radio button is selected.
     */
    public void onActionPartInHouse(ActionEvent actionEvent) {
        machineIDorCompanyNameLabel.setText("Machine ID");
    }

    /**
     * This method is called when the Outsourced radio button is selected.
     * This method is called when the user selects Outsourced and updates the label to Company Name.
     *
     * @param actionEvent The event that is triggered when the Outsourced radio button is selected.
     */
    public void onActionPartOutsourced(ActionEvent actionEvent) {
        machineIDorCompanyNameLabel.setText("Company Name");
    }

    /**
     * This method handles the save button action event. First, it validates the input fields.
     * If InHouse radio button is selected then it will save as a new InHouse part.
     * If Outsourced radio button is selected it will save as a new OutSourced part.
     * If any fields are blank or entered incorrectly it will display an error message.
     *
     * @param actionEvent The event that triggers the save button.
     * @throws IOException If an error occurs while loading the MainScreen-view.
     */
    public void saveButton(ActionEvent actionEvent) throws IOException {
        try {

            int id = Inventory.getPartID();
            String name = partName.getText();
            double price = Double.parseDouble(partPrice.getText());
            int stock = Integer.parseInt(partInv.getText());
            int min = Integer.parseInt(partMin.getText());
            int max = Integer.parseInt(partMax.getText());

            if (name.isEmpty()) {
                displayAlert(2);
            }
            if (isMinMaxValueCorrect(min, max) && isInventoryValueCorrect(min, max, stock)) {

                if (inHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(machineIDCompanyNameTf.getText());
                    InHouse inHouse = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.addPart(inHouse);
                } else {
                        String companyName = machineIDCompanyNameTf.getText();
                        OutSourced outSourcedPart = new OutSourced(id, name, price, stock, min, max, companyName);
                        Inventory.addPart(outSourcedPart);
                    }
                }
        } catch (NumberFormatException e) {
            displayAlert(1);
        }
        Parent root = FXMLLoader.load(getClass().getResource("/myatt/View/MainScreen-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /** This method handles the cancel button.
     * When pressed it should send you back to the main screen.
     *
     * @param actionEvent The event that triggers the cancel button.
     * @throws IOException If an error occurs while loading the MainScreen-view.
     */
    @FXML
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/myatt/View/MainScreen-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Return to main screen.");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that validates that the Inventory value must be equal to or between Min and Max.
     *
     * @param min The minimum inventory value.
     * @param max The maximum inventory value.
     * @param stock The current stock value.
     * @return true if the inventory value is between the min and max values, false otherwise.
     */
    private boolean isInventoryValueCorrect(int min, int max, int stock) {
        if (stock < min || stock > max) {
            displayAlert(3);
            return false;
        }
        return true;
    }

    /**
     * Method that validates that the min value is greater than 0 and less than Max value.
     * The max value must be greater than the Min value.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return true if the min value is greater than 0 and less than the max value, false otherwise.
     */
    private boolean isMinMaxValueCorrect(int min, int max) {
        if (min <= 0 || min >= max) {
            displayAlert(4);
            return false;
        }
        return true;
    }

    /**
     * Method that displays an alert based on the alert type.
     * @param alertType The type of alert to display.
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText("Contains blank text fields or invalid characters");
                alert.showAndWait();
                break;

                case 2:
                    alert.setTitle("Error");
                    alert.setHeaderText("Name Field Empty");
                    alert.setContentText("Name Field cannot be empty");
                    alert.showAndWait();
                    break;

                    case 3:
                        alert.setTitle("Error");
                        alert.setHeaderText("Incorrect Inventory value");
                        alert.setContentText("Inventory value must be equal to or between Min and Max");
                        alert.showAndWait();
                        break;

                        case 4:
                            alert.setTitle("Error");
                            alert.setHeaderText("Incorrect Min or Max value");
                            alert.setContentText("Min value must be greater than 0 and less than Max value. Max value must be greater then Min value.");
                            alert.showAndWait();
                            break;


        }
    }
}



