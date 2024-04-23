package model;

import java.time.LocalDateTime;

/**
 * @author Hamza Yousaf
 *
 * Class for the Appointment class model. Creates constructors, setters and getters for appointment objects
 *  */
public class Appointment
{
    String title, description, location, contactName, type, month;
    int appointmentId, customerId, contactId, userId, count;
    LocalDateTime startTime, endTime;

    /**
     * Constructs an Appointment object for displaying in the TableView.
     * This constructor initializes all attributes based on database query results.
     * The 'contactName' is retrieved through a JOIN query from the contacts table.
     *
     * @param appointmentId Unique identifier from the appointment table.
     * @param title Title from the appointment table.
     * @param description Description from the appointment table.
     * @param location Location from the appointment table.
     * @param contactName Contact name from the contacts table.
     * @param type Type from the appointment table.
     * @param startTime Start time, converted from timestamp in the appointment table.
     * @param endTime End time, converted from timestamp in the appointment table.
     * @param customerId Customer ID from the appointment table.
     * @param contactId Contact ID from the appointment table.
     * @param userId User ID from the appointment table.
     */
    public Appointment(int appointmentId, String title, String description, String location, String contactName, String type, LocalDateTime startTime, LocalDateTime endTime, int customerId, int contactId, int userId)
    {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.contactId = contactId;
        this.userId = userId;
    }

    /**
     * Constructor for creating Appointment objects that are upcoming within 15 minutes.
     * Utilized after logging into the application to alert about imminent appointments.
     *
     * @param appointmentId Appointment ID from the database.
     * @param startTime Start time, converted from timestamp in the appointment table.
     * @param endTime End time, converted from timestamp in the appointment table.
     */
    public Appointment(int appointmentId, LocalDateTime startTime, LocalDateTime endTime)
    {
        this.appointmentId = appointmentId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructor for generating month and type reports. Initializes month, type, and count for report generation.
     *
     * @param month Selected month for the report.
     * @param type Appointment type from the database.
     * @param count Number of appointments matching the month and type criteria.
     */
    public Appointment(String month, String type, int count)
    {
        this.month = month;
        this.type = type;
        this. count = count;
    }

    /**
     * Default constructor for creating a placeholder Appointment object. Indicates no imminent appointments on login.
     */
    public Appointment(){

    }

    /** Returns the contact name of the appointment.
     *  @return The contact name of this appointment. */
    public String getContactName() {
        return contactName;
    }

    /** Sets the contact name of the appointment.
     *  @param contactName The new contact name for this appointment. */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Returns the user ID associated with the appointment.
     *  @return The user ID of this appointment. */
    public int getUserId() {
        return userId;
    }

    /** Sets the user ID for the appointment.
     *  @param userId The new user ID for this appointment. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** Returns the appointment ID.
     *  @return The appointment ID. */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** Sets the appointment ID.
     *  @param appointmentId The new appointment ID. */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /** Returns the title of the appointment.
     *  @return The title of this appointment. */
    public String getTitle() {
        return title;
    }

    /** Sets the title of the appointment.
     *  @param title The new title for this appointment. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Returns the description of the appointment.
     *  @return The description of this appointment. */
    public String getDescription() {
        return description;
    }

    /** Sets the description of the appointment.
     *  @param description The new description for this appointment. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Returns the location of the appointment.
     *  @return The location of this appointment. */
    public String getLocation() {
        return location;
    }

    /** Sets the location of the appointment.
     *  @param location The new location for this appointment. */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Returns the type of the appointment.
     *  @return The type of this appointment. */
    public String getType() {
        return type;
    }

    /** Sets the type of the appointment.
     *  @param type The new type for this appointment. */
    public void setType(String type) {
        this.type = type;
    }

    /** Sets the start time of the appointment.
     *  @param startTime The new start time for this appointment. */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** Returns the start time of the appointment.
     *  @return The start time of this appointment. */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** Sets the end time of the appointment.
     *  @param endTime The new end time for this appointment. */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;}

    /** Returns the end time of the appointment.
     *  @return The end time of this appointment. */
    public LocalDateTime getEndTime() {
        return endTime;}

    /** Returns the month associated with the appointment.
     *  @return The month of this appointment. */
    public String getMonth() {
        return month;
    }

    /** Sets the month for the appointment.
     *  @param month The new month for this appointment. */
    public void setMonth(String month) {
        this.month = month;
    }

    /** Returns the count of appointments for the specified month and type.
     *  @return The count of appointments. */
    public int getCount() {
        return count;
    }

    /** Sets the count for appointments of a specific type in a given month.
     *  @param count The new count for appointments. */
    public void setCount(int count) {
        this.count = count;
    }


    /** Returns the customer ID associated with the appointment.
     *  @return The customer ID of this appointment. */
    public int getCustomerId() {
        return customerId;
    }

    /** Sets the customer ID for the appointment.
     *  @param customerId The new customer ID for this appointment. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Returns the contact ID associated with the appointment.
     *  @return The contact ID of this appointment. */
    public int getContactId() {
        return contactId;
    }

    /** Sets the contact ID for the appointment.
     *  @param contactId The new contactId for this appointment's */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }


    /** Overrides the default toString method to return the appointment's title.
     *  @return The title of this appointment. */
    @Override
    public String toString()
    {
        return title;
    }
}