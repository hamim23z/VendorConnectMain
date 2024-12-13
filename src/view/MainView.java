package view;

import dao.UserDAO;
import dao.VendorDAO;
import model.Vendor;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainView {

    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Vendor Connect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame to full-screen mode
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create the CardLayout for managing multiple pages
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

       
        
        // --- Home Page ---
        JPanel homePanel = new JPanel(new BorderLayout()); 

        // --- Map setup ---
        JPanel mapPanel = new JPanel(new BorderLayout()); 
        JXMapViewer mapViewer = new JXMapViewer(); 
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo())); // Use OpenStreetMap tiles
        mapViewer.setCenterPosition(new GeoPosition(40.7128, -74.0060)); // NYC coordinates
        mapViewer.setZoom(8);

        // Add interaction listeners for panning and zooming
        mapViewer.addMouseListener(new PanMouseInputListener(mapViewer));
        mapViewer.addMouseMotionListener(new PanMouseInputListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        // Add the JXMapViewer to the mapPanel
        mapPanel.add(mapViewer, BorderLayout.CENTER); 

        // Add the mapPanel to the homePanel
        homePanel.add(mapPanel, BorderLayout.CENTER); 

        // --- Input fields setup ---
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10)); 
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        // Add input fields with labels
        JLabel nameLabel = new JLabel("Vendor Name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Vendor Description:");
        JTextField descriptionField = new JTextField();

        JLabel addressLabel = new JLabel("Vendor Address:");
        JTextField addressField = new JTextField();

        JLabel phoneLabel = new JLabel("Vendor Phone:");
        JTextField phoneField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);

        // --- Combine input and button panels ---
        JPanel bottomPanel = new JPanel(new BorderLayout()); 
        bottomPanel.add(inputPanel, BorderLayout.CENTER); 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        JButton addButton = new JButton("Add Vendor"); 
        buttonPanel.add(addButton); 
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH); 

        // Add the combined bottom panel to the homePanel
        homePanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add functionality to the add button
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            
            if (phone.length() != 10) {
                JOptionPane.showMessageDialog(frame, "Phone number must be 10 digits long (e.g., 1234567890)", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            VendorDAO vendorDAO = new VendorDAO();
            boolean isAdded = vendorDAO.addVendor(name, description, address, phone);
            if (isAdded) {
                JOptionPane.showMessageDialog(frame, "Vendor added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add vendor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        // --- Navbar setup ---
        JPanel navbarPanel = new JPanel(new BorderLayout()); // Panel to hold the navbar (left and right sections)
        navbarPanel.setBackground(Color.BLACK); // Set background color to match the navbar color
        navbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left side of the navbar
        JLabel logoLabel = new JLabel("VendorConnect");
        logoLabel.setForeground(Color.WHITE); // Set text color to white
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Optional: set font style for the logo/brand name
        logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Make it clickable

        // Add mouse listener to logoLabel to go to the homepage when clicked
        logoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "Home"); // Navigate to "Home" panel
            }
        });

        // Links (How to Use, About Us, Login) on the right
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0)); // Right-aligned links with spacing
        linkPanel.setBackground(Color.BLACK); // Match navbar background color

        // Create clickable JLabel links for navigation
        JLabel howToUseLabel = new JLabel("How to Use");
        JLabel aboutUsLabel = new JLabel("About Us");
        JLabel favoritesLabel = new JLabel("Favorites");
        JLabel loginLabel = new JLabel("Login");

        // Set text color to white for all links
        howToUseLabel.setForeground(Color.WHITE);
        aboutUsLabel.setForeground(Color.WHITE);
        favoritesLabel.setForeground(Color.WHITE);
        loginLabel.setForeground(Color.WHITE);

        // Set hand cursor for each link to indicate clickability
        howToUseLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aboutUsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        favoritesLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add mouse listeners to handle navigation
        howToUseLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "HowToUse"); // Navigate to "How to Use" panel
            }
        });

        aboutUsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "AboutUs"); // Navigate to "About Us" panel
            }
        });
        
        favoritesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "Favorites"); // Navigate to "Favorites" panel
            }
        });

        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cardPanel, "Login"); // Navigate to "Login" panel
            }
        });

        // Add the clickable labels to the link panel
        linkPanel.add(howToUseLabel);
        linkPanel.add(aboutUsLabel);
        linkPanel.add(favoritesLabel);
        linkPanel.add(loginLabel);

        // Add the link panel to the right side of the navbar
        navbarPanel.add(linkPanel, BorderLayout.EAST);

        // Add the logo to the left side of the navbar
        navbarPanel.add(logoLabel, BorderLayout.WEST);

        // Add the navbar to the top of the main frame
        frame.add(navbarPanel, BorderLayout.NORTH);

        
        // --- "How to Use" Page ---
        JPanel howToUsePanel = new JPanel();
        howToUsePanel.setLayout(new BorderLayout());
        howToUsePanel.setBackground(new Color(211, 211, 211)); // Light gray background

        // Create a JTextPane for instructions
        JTextPane instructions = new JTextPane();
        instructions.setContentType("text/plain"); // Set plain text
        instructions.setText(
                "1. **Add a Vendor:**\n" +
                "   - Enter the vendor's name, description, address, and phone number.\n" +
                "   - Click the \"Add Vendor\" button.\n\n" +
                "2. **View Vendors on Map:**\n" +
                "   - The map will display the locations of all added vendors.\n" +
<<<<<<< HEAD
                "   - You can interact with the map to zoom, pan, and get more details about each vendor.\n\n" +
=======
                "   - It will show all the details of the vendor.\n\n" +
>>>>>>> decbef845e4327ae45047c3538e583051a1b3e98
                "3. **Vendor Search:**\n" +
                "   - Use the search bar to find a specific vendor by name or description.\n" +
                "   - The search results will dynamically filter the vendor list as you type.\n" +
                "   - Click on a search result to view more details or perform actions (e.g., Edit or Delete).\n\n" +
                "4. **Edit a Vendor:**\n" +
                "   - Select a vendor from the list.\n" +
                "   - Update the desired fields such as name, description, address, or phone number.\n" +
                "   - Click the \"Update Vendor\" button to save changes.\n\n" +
                "5. **Delete a Vendor:**\n" +
                "   - Select a vendor from the list.\n" +
<<<<<<< HEAD
                "   - Click the \"Delete Vendor\" button to remove the vendor from the system.\n\n"
=======
                "   - Click the \"Delete Vendor\" button to remove the vendor from the system.\n\n" +
>>>>>>> decbef845e4327ae45047c3538e583051a1b3e98
        );
        instructions.setEditable(false); // Make the text area read-only
        instructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding
        instructions.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for better readability

        // Center the text using StyledDocument
        StyledDocument doc = instructions.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Add a scroll pane to the JTextPane
        JScrollPane scrollPane = new JScrollPane(instructions);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scroll pane to the howToUsePanel
        howToUsePanel.add(scrollPane, BorderLayout.CENTER);


        // --- "About Us" Page ---
        JPanel aboutUsPanel = new JPanel();
        aboutUsPanel.setLayout(new BorderLayout());
        howToUsePanel.setBackground(new Color(0, 0, 255)); // Blue

        // Create a JTextArea for about us information
        JTextArea aboutUsText = new JTextArea(
                "Welcome to VendorConnect, your one-stop platform for discovering and connecting with local businesses.\n\n" +
                        "We believe in supporting local economies and empowering businesses to thrive. VendorConnect aims to provide a user-friendly platform for vendors to showcase their services and for customers to find the perfect local businesses that meet their needs.\n\n" +
                        "Our mission is to create a vibrant online community that connects businesses with their ideal customers. We strive to provide a seamless experience for both vendors and customers, making it easy to find, connect, and support local businesses.\n\n" +
                        "We are constantly working to improve VendorConnect and add new features to enhance the user experience. We welcome your feedback and suggestions as we continue to build a better platform for our community.\n\n" +
                        "At VendorConnect, we understand the challenges that local businesses face in a competitive market. Our platform is designed to give you the visibility and tools to stand out, whether you're a small startup or an established business. By listing your services on VendorConnect, you gain access to a broader audience of potential customers who are eager to support local enterprises.\n\n" +
                        "Our team is dedicated to creating a safe and reliable space for both vendors and customers. We prioritize transparency, security, and customer satisfaction, ensuring that every transaction and interaction on our platform is smooth and trustworthy.\n\n" +
                        "Join our growing community of local businesses and customers today! Together, we can help strengthen local economies and make a positive impact in our communities."
        );
        aboutUsText.setEditable(false); // Make the text area read-only
        aboutUsText.setLineWrap(true);
        aboutUsText.setWrapStyleWord(true);
        aboutUsText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add a scroll pane to the JTextArea
        JScrollPane scrollPane1 = new JScrollPane(aboutUsText);

        // Add the scroll pane to the aboutUsPanel
        aboutUsPanel.add(scrollPane1, BorderLayout.CENTER);

        
        // --- "Favorites" Page ---
        JPanel favoritesPanel = new JPanel();
        favoritesPanel.setLayout(new GridLayout(0, 2, 20, 10));

        // Create a VendorDAO object to fetch vendor data
        VendorDAO vendorDAO = new VendorDAO();
        List<Vendor> vendors = vendorDAO.getAllVendors();

        for (Vendor vendor : vendors) {
            JPanel vendorCard = new JPanel();
            vendorCard.setLayout(new BorderLayout());
            vendorCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            vendorCard.setBackground(Color.WHITE);
            vendorCard.setPreferredSize(new Dimension(300, 130));

            // Create labels for each vendor's details
            JLabel nameLabel1 = new JLabel("Name: " + vendor.getName());
            nameLabel1.setFont(new Font("Arial", Font.BOLD, 14));

            JLabel descriptionLabel1 = new JLabel("Description: " + vendor.getDescription());
            descriptionLabel1.setFont(new Font("Arial", Font.PLAIN, 12));

            JLabel addressLabel1 = new JLabel("Address: " + vendor.getAddress());
            addressLabel1.setFont(new Font("Arial", Font.PLAIN, 12));

            JLabel phoneLabel1 = new JLabel("Phone: " + vendor.getPhone());
            phoneLabel1.setFont(new Font("Arial", Font.PLAIN, 12));


            // Add the labels to the vendor card
            JPanel labelsPanel = new JPanel();
            labelsPanel.setLayout(new GridLayout(0, 1, 0, 8));
            labelsPanel.add(nameLabel1);
            labelsPanel.add(descriptionLabel1);
            labelsPanel.add(addressLabel1);
            labelsPanel.add(phoneLabel1);
            vendorCard.add(labelsPanel, BorderLayout.CENTER);

            // Add the vendor card to the favorites panel
            favoritesPanel.add(vendorCard);
        }

        // Add the favorites panel to a scrollable container
        JScrollPane scrollPane11 = new JScrollPane(favoritesPanel);
        scrollPane11.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add scrollPane11 directly to cardPanel
        cardPanel.add(scrollPane11, "Favorites");

        
        // --- "Login" Page ---
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add the login page content here as before, just like in your original code
        JPanel loginPanel1 = new JPanel();
        loginPanel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(5, 5, 5, 5);

        // Signup Panel
        JPanel signupPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        signupPanel.setBorder(BorderFactory.createTitledBorder("Sign Up"));

        JTextField signupUsernameField = new JTextField();
        JTextField signupEmailField = new JTextField();
        JPasswordField signupPasswordField = new JPasswordField();
        JPasswordField signupConfirmPasswordField = new JPasswordField();

        signupPanel.add(new JLabel("Username:"));
        signupPanel.add(signupUsernameField);
        signupPanel.add(new JLabel("Email:"));
        signupPanel.add(signupEmailField);
        signupPanel.add(new JLabel("Password:"));
        signupPanel.add(signupPasswordField);
        signupPanel.add(new JLabel("Confirm Password:"));
        signupPanel.add(signupConfirmPasswordField);

        JButton signupButton = new JButton("Sign Up");
        signupPanel.add(new JLabel()); // Empty label for spacing
        signupPanel.add(signupButton);

        // Login Panel
        JPanel loginFormPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginFormPanel.setBorder(BorderFactory.createTitledBorder("Login"));

        JTextField loginUsernameField = new JTextField();
        JPasswordField loginPasswordField = new JPasswordField();

        loginFormPanel.add(new JLabel("Username:"));
        loginFormPanel.add(loginUsernameField);
        loginFormPanel.add(new JLabel("Password:"));
        loginFormPanel.add(loginPasswordField);

        JButton loginButton = new JButton("Login");
        JButton switchToSignupButton = new JButton("Create Account");

        loginFormPanel.add(switchToSignupButton);
        loginFormPanel.add(loginButton);

        // Action Listeners
        UserDAO userDAO = new UserDAO();

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = signupUsernameField.getText();
                String email = signupEmailField.getText();
                String password = new String(signupPasswordField.getPassword());
                String confirmPassword = new String(signupConfirmPasswordField.getPassword());

                // Validate inputs
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Attempt signup
                boolean signupSuccess = userDAO.signupUser(username, email, password);
                if (signupSuccess) {
                    JOptionPane.showMessageDialog(frame, "Account created successfully!");
                    // Switch to the "Home" panel
                    cardLayout.show(cardPanel, "Home"); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists or signup failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUsernameField.getText();
                String password = new String(loginPasswordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Attempt login
                boolean loginSuccess = userDAO.loginUser(username, password);
                if (loginSuccess) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    // Switch to the "Home" panel
                    cardLayout.show(cardPanel, "Home"); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        switchToSignupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch between login and signup panels
                loginPanel1.removeAll();
                loginPanel1.add(signupPanel);
                loginPanel1.revalidate();
                loginPanel1.repaint();
            }
        });

        // Initial login panel setup
        loginPanel1.add(loginFormPanel);

        // --- Card Layout ---
        cardPanel.add(homePanel, "Home");
        cardPanel.add(howToUsePanel, "HowToUse");
        cardPanel.add(aboutUsPanel, "AboutUs");
        cardPanel.add(favoritesPanel, "Favorites");
        cardPanel.add(loginPanel1, "Login");

        // Add cardPanel to the main frame
        frame.add(cardPanel, BorderLayout.CENTER);

        // Frame setup
        frame.setSize(800, 600);
        frame.setVisible(true);

        // Show the "Home" page by default
        cardLayout.show(cardPanel, "Home");
    }
}