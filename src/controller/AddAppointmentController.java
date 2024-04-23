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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;


/**
 *
 * @author Hamza Yousaf
 *
 * Controller for the "AddAppointment" screen, Manages adding appointments to the database.
 */
public class AddAppointmentController implements Initializable
{
    @FXML private ComboBox typebox, locationbox, contactbox, starttimebox, endtimebox, customerbox, userIdbox;
    @FXML private DatePicker PickEndDate, PickStartDate;
    @FXML private TextField titlefield, descriptionfield;

    /**
     * Initializes the Add Appointment screen. Invoked at screen launch, populates combo boxes.
     * @param url The URL for initialization
     * @param resourceBundle The resource bundle for initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateComboBoxes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the form's combo boxes. Utilizes ObservableLists for each combo box's content.
     * @throws SQLException If SQL database access fails
     */
    public void populateComboBoxes() throws SQLException {
        // Setting items for all combo boxes
        locationbox.setItems(DataLists.getPlaces());
        contactbox.setItems(ContactDAO.getContactNames());
        typebox.setItems(DataLists.getTypes());
        starttimebox.setItems(DataLists.getTimes());
        endtimebox.setItems(DataLists.getTimes());
        customerbox.setItems(CustomerDAO.getCustomerIds());
        userIdbox.setItems(UserDAO.getUsers());
    }

    /**
     * Validates if all form fields are filled. Displays an error if any field is empty.
     * @return true if all fields are filled, false otherwise
     */
    public boolean inputCheck() {
        if (titlefield.getText().isEmpty() ||
                descriptionfield.getText().isEmpty() ||
                locationbox.getValue() == null ||
                typebox.getValue() == null ||
                PickStartDate.getValue() == null ||
                starttimebox.getValue() == null ||
                PickEndDate.getValue() == null ||
                endtimebox.getValue() == null ||
                customerbox.getValue() == null ||
                userIdbox.getValue() == null ||
                contactbox.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Check Inputs");
            alert.setContentText("All fields must be completed.");
            alert.show();
        }
        return true;
    }


    /**
     * Resets all input fields to their default state, allowing for new entry.
     *
     */
    public void resetFields()
    {
        titlefield.setText("");
        descriptionfield.setText("");
        locationbox.setValue(null);
        typebox.setValue(null);
        PickStartDate.setValue(null);
        starttimebox.setValue(null);
        PickEndDate.setValue(null);
        endtimebox.setValue(null);
        customerbox.setValue(null);
        userIdbox.setValue(null);
        contactbox.setValue(null);
    }

    /**
     * Verifies the appointment timing. Ensures it adheres to business rules such as office hours and avoids overlap.
     * Uses a lambda for efficient error message display.
     * @return true if timing is valid, false otherwise
     * @throws SQLException If SQL database access fails
     */
    public boolean goodAppointmentTime() throws SQLException {
        // Logic for checking appointment time
        boolean good = true;
        //Start
        LocalDate ldStart = PickStartDate.getValue();
        LocalTime ltStart = LocalTime.parse(starttimebox.getValue().toString());
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);

        //End
        LocalDate ldEnd = PickEndDate.getValue();
        LocalTime ltEnd = LocalTime.parse(endtimebox.getValue().toString());
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);

        int customerId = Integer.parseInt((String) customerbox.getSelectionModel().getSelectedItem());

        Alerts error = (d, m) ->
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText(d);
            alert.setContentText(m);
        };

        if(PickStartDate.getValue() == null ||
                starttimebox.getValue() == null ||
                PickEndDate.getValue() == null ||
                endtimebox.getValue() == null )
        {
            good = false;
        }
        else if(ldtEnd.isBefore(ldtStart))
        {
            error.makeAlert("Time Error", "End time must be after Start Time");
            good = false;
        }
        else if (ldtStart.isEqual(ldtEnd))
        {
            error.makeAlert("Time Error", "Start and end time are the same.");
            good = false;
        }
        else if (!DataLists.checkTimeRange(ldtStart, ldtEnd))
        {
            error.makeAlert("Time Error", "Time must be between 8:00AM - 10:00PM ET");
            good = false;
        }
        else if (!AppointmentDAO.checkAppointmentOverlap(ldtStart, ldtEnd, customerId).equals("No"))
        {
            error.makeAlert("Conflicting Time", AppointmentDAO.checkAppointmentOverlap(ldtStart, ldtEnd, customerId));
            good = false;
        }
        return good;
    }

    /**
     * Submits the appointment details to the database. Validates input and timing before insertion.
     * Informs the user upon successful addition.
     * @throws SQLException If SQL database access fails
     */
    public void insertAppointment() throws SQLException {
        // Logic for inserting appointment
        try{
            boolean inputWorks = inputCheck();
            boolean timeWorks = goodAppointmentTime();
            if(inputWorks && timeWorks)
            {
                String title = titlefield.getText();
                String description = descriptionfield.getText();
                String location = locationbox.getValue().toString();
                String type = typebox.getValue().toString();

                //Start
                LocalDate ldStart = PickStartDate.getValue();
                LocalTime ltStart = LocalTime.parse(starttimebox.getValue().toString());
                LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);
                Timestamp tsStart = Timestamp.valueOf(ldtStart);

                //End
                LocalDate ldEnd = PickEndDate.getValue();
                LocalTime ltEnd = LocalTime.parse(endtimebox.getValue().toString());
                LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
                Timestamp tsEnd = Timestamp.valueOf(ldtEnd);

                int customerId = Integer.parseInt((String) customerbox.getSelectionModel().getSelectedItem());
                int userId = Integer.parseInt((String) userIdbox.getSelectionModel().getSelectedItem());
                int contactId = ContactDAO.getContactId(contactbox.getValue().toString());

                AppointmentDAO.insertAppointment(title, description, location, type, tsStart, tsEnd, customerId, userId, contactId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Successfully added.");
                alert.setContentText("Appointment for customer " + customerId + " successfully added.");
            }
        }
        catch (Exception e)
        {

        }
    }


    /**
     * Navigates back to the Scheduler Dashboard.
     * @param actionEvent The event triggered by button click
     * @throws IOException If FXML loading fails
     */
    public void toSchedulerDashboard(ActionEvent actionEvent) throws IOException
    {
        // Logic to return to the dashboard
        Parent root = FXMLLoader.load(getClass().getResource("../view/Schedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1240, 600);
        stage.setTitle("Schedule Screen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}