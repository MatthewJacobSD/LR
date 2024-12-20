import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SimpleLoginRegister {

    private static final HashMap<String, String> users = new HashMap<>();

    public static void main(String[] args) {
        // Initialize with default user
        users.put("username", "password123");
        new LoginGUI();
    }

    //Login window
    static class LoginGUI extends JFrame {
        public LoginGUI() {
            setTitle("Login");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            //Username
            JLabel usernameLabel = new JLabel("Username:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(usernameLabel, gbc);

            JTextField usernameField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(usernameField, gbc);

            //Password
            JLabel passwordLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(passwordLabel, gbc);

            JPasswordField passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            panel.add(passwordField, gbc);

            //Login button
            JButton loginButton = new JButton("Login");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            panel.add(loginButton, gbc);

            loginButton.addActionListener(_ -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                //Validation check, open dashboard if true
                if (users.containsKey(username) && users.get(username).equals(password)) {
                    new DashboardGUI(username);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            //Register button
            JButton registerButton = new JButton("Register");
            gbc.gridy = 3;
            panel.add(registerButton, gbc);

            registerButton.addActionListener(_ -> {
                new RegisterGUI();
                dispose();
            });

            add(panel);
            setVisible(true);
        }
    }

    //Register Window
    static class RegisterGUI extends JFrame {
        public RegisterGUI() {
            setTitle("Register");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel usernameLabel = new JLabel("Username:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(usernameLabel, gbc);

            JTextField usernameField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(usernameField, gbc);

            JLabel passwordLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(passwordLabel, gbc);

            JPasswordField passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            panel.add(passwordField, gbc);

            JButton registerButton = new JButton("Register");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            panel.add(registerButton, gbc);

            registerButton.addActionListener(_ -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                //Validation check
                if (!users.containsKey(username)) {
                    users.put(username, password);
                    JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new LoginGUI();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            add(panel);
            setVisible(true);
        }
    }

    //Dashboard window
    static class DashboardGUI extends JFrame {
        public DashboardGUI(String username) {
            setTitle("Dashboard");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            add(welcomeLabel, BorderLayout.CENTER);

            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(_ -> {
                new LoginGUI();
                dispose();
            });
            add(logoutButton, BorderLayout.SOUTH);

            setVisible(true);
        }
    }
}
