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
        new LoginGUI();
    }

    private static class LoginGUI extends JFrame {

        public LoginGUI() {
            initComponents();
        }

        private void initComponents() {
            setTitle("Login");
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
            gbc.gridy = 0;
            panel.add(usernameField, gbc);

            JLabel passwordLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(passwordLabel, gbc);

            JPasswordField passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(passwordField, gbc);

            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    if (validateLogin(username, password)) {
                        openDashboard();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    }
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            panel.add(loginButton, gbc);

            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openRegisterGUI();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            panel.add(registerButton, gbc);

            getContentPane().add(panel);
            setVisible(true);
        }

        private boolean validateLogin(String username, String password) {
            Map<String, String> users = readUsersFromFile();
            return users.containsKey(username) && users.get(username).equals(password);
        }

        private Map<String, String> readUsersFromFile() {
            Map<String, String> users = new HashMap<>();
            File file = new File("users.txt");

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    users.put(parts[0], parts[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return users;
        }

        private void openRegisterGUI() {
            dispose();
            new RegisterGUI();
        }

        private void openDashboard() {
            dispose();
            new DashboardGUI();
        }
    }

    private static class RegisterGUI extends JFrame {

        public RegisterGUI() {
            initComponents();
        }

        private void initComponents() {
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
            gbc.gridy = 0;
            panel.add(usernameField, gbc);

            JLabel passwordLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(passwordLabel, gbc);

            JPasswordField passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(passwordField, gbc);

            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    if (validateRegistration(username, password)) {
                        registerUser(username, password);
                        JOptionPane.showMessageDialog(null, "Registration successful.");
                        openLoginGUI();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                    }
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            panel.add(registerButton, gbc);

            getContentPane().add(panel);
            setVisible(true);
        }

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

        private void registerUser(String username, String password) {
            Map<String, String> users = readUsersFromFile();
            users.put(username, password);
            writeUsersToFile(users);
        }

        private static HashMap<String, String> readUsersFromFile() {
            HashMap<String, String> users = new HashMap<>();
            File file = new File("users.txt");

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    users.put(parts[0], parts[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return users;
        }

        private void writeUsersToFile(Map<String, String> users) {
            File file = new File("users.txt");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Map.Entry<String, String> entry : users.entrySet()) {
                    bw.write(entry.getKey() + ":" + entry.getValue());
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private HashMap<String, String> getUsers() {
            HashMap<String, String> users = new HashMap<>();
            try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    users.put(parts[0], parts[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users;
        }

        private void openLoginGUI() {
            dispose();
            new LoginGUI();
        }
    }
    private static class DashboardGUI extends JFrame {

        public DashboardGUI() {
            initComponents();
        }

        private void initComponents() {
            setTitle("Dashboard");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JButton logoutButton = new JButton("Logout");
            logoutButton.setPreferredSize(new Dimension(80, 30)); // Set the preferred size of the button
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openLoginGUI();
                }
            });
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(logoutButton);

            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            setVisible(true);
        }

        private void openLoginGUI() {
            dispose();
            new LoginRegisterGUI.LoginGUI();
        }
    }


}