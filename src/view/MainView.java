package view;

import dao.VendorDAO;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainView {

    public void start(Stage primaryStage) {
        // Create TextFields for vendor input
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Vendor Name");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter Vendor Description");

        TextField addressField = new TextField();
        addressField.setPromptText("Enter Vendor Address");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter Vendor Phone");

        // Button to add vendor
        Button addButton = new Button("Add Vendor");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            
            // Call your addVendor method here to add it to the database
            VendorDAO vendorDAO = new VendorDAO();
            boolean isAdded = vendorDAO.addVendor(name, description, address, phone);
            if (isAdded) {
                System.out.println("Vendor added successfully!");
            } else {
                System.out.println("Failed to add vendor.");
            }
        });

        // Create WebView to display map
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        
        // Load the map.html file from the view package
        webEngine.load(getClass().getResource("/view/map.html").toExternalForm()); // Adjust path if needed

        // Layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        
        // Add WebView and vendor input fields to the layout
        layout.getChildren().addAll(webView, nameField, descriptionField, addressField, phoneField, addButton);

        // Scene
        Scene scene = new Scene(layout, 600, 600); // Adjust size to fit map
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vendor Connect");
        primaryStage.show();
    }
}