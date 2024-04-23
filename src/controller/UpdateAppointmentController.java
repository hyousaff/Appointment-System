package controller;

import DAO.*;
import DataBase.DataLists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 *
 * @author Hamza Yousaf
 *
 * Controller for the "UpdateAppointment" view, managing the update process of appointment details in the database.
 * Validates inputs and ensures updated data adheres to business rules.
 */
public class UpdateAppointmentController implements Initializable {
    @FXML public TextField AppIDField, TitleField, DescriptionField;
    @FXML public Button UpdateButton;
    @FXML public ComboBox ComboType, ComboLocation, ComboContact, ComboStartTime, ComboEndTime, ComboCustomerID, ComboUserID;
    @FXML public DatePicker PickEndDate, PickStartDate;
    LocalDateTime StartOriginal;
    LocalDateTime EndOriginal;

    /**
     * Initializes the view with methods to populate combo boxes. Called when the view is launched.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Records the original start and end times of an appointment, aiding in conflict checks during update.
     */
    public void setOriginalApptTime(LocalDateTime start, LocalDateTime end)
    {
        StartOriginal = start;
        EndOriginal = end;
    }

    /**
     * Records the original start and end times of an appointment, aiding in conflict checks during update.
     */
    public boolean updateTimeSame()
    {
        boolean good = false;
        //Start
        LocalDate ldStart = PickStartDate.getValue();
        LocalTime ltStart = LocalTime.parse(ComboStartTime.getValue().toString());
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);

