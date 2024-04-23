package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DataBase.DataLists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author Hamza Yousaf
 *
 * Controller for the "SchedulerDashboard" view, Manages the display of appointments in various reports and navigation through the application.
 */
public class ScheduleScreenController implements Initializable
{   // FXML annotated fields and TableView setup

    @FXML public TableView<Appointment> ContactTabReport, TypeTabReport, CustomTabReport, AppointmentsTable;
    @FXML public ComboBox ComboBoxType, ComboBoxMonth, ComboBoxContact, ComboBoxLocation;
    @FXML public Button MonthButtonType;
    @FXML public RadioButton RadioAllApp, RadioMonthApp, RadioWeekApp;
    @FXML public TableColumn ContactColAppID, ContactColTitle, ContactColDescription, ContactColLocation, ContactColContact, ContactColType, ContactColStart, ContactColEnd, ContactColCustomerID, ContactColUserID;
    @FXML public TableColumn MonthReport, TypeReport, CountColumn;
    @FXML public TableColumn CustomColAppID, CustomColTitle, CustomColDescription, CustomColLocation, CustomColContact, CustomColType, CustomColStart, CustomColEnd, CustomColCustomerID, CustomColUserID;
    @FXML public TableColumn ColAppID, ColTitle, ColDescription, ColLocation, ColContact, ColType, ColStart, ColEnd, ColCustomerID, ColUserID;

