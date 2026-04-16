package inventory.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import inventory.Model.InHouse;
import inventory.Model.Inventory;
import inventory.Model.OutSourced;
import inventory.Model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class controls the Modify Part form. It is used to modify the information of an existing part in the inventory.
 * It also contains methods to save the modified part to the inventory and to return to the MainScreen-view.
 * <p>
 * This class implements the {@link Initializable} interface to initialize the controller and its user interface elements.
 *
 * @author Shelly Myatt
 */
public class ModifyPartController implements Initializable {
//FXML Fields for Modify Part Form
    public AnchorPane modifyPartWindow;
    int partInfoLocation;
    boolean partLocatedInHouse = true;
    public Label PartID;
    public Label PartName;
    public RadioButton modifyPartsInHouseRadioButton;
    public ToggleGroup ModifyRadioButton;
    public RadioButton modifyPartsOutsourcedRadioButton;
    public TextField modifyPartID;
    public TextField modifyPartName;
    public TextField modifyPartInv;
    public TextField modifyPartPrice;
    public TextField modifyPartMax;
    public TextField modifyPartMin;
    public TextField modifyPartMachineID;
    public Label PartInv;
    public Label PartPrice;
    public Label PartMax;
    public Label PartMin;
    public Label machineIDorCompanyName;

    /**
     * This method populates the user interface with the selected part information.
     * It also sets the text of the machineIDorCompanyName Label to "Machine ID" or "Company Name"
     * depending on whether the part is in house or outsourced.
     * <p>
     * RUNTIME ERROR: I originally set the text of modifyPartMachineID TextField to machineIDorCompanyName Label.
     * That incorrectly filled the label in with the company name or machine ID
     * CORRECTION: I corrected this by setting the text to the modifyPartMachineID TextField.
     * @param part The selected part
     */
    public void transferPartInfo(Part part) {
    partInfoLocation = Inventory.getAllParts().indexOf(part);
    if (part instanceof InHouse) {
        modifyPartsInHouseRadioButton.setSelected(true);
        partLocatedInHouse = true;
        machineIDorCompanyName.setText("Machine ID");
        modifyPartMachineID.setText(Integer.toString(((InHouse) part).getMachineID()));
    } else if (part instanceof OutSourced) {
        modifyPartsOutsourcedRadioButton.setSelected(true);
        partLocatedInHouse = false;
        machineIDorCompanyName.setText("Company Name");
        modifyPartMachineID.setText(((OutSourced) part).getCompanyName());
    }
    modifyPartID.setText(Integer.toString(part.getId()));
    modifyPartName.setText(part.getName());
    modifyPartInv.setText(String.valueOf(part.getStock()));
    modifyPartPrice.setText(String.valueOf(part.getPrice()));
    modifyPartMax.setText(String.valueOf(part.getMax()));
    modifyPartMin.setText(String.valueOf(part.getMin()));
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * This method toggles between In House and Outsourced.
     * It changes the text of the machineIDorCompanyName Label to "Machine ID" or "Company Name"
     *
     * @param actionEvent The event that triggered by clicking the InHouse radio button.
     */
    public void onActionPartInHouse(ActionEvent actionEvent) {
        machineIDorCompanyName.setText("Machine ID");
    }

    /**
     * This method toggles between In House and Outsourced.
     * It changes the text of the machineIDorCompanyName Label to "Machine ID" or "Company Name"
     *
     * @param actionEvent The event that triggered by clicking the Outsourced radio button.
     */
    public void onActionPartOutsourced(ActionEvent actionEvent) {
        machineIDorCompanyName.setText("Company Name");
    }
    /**
     * This method handles the save button for the Modify Part form. If In House radio button is selected it retrieves
     * the machine ID, creates a new InHouse part object and saves it to the inventory.
     * If Outsourced radio button is selected it retrieves the company name,
     * creates a OutSourced part object and saves it to the inventory.
     * If any fields are blank or entered incorrectly it will throw an error message.
     * <p>
     * RUNTIME ERROR: I originally tried to get the company name associated with the outsourced part
     * from the radio button but that clearly does not hold the company name.
     * CORRECTION: I fixed this by retrieving the company name from the text field modifyPartMachineID.
     * <p>
     * RUNTIME ERROR: I originally was generating a new ID with getPartID() and it was not working.
     * CORRECTION: I fixed this by retrieving the existing ID from the modifyPartID field.
     *
     * @param actionEvent The event that triggers the save button.
     * @throws IOException If an error occurs while loading the MainScreen-view.
     */
    public void modifyPartSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            double price = Double.parseDouble(modifyPartPrice.getText());
            int stock = Integer.parseInt(modifyPartInv.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());

            if (name.isEmpty()) {
                displayAlert(2);
            }
            if (isMinMaxValueCorrect(min, max) && isInventoryValueCorrect(min, max, stock)) {

                if (modifyPartsInHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(modifyPartMachineID.getText());
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.updatePart(id, newPart);
                } else {
                    String companyName = modifyPartMachineID.getText();
                    OutSourced newPart = new OutSourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(id, newPart);
                }
            }
        } catch (NumberFormatException e) {
            displayAlert(1);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/inventory/View/MainScreen-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Modify Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /** This method handles the cancel button.
     * When clicked it should send you back to the main screen.
     *
     * @param actionEvent The event that triggers the cancel button.
     * @throws IOException If an error occurs while loading the MainScreen-view.
     */
    public void modifyPartCancelButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/inventory/View/MainScreen-view.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Return to main screen.");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Method that ensures that the Inventory value must be equal to or between Min and Max.
     * <p>
     * RUNTIME ERROR: I didn't add return false; when I originally created this method.
     * It saved the modifications even if they were incorrect.
     * CORRECTION: I fixed this by adding return false; after the conditional statement.
     *
     * @param min The minimum value of the inventory.
     * @param max The maximum value of the inventory.
     * @param stock The current value of the inventory
     * @return True if the inventory value is between the min and max values.
     */
    private boolean isInventoryValueCorrect(int min, int max, int stock) {
        if (stock < min || stock > max) {
            displayAlert(3);
            return false;
        }
        return true;
    }

    /**
     * Method for ensuring the min value is greater than 0 and less than Max value.
     * The max value must be greater than the Min value.
     * <p>
     * RUNTIME ERROR: I didn't add return false; when I originally created this method.
     * It saved the modifications even if they were not correct.
     * CORRECTION: I fixed this by adding return false; after the conditional statement.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return True if the min value is greater than 0 and less than the max value.
     */
    private boolean isMinMaxValueCorrect(int min, int max) {
        if (min <= 0 || min >= max) {
            displayAlert(4);
            return false;
        }
        return true;
    }

    /**
     * Displays an alert with the given alert type.
     * Indicating what went wrong.
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
                alert.setHeaderText("Incorrect Min value");
                alert.setContentText("Min value must be greater than 0 and less than Max value. Max must be greater than Min value.");
                alert.showAndWait();
                break;


        }
    }
}
