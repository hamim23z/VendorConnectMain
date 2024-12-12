package dao;

import db.DatabaseConnector;
import model.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                String name = rs.getString("name");
                String description = rs.getString("description");  // Retrieve description
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                return new Vendor(name, description, address, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}