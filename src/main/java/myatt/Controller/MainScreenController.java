package myatt.Controller;

import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import myatt.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The {@code MainScreenController} class controls the MainScreen-view.
 * It is used to display and manage user interactions with the Parts and Products tables in the MainScreen-view.
 * It also contains methods to add, modify, and delete the selected part or product to the inventory and to return to the MainScreen-view.
 *
 * This class implements the {@link Initializable} interface to initialize the controller and its user interface elements.
 */

public class MainScreenController implements Initializable {

    public TableView partsTable;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TextField partSearchTf;
    public TextField productSearchTf;
    public TableColumn productIDColumn;
    public TableColumn productNameColumn;
    public TableView productTable;
    public AnchorPane Parts;
    public AnchorPane Products;
    public TableColumn productsInventoryCol;
    public TableColumn productPriceCol;
    public TableColumn partsInventoryCol;
    public TableColumn partPriceCol;

    /**
     * This method initializes the controller class.
     * This method is called automatically after the fxml file has been loaded.
     * It populates the Parts and Products tables in the MainScreen-view.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     *            @param resourceBundle The resource bundle to use for localizing the root object or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fills the Parts table in the MainScreen-view.

        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        // Fills the Products table in the MainScreen-view.
        productTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method handles the add part button event.
     * It takes you to the AddPart-view and allows you to add a new part to the parts table.
     *
     * @param actionEvent The add part button click event.
     * @throws IOException If an error occurs while loading the AddPart-view
     */
    public void addPartsButton(ActionEvent actionEvent) throws IOException {
        System.out.println("Part has been added");
        Parent root = FXMLLoader.load(getClass().getResource("/myatt/View/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method handles the add product button for the product table.
     * It will take you to a new screen that allows you to add a new product.
     *
     * @param actionEvent The add product button click event.
     * @throws IOException If an error occurs while loading the AddProduct-view
     */
    public void addProductsButton(ActionEvent actionEvent) throws IOException {
        System.out.println("Product has been added");
        Parent root = FXMLLoader.load(getClass().getResource("/myatt/View/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method handles the modify part button for the parts table.
     * It will take you to a new screen that allows you to modify a part.
     * If no part is selected an alert will prompt you to first select a part to modify.
     *
     * RUNTIME ERROR: Previously, clicking the Modify button without first selecting a part would cause a NullPointerException.
     * CORRECTION: I corrected this by creating an alert that would prompt the user to please select a part to modify.
     * FUTURE ENHANCEMENT: The modify button could be disabled and a lighter color until the user selects a part to modify.
     * Once a part is selected the modify button would change color and be enabled.
     *
     * @param actionEvent The modify part button click event.
     * @throws IOException If an error occurs while loading the ModifyPart-view
     */
    public void modifyPartsButton(ActionEvent actionEvent) throws IOException {
        Part selectModifyPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (selectModifyPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to Modify");
            alert.showAndWait();

        } else {
            System.out.println("Part has been modified");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/myatt/View/ModifyPart.fxml"));
        loader.load();

        ModifyPartController modifyPartController = loader.getController();
        modifyPartController.transferPartInfo(selectModifyPart);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle("Modify Part");
        stage.show();
        }
    }

    /**
     * This method is the modify product button for the product table. It will take you to a new screen that allows you to modify a new product.
     * If no product is selected an alert will prompt you to first select a product to modify.
     * <p>
     * RUNTIME ERROR: Previously, clicking the Modify button without first selecting a product would cause a NullPointerException.
     * CORRECTION: I corrected this by creating an alert that would prompt the user to please select a product to modify.
     *
     * @param actionEvent The modify product button click event.
     * @throws IOException If an error occurs while loading the ModifyProduct-view
     */
    public void modifyProductsButton(ActionEvent actionEvent) throws IOException {
        Product selectModifyProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        if (selectModifyProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product to Modify");
            alert.showAndWait();

        } else {
            System.out.println("Product has been modified");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/myatt/View/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.transferProductInfo(selectModifyProduct);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Product");
            stage.show();
        }
    }

    /**
     * This method handles the delete part button.
     * It allows you to delete a selected part from the parts table.
     * A pop-up will ask if your sure you want to delete.
     * If you click OK, the part will be deleted.
     * If you click Cancel, the part will not be deleted and no changes will be made.
     *
     * @param actionEvent The delete part button click event.
     */
    public void deletePartsButton(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
                System.out.println("Part deleted");
            } else {
                System.out.println("Part not deleted");
            }
        }
    }

    /**
     * This method handles the delete product button.
     * It allows you to delete a selected product.
     * A pop-up will ask if your sure you want to delete.
     * If you click OK, the product will be deleted.
     * If you click Cancel, the product will not be deleted and no changes will be made.
     *
     * @param actionEvent The delete product button click event.
     */
    public void deleteProductButton(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product to delete");
            alert.showAndWait();
        } else if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("This product has associated Parts");
            alert.setContentText("Any associated parts must be deleted first");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
                System.out.println("Product deleted");
            } else {
                System.out.println("Product not deleted");
            }
        }
    }

    /**
     * This method searches for parts based on their ID or name.
     * It is case-insensitive and allows partial matching.
     * If no results are found, an alert is displayed.
     * If results are found, the results are displayed in the parts table.
     *
     * @param actionEvent The search button click event.
     */
    public void partSearch(ActionEvent actionEvent) throws IOException {
        ObservableList<Part> searchedForPart = FXCollections.observableArrayList();
        String search = partSearchTf.getText().toLowerCase();

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
            partsTable.setItems(searchedForPart);
        }
    }

    /**
     * This method searches for products based on their ID or name.
     * It is case-insensitive and allows partial matching.
     * If no results are found, an alert is displayed.
     * If results are found, the results are displayed in the products table.
     *
     * @param actionEvent The search button click event.
     */
    public void productSearch(ActionEvent actionEvent) throws IOException {
        ObservableList<Product> searchedForProduct = FXCollections.observableArrayList();
        String search = productSearchTf.getText().toLowerCase();

        for (Product searchProduct : Inventory.getAllProducts()) {
            if (searchProduct.getName().toLowerCase().contains(search) ||
                    Integer.toString(searchProduct.getId()).contains(search)) {
                searchedForProduct.add(searchProduct);
            }
        }
        if (searchedForProduct.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Failed");
            alert.setHeaderText("No Product Found");
            alert.setContentText("No search results found");
            alert.showAndWait();
        } else {
            productTable.setItems(searchedForProduct);
        }
    }

    /**
     * This method handles the exit button
     * WHen the exit button is clicked, the user exits the application.
     * @param actionEvent The exit button click event.
     */
    public void exitProductButton(ActionEvent actionEvent) {
        Platform.exit();
    }
}