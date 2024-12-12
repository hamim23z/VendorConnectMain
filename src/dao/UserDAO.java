package dao;

import db.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    // Method to check if username already exists
    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to signup a new user
    public boolean signupUser(String username, String email, String password) {
        // Check if username is already taken
        if (isUsernameTaken(username)) {
            return false;
        }

        String query = "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to login a user
    public boolean loginUser(String username, String password) {
        String query = "SELECT password FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Simple string comparison
                String storedPassword = rs.getString("password");
                return password.equals(storedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}