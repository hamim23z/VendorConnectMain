package view;

import dao.VendorDAO;
import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {

    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Vendor Connect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

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
        inputPanel.setLayout(new GridLayout(2, 4, 10, 10)); // 2 rows, 4 columns, with 10px gaps

        // Add input fields with labels side by side
        JLabel nameLabel = new JLabel("Vendor Name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Vendor Description:");
        JTextField descriptionField = new JTextField();

        JLabel addressLabel = new JLabel("Vendor Address:");
        JTextField addressField = new JTextField();

        JLabel phoneLabel = new JLabel("Vendor Phone:");
        JTextField phoneField = new JTextField();

        // Add components to input panel in 2x2 layout
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);

        // Create a panel for the Add Vendor button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Vendor");
        buttonPanel.add(addButton);

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

        // Add the inputPanel and buttonPanel to a parent panel
        JPanel homeInputPanel = new JPanel();
        homeInputPanel.setLayout(new BorderLayout(10, 10));
        homeInputPanel.add(inputPanel, BorderLayout.CENTER);
        homeInputPanel.add(buttonPanel, BorderLayout.SOUTH);

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
        homePanel.add(homeInputPanel, BorderLayout.SOUTH);
        homePanel.add(mapPanel, BorderLayout.CENTER);
        // --- End of homepage setup ---

        // Create the "How to Use" page
        JPanel howToUsePanel = new JPanel();
        howToUsePanel.setLayout(new BorderLayout());

        // Create a JTextArea for instructions
        JTextArea instructions = new JTextArea(
                "1. **Add a Vendor:**\n" +
                "   - Enter the vendor's name, description, address, and phone number.\n" +
                "   - Click the \"Add Vendor\" button.\n\n" +
                "2. **View Vendors on Map:**\n" +
                "   - (Map functionality to be implemented later)\n" +
                "   - The map will display the locations of all added vendors.\n" +
                "   - You can interact with the map to zoom, pan, and get more details about each vendor.\n\n" +
                "3. **Other Features:**\n" +
                "   - (List future features here, e.g., vendor search, filtering, etc.)");
        instructions.setEditable(false); // Make the text area read-only
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding

        // Add a scroll pane to the JTextArea
        JScrollPane scrollPane = new JScrollPane(instructions);

        // Add the scroll pane to the howToUsePanel
        howToUsePanel.add(scrollPane, BorderLayout.CENTER);
 

        JPanel aboutUsPanel = new JPanel();
        aboutUsPanel.setLayout(new BorderLayout());

        // Create a JTextArea for about us information
        JTextArea aboutUsText = new JTextArea(
                "Welcome to VendorConnect, your one-stop platform for discovering and connecting with local businesses.\n\n" +
                "We believe in supporting local economies and empowering businesses to thrive. VendorConnect aims to provide a user-friendly platform for vendors to showcase their services and for customers to find the perfect local businesses that meet their needs.\n\n" +
                "Our mission is to create a vibrant online community that connects businesses with their ideal customers. We strive to provide a seamless experience for both vendors and customers, making it easy to find, connect, and support local businesses.\n\n" +
                "We are constantly working to improve VendorConnect and add new features to enhance the user experience. We welcome your feedback and suggestions as we continue to build a better platform for our community.");
        aboutUsText.setEditable(false); // Make the text area read-only
        aboutUsText.setLineWrap(true);
        aboutUsText.setWrapStyleWord(true);
        aboutUsText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding

        // Add a scroll pane to the JTextArea
        JScrollPane scrollPane1 = new JScrollPane(aboutUsText);

        // Add the scroll pane to the aboutUsPanel
        aboutUsPanel.add(scrollPane1, BorderLayout.CENTER);

        
        
        
        // Create the "Login" page
     // Inside the main method, replace the login panel creation with:
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

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
                loginPanel.removeAll();
                loginPanel.add(signupPanel);
                loginPanel.revalidate();
                loginPanel.repaint();
            }
        });

        // Initial login panel setup
        loginPanel.add(loginFormPanel);

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