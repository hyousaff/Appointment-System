package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author Hamza Yousaf
 *
 * Controller for the "Customer" view, Manages the Customer TableView and navigation for adding, updating, and deleting customers.
 */
public class CustomerController implements Initializable
{
    @FXML private TableView<Customer> TableCustomer;
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    @FXML private TableColumn ColCustomerID, ColCustomerName, ColAddress, ColPostalCode, ColPhone, ColDivisionName, ColCountry;


    /**
     * Initializes the Customer view. Called at launch, it prepares the list of Customers and populates the TableView.
     * @param url The URL for initialization
     * @param resourceBundle The resource bundle for initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            allCustomers = CustomerDAO.getCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateTable();
    }

    /**
     * Fills the Customer TableView with data. Sets up columns with the appropriate data fields.
     */
    public void populateTable(){
        TableCustomer.setItems(allCustomers);
        // Setting up each column
        ColCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        ColCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        ColPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        ColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ColDivisionName.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        ColCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    /**
     * Navigates to the AddCustomer view.
     * @param actionEvent The event triggered by button click
     * @throws IOException If FXML loading fails
     */
    public void AddCustomer(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 550);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Deletes a selected customer from the database. Verifies selection and associated appointments before deletion. Informs the user upon successful deletion.
     * @throws SQLException If SQL database access fails
     */
    public void DeleteCustomer() throws SQLException {
        if(TableCustomer.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Select a customer to delete.");
            return;
        }
        if(CustomerDAO.checkCustomerAppointments(TableCustomer.getSelectionModel().getSelectedItem().getCustomerId()) > 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Customer appointment needs to be deleted first.");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm delete customer (ID:" + TableCustomer.getSelectionModel().getSelectedItem().getCustomerId() + ") ?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        CustomerDAO.deleteCustomer(TableCustomer.getSelectionModel().getSelectedItem().getCustomerId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Alert alertDelete = new Alert(Alert.AlertType.INFORMATION);
                    alertDelete.show();
                    alertDelete.setHeaderText("Customer Delete Successful");
                    alertDelete.setContentText("Customer ID:" + TableCustomer.getSelectionModel().getSelectedItem().getCustomerId() + " deleted.");
                    try {
                        allCustomers = CustomerDAO.getCustomers();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            populateTable();
        }
    }

    /**
     * Navigates to the UpdateCustomer view. Validates customer selection before proceeding.
     * @param actionEvent The event triggered by button click
     * @throws IOException If FXML loading fails
     * @throws SQLException If SQL database access fails
     */
    public void UpdateCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        if( TableCustomer.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Select a customer to update.");
            return;
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateCustomer.fxml"));
            loader.load();

            UpdateCustomerController myUpdate = loader.getController();
            myUpdate.setCustomer(TableCustomer.getSelectionModel().getSelectedItem().getCustomerId(),
                    TableCustomer.getSelectionModel().getSelectedItem().toString(),
                    TableCustomer.getSelectionModel().getSelectedItem().getAddress(),
                    TableCustomer.getSelectionModel().getSelectedItem().getPostalCode(),
                    TableCustomer.getSelectionModel().getSelectedItem().getPhone(),
                    CountryDAO.getCountryForBox(TableCustomer.getSelectionModel().getSelectedItem().getDivisionIdFK()),
                    FirstLevelDivisionDAO.returnDivision(TableCustomer.getSelectionModel().getSelectedItem().getDivisionIdFK())
            );

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Customer");
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

    /**
     * Returns to the main Scheduler Dashboard.
     * @param actionEvent The event triggered by button click
     * @throws IOException If FXML loading fails
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