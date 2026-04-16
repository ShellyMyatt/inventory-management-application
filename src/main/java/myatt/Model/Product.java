package myatt.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class describes a product in the inventory.
 * A product can have multiple parts associated with it.
 */
public class Product {

    public static ObservableList<Part> getAllAssociatedParts;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;

    /**
     * This constructor creates a new Product with the given id, name, price, stock, min, and max.
     * @param id The id of the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The stock level of the product.
     * @param min The minimum stock of the product.
     * @param max The maximum stock of the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Gets the ID of the product.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the product.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the stock level of the product.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock level of the product.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the price of the product.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the maximum stock level of the product.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum stock level of the product.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the minimum stock level of the product.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum stock level of the product.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }


    /**
     * Adds a part to the associated part list for the product.
     * @param part The part to add
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /**
     * Gets the list of parts associated with the product.
     * @return The list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
            return associatedParts;
    }

}