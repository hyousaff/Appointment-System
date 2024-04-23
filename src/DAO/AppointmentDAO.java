package DAO;

import DataBase.DataLists;
import DataBase.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Hamza Yousaf
 *
 * The Abstract Class for AppointmentDAO. This handles database queries related to appointments. */
public abstract class AppointmentDAO
{
    /**
     * Retrieves all appointments from the database for display in the main appointment view.
     * This method executes a database query to fetch all columns from the appointments table,
     * and joins with the contacts table to include the contact's name.
     *
     * @return An ObservableList containing Appointment objects, each representing an appointment record.
     * @throws SQLException If there is an error in executing the query.
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.*, contacts.Contact_Name\n" +
                "FROM appointments\n" +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "ORDER BY Appointment_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /**
     * Retrieves appointments for the main appointment view, filtered to include only those occurring in the current month.
     * This method performs a database query similar to getAppointments, but it specifically filters records based on the
     * current month and year. The SQL query utilizes the current date to filter and retrieve relevant appointments.
     *
     * @return An ObservableList containing Appointment objects for the current month.
     * @throws SQLException If there is an error in executing the query.
     */
    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now();
        Timestamp todayTS = Timestamp.valueOf(today);
        String sql = "SELECT appointments.*, contacts.Contact_Name\n" +
                "FROM appointments\n" +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE MONTH(`Start`) = MONTH(?) AND YEAR(`Start`) = YEAR(?)\n" +
                "ORDER BY Appointment_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, todayTS);
        ps.setTimestamp(2, todayTS);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /**
     * Retrieves appointments for the main appointment view, specifically filtering for those occurring in the upcoming week.
     * This method performs a database query similar to getAppointments but narrows the results to appointments between the
     * current date and the date seven days from now. The SQL query uses the current date and a date seven days in the future
     * as parameters to identify relevant appointments within the next week.
     *
     * @return An ObservableList containing Appointment objects scheduled within the next seven days.
     * @throws SQLException If there is an error in executing the query.
     */
    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now();
        String sql = "SELECT appointments.*, contacts.Contact_Name" +
                " FROM appointments" +
                " JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID" +
                " WHERE DATE(`Start`) >= ? AND DATE(`End`) <= (?)" +
                " ORDER BY Appointment_ID ASC";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(today));
        ps.setTimestamp(2, Timestamp.valueOf(today.plusDays(7)));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /**
     * Inserts a new appointment into the database using the provided parameters. This method collects user input from
     * the AddAppointment view and constructs an INSERT SQL statement to add the appointment details to the database.
     *
     * @param title       The title of the appointment, entered by the user.
     * @param description The detailed description of the appointment.
     * @param location    The location for the appointment, selected from a combo box.
     * @param type        The type of the appointment, chosen from a predefined list.
     * @param tsStart     The timestamp representing the start time of the appointment.
     * @param tsEnd       The timestamp representing the end time of the appointment.
     * @param customerId  The ID of the customer associated with this appointment.
     * @param userId      The ID of the user or employee who is handling the appointment.
     * @param contactId   The ID of the contact associated with the appointment, derived from the contact name selection.
     * @throws SQLException If there is an issue executing the INSERT statement in the database.
     */
    public static void insertAppointment(String title, String description, String location, String type, Timestamp tsStart, Timestamp tsEnd, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,  description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, tsStart);
        ps.setTimestamp(6, tsEnd);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.executeUpdate();
    }

    /**
     * Updates an existing appointment in the database using the provided parameters. This method collects user input from
     * the UpdateAppointment view and constructs an UPDATE SQL statement to modify the appointment details.
     *
     * @param title        The updated title for the appointment, entered in the titleTextfield.
     * @param description  The updated description, entered in the descriptionTextField.
     * @param location     The updated location, selected from the locationComboBox.
     * @param type         The updated appointment type, chosen from the typeComboBox.
     * @param tsStart      The timestamp for the updated start time of the appointment.
     * @param tsEnd        The timestamp for the updated end time of the appointment.
     * @param customerId   The updated customer ID, selected from the customerIdComboBox.
     * @param userId       The updated user ID, selected from the userIdComboBox.
     * @param contactId    The updated contact ID, derived from the contact name selected in the contactComboBox.
     * @param appointmentId The ID of the appointment being updated, initially collected when the user selects an
     *                      appointment and displayed in the appointmentIdTextfield (cannot be changed by the user).
     * @throws SQLException If there is an issue executing the UPDATE statement in the database.
     */
    public static void updateAppointment(String title, String description, String location, String type, Timestamp tsStart, Timestamp tsEnd, int customerId, int userId, int contactId, int appointmentId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS\n" +
                "set Title = ?, Description = ?,\n" +
                "Location = ?, Type = ?,\n" +
                "Start = ?, End = ?, \n" +
                "Customer_ID = ?, User_ID = ?,\n" +
                "Contact_ID = ?\n" +
                "where appointment_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,  description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, tsStart);
        ps.setTimestamp(6, tsEnd);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, appointmentId);
        ps.executeUpdate();
    }

    /**
     * Deletes a selected appointment from the database. This method uses the appointmentId of the chosen appointment
     * to execute a DELETE SQL statement, removing the specified appointment record.
     *
     * @param appointmentId The unique identifier of the appointment to be deleted. This ID is obtained from the
     *                      appointment selected in the appointment TableView and is used to specify which appointment
     *                      to remove from the database.
     * @throws SQLException If there is an issue executing the DELETE statement in the database.
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

    /**
     * Checks for any appointments scheduled to start within 15 minutes of the user's login time. This method is
     * typically called upon successful user login to alert them of imminent appointments.
     *
     * @param nowLDT The LocalDateTime of the user's login, used to query the database for appointments starting
     *               within 15 minutes of this time.
     * @return An Appointment object if there is an appointment within the next 15 minutes; otherwise, returns an
     *         appointment with default values (effectively a null appointment).
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static Appointment checkAppointment(LocalDateTime nowLDT) throws SQLException {
        String sql = "SELECT *\n" +
                "FROM appointments\n" +
                "WHERE Start >= ? AND Start <= ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(nowLDT));
        ps.setTimestamp(2, Timestamp.valueOf(nowLDT.plusMinutes(15)));
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            Appointment nullAppointment = new Appointment();
            return nullAppointment;
        } else {
            rs.next();
            int appointmentId = rs.getInt("Appointment_ID");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            Appointment nextAppointment = new Appointment(appointmentId, startTime, endTime);
            return nextAppointment;
        }
    }

    /**
     * Checks for overlapping appointments for a specified customer. This method is used to prevent scheduling
     * conflicts during appointment creation. It queries the database to find any existing appointments that overlap
     * with the proposed new appointment times.
     *
     * @param startLDT    The proposed start time of the new appointment.
     * @param endLDT      The proposed end time of the new appointment.
     * @param customerId  The ID of the customer for whom the overlap check is being performed.
     * @return A descriptive string of any overlapping appointment found; returns "No" if no overlap is detected.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static String checkAppointmentOverlap(LocalDateTime startLDT, LocalDateTime endLDT, int customerId) throws SQLException {
        String sql = "SELECT Customer_ID, Start, End FROM appointments WHERE ((Start < ?) AND (End > ?)) AND Customer_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(endLDT));
        ps.setTimestamp(2, Timestamp.valueOf(startLDT));
        ps.setInt(3, customerId);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            return "No";
        } else {
            rs.next();
            LocalDateTime startConflictDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endConflictDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerIdFound = rs.getInt("Customer_ID");
            LocalTime startConflictTime = startConflictDateTime.toLocalTime();
            LocalTime endConflictTime = endConflictDateTime.toLocalTime();
            String overlapReport = "No";
            if(endLDT.equals(startConflictDateTime) || startLDT.equals(endConflictDateTime))
            {
                return overlapReport;
            }
            return "Overlaps with appointment (" + DataLists.myFormattedTF(startConflictTime) + " - " + DataLists.myFormattedTF(endConflictTime) + ") for Customer_ID: " + customerIdFound + ".";
        }
    }

    /**
     * Checks for overlapping appointments for a specified customer, excluding the appointment being updated.
     * This method is used during the update process to ensure the new times do not conflict with other existing
     * appointments, except the one being updated. It queries the database for any overlapping appointments
     * considering the updated times, omitting the current appointment.
     *
     * @param startLDT      The proposed new start time for the appointment being updated.
     * @param endLDT        The proposed new end time for the appointment being updated.
     * @param customerId    The ID of the customer for whom the overlap check is being performed.
     * @param appointmentId The ID of the appointment being updated, to be excluded from the overlap check.
     * @return A descriptive string of any overlapping appointment found; returns "No" if no overlap is detected.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static String checkUpdateAppointmentOverlap(LocalDateTime startLDT, LocalDateTime endLDT, int customerId, int appointmentId) throws SQLException {
        String sql = "SELECT Appointment_ID, Customer_ID, Start, End FROM appointments WHERE ((Start < ?) AND (End > ?)) AND Customer_ID = ? AND NOT Appointment_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(endLDT));
        ps.setTimestamp(2, Timestamp.valueOf(startLDT));
        ps.setInt(3, customerId);
        ps.setInt(4, appointmentId);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            return "No";
        } else {
            rs.next();
            LocalDateTime startConflictDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endConflictDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerIdFound = rs.getInt("Customer_ID");
            LocalTime startConflictTime = startConflictDateTime.toLocalTime();
            LocalTime endConflictTime = endConflictDateTime.toLocalTime();
            String overlapReport = "No";
            if(endLDT.equals(startConflictDateTime) || startLDT.equals(endConflictDateTime))
            {
                return overlapReport;
            }
            return "Overlaps with appointment (" + DataLists.myFormattedTF(startConflictTime) + " - " + DataLists.myFormattedTF(endConflictTime) + ") for Customer_ID: " + customerIdFound + ".";
        }
    }
    /**
     * Retrieves a count of appointments based on a specified month and type for reporting purposes.
     * This method queries the database and generates a report of appointments by month and type.
     *
     * @param month The month as a string, selected from the month combo box, used in the database query.
     * @param type  The type of appointment, selected from the type combo box, used in the database query.
     * @return An ObservableList of Appointment objects, each containing month, type, and their count for the report.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList<Appointment> getMonthTypeReport(String month, String type) throws SQLException {
        ObservableList<Appointment> monthTypeAppointments = FXCollections.observableArrayList();
        String sql = "SELECT Count(*) FROM APPOINTMENTS WHERE monthname(Start)=? AND Type=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, month);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("Count(*)");
        Appointment monthTypeAppointment = new Appointment(month, type, count);
        monthTypeAppointments.add(monthTypeAppointment);
        return monthTypeAppointments;
    }

    /**
     * Queries the database to generate a report of appointments for a specific contact.
     * The method fetches all appointments associated with the selected contact name.
     *
     * @param contactNameBox The name of the contact, obtained from the contact combo box, used to filter appointments.
     * @return An ObservableList of Appointments associated with the given contact.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList<Appointment> getContactReport(String contactNameBox) throws SQLException {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        String sql =     "SELECT appointments.*, contacts.Contact_Name\n" +
                "FROM appointments\n" +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE Contact_Name = ? ORDER BY Appointment_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactNameBox);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            contactAppointments.add(appointment);
        }
        return contactAppointments;
    }

    /**
     * Queries the database to generate a report of appointments for a specific location.
     * The method fetches all appointments associated with the selected location.
     *
     * @param locationFromBox The location, obtained from the location combo box, used to filter appointments.
     * @return An ObservableList of Appointments associated with the given location.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList<Appointment> getLocationReport(String locationFromBox) throws SQLException {
        ObservableList<Appointment> locationAppointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.*, contacts.Contact_Name\n" +
                "                FROM appointments\n" +
                "                JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "                WHERE Location = ? ORDER BY Appointment_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, locationFromBox);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            locationAppointments.add(appointment);
        }
        return locationAppointments;
    }
}