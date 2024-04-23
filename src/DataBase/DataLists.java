package DataBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author Hamza Yousaf
 *
 * This is the class for CollectionLists. It contains variables and methods that populates combo boxes and helps with
 *  time-related operations. */
public abstract class DataLists {

    //Places for appointments
    private static ObservableList<String> places = FXCollections.observableArrayList();
    public static String[] officeLocation = {"Phoenix, Arizona", "White Plains, New York", "Montreal, Canada", "London, England"};
    public static boolean locationLoaded = false;

    //Types for appointments
    private static ObservableList<String> types = FXCollections.observableArrayList();
    public static String[] typeAppointment = {"Sales", "Planning Session", "De-Briefing", "HR" };
    public static boolean typesLoaded = false;

    //Times for appointments
    private static ObservableList <LocalTime> myLT = FXCollections.observableArrayList();
    public static LocalTime [] time = new LocalTime[48];
    public static boolean timesLoaded = false;

    //Months for report 1
    private static ObservableList <String> allMonths = FXCollections.observableArrayList();
    public static String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static boolean monthsLoaded = false;

    /**
     * Checks if a given appointment time range falls within the working hours of the Eastern Time (ET) office.
     *
     * @param startLDT The start time of the appointment.
     * @param endLDT The end time of the appointment.
     * @return `true` if the appointment falls within the ET office hours, `false` otherwise.
     */
    public static boolean checkTimeRange(LocalDateTime startLDT, LocalDateTime endLDT)
    {
        boolean withinTime = false;
        ZoneId myBusinessZone = ZoneId.of("America/Indianapolis");
        ZoneId myZoneId = ZoneId.systemDefault();
        LocalDateTime appointmentDateTime = startLDT;
        LocalDate appointmentDate = appointmentDateTime.toLocalDate();

        //Open hour at ET office, perceived from any timezone
        LocalTime openHour = LocalTime.of(8, 0);
        LocalDateTime businessOpenDT = LocalDateTime.of(appointmentDate, openHour);
        ZonedDateTime zonedBusinessOpenDT = businessOpenDT.atZone(myBusinessZone);
        ZonedDateTime myZonedOpenDT = zonedBusinessOpenDT.withZoneSameInstant(myZoneId);
        LocalDateTime myOpenLDT = myZonedOpenDT.toLocalDateTime();

        //Closed hour at ET office, perceived from any timezone
        LocalTime closedHour = LocalTime.of(22, 0);
        LocalDateTime businessClosedDT = LocalDateTime.of(appointmentDate, closedHour);
        ZonedDateTime zonedBusinessClosedDT = businessClosedDT.atZone(myBusinessZone);
        ZonedDateTime myZonedClosedDT = zonedBusinessClosedDT.withZoneSameInstant(myZoneId);
        LocalDateTime myClosedLDT = myZonedClosedDT.toLocalDateTime();

        if (startLDT.isAfter(myOpenLDT) && (endLDT.isBefore(myClosedLDT)))
        {
            withinTime = true;
        }

        if (startLDT.isEqual(myOpenLDT) || (endLDT.isEqual(myClosedLDT)))
        {
            withinTime = true;
        }
        return withinTime;
    }

    /**
     * Loads a list of time slots in 30-minute intervals from 00:00 to 23:30.
     */
    public static void loadTimes()
    {
        for (int i = 0; i <= 23; i++) {
            {
                time[i] = LocalTime.of((i), 0);
                myLT.add(time[i]);
                time[i + 1] = LocalTime.of((i), 30);
                myLT.add(time[i + 1]);
            }
        }
    }

    /**
     * Gets the list of time slots in 30-minute intervals.
     *
     * @return An ObservableList of LocalTime objects representing time slots.
     */
    public static ObservableList<LocalTime> getTimes() {
        if(!timesLoaded) {
            loadTimes();
            timesLoaded = true;
        }
        return myLT;
    }

    /**
     * Loads a list of office locations.
     */
    public static void loadPlaces() {
        for (int i = 0; i <= 3; i++) {
            places.add(officeLocation[i]);
        }
    }

    /**
     * Gets the list of office locations.
     *
     * @return An ObservableList of office locations.
     */
    public static ObservableList<String> getPlaces() {
        if(!locationLoaded) {
            loadPlaces();
            locationLoaded = true;
        }
        return places;
    }

    /**
     * Returns the index of a given location in the list of office locations.
     *
     * @param location The location to search for.
     * @return The index of the location in the list.
     */
    public static int returnUpdateLocation(String location) {
        int locationIndex = 0;
        for (int i = 0; i <= 3; i++) {
            if (location.equals(officeLocation[i]))
            {
                locationIndex = i;
                break;
            }
        }
        return locationIndex;
    }

    /**
     * Loads a list of appointment types.
     */
    public static void loadTypes() {
        for (int i = 0; i <= 3; i++) {
            types.add(typeAppointment[i]);
        }
    }

    /**
     * Gets the list of appointment types.
     *
     * @return An ObservableList of appointment types.
     */
    public static ObservableList<String> getTypes() {
        if(!typesLoaded) {
            loadTypes();
            typesLoaded = true;
        }
        return types;
    }

    /**
     * Loads a list of months.
     */
    public static void loadMonths() {
        for (int i = 0; i <= 11; i++) {
            allMonths.add(months[i]);
        }
    }

    /**
     * Gets the list of months.
     *
     * @return An ObservableList of months.
     */
    public static ObservableList<String> getMonths() {
        if(!monthsLoaded) {
            loadMonths();
            monthsLoaded = true;
        }
        return allMonths;
    }

    /**
     * Formats a LocalDateTime object into a string with the format "MM-dd-yyyy HH:mm".
     *
     * @param myLDT The LocalDateTime object to be formatted.
     * @return A formatted date and time string.
     */
    public static String myFormattedDTF (LocalDateTime myLDT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String formattedDate = myLDT.format(format);
        return formattedDate;
    }

    /**
     * Formats a LocalTime object into a string with the format "HH:mm".
     *
     * @param myLT The LocalTime object to be formatted.
     * @return A formatted time string.
     */
    public static String myFormattedTF (LocalTime myLT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = myLT.format(format);
        return formattedDate;
    }
}