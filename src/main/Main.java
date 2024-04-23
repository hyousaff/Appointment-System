package main;

import DataBase.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

/**
 *
 * @author Hamza Yousaf
 * Main Class for the java program, launches the program.
 */

/**
 *
 * JAVADOC LOCATION: C195/JavaDoc
 *
 *
 * 2 Lambda Expressions used:
 *
 * Alerts.JAVA
 * Interface for the Errors lambda expression. Simplifies the creation of alert windows, reducing repetitive code lines.
 *
 *
 * TableColumns.JAVA
 * Interface for the TableColumns lambda expression utilized in the SchedulerDashboardController.
 * This interface simplifies the initialization of TableColumns in a TableView, reducing the amount of
 * repetitive code required. The lambda expression is employed in multiple methods, including populateTable,
 * populateTableMonth, and populateTableWeek, to streamline the setup of table columns.
 *
 * */




public class Main extends Application {

    /**
     * The JavaFX application's entry point that launches the login view.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an error occurs while loading the login view.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("Appointment Schedule Login");
        primaryStage.setScene(new Scene(root, 350, 380));
        primaryStage.show();
    }

    /**
     * The main method for the program.
     *
     * @param args Command-line arguments.
     * @throws SQLException If an error occurs while interacting with the SQL database.
     * @throws IOException  If an error occurs while reading or writing files.
     */
    public static void main(String[] args) throws SQLException, IOException {

        //Open Database connection
        JDBC.openConnection();

        //Change Locale to French
        //Locale.setDefault(new Locale("fr","FR"));

        launch(args);
        JDBC.closeConnection();
    }
}