    public ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Lambda expression to streamline the setting of table columns, reducing redundancy.
     */
    TableColumns columnSetter = (a, t, d, l, c, t2, st, et, cId, uId) ->
    {
        // Logic to set table columns
        ColAppID.setCellValueFactory(new PropertyValueFactory<>(a));
        ColTitle.setCellValueFactory(new PropertyValueFactory<>(t));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>(d));
        ColLocation.setCellValueFactory(new PropertyValueFactory<>(l));
        ColContact.setCellValueFactory(new PropertyValueFactory<>(c));
        ColType.setCellValueFactory(new PropertyValueFactory<>(t2));
        ColStart.setCellValueFactory(new PropertyValueFactory<>(st));
        ColEnd.setCellValueFactory(new PropertyValueFactory<>(et));
        ColCustomerID.setCellValueFactory(new PropertyValueFactory<>(cId));
        ColUserID.setCellValueFactory(new PropertyValueFactory<>(uId));
    };

    /**
     * Initializes the scheduler dashboard, loading appointment data and preparing combo boxes for reporting.
     * @param url The URL for initialization
     * @param resourceBundle The resource bundle for initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allAppointments = AppointmentDAO.getAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateTable();

        // Combobox initialization logic
        ComboBoxType.setItems(DataLists.getTypes());
        ComboBoxMonth.setItems(DataLists.getMonths());
        try {
            ComboBoxContact.setItems(ContactDAO.getContactNames());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ComboBoxLocation.setItems(DataLists.getPlaces());
    }

    /**
     * Populates the main appointment table and sets up table columns.
     * Visibility of other tables is adjusted for clear display.
     */
    public void populateTable()
    {
        AppointmentsTable.setItems(allAppointments);

        columnSetter.setColumns("appointmentId", "title", "description", "location", "contactName",
                "type", "startTime", "endTime", "customerId", "userId");

        AppointmentsTable.visibleProperty().setValue(true);
        TypeTabReport.visibleProperty().setValue(false);
        ContactTabReport.visibleProperty().setValue(false);
        CustomTabReport.visibleProperty().setValue(false);
    }

    /**
     * Loads and displays appointments for the current month as per the user's system time zone. This method queries the
     * database for appointments corresponding to the current month. It also sets up the table columns using the
     * TableColumns interface and a lambda expression, streamlining the process of initializing table columns. The method
     * additionally manages the visibility of this and other TableView elements to ensure that only the relevant table
     * is displayed at any given time.
     */
    public void populateTableMonth()
    {
        try {
            AppointmentsTable.setItems(AppointmentDAO.getAppointmentsByMonth());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        columnSetter.setColumns("appointmentId", "title", "description", "location", "contactName",
                "type", "startTime", "endTime", "customerId", "userId");

        AppointmentsTable.visibleProperty().setValue(true);
        TypeTabReport.visibleProperty().setValue(false);
        ContactTabReport.visibleProperty().setValue(false);
        CustomTabReport.visibleProperty().setValue(false);
    }

    /**
     * Populates the TableView with appointments scheduled for the current day and the upcoming seven days, based on the user's local time zone.
     * This method performs a database query to fetch appointments falling within the next week. It initializes the TableColumns,
     * streamlining this process by using the TableColumns interface with a lambda expression for efficiency. The method also manages
     * the visibility of various TableView elements, ensuring only relevant data is displayed while hiding others.
     */
    public void populateTableWeek() {
        {
            try {
                AppointmentsTable.setItems(AppointmentDAO.getAppointmentsByWeek());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            columnSetter.setColumns("appointmentId", "title", "description", "location", "contactName",
                    "type", "startTime", "endTime", "customerId", "userId");

            AppointmentsTable.visibleProperty().setValue(true);
            TypeTabReport.visibleProperty().setValue(false);
            ContactTabReport.visibleProperty().setValue(false);
            CustomTabReport.visibleProperty().setValue(false);
        }
    }

    /**
     * Navigates to the customer dashboard.
     * @param actionEvent The event triggered by button click.
     */
    public void toCustomerGUI(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 400);
        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** This method brings the user to the AddAppointment view.
     * @param actionEvent The action when the button is clicked. */
    public void AddAppointment(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Transitions the user to the UpdateAppointment view. This method first checks if an appointment has been selected
     * from the TableView. If no selection is made, it displays an error alert. Upon selecting an appointment, it loads
     * the UpdateAppointment view and pre-populates the fields with the selected appointment's data.
     * @param actionEvent The event triggered when the button is clicked.
     */
    public void UpdateAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        if( AppointmentsTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Select an appointment to update.");
            return;
        }
        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointmentController myUpdate = loader.getController();
            myUpdate.setAppointmentId(String.valueOf(AppointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId()));
            myUpdate.setTitle(AppointmentsTable.getSelectionModel().getSelectedItem().getTitle());
            myUpdate.setDescription(AppointmentsTable.getSelectionModel().getSelectedItem().getDescription());
            myUpdate.setLocation(AppointmentsTable.getSelectionModel().getSelectedItem().getLocation());
            myUpdate.setContact(AppointmentsTable.getSelectionModel().getSelectedItem().getContactName());
            myUpdate.setType(AppointmentsTable.getSelectionModel().getSelectedItem().getType());
            myUpdate.setStartDate(AppointmentsTable.getSelectionModel().getSelectedItem().getStartTime().toLocalDate());
            myUpdate.setEndDate(AppointmentsTable.getSelectionModel().getSelectedItem().getEndTime().toLocalDate());
            myUpdate.setStartTime(AppointmentsTable.getSelectionModel().getSelectedItem().getStartTime().toLocalTime());
            myUpdate.setEndTime(AppointmentsTable.getSelectionModel().getSelectedItem().getEndTime().toLocalTime());
            myUpdate.setCustomerId(String.valueOf(AppointmentsTable.getSelectionModel().getSelectedItem().getCustomerId()));
            myUpdate.setUserId(String.valueOf(AppointmentsTable.getSelectionModel().getSelectedItem().getUserId()));

            myUpdate.setOriginalApptTime(AppointmentsTable.getSelectionModel().getSelectedItem().getStartTime(), AppointmentsTable.getSelectionModel().getSelectedItem().getEndTime());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

    /**
     * Removes the selected appointment from the database. Initially checks if an appointment is selected in the TableView.
     * If no selection is made, an error alert is displayed. Upon selection, the user is prompted to confirm the deletion.
     * After confirmation, the appointment is deleted and a success message is shown. The TableView is then refreshed
     * to reflect the changes.
     */
    public void deleteAppointment() throws SQLException {
        if( AppointmentsTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Select an appointment to delete.");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm to delete appointment (ID:" + AppointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId() + ") ?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String appointmentId = String.valueOf(AppointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId());
                    String type = AppointmentsTable.getSelectionModel().getSelectedItem().getType();
                    try {
                        AppointmentDAO.deleteAppointment(AppointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Alert alertDeleted = new Alert(Alert.AlertType.INFORMATION);
                    alertDeleted.show();
                    alertDeleted.setHeaderText("Delete Successful");
                    alertDeleted.setContentText("Appointment (" + "ID:" + appointmentId + ", Type: " + type + ") deleted");
                }
            });
            try {
                allAppointments = AppointmentDAO.getAppointments();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            populateTable();
        }
    }

    /**
     * Generates and displays a report based on type and month. Ensures correct table visibility.
     * @throws SQLException If SQL database access fails
     */
    public void getTypeMonthReport() throws SQLException {

        try{

            TypeTabReport.setItems(AppointmentDAO.getMonthTypeReport(ComboBoxMonth.getValue().toString(), ComboBoxType.getValue().toString()));

            MonthReport.setCellValueFactory(new PropertyValueFactory<>("month"));
            TypeReport.setCellValueFactory(new PropertyValueFactory<>("type"));
            CountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

            AppointmentsTable.visibleProperty().setValue(false);
            TypeTabReport.visibleProperty().setValue(true);
            ContactTabReport.visibleProperty().setValue(false);
            CustomTabReport.visibleProperty().setValue(false);

            RadioAllApp.setSelected(false);
            RadioMonthApp.setSelected(false);
            RadioWeekApp.setSelected(false);
        }        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Select Month/Type");
            alert.setContentText("Select Month and Type first");
        }
    }

    /**
     * Displays the contact-based report in the reportContactTable. This method executes when a user selects a contact
     * from the combo box and retrieves the corresponding appointment data. If no contact is selected, it prompts the
     * user with an error alert. The method initializes the necessary TableColumns and ensures only the relevant
     * reportContactTable is visible. Additionally, it deselects the view radio buttons to allow for a fresh selection
     * and view reset of the appointment TableView.
     */
    public void getContactReport() throws SQLException {
        try {
            ContactTabReport.setItems(AppointmentDAO.getContactReport(ComboBoxContact.getValue().toString()));

            ContactColAppID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            ContactColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            ContactColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            ContactColLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            ContactColContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            ContactColType.setCellValueFactory(new PropertyValueFactory<>("type"));
            ContactColStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            ContactColEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            ContactColCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            ContactColUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

            AppointmentsTable.visibleProperty().setValue(false);
            TypeTabReport.visibleProperty().setValue(false);
            ContactTabReport.visibleProperty().setValue(true);
            CustomTabReport.visibleProperty().setValue(false);

            RadioAllApp.setSelected(false);
            RadioMonthApp.setSelected(false);
            RadioWeekApp.setSelected(false);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Contact Selection");
            alert.setContentText("Contact must be selected first");
        }
    }

    /**
     * Displays a customized report in the reportCustomTable based on the user's location selection. The method queries
     * and shows results according to the chosen location from the combo box. If no location is selected, the user is
     * notified with an alert. This method also initializes the necessary TableColumns for the report and manages the
     * visibility of various TableView elements to display only the reportCustomTable. Additionally, it resets the View
     * radio buttons for a fresh selection in the appointment TableView.
     */
    public void getCustomReport() throws SQLException {

        try {
            CustomTabReport.setItems(AppointmentDAO.getLocationReport(ComboBoxLocation.getValue().toString()));

            CustomColAppID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            CustomColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            CustomColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            CustomColLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            CustomColContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            CustomColType.setCellValueFactory(new PropertyValueFactory<>("type"));
            CustomColStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            CustomColEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            CustomColCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            CustomColUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

            AppointmentsTable.visibleProperty().setValue(false);
            TypeTabReport.visibleProperty().setValue(false);
            ContactTabReport.visibleProperty().setValue(false);
            CustomTabReport.visibleProperty().setValue(true);

            RadioAllApp.setSelected(false);
            RadioMonthApp.setSelected(false);
            RadioWeekApp.setSelected(false);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Location Selection");
            alert.setContentText("Location must be selected first");
        }
    }
}