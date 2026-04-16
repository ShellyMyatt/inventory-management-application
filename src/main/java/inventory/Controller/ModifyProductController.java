package inventory.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import inventory.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The {@code ModifyProductController} class controls the Modify Product form.
 * It is used to modify the information of an existing product in the inventory.
 * It also contains methods to save the modified product to the inventory and to return to the MainScreen-view.
 * <p>
 * This class implements the {@link Initializable} interface to initialize
 * the controller and its user interface elements.
 *
 * @author Shelly Myatt
 */

public class ModifyProductController implements Initializable {
    public TextField modifyProductID;
    public TextField modifyProductName;
    public TextField modifyProductInv;
    public TextField modifyProductPrice;
    public TextField modifyProductMin;
    public TextField modifyProductMax;
    public TextField modifyProductSearch;
    public TableView modifyProductDataTable;
    public TableColumn modifyPartIDCol;
    public TableColumn modifyPartNameCol;
    public TableColumn modifyInventoryCol;
    public TableColumn modifyPriceCol;
    public TableView assoModifyProductDataTable;
    public TableColumn assoPartIDCol;
    public TableColumn assoModifyPartNameCol;
    public TableColumn assoModifyInventoryCol;
    public TableColumn assoModifyPriceCol;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This method transfers the selected product's information to the Modify Product form.
     * This method is used to populate the Modify Product form with the selected product's information.
     * @param product The selected product.
     */
    public void transferProductInfo(Product product) {

        modifyProductID.setText(Integer.toString(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
        modifyProductMax.setText(String.valueOf(product.getMax()));

        associatedParts.clear();
        associatedParts.addAll(product.getAllAssociatedParts());
        assoModifyProductDataTable.setItems(associatedParts);
    }

    /**
     * This method initializes the controller class.
     * This method is called automatically after the fxml file has been loaded.
     * It populates the part table with all parts in the inventory.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + "Initializing AddProductController");

        //Sets items in the modify product data, in the table view
        modifyProductDataTable.setItems(Inventory.getAllParts());

        //Displays the data in the columns of the modifyProductDataTable
        modifyPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Sets items in the associated modify product data, in the table view
        assoModifyProductDataTable.setItems(associatedParts);

        //Displays the data in the columns of the assoModifyProductDataTable
        assoPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoModifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoModifyInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoModifyPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * This method is used to search for a part in the inventory.
     * It pulls up parts based on ID or part name.
     * It is case-insensitive and allows partial matching.
     * If no results are found, an alert is displayed.
     *
     * @param actionEvent The action event that triggered this method. In this case, it is the enter key.
     */
    public void modifyProductSearch(ActionEvent actionEvent) {
        ObservableList<Part> searchedForPart = FXCollections.observableArrayList();
        String search = modifyProductSearch.getText().toLowerCase();

        for (Part searchPart : Inventory.getAllParts()) {
            if (searchPart.getName().toLowerCase().contains(search) ||
                    Integer.toString(searchPart.getId()).contains(search)) {
                searchedForPart.add(searchPart);
            }
        }
        if (searchedForPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Failed");
            alert.setHeaderText("No Part Found");
            alert.setContentText("No search results found");
            alert.showAndWait();
        } else {
            modifyProductDataTable.setItems(searchedForPart);
        }
    }

    /**
     * This method adds a selected part from the part table to the associated parts table.
     * If the part is already in the associated parts table or no part is selected, an alert is displayed.
     *
     * @param actionEvent The action event that triggered this method. Clicking the Add button.
     */
    public void modifyProductAddButton(ActionEvent actionEvent) {
        Part part = (Part) modifyProductDataTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            displayAlert(6);
        } else if (!associatedParts.contains(part)) {
            associatedParts.add(part);
            assoModifyProductDataTable.setItems(associatedParts);
        }
    }

    /**
     * This method Removes any selected part from the associated parts table.
     * If no part is selected, an alert is displayed.
     * If a part is selected, an alert is displayed asking the user if they want to remove it.
     *
     * @param actionEvent The action event that triggered this method. Clicking the Remove button.
     */
    public void modifyProductRemoveAssociatedPartButton(ActionEvent actionEvent) {
        Part selectedPart = (Part) assoModifyProductDataTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(6);

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to Remove this associated part?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                System.out.println("Part removed");
            } else {
                System.out.println("Part not removed");
            }
        }
    }

    /**
     * This method saves the modified product data to the inventory.
     * If the product name is empty, an alert is displayed.
     * If the min, max, or inventory values are incorrect, an alert is displayed.
     * After the product is saved, the user is returned to the main screen.
     *
     * @param actionEvent The action event that triggered this method. Clicking the Save button. <></>
     */
    public void modifyProductSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(modifyProductID.getText());
            String name = modifyProductName.getText();
            double price = Double.parseDouble(modifyProductPrice.getText());
            int stock = Integer.parseInt(modifyProductInv.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            int max = Integer.parseInt(modifyProductMax.getText());

            if (name.isEmpty()) {
                displayAlert(2);
            }
            if (isMinMaxValueCorrect(min, max) && isInventoryValueCorrect(min, max, stock)) {

            Product associatedProduct = new Product(id, name, price, stock, min, max);

            associatedProduct.getAllAssociatedParts().clear();
            associatedProduct.getAllAssociatedParts().addAll(associatedParts);

            Inventory.updateProduct(id, associatedProduct);

                Parent root = FXMLLoader.load(getClass().getResource("/inventory/View/MainScreen-view.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Add Product");
                stage.setScene(scene);
                stage.show();
        }

        } catch (NumberFormatException e) {
            displayAlert(1);
        }
    }

    /**
     * This method cancels the modification operation, and takes you back to the main screen view.
     *
     * @param actionEvent The action event that triggered this method. Clicking the Cancel button.
     * @throws IOException If an error occurs while loading the main screen view.
     */
    public void modifyProductCancelButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/inventory/View/MainScreen-view.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Return to main screen.");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for ensuring the inventory value is between the min and max values.
     *
     * @param min The minimum inventory value.
     * @param max The maximum inventory value.
     * @param stock The current inventory value.
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
     * Method for ensuring the min and max values are correct.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return true if the min and max values are correct, false otherwise.
     */
    private boolean isMinMaxValueCorrect(int min, int max) {
        if (min <= 0 || min >= max) {
            displayAlert(4);
            return false;
        }
        return true;
    }

    /**
     * Displays an alert based on the alert type.
     *
     * @param alertType The type of alert to display.
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
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
                alert.setContentText("Min value must be greater than 0 and less than Max value. Max value must be greater then Min value.");
                alert.showAndWait();
                break;

            case 5:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;

            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
                break;


        }
    }
}
