package DAO;

import DataBase.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hamza Yousaf
 *
 * The Abstract Class for CountryDAO. This handles database queries related to countries. */
public abstract class CountryDAO {
    static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    static ObservableList<String> allCountriesStrings = FXCollections.observableArrayList();

    /**
     * Retrieves a list of all countries from the database. Each country record is transformed into a Country object
     * and added to an ObservableList, which is used primarily for populating country-related combo boxes in the UI.
     *
     * @return An ObservableList of Country objects representing all countries in the database.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList<Country> getCountries() throws SQLException {

        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            allCountries.add(country);
        }
        return allCountries;
    }

    /**
     * Clears the ObservableLists of countries and country strings. This method is called to reset the lists and
     * prevent duplication of entries, ensuring that the lists accurately reflect the current state of the database.
     */
    public static void clearCountries()
    {
        allCountries.clear();
        allCountriesStrings.clear();
    }

    /**
     * Retrieves the country ID associated with a given first level division ID. This method is useful for
     * obtaining a country ID based on a customer's first level division, particularly in update scenarios.
     *
     * @param divisionId The division ID for which the corresponding country ID is sought.
     * @return The country ID associated with the specified division ID.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static int returnUpdateCountryId(int divisionId) throws SQLException {
        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countryId = rs.getInt("Country_ID");
        return countryId;
    }

    /**
     * Retrieves a Country object based on a given country ID. This method is used to fetch a country's details,
     * including its name, for populating UI elements during customer updates.
     *
     * @param countryUpdateId The ID of the country to retrieve.
     * @return A Country object representing the specified country.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static Country returnUpdateCountry(int countryUpdateId) throws SQLException {
        String sql = "SELECT Country_ID, Country FROM Countries WHERE Country_Id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryUpdateId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countryId = rs.getInt("Country_ID");
        String countryName = rs.getString("Country");
        Country country = new Country(countryId, countryName);
        return country;
    }

    /**
     * Retrieves a Country object for a given division ID. This method is primarily used in UI contexts where
     * a country needs to be selected automatically based on a customer's first level division selection.
     *
     * @param divisionId The division ID from which the corresponding country is determined.
     * @return A Country object associated with the given division ID.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static Country getCountryForBox(int divisionId) throws SQLException {
        return returnUpdateCountry(returnUpdateCountryId(divisionId));
    }
}