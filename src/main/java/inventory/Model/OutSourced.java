package inventory.Model;

/**
 * This class inherits the Part class and describes the OutSourced parts.
 */

public class OutSourced extends Part {
    private String companyName;

    /**
     * This constructs a OutSourced part with the given id, name, price, stock, min, max, and company name.
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The stock level of the part.
     * @param min The minimum stock of the part.
     * @param max The maximum stock of the part.
     * @param companyName The name of the company that supplies the part.
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Gets the name of the part supplier company.
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *Sets the name of the part supplier company.
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
