package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    // Update the URL to use 127.0.0.1 and your actual database name (VendorConnectDB)
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/VendorConnectDB?useSSL=false&serverTimezone=UTC";
    
    // Replace with your MySQL username and password
    private static final String USER = "root"; // Or your DB username
    private static final String PASSWORD = "choudhury36"; // Replace with your DB password

    public static Connection getConnection() throws SQLException {
        try {
            // Load the JDBC driver (not strictly necessary with newer versions of Java)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish and return a connection to the database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Handle case where JDBC driver is not found
            e.printStackTrace();
            throw new SQLException("MySQL JDBC driver not found");
        }
    }
}