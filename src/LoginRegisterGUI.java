import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginRegisterGUI {

    public static void main(String[] args) {
        new LoginGUI(); // Start the application by creating a new LoginGUI instance
    }

    // LoginGUI class for the login functionality
    private static class LoginGUI extends JFrame {

        public LoginGUI() {
            initComponents(); // Initialize components
        }

        private void initComponents() {
            setTitle("Login"); // Set the title of the window
            setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close operation
            setSize(400, 300); // Set window size
            setLocationRelativeTo(null); // Center window on screen

            // Panel to hold components using GridBagLayout for positioning
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints(); // Constraints for layout
            gbc.insets = new Insets(10, 10, 10, 10); // Padding
            gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally

            // Username label
            JLabel usernameLabel = new JLabel("Username:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(usernameLabel, gbc);

            // Username text field
            JTextField usernameField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(usernameField, gbc);

            // Password label
            JLabel passwordLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(passwordLabel, gbc);

            // Password field
            JPasswordField passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(passwordField, gbc);

            // Login button
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Action performed when login button is clicked
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    // Validate login credentials
                    if (validateLogin(username, password)) {
                        openDashboard(); // Open dashboard if login successful
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password."); // Show error message
                    }
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            panel.add(loginButton, gbc);

            // Register button
            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openRegisterGUI(); // Open register GUI when clicked
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(registerButton, gbc);

            getContentPane().add(panel); // Add panel to content pane
            setVisible(true); // Set window visible
        }

        // Method to validate login credentials
        private boolean validateLogin(String username, String password) {
            Map<String, String> users = readUsersFromFile(); // Read users from file
            return users.containsKey(username) && users.get(username).equals(password); // Check if username and password match
        }

        // Method to read users from file and return as a map
        private Map<String, String> readUsersFromFile() {
            Map<String, String> users = new HashMap<>(); // Map to hold users
            File file = new File("users.txt"); // File containing user data

            try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Read file using BufferedReader
                String line;
                while ((line = br.readLine()) != null) { // Read each line
                    String[] parts = line.split(":"); // Split line into username and password
                    users.put(parts[0], parts[1]); // Add to map
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle IO exception
            }

            return users; // Return map of users
        }

        // Method to open the register GUI
        private void openRegisterGUI() {
            dispose(); // Dispose current window
            new RegisterGUI(); // Open new register GUI
        }

        // Method to open the dashboard GUI
        private void openDashboard() {
            dispose(); // Dispose current window
            new DashboardGUI(); // Open dashboard GUI
        }
    }

    // RegisterGUI class for user registration
    private static class RegisterGUI extends JFrame {

        public RegisterGUI() {
            initComponents(); // Initialize components
        }

        private void initComponents() {
            setTitle("Register"); // Set title of window
            setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close operation
            setSize(400, 300); // Set window size
            setLocationRelativeTo(null); // Center window on screen

            // Panel to hold components using GridBagLayout for positioning
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints(); // Constraints for layout
            gbc.insets = new Insets(10, 10, 10, 10); // Padding
            gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally

            // Username label
            JLabel usernameLabel = new JLabel("Username:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(usernameLabel, gbc);

            // Username text field
            JTextField usernameField = new JTextField(15);
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(usernameField, gbc);

            // Password label
            JLabel passwordLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(passwordLabel, gbc);

            // Password field
            JPasswordField passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(passwordField, gbc);

            // Register button
            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Action performed when register button is clicked
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    // Validate registration input
                    if (validateRegistration(username, password)) {
                        registerUser(username, password); // Register user
                        JOptionPane.showMessageDialog(null, "Registration successful."); // Show success message
                        openLoginGUI(); // Open login GUI after registration
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please try again."); // Show error message
                    }
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            panel.add(registerButton, gbc);

            getContentPane().add(panel); // Add panel to content pane
            setVisible(true); // Set window visible
        }

        // Method to validate registration input
        private boolean validateRegistration(String username, String password) {
            // Check if the username is unique
            if (getUsers().containsKey(username)) {
                return false;
            }

            // Check if the password is at least 8 characters long
            if (password.length() < 8) {
                return false;
            }

            // Check if the password contains at least one digit
            if (!password.matches(".*\\d.*")) {
                return false;
            }

            // Check if the password contains at least one lowercase letter
            if (!password.matches(".*[a-z].*")) {
                return false;
            }

            // Check if the password contains at least one uppercase letter
            return password.matches(".*[A-Z].*");
        }

        // Method to register a new user
        private void registerUser(String username, String password) {
            Map<String, String> users = readUsersFromFile(); // Read users from file
            users.put(username, password); // Add new user
            writeUsersToFile(users); // Write updated users to file
        }

        // Method to read users from file and return as a map
        private static HashMap<String, String> readUsersFromFile() {
            HashMap<String, String> users = new HashMap<>(); // Map to hold users
            File file = new File("users.txt"); // File containing user data

            try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Read file using BufferedReader
                String line;
                while ((line = br.readLine()) != null) { // Read each line
                    String[] parts = line.split(":"); // Split line into username and password
                    users.put(parts[0], parts[1]); // Add to map
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle IO exception
            }

            return users; // Return map of users
        }

        // Method to write users to file
        private void writeUsersToFile(Map<String, String> users) {
            File file = new File("users.txt"); // File containing user data

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) { // Write to file using BufferedWriter
                for (Map.Entry<String, String> entry : users.entrySet()) { // Iterate over users
                    bw.write(entry.getKey() + ":" + entry.getValue()); // Write username and password
                    bw.newLine(); // Write newline
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle IO exception
            }
        }

        // Method to get users from file
        private HashMap<String, String> getUsers() {
            HashMap<String, String> users = new HashMap<>(); // Map to hold users
            try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) { // Read file using BufferedReader
                String line;
                while ((line = br.readLine()) != null) { // Read each line
                    String[] parts = line.split(":"); // Split line into username and password
                    users.put(parts[0], parts[1]); // Add to map
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle IO exception
            }
            return users; // Return map of users
        }

        // Method to open the login GUI
        private void openLoginGUI() {
            dispose(); // Dispose current window
            new LoginGUI(); // Open login GUI
        }
    }

    // DashboardGUI class for the dashboard functionality
    private static class DashboardGUI extends JFrame {

        public DashboardGUI() {
            initComponents(); // Initialize components
        }

        private void initComponents() {
            setTitle("Dashboard"); // Set title of window
            setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close operation
            setSize(400, 300); // Set window size
            setLocationRelativeTo(null); // Center window on screen

            // Logout button
            JButton logoutButton = new JButton("Logout");
            logoutButton.setPreferredSize(new Dimension(80, 30)); // Set the preferred size of the button
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openLoginGUI(); // Open login GUI when clicked
                }
            });
            JPanel buttonPanel = new JPanel(new FlowLayout()); // Panel to hold logout button
            buttonPanel.add(logoutButton); // Add logout button to panel

            getContentPane().add(buttonPanel, BorderLayout.SOUTH); // Add button panel to content pane
            setVisible(true); // Set window visible
        }

        // Method to open the login GUI
        private void openLoginGUI() {
            dispose(); // Dispose current window
            new LoginGUI(); // Open login GUI
        }
    }
}
