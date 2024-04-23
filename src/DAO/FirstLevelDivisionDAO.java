package DAO;

import DataBase.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hamza Yousaf
 *
 * Class for FirstLevelDivisionDAO. This handles database queries related to First Level Divisions. */
public abstract class FirstLevelDivisionDAO
{
    static ObservableList<FirstLevelDivision> allFLD = FXCollections.observableArrayList();
    static ObservableList<String> allFLDStrings = FXCollections.observableArrayList();

    /**
     * Queries the database to retrieve all first-level divisions associated with a specific country.
     * This method is used to populate combo boxes with first-level divisions based on the selected country.
     *
     * @param countryIdFK The ID of the country for which first-level divisions are being queried.
     * @return An ObservableList of FirstLevelDivision objects for the specified country.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList<FirstLevelDivision> getAllFLD(int countryIdFK) throws SQLException {

        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE country_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryIdFK);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            FirstLevelDivision fld = new FirstLevelDivision(divisionId, divisionName, countryIdFK);
            allFLD.add(fld);
        }
        return allFLD;
    }

    /**
     * Clears the ObservableLists of all first-level divisions and their string representations.
     * This method prevents duplication in the lists and ensures they accurately reflect current database records.
     */
    public static void clearLists()
    {
        allFLD.clear();
        allFLDStrings.clear();
    }

    /**
     * Retrieves the division ID corresponding to a given division name. This method is primarily used
     * when inserting a new customer, requiring the division ID for database entry.
     *
     * @param division The name of the division, typically selected by the user from a combo box.
     * @return The ID of the division matching the given name.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static int getMatchingDivisionId(String division) throws SQLException
    {
        int divisionId = -1;
        String sql = "SELECT DIVISION_ID FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }

    /**
     * Retrieves a FirstLevelDivision object based on a given division ID. This method is used in updating customer
     * information, particularly for setting the appropriate first-level division in the UpdateCustomer view.
     *
     * @param divisionId The division ID used to find the corresponding FirstLevelDivision.
     * @return A FirstLevelDivision object representing the division with the specified ID.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static FirstLevelDivision returnDivision(int divisionId) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String division = rs.getString("Division");
        FirstLevelDivision updateDivision = new FirstLevelDivision(division);
        return updateDivision;
    }
}