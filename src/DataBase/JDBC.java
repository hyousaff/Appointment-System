package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Hamza Yousaf
 *
 * This is the JDBC class. This is the Java Database Connection driver's class.
 * It manages the methods that are used to link the Java application and MySQL database. */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//127.0.0.1:3306/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /**
     * Opens a connection between the Java program and the MySQL database using the specified connection details.
     * It loads the MySQL JDBC driver, establishes a connection, and prints a success message upon successful connection.
     */
    public static void openConnection(){
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Closes the connection between the Java program and the MySQL database.
     * This method should be called when the Java program is being closed or when the connection is no longer needed.
     */
    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection closed!");

        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}