package model;

/**
 * @author Hamza Yousaf
 *
 * Represents the model for a FirstLevelDivision. This class includes constructors to create FirstLevelDivision
 * instances and methods to access and modify their attributes. Primarily used for populating FirstLevelDivision
 * combo boxes, and in the UpdateCustomer view to display a customer's associated division.
 */
public class FirstLevelDivision
{
    int divisionId, countryIdFK;
    String divisionName;

    /**
     * Constructor for creating FirstLevelDivision objects with an ID, name, and associated country ID.
     * @param divisionId The division ID as an integer.
     * @param divisionName The name of the division as a String.
     * @param countryIdFK The associated country's ID as a foreign key.
     */
    public FirstLevelDivision(int divisionId, String divisionName, int countryIdFK)
    {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryIdFK = countryIdFK;
    }

    /**
     * Constructor for FirstLevelDivision objects, mainly used in UpdateCustomer view.
     * @param divisionName The name of the division as a String.
     */
    public FirstLevelDivision(String divisionName)
    {
        this.divisionName = divisionName;
    }

    /**
     * Retrieves the division ID of this FirstLevelDivision object.
     * @return Division ID as an integer. */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division ID for this FirstLevelDivision object.
     * @param divisionId The new division ID as an integer. */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Retrieves the name of this FirstLevelDivision object.
     * @return Division name as a String. */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the name for this FirstLevelDivision object.
     * @param divisionName The new division name as a String. */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Retrieves the country ID foreign key of this FirstLevelDivision object.
     * @return Country ID foreign key as an integer. */
    public int getCountryIdFK() {
        return countryIdFK;
    }

    /**
     * Sets the country ID foreign key for this FirstLevelDivision object.
     * @param countryIdFK The new country ID foreign key as an integer. */
    public void setCountryIdFK(int countryIdFK) {
        this.countryIdFK = countryIdFK;
    }

    /**
     * Overrides the default toString method to return the division's name.
     * @return The name of the division. */
    @Override
    public String toString()
    {
        return divisionName;
    }
}