        //End
        LocalDate ldEnd = PickEndDate.getValue();
        LocalTime ltEnd = LocalTime.parse(ComboEndTime.getValue().toString());
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
        if(ldtStart.isEqual(StartOriginal) && ldtEnd.isEqual(EndOriginal))
        {
            good = true;
        }
        return good;
    }

    /** This method populates the combo boxes for the form. Each combo box uses a method to get an ObservableList for
     *  each respective combo box. */
    public void populateComboBoxes() throws SQLException {
        ComboLocation.setItems(DataLists.getPlaces());
        ComboContact.setItems(ContactDAO.getContactNames());
        ComboType.setItems(DataLists.getTypes());
        ComboStartTime.setItems(DataLists.getTimes());
        ComboEndTime.setItems(DataLists.getTimes());
        ComboCustomerID.setItems(CustomerDAO.getCustomerIds());
        ComboUserID.setItems(UserDAO.getUsers());
    }

    /**
     * Sets the appointment ID in the corresponding text field.
     * @param appointmentId The ID of the appointment to be updated.
     */
    public void setAppointmentId(String appointmentId) {
        AppIDField.setText(appointmentId);
    }

    /**
     * Sets the title in the corresponding text field.
     * @param title The title of the appointment.
     */
    public void setTitle(String title)
    {
        TitleField.setText(title);
    }

    /**
     * Sets the description in the corresponding text field.
     * @param description The description of the appointment.
     */
    public void setDescription(String description)
    {
        DescriptionField.setText(description);
    }

    /**
     * Selects the appropriate location in the location combo box.
     * @param location The location of the appointment.
     */
    public void setLocation(String location) {
        ComboLocation.getSelectionModel().select(DataLists.returnUpdateLocation(location));
    }

    /**
     * Sets the selected contact in the contact combo box.
     * @param contact The contact associated with the appointment.
     */
    public void setContact(String contact) {
        ComboContact.setValue(contact);
    }

    /**
     * Sets the appointment type in the type combo box.
     * @param type The type of the appointment.
     */
    public void setType(String type)
    {
        ComboType.setValue(type);
    }

    /**
     * Sets the start date in the date picker.
     * @param startDate The start date of the appointment.
     */
    public void setStartDate(LocalDate startDate)
    {
        PickStartDate.setValue(startDate);
    }

    /**
     * Sets the end date in the date picker.
     * @param endDate The end date of the appointment.
     */
    public void setEndDate(LocalDate endDate)
    {
        PickEndDate.setValue(endDate);
    }

    /**
     * Sets the start time in the time combo box.
     * @param localTime The start time of the appointment.
     */
    public void setStartTime(LocalTime localTime)
    {
        ComboStartTime.setValue(localTime);
    }

    /**
     * Sets the end time in the time combo box.
     * @param localTime The end time of the appointment.
     */
    public void setEndTime(LocalTime localTime)
    {
        ComboEndTime.setValue(localTime);
    }

    /**
     * Sets the customer ID in the customer ID combo box.
     * @param customerId The ID of the customer associated with the appointment.
     */
    public void setCustomerId(String customerId) throws SQLException {
        ComboCustomerID.getSelectionModel().select(CustomerDAO.getMatchingCustomerId(customerId));
    }

    /**
     * Sets the user ID in the user ID combo box.
     * @param userId The ID of the user associated with the appointment.
     */
    public void setUserId(String userId) throws SQLException {
        ComboUserID.getSelectionModel().select(UserDAO.getMatchingUserId(userId));
    }

    /**
     * Validates that all form fields are filled out, displaying an alert if any are missing.
     */
    public boolean inputCheck()
    {
        boolean good = true;
        if (TitleField.getText() == "" ||
                DescriptionField.getText() == "" ||
                ComboLocation.getValue() == null ||
                ComboType.getValue() == null ||
                PickStartDate.getValue() == null ||
                ComboStartTime.getValue() == null ||
                PickEndDate.getValue() == null ||
                ComboEndTime.getValue() == null ||
                ComboCustomerID.getValue() == null ||
                ComboUserID.getValue() == null ||
                ComboContact.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Inputs");
            alert.setContentText("Please complete all fields.");
            good = false;
        }
        return good;
    }

    /**
     * Ensures the selected appointment times are valid, checking for conflicts and adherence to business hours.
     */
    public boolean goodAppointmentTime() throws SQLException {

        boolean good = true;
        //Start
        LocalDate ldStart = PickStartDate.getValue();
        LocalTime ltStart = LocalTime.parse(ComboStartTime.getValue().toString());
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);

        //End
        LocalDate ldEnd = PickEndDate.getValue();
        LocalTime ltEnd = LocalTime.parse(ComboEndTime.getValue().toString());
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);

        int customerId = Integer.parseInt((String) ComboCustomerID.getSelectionModel().getSelectedItem());
        int appointmentId = Integer.parseInt(AppIDField.getText());
        if(PickStartDate.getValue() == null ||
                ComboStartTime.getValue() == null ||
                PickEndDate.getValue() == null ||
                ComboEndTime.getValue() == null )
        {
            good = false;
        }
        else if(ldtEnd.isBefore(ldtStart))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Time Error");
            alert.setContentText("End time must be after Start Time");
            good = false;
        }
        else if (ldtStart.isEqual(ldtEnd))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Time Error");
            alert.setContentText("Start and end time are the same.");
            good = false;
        }
        else if (updateTimeSame() == true)
        {
            good = true;
        }
        else if (!DataLists.checkTimeRange(ldtStart, ldtEnd))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Time Error");
            alert.setContentText("Time must be between 8:00AM - 10:00PM ET");
            good = false;
        }
        else if (!AppointmentDAO.checkUpdateAppointmentOverlap(ldtStart, ldtEnd, customerId, appointmentId).equals("No"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Conflicting Time");
            alert.setContentText(AppointmentDAO.checkAppointmentOverlap(ldtStart, ldtEnd, customerId));
            good = false;
        }
        return good;
    }

    /**
     * Gathers updated appointment details from the form, validates inputs, and updates the appointment in the database.
     */
    public void updateAppointment() throws SQLException {
        try {
            boolean inputWorks = inputCheck();
            boolean timeWorks = goodAppointmentTime();
            if (inputWorks && timeWorks)
            {
                int appointmentId = Integer.parseInt(AppIDField.getText());
                String title = TitleField.getText();
                String description = DescriptionField.getText();
                String location = ComboLocation.getValue().toString();
                String type = ComboType.getValue().toString();

                //Start
                LocalDate ldStart = PickStartDate.getValue();
                LocalTime ltStart = LocalTime.parse(ComboStartTime.getValue().toString());
                LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);
                Timestamp tsStart = Timestamp.valueOf(ldtStart);

                //End
                LocalDate ldEnd = PickEndDate.getValue();
                LocalTime ltEnd = LocalTime.parse(ComboEndTime.getValue().toString());
                LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
                Timestamp tsEnd = Timestamp.valueOf(ldtEnd);

                int customerId = Integer.parseInt((String) ComboCustomerID.getSelectionModel().getSelectedItem());
                int userId = Integer.parseInt((String) ComboUserID.getSelectionModel().getSelectedItem());
                int contactId = ContactDAO.getContactId(ComboContact.getValue().toString());

                AppointmentDAO.updateAppointment(title, description, location, type, tsStart, tsEnd, customerId, userId, contactId, appointmentId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Successfully updated.");
                alert.setContentText("Appointment for customer " + customerId + " successfully updated.");

                StartOriginal = ldtStart;
                EndOriginal = ldtEnd;
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Clears all input fields, allowing for complete modification of the update form.
     */
    public void resetFields()
    {
        TitleField.setText("");
        DescriptionField.setText("");
        ComboLocation.setValue(null);
        ComboType.setValue(null);
        PickStartDate.setValue(null);
        ComboStartTime.setValue(null);
        PickEndDate.setValue(null);
        ComboEndTime.setValue(null);
        ComboCustomerID.setValue(null);
        ComboUserID.setValue(null);
        ComboContact.setValue(null);
    }

    /**
     * Navigates the user back to the main scheduling dashboard.
     */
    public void toSchedulerDashboard(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Schedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1240, 600);
        stage.setTitle("Schedule Screen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}