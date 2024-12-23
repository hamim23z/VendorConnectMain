package dao;

import db.DatabaseConnector;
import model.Vendor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendorDAO {

    private Connection connection;

    public VendorDAO() {
        try {
            this.connection = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new vendor to the database
    public boolean addVendor(String name, String description, String address, String phone) {
        String query = "INSERT INTO vendors (name, description, address, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, description);  // Set description here
            ps.setString(3, address);
            ps.setString(4, phone);
            int result = ps.executeUpdate();
            return result > 0;  // Returns true if the vendor was added
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a vendor by ID
    public Vendor getVendor(int id) {
        String query = "SELECT * FROM vendors WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Retrieve the vendor data, including id
                int vendorId = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");  // Retrieve description
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                // Return a new Vendor with the retrieved data
                return new Vendor(vendorId, name, description, address, phone);  // Pass id to constructor
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to fetch all vendors
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT * FROM vendors";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Retrieve vendor data for each row in the result set
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                // Add each vendor to the list
                vendors.add(new Vendor(id, name, description, address, phone)); // Pass parameters in the correct order
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendors;
    }
}