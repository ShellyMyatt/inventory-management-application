package myatt.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myatt.Model.*;

import java.io.IOException;

/**
 * The main class is the starting point of the application.
 * It initializes the application and loads the MainScreen-view.
 * It also sets up the inventory with sample data.
 * <p>
 * FUTURE ENHANCEMENT: The modify and delete buttons could be disabled and appear as a lighter color
 * until the user selects a part or product.
 * Once the part or product is selected the modify or delete button would change to an active color and be enabled.
 * RUNTIME ERROR: Previously, clicking the Modify part button without first selecting a part would cause a NullPointerException.
 * CORRECTION: I corrected this by creating an alert that would prompt the user to please select a part to modify.
 * RUNTIME ERROR: Method for ensuring the min value is greater than 0 and less than Max value.
 * I didn't add return false; when I originally created this method.
 * It saved the modifications even if they were not correct.
 * CORRECTION: I fixed this by adding return false; after the conditional statement.
 * RUNTIME ERROR: I originally tried to get the company name associated with the outsourced part
 * from the radio button but that clearly does not hold the company name.
 * CORRECTION: I fixed this by retrieving the company name from the text field modifyPartMachineID.
 * RUNTIME ERROR: I originally was generating a new ID with getPartID() and it was not working.
 * CORRECTION: I fixed this by retrieving the existing ID from the modifyPartID field.
 * The JavaDOC's is saved in the main folder, in the java folder, in the Myatt folder.
 * @author Shelly Myatt
 */

public class Main extends Application {

    /**
     * The start method is called after the application is launched.
     * It loads the MainScreen-view and sets it as the primary stage's scene.
     * @param stage the primary stage
     * @throws IOException if the FXML file cannot be loaded
     */

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/myatt/View/MainScreen-view.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method is the starting point of the application.
     * It sets up the inventory with sample data.
     * It launches the application.
     *
     * @param args the command line arguments
     */
        public static void main(String[] args) {
            InHouse chocolate = new InHouse(Inventory.getPartID(), "Chocolate", 25.00, 5, 1, 10, 56);
            Inventory.addPart(chocolate);

            OutSourced sugar = new OutSourced(Inventory.getPartID(), "Sugar",200.00, 4, 1, 8, "Pioneer Sugar Company");
            Inventory.addPart(sugar);

            Product chocolateFrogs = new Product(Inventory.getProductID(), "Chocolate Frogs", 3.99, 255, 1, 500);
            Inventory.addProduct(chocolateFrogs);

            Product bertieBottsEveryFlavourBeans= new Product(Inventory.getProductID(), "Bertie Bott's Every Flavour Beans", 6.00, 45, 1, 50);
            bertieBottsEveryFlavourBeans.addAssociatedParts(sugar);
            Inventory.addProduct(bertieBottsEveryFlavourBeans);

        launch(args);
    }
}