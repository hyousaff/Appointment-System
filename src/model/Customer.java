package model;

/**
 * @author Hamza Yousaf
 *
 * Represents the model for a Customer. This class includes a constructor for creating Customer instances
 * and methods to manipulate their attributes. It's primarily used for displaying customer information in
 * the TableView. Customer attributes are populated from the Customers table in the database, with 'country'
 * obtained via a JOIN query on the Country table.
 */
public class Customer
{
    int customerId, divisionIdFK, countryId;
    String name, address, postalCode, phone, divisionName, country;

    /**
     * Main constructor for creating a Customer object with detailed attributes.
     * @param customerId Customer's ID from the database.
     * @param name Customer's name.
     * @param address Customer's address.
     * @param postalCode Customer's postal code.
     * @param phone Customer's phone number.
     * @param divisionName Name of the division from the database.
     * @param divisionIdFK Division ID linked to the customer.
     * @param country Customer's country, obtained via JOIN query.
     */
    public Customer(int customerId, String name, String address, String postalCode, String phone, String divisionName, int divisionIdFK, String country)
    {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionName = divisionName;
        this.divisionIdFK = divisionIdFK;
        this.country = country;
    }

    /** Returns the customer's ID.
     * @return Customer ID as an integer. */
    public int getCustomerId() {
        return customerId;
    }

    /** Sets the customer's ID.
     * @param customerId The new customer ID. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Returns the customer's name.
     * @return Customer's name as a String. */
    public String getName() {
        return name;
    }

    /** Sets the customer's name.
     * @param name The new name of the customer. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the customer's address.
     * @return Customer's address as a String. */
    public String getAddress() {
        return address;
    }

    /** Sets the customer's address.
     * @param address The new address of the customer. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Returns the customer's postal code.
     * @return Customer's postal code as a String. */
    public String getPostalCode() {
        return postalCode;
    }

    /** Sets the customer's postal code.
     * @param postalCode The new postal code of the customer. */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Returns the customer's phone number.
     * @return Customer's phone number as a String. */
    public String getPhone() {
        return phone;
    }

    /** Sets the customer's phone number.
     * @param phone The new phone number of the customer. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Returns the division ID associated with the customer.
     * @return Division ID as an integer. */
    public int getDivisionIdFK() {
        return divisionIdFK;
    }

    /** Sets the division ID for the customer.
     * @param divisionIdFK The new division ID. */
    public void setDivisionIdFK(int divisionIdFK) {
        this.divisionIdFK = divisionIdFK;
    }

    /** Returns the name of the division associated with the customer.
     * @return Division name as a String. */
    public String getDivisionName() {
        return divisionName;
    }

    /** Sets the division name for the customer.
     * @param divisionName The new division name. */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** Returns the country of the customer.
     * @return Customer's country as a String. */
    public String getCountry() {
        return country;
    }

    /** Sets the country for the customer.
     * @param country The new country of the customer. */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Returns the country ID of the customer.
     * @return Country ID as an integer. */
    public int getCountryId() {
        return countryId;
    }

    /** Sets the country ID for the customer.
     * @param countryId The new country ID. */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** Overrides the default toString method to return the customer's name.
     * @return The name of the customer. */
    @Override
    public String toString()
    {
        return name;
    }

}