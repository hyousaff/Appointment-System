package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 *  @author Hamza Yousaf
 *
 * Controller class for the "AddCustomer" view, Manages the process of adding new customers to the database.
 * */
public class AddCustomerController implements Initializable {
    // FXML annotated fields
    @FXML
    private ComboBox<Country> countryBox;
    @FXML
    private ComboBox<FirstLevelDivision> FLDbox;
    @FXML
    private TextField customerNameField, addressField, postalcodeField, phoneField;

    /**
     * Initialization method for AddCustomer view. Called at view launch, it populates the country combo box.
     *
     * @param url            The URL for initialization
     * @param resourceBundle The resource bundle for initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateCountries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the country combo box. Retrieves a list of countries for selection.
     *
     * @throws SQLException If SQL database access fails
     */
    public void populateCountries() throws SQLException {
        CountryDAO.clearCountries();
        countryBox.setItems(CountryDAO.getCountries());
    }

    /**
     * Handles country selection. Uses the selected country to populate First Level Divisions.
     *
     * @throws SQLException If SQL database access fails
     */
    public void passCountry() throws SQLException {
        try {
            FLDbox.getItems().clear();
            populateFirstLevelDivisions(countryBox.getValue().getCountryId());
        } catch (Exception e) {
        }
    }

    /**
     * Populates First Level Division combo box based on selected country.
     *
     * @param countryId Country ID for retrieving corresponding divisions
     * @throws SQLException If SQL database access fails
     */
    public void populateFirstLevelDivisions(int countryId) throws SQLException {
        FirstLevelDivisionDAO.clearLists();
        FLDbox.setItems(FirstLevelDivisionDAO.getAllFLD(countryId));
    }

    /**
     * Adds a new customer to the database. Validates inputs before submission.
     * Displays a success message upon successful addition.
     *
     * @throws SQLException If SQL database access fails
     */
    public void addCustomer() throws SQLException {
        try {
            if (inputCheck()) {
                // Retrieving input values
                String customerName = customerNameField.getText();
                String address = addressField.getText();
                String postalCode = postalcodeField.getText();
                String phone = phoneField.getText();
                int divisionIdFK = FirstLevelDivisionDAO.getMatchingDivisionId(FLDbox.getSelectionModel().getSelectedItem().toString());

                // Inserting customer
                CustomerDAO.insertCustomer(customerName, address, postalCode, phone, divisionIdFK);

                // Success Alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Success.");
                alert.setContentText("Customer " + customerName + " successfully added.");
            }
        } catch (Exception e) {
            // Exception handling
        }
    }

    /**
     * Verifies all form fields are filled. Displays an error if incomplete.
     *
     * @return true if all fields are filled, false otherwise
     */
    public boolean inputCheck() {
        boolean good = true;
        if (customerNameField.getText().isEmpty() ||
                addressField.getText().isEmpty() ||
                postalcodeField.getText().isEmpty() ||
                phoneField.getText().isEmpty() ||
                countryBox.getValue() == null ||
                FLDbox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Check Inputs");
            alert.setContentText("Please complete all fields.");
            alert.show();
            return false;
        }
        return true;
    }

    /**
     * Resets all input fields. Facilitates starting a new entry efficiently.
     */
    public void resetFields() {
        customerNameField.clear();
        addressField.clear();
        postalcodeField.clear();
        phoneField.clear();
        countryBox.setValue(null);
        FLDbox.setValue(null);
    }

    /**
     * Navigates back to the customer dashboard.
     * @param actionEvent The event triggered by button click
     */
    public void toCustomerGUI(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Customer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 400);
        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}