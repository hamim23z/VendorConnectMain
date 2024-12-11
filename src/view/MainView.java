package view;

import dao.VendorDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {

    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Vendor Connect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);  // Adjusted size for better layout

        // Create the CardLayout for managing multiple pages
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);  // This will hold all the different panels/pages

        // Create the top navbar panel
        JPanel navbarPanel = new JPanel();
        navbarPanel.setLayout(new BorderLayout());
        navbarPanel.setBackground(Color.BLACK); // Set navbar background color to black
        navbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding (top, left, bottom, right)

        // Create the "VendorConnect" label on the left
        JLabel vendorConnectLabel = new JLabel("VendorConnect", JLabel.LEFT);
        vendorConnectLabel.setFont(new Font("Arial", Font.BOLD, 20));
        vendorConnectLabel.setForeground(Color.WHITE); // Set text color to white
        
        // Make the VendorConnect label clickable
        vendorConnectLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Change cursor to hand

        // Add action listener to the label to switch to the home page
        vendorConnectLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "Home");  // Show the "Home" panel when clicked
            }
        });

        navbarPanel.add(vendorConnectLabel, BorderLayout.WEST);

        // Create clickable text labels instead of buttons
        JPanel linkPanel = new JPanel();
        linkPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0)); // Add gap of 20px between items
        linkPanel.setBackground(Color.BLACK); // Set panel background to black

        // Create JLabel for "How to Use", "About Us", "Login"
        JLabel howToUseLabel = new JLabel("How to Use");
        JLabel aboutUsLabel = new JLabel("About Us");
        JLabel loginLabel = new JLabel("Login");

        // Set the color to white for the text of all labels
        howToUseLabel.setForeground(Color.WHITE);
        aboutUsLabel.setForeground(Color.WHITE);
        loginLabel.setForeground(Color.WHITE);

        // Make the labels clickable like anchor tags
        howToUseLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aboutUsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add MouseListener to the labels to switch between pages
        howToUseLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "HowToUse");
            }
        });

        aboutUsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "AboutUs");
            }
        });

        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "Login");
            }
        });

        // Add labels to the panel (this acts like links)
        linkPanel.add(howToUseLabel);
        linkPanel.add(aboutUsLabel);
        linkPanel.add(loginLabel);

        navbarPanel.add(linkPanel, BorderLayout.EAST);

        // Add the navbar to the top of the frame
        frame.add(navbarPanel, BorderLayout.NORTH);

        // --- Create the homepage panel with vendor input and map (initialized only once) ---

        // Create a JPanel for vendor input fields and button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns
        
        // Add input fields with labels
        JLabel nameLabel = new JLabel("Vendor Name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Vendor Description:");
        JTextField descriptionField = new JTextField();

        JLabel addressLabel = new JLabel("Vendor Address:");
        JTextField addressField = new JTextField();

        JLabel phoneLabel = new JLabel("Vendor Phone:");
        JTextField phoneField = new JTextField();

        JButton addButton = new JButton("Add Vendor");

        // Add components to input panel
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel()); // Empty space in the grid
        inputPanel.add(addButton);

        // Add functionality to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();

                VendorDAO vendorDAO = new VendorDAO();
                boolean isAdded = vendorDAO.addVendor(name, description, address, phone);
                if (isAdded) {
                    JOptionPane.showMessageDialog(frame, "Vendor added successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add vendor.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create a JPanel for displaying the map (placeholder for now)
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());

        JLabel mapPlaceholder = new JLabel("Map Placeholder", SwingConstants.CENTER);
        mapPlaceholder.setFont(new Font("Arial", Font.BOLD, 16));
        mapPlaceholder.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mapPanel.add(mapPlaceholder, BorderLayout.CENTER);

        // Create the homePanel (which will be reused)
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        homePanel.add(inputPanel, BorderLayout.SOUTH);
        homePanel.add(mapPanel, BorderLayout.CENTER);

        // --- End of homepage setup ---

        // Create the "How to Use" page
        JPanel howToUsePanel = new JPanel();
        howToUsePanel.add(new JLabel("How to Use - Instructions"));

        // Create the "About Us" page
        JPanel aboutUsPanel = new JPanel();
        aboutUsPanel.add(new JLabel("About Us - Information"));

        // Create the "Login" page
        JPanel loginPanel = new JPanel();
        loginPanel.add(new JLabel("Login Page"));

        // Add all pages to the cardPanel (this is the container for all pages)
        cardPanel.add(homePanel, "Home");
        cardPanel.add(howToUsePanel, "HowToUse");
        cardPanel.add(aboutUsPanel, "AboutUs");
        cardPanel.add(loginPanel, "Login");

        // Add the cardPanel to the main frame
        frame.add(cardPanel, BorderLayout.CENTER);

        // Set the default page to Home
        cardLayout.show(cardPanel, "Home");

        // Make the frame visible
        frame.setVisible(true);
    }
}