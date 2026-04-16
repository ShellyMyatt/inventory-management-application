package myatt.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is the inventory it stores the parts and products.
 * It also contains methods to make updates and changes to the parts and products in inventory.
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int partID = 0;

    private static int ProductID = 0;

    /**
     * Gets all the parts in the inventory.
     * @return List of all the parts in the inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all the products in the inventory.
     * @return List of all the products in the inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Adds a new part to the inventory.
     * @param newPart The part to be added to the inventory.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory.
     * @param newProduct The product to be added to the inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method returns the next available part ID.
     * @return the next available part ID.
     */
    public static int getPartID() {
        return ++partID;
    }

    /**
     * This method returns the next available product ID.
     * @return the next available product ID.
     */
    public static int getProductID() {
        return ++ProductID;
    }

    /**
     * This method updates a part in the inventory.
     * @param id The ID of the part to be updated.
     * @param newPart The new part with the updated information.
     */
    public static void updatePart (int id, Part newPart) {
        for(int index = 0; index < allParts.size(); index++) {
            Part currentPart = allParts.get(index);

            System.out.println("looking for part with id" + currentPart.getId() + "against" + id);

            if (currentPart.getId() == id) {
                System.out.println("Part found. updating part" + index);
                allParts.set(index, newPart);
                return;
            }
        }
        System.out.println("Part with id " + id + " not found");
    }

    /**
     * This method updates a product in the inventory.
     * @param id The ID of the product to be updated.
     * @param selectedProduct The new product with the updated information.
     */
    public static void updateProduct (int id, Product selectedProduct) {
        int index = -1;
        for (Product product : allProducts) {
            index++;
            if (product.getId() == id) {
                break;
            }
        }
        allProducts.set(index, selectedProduct);
    }

    /**
     * This method deletes a part from the inventory.
     * @param selectedPart The part to be deleted.
     * @return true if the part was deleted successfully, false otherwise.
     */
    public static boolean deletePart (Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method deletes a product from the inventory.
     * @param selectedProduct The product to be deleted.
     * @return true if the product was deleted successfully, false otherwise.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
}
