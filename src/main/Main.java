package main;

import dao.VendorDAO;
import view.MainView;

public class Main {

    public static void main(String[] args) {
        // Test database connection and operations
        VendorDAO vendorDAO = new VendorDAO();

        // Test adding a vendor to the database
        boolean isAdded = vendorDAO.addVendor("Best Pizza", "A place for the best pizza in town", "123 Pizza St", "555-1234");
        if (isAdded) {
            System.out.println("Vendor added successfully!");
        } else {
            System.out.println("Failed to add vendor.");
        }

        // Test retrieving a vendor by ID
        System.out.println("Fetching vendor with ID 1...");
        var vendor = vendorDAO.getVendor(1); // Assuming there's a vendor with ID 1
        if (vendor != null) {
            System.out.println("Vendor retrieved: " + vendor);
        } else {
            System.out.println("Vendor not found.");
        }

        // Initialize and show the Swing GUI
        MainView.main(args);
    }
}
