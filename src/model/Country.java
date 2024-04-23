package model;

/**
 * @author Hamza Yousaf
 *
 * Represents the model for a Country. Includes a constructor for creating Country instances and methods
 * to access and modify their attributes, useful for populating country combo boxes.
 */
public class Country
{
    int countryId;
    String countryName;

    /**
     * Constructor for creating a Country object with an ID and a name.
     * @param countryId The ID of the country as an integer.
     * @param countryName The name of the country as a String.
     */
    public Country(int countryId, String countryName)
    {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Retrieves the country ID of this Country object.
     * @return The integer ID of the country.
     */

    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the country ID for this Country object.
     * @param countryId The integer ID to assign to this country.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Retrieves the name of this Country object.
     * @return The name of the country as a String.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the name for this Country object.
     * @param countryName The new name to assign to this country.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Overrides the default toString method to return the country's name.
     * @return The name of the country.
     */
    @Override
    public String toString() {
        return countryName;
    }
}