package main;

import dao.VendorDAO;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Vendor;
import view.MainView;  // Assuming you create a MainView class for the GUI

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        VendorDAO vendorDAO = new VendorDAO();

        // Test adding a vendor to the database
        boolean isAdded = vendorDAO.addVendor("Best Pizza", "A place for the best pizza in town", "123 Pizza St", "555-1234");
        if (isAdded) {
            System.out.println("Vendor added successfully!");
        } else {
            System.out.println("Failed to add vendor.");
        }

        // Test retrieving a vendor by ID
        Vendor vendor = vendorDAO.getVendor(1);  // Assuming there's a vendor with ID 1
        if (vendor != null) {
            System.out.println("Vendor retrieved: " + vendor);
        } else {
            System.out.println("Vendor not found.");
        }

        // Initialize your JavaFX GUI
        MainView view = new MainView();
        view.start(primaryStage);  // Start the view with the Stage
    }
}