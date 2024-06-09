import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {
    private static Bank bank;
    private static Admin admin = new Admin("admin", "admin");
    private static final String DATA_FILE = "bank_data.ser";

    public static void main(String[] args) {
        try {
            bank = Bank.loadFromFile(DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            bank = new Bank();
        }

        SwingUtilities.invokeLater(Main::createAndShowGUI);

        // Add a shutdown hook to save data when the application exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                bank.saveToFile(DATA_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Online Banking Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // User section
        JLabel userLabel = new JLabel("User");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        JButton userLoginButton = new JButton("User Login");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(userLoginButton, gbc);
        userLoginButton.addActionListener(e -> showUserLogin());

        JButton userRegisterButton = new JButton("User Register");
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(userRegisterButton, gbc);
        userRegisterButton.addActionListener(e -> RegistrationForm.showRegistrationForm());

        // Admin section
        JLabel adminLabel = new JLabel("Admin");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(adminLabel, gbc);

        JButton adminLoginButton = new JButton("Admin Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(adminLoginButton, gbc);
        adminLoginButton.addActionListener(e -> showAdminLogin());



    }

    private static void showUserLogin() {
        JFrame frame = new JFrame("User Login");
        frame.setSize(300, 200);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeUserLoginComponents(panel, frame);
        frame.setVisible(true);
    }

    private static void placeUserLoginComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                User user = Main.getBank().getUser(username); // Use getBank() method
                if (user != null && user.getPassword().equals(password)) {
                    UserDashboard.show(user); // Show user dashboard
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static void showAdminLogin() {
        JFrame frame = new JFrame("Admin Login");
        frame.setSize(300, 200);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeAdminLoginComponents(panel, frame);
        frame.setVisible(true);
    }

    private static void placeAdminLoginComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (admin.login(username, password)) {
                    showAdminMenu();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid admin credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static void showAdminMenu() {
        JFrame frame = new JFrame("Admin Menu");
        frame.setSize(400, 300);
        AdminDashboard adminDashboard = new AdminDashboard(Main.getBank(), frame); // Use getBank() method
        adminDashboard.setVisible(true);
    }

    // Add a public static method to get the bank instance
    public static Bank getBank() {
        return bank;
    }
}
