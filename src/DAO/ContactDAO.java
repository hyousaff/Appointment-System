package DAO;

import DataBase.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hamza Yousaf
 *
 * The Abstract Class for AppointmentDAO. This handles database queries related to appointments. */
public abstract class ContactDAO {

    /**
     * Retrieves the database ID for a contact based on a provided contact name.
     * This method is used when a contact's name is known (e.g., from a combo box selection),
     * and the corresponding contact ID is required.
     *
     * @param contactName The name of the contact, typically obtained from a contactComboBox.
     * @return The integer ID corresponding to the contact name.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static int getContactId(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM CONTACTS WHERE Contact_Name = ?" ;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        int contactId = 0;
        while(rs.next())
        {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }

    /**
     * Gathers all contact names from the database for use in populating combo boxes.
     * This method queries all contact names to create a list suitable for UI selections.
     *
     * @return An ObservableList of strings containing all contact names.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList<String> getContactNames() throws SQLException {
        String sql = "SELECT Contact_Name FROM CONTACTS";
        ObservableList<String> contacts = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String contactName = rs.getString("Contact_Name");
            contacts.add(contactName);
        }
        return contacts;
    }
}