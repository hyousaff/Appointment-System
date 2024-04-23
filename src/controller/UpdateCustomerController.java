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
 * @author Hamza Yousaf
 * This is the controller class for the "UpdateCustomer" view. It handles the business logic for updating a customer
 *  in the database. */
public class UpdateCustomerController implements Initializable
{
    @FXML public TextField CustomerIDField, CustomerNameField, AddressField, PostalCodeField, PhoneField;
    @FXML public ComboBox<Country> ComboCountry;
    @FXML public ComboBox<FirstLevelDivision> ComboFLDBox;

    /**
     * Initializes the controller. Populates country combo box when the view is launched.
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
     * Populates the country combo box using data from the CountryDAO.
     */
    public void populateCountries() throws SQLException {
        CountryDAO.clearCountries();
        ComboCountry.setItems(CountryDAO.getCountries());
    }

    /**
     * Sets the fields of the form with the selected customer's details. This method is invoked to pre-populate the form
     * when updating an existing customer's information.
     *
     * @param customerId   The unique ID of the customer being updated. This field is uneditable.
     * @param customerName The name of the customer being updated.
     * @param address      The address of the customer being updated.
     * @param postalCode   The postal code of the customer being updated.
     * @param phone        The phone number of the customer being updated.
     * @param country      The country of the customer, used to preselect the correct value in the country combo box.
     * @param division     The first level division of the customer, used to preselect the correct value in the division combo box.
     */
    public void setCustomer(int customerId, String customerName, String address, String postalCode, String phone, Country country, FirstLevelDivision division)
    {
        CustomerIDField.setText(String.valueOf(customerId));
        CustomerNameField.setText(customerName);
        AddressField.setText(address);
        PostalCodeField.setText(postalCode);
        PhoneField.setText(phone);
        ComboCountry.setValue(country);
        ComboFLDBox.setValue(division);
    }

    /**
     * Handles the selection of a country from the country combo box. Upon selection, this method triggers the
     * population of the first-level divisions combo box based on the selected country.
     */
    public void passCountry() throws SQLException {
        try {
            ComboFLDBox.getItems().removeAll();
            populateFirstLevelDivisions(ComboCountry.getValue().getCountryId());
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Fills the first-level divisions combo box with appropriate divisions based on the selected country.
     * This method is invoked after a country is chosen by the user. It queries the database to retrieve the
     * relevant first-level divisions associated with the given country.
     *
     * @param countryId The ID of the country for which first-level divisions are to be fetched.
     */
    public void populateFirstLevelDivisions(int countryId) throws SQLException {
        FirstLevelDivisionDAO.clearLists();
        ComboFLDBox.setItems(FirstLevelDivisionDAO.getAllFLD(countryId));
    }

    /**
     * Updates the customer's information in the database. Retrieves values from the form fields and sends them
     * as an UPDATE statement to the database. Before updating, it checks if all inputs are filled using the
     * inputCheck method. Upon successful update, a confirmation message is displayed.
     */
    public void updateCustomer() throws SQLException {
        try{
            if (inputCheck() == true) {
                int customerId = Integer.parseInt(CustomerIDField.getText());
                String customerName = CustomerNameField.getText();
                String address = AddressField.getText();
                String postalCode = PostalCodeField.getText();
                String phone = PhoneField.getText();
                int divisionIdFK = FirstLevelDivisionDAO.getMatchingDivisionId(ComboFLDBox.getSelectionModel().getSelectedItem().toString());

                CustomerDAO.updateCustomer(customerName, address, postalCode, phone, divisionIdFK, customerId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Success.");
                alert.setContentText("Customer " + customerId + " successfully updated.");
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Navigates the user to the customer dashboard. This method is triggered when the corresponding button is clicked.
     * It loads the Customer Dashboard view and displays it in the current stage.
     *
     * @param actionEvent The event triggered by the button click, used to get the source stage for navigation.
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

    /**
     * Clears all input fields in the form. This allows the user to start a new entry from scratch without manually
     * clearing each field.
     */
    public void resetFields()
    {
        CustomerNameField.setText("");
        AddressField.setText("");
        PostalCodeField.setText("");
        PhoneField.setText("");
        ComboCountry.setValue(null);
        ComboFLDBox.setValue(null);
    }

    /**
     * Validates whether all fields in the form are completed. Displays an error alert if any field is left empty.
     *
     * @return A boolean indicating whether all fields are filled. Returns true if all fields are complete, false otherwise.
     */
    public boolean inputCheck()
    {
        boolean good = true;
        if(CustomerNameField.getText().isEmpty() ||
                AddressField.getText().isEmpty() ||
                PostalCodeField.getText().isEmpty() ||
                PhoneField.getText().isEmpty() ||
                ComboCountry.getValue() == null ||
                ComboFLDBox.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Inputs");
            alert.setContentText("All fields must be completed.");
            good = false;
        }
        return good;
    }
}