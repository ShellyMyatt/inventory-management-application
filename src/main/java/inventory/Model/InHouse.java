package inventory.Model;

/**
 * This class inherits the Part class and describes the InHouse parts.
 */
public class InHouse extends Part {
    private int machineID;

    /**
     * This constructor creates a new InHouse part with the given id, name, price, stock, min, max, and machine ID.
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The stock level of the part.
     * @param min The minimum stock of the part.
     * @param max The maximum stock of the part.
     * @param machineID The machine ID of the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Sets the machine ID.
     * @param machineID The machine ID of the part.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * Gets the machine ID.
     * @return The machine ID of the part.
     */
    public int getMachineID() {
        return machineID;
    }
}

