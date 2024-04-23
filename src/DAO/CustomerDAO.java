package DAO;

import DataBase.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Hamza Yousaf
 *
 * The Abstract Class for AppointmentDAO. This handles database queries related to customers. */
public abstract class CustomerDAO {

    /**
     * Fetches customer details from the database to populate a TableView. It joins the Customers, Countries, and
     * First_Level_Divisions tables to provide a comprehensive list of customer information.
     *
     * @return An ObservableList of Customer objects for use in UI elements like TableViews.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT customers.*, first_level_divisions.* , countries.Country FROM customers JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID  JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String divisionName = rs.getString("Division");
            int divisionIdFK = rs.getInt("Division_ID");
            String country = rs.getString("Country");
            Customer customer = new Customer(customerId, name, address, postalCode, phone, divisionName, divisionIdFK, country);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    /**
     * Inserts a new customer into the database using input from the AddCustomer view. Parameters are derived from
     * user input in various fields of the AddCustomer form.
     *
     * @param customerName Customer's name.
     * @param address      Customer's address.
     * @param postalCode   Customer's postal code.
     * @param phone        Customer's phone number.
     * @param divisionIdFK Customer's division ID, selected from a combo box.
     * @throws SQLException If there is an issue executing the INSERT statement in the database.
     */
    public static void insertCustomer(String customerName, String address, String postalCode, String phone, int divisionIdFK) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2,  address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionIdFK);
        ps.executeUpdate();
    }

    /**
     * Updates an existing customer's details in the database. This method is called with parameters from the UpdateCustomer
     * view to modify a customer's information.
     *
     * @param customerName Customer's updated name.
     * @param address      Customer's updated address.
     * @param postalCode   Customer's updated postal code.
     * @param phone        Customer's updated phone number.
     * @param divisionIdFK Customer's updated division ID.
     * @param customerId   ID of the customer being updated.
     * @throws SQLException If there is an issue executing the UPDATE statement in the database.
     */
    public static void updateCustomer(String customerName, String address, String postalCode, String phone, int divisionIdFK, int customerId) throws SQLException {
        String sql = "UPDATE CUSTOMERS set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?," +
                "                Division_ID = ?" +
                "                where Customer_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2,  address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionIdFK);
        ps.setInt(6, customerId);
        ps.executeUpdate();
    }

    /**
     * Checks if a customer has any existing appointments. This method is useful for validating customer deletion
     * or updating operations.
     *
     * @param customerId The ID of the customer whose appointments are being checked.
     * @return The count of appointments associated with the specified customer.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static int checkCustomerAppointments(int customerId) throws SQLException
    {
        String sql = "SELECT COUNT(appointment_id) AS RowCount from APPOINTMENTS where customer_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("RowCount");
        return count;
    }

    /**
     * Deletes a customer from the database. This method is used to remove a customer based on their ID.
     *
     * @param customerId The ID of the customer to be deleted.
     * @throws SQLException If there is an issue executing the DELETE statement in the database.
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }

    /**
     * Retrieves a list of customer IDs from the database. This method is typically used to populate combo boxes
     * in the UI with customer IDs.
     *
     * @return An ObservableList containing the IDs of all customers.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList <String> getCustomerIds() throws SQLException {
        String sql = "SELECT Customer_ID FROM CUSTOMERS";
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String customerId = rs.getString("Customer_ID");
            customerIds.add(customerId);
        }
        return customerIds;
    }

    /**
     * Determines the index of a given customer ID in a list. This method is used in UI contexts, such as when
     * selecting a customer to update from a TableView, to find the index of the customer's ID in a combo box.
     *
     * @param customerId The customer ID whose index is to be found.
     * @return The index position of the customer ID within the combo box list.
     * @throws SQLException If there is an issue retrieving customer IDs from the database.
     */
    public static int getMatchingCustomerId(String customerId) throws SQLException {

        String[] customerIdList = getCustomerIds().toArray(new String[0]);
        int customerIdIndex = 0;
        for (int i = 0; i <= customerIdList.length; i++) {
            if (customerId.equals(customerIdList[i]))
            {
                customerIdIndex = i;
                break;
            }
        }
        return customerIdIndex;
    }
}