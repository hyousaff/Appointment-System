package controller;

import DAO.AppointmentDAO;
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
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Hamza Yousaf
 *
 * Controller for the "Login" view, Handles login processes and checks for upcoming appointments.
 */
public class LoginController implements Initializable {

    @FXML private Button LoginButton;
    @FXML private Label LocationDetectLabel, PasswordLabel, LocationLabel, UsernameLabel;
    @FXML private TextField PasswordField, UsernameField;
    ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
    ZoneId myZoneId = ZoneId.systemDefault();

    String error = "Error";
    String errorDetail = "Invalid username or password.";

    /**
     * Initializes the Login view. Localizes labels based on the system's locale. Supports English and French.
     * @param url The URL for initialization
     * @param resourceBundle The resource bundle for initialization
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocationLabel.setText(myZoneId.toString());
        LocationDetectLabel.setText(rb.getString(LocationDetectLabel.getText()));
        UsernameLabel.setText(rb.getString(UsernameLabel.getText()));
        PasswordLabel.setText(rb.getString(PasswordLabel.getText()));
        LoginButton.setText(rb.getString(LoginButton.getText()));
    }

    /**
     * Handles user login. Validates credentials and navigates to the main dashboard on success.
     * Records login attempts.
     * @param actionEvent The action when the button is clicked.
     */
    public void login(ActionEvent actionEvent) throws IOException, SQLException {

        if ((UsernameField.getText().equals("admin") && PasswordField.getText().equals("admin"))) {
            recordActivity(UsernameField.getText(), "login successful");
        } else {
            recordActivity(UsernameField.getText(), "login unsuccessful");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText(rb.getString(error));
            alert.setContentText(rb.getString(errorDetail));
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("../view/Schedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1240, 600);
        stage.setTitle("Schedule Screen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        showNextAppointment();
    }

    /**
     * Records login attempts, both successful and unsuccessful, to a file with timestamps.
     * @param userAttempt Username used in the login attempt
     * @param attempt Result of the login attempt
     */
    public void recordActivity(String userAttempt, String attempt) throws IOException {
        LocalDateTime myLDT = LocalDateTime.now();
        String loginAttemptTime = DataLists.myFormattedDTF(myLDT);
        String loginActivity = "Login by \"" + userAttempt + "\" was " + attempt + " at " + loginAttemptTime + " " + myZoneId.toString();

        // Filename variable
        String fileName = "login_activity.txt";

        // create filewriter object, creats and open files, write and close
        try (FileWriter fWriter = new FileWriter(fileName, true);
             PrintWriter outputFile = new PrintWriter(fWriter)) {
            outputFile.println(loginActivity);
        } // Both outputFile and fWriter are closed here, even if an exception is thrown.
        catch (IOException e) {
            // Log the exception to the console
            e.printStackTrace(); }
    }

    /**
     * Checks for any appointments in the next 15 minutes after successful login. Informs the user accordingly.
     * @throws SQLException If SQL database access fails
     */
    public void showNextAppointment() throws SQLException {
        Appointment nextAppointment = AppointmentDAO.checkAppointment(LocalDateTime.now());
        if (nextAppointment.getStartTime() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.show();
            alert.setHeaderText("Upcoming Appointment");
            alert.setContentText("No upcoming appointments.");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.show();
            alert.setHeaderText("Upcoming Appointment");
            alert.setContentText("Appointment ID: " + nextAppointment.getAppointmentId() + " on " + DataLists.myFormattedDTF(nextAppointment.getStartTime()));
        }
    }

    /**
     * Closes the application when the cancel button is clicked.
     * @param event The event triggered by button click
     */
    @FXML
    private void cancelButton(ActionEvent event) {
        // This line gets the current stage/window and closes it, effectively exiting the application
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}