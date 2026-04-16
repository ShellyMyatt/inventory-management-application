package myatt.Controller;

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
import myatt.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The {@code AddProductController} class controls the Add Product form. It is used to add new products to the inventory.
 * It is used to create a new product or to add parts to an existing product.
 * It also contains methods to save the product to the inventory and to return to the MainScreen-view.
 *
 * This class implements the {@link Initializable} interface to initialize the controller and its user interface elements.
 */

public class AddProductController implements Initializable {

    //FXML Fields for Add Product Form
    public TextField productID;
    public TextField productName;
    public TextField productInv;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;
    public TextField productSearch;
    public TableView productDataTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn inventoryCol;
    public TableColumn priceCol;
    public TableView assoProductDataTable;
    public TableColumn assoPartIDCol;
    public TableColumn assoPartNameCol;
    public TableColumn assoInventoryCol;
    public TableColumn assoPriceCol;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This method initializes the controller class.
     * This method is called automatically after the fxml file has been loaded.
     * It populates the part table with all parts in the inventory.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle to use for localizing the root object or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

      System.out.println(getClass().getName() + "Initializing AddProductController");

        productDataTable.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assoProductDataTable.setItems(Product.getAllAssociatedParts);

        assoPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method is used to search for a part in the inventory.
     * It pulls up parts based on ID or part name.
     * It is case-insensitive and allows partial matching.
     * If no results are found, an alert is displayed.
     *
     * @param actionEvent The action event that triggered this method. In this case, it is the enter key.
     */
    public void productSearch(ActionEvent actionEvent) {
        ObservableList<Part> searchedForPart = FXCollections.observableArrayList();
        String search = productSearch.getText().toLowerCase();

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
            productDataTable.setItems(searchedForPart);
        }
    }

    /**
     * This method adds a selected part from the part table to the associated parts table.
     * If the part is already in the associated parts table or no part is selected, an alert is displayed.
     *
     * @param actionEvent The action event that triggered this method. Clicking the Add button.
     */
    public void productAddButton(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) productDataTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(6);
        } else if (!associatedParts.contains(selectedPart)){
            associatedParts.add(selectedPart);
            assoProductDataTable.setItems(associatedParts);
        }
    }

    /**
     * This method Removes any selected part from the associated parts table.
     * If no part is selected, an alert is displayed.
     * If a part is selected, an alert is displayed asking the user if they want to remove it.
     *
     * @param actionEvent The action event that triggered this method. Clicking the Remove button.
     */
    public void productRemoveAssociatedPartButton(ActionEvent actionEvent) {
        Part selectedPart = (Part) assoProductDataTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(6);

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to Remove this associated part?");
            alert.showAndWait();
            associatedParts.remove(selectedPart);
        }
    }

    /**
     * This method saves the new product data to the inventory.
     * If the product name is empty, an alert is displayed.
     * If the min, max, or inventory values are incorrect, an alert is displayed.
     * After the product is saved, the user is returned to the main screen.
     *
     * @param actionEvent The action event that triggered this method. Clicking the Save button. <></>
     */
    public void productSaveButton(ActionEvent actionEvent) throws IOException {
try {
        int id = Inventory.getProductID();
        String name = productName.getText();
        double price = Double.parseDouble(productPrice.getText());
        int stock = Integer.parseInt(productInv.getText());
        int min = Integer.parseInt(productMin.getText());
        int max = Integer.parseInt(productMax.getText());


        if (name.isEmpty()) {
            displayAlert(2);
        }
            if (isMinMaxValueCorrect(min, max) && isInventoryValueCorrect(min, max, stock)) {
                Product product = new Product(id, name, price, stock, min, max);

                for (Part part : associatedParts)
                    product.addAssociatedParts(part);
                Inventory.addProduct(product);
            }
    } catch (NumberFormatException e) {
        displayAlert(1);
    }
    Parent root = FXMLLoader.load(getClass().getResource("/myatt/View/MainScreen-view.fxml"));
    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setTitle("Add Product");
    stage.setScene(scene);
    stage.show();
}

    /**
     * This method cancels the operation, and takes you back to the main screen view.
     *
     * @param actionEvent The action event that triggered this method. Clicking the Cancel button.
     * @throws IOException If an error occurs while loading the main screen view.
     */
    public void productCancelButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/myatt/View/MainScreen-view.fxml")));
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

