import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationForm {
    public static void showRegistrationForm() {
        JFrame frame = new JFrame("User Register");
        frame.setSize(300, 380); // Adjusted size to fit all components
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, frame);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        // Registration form components
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

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(10, 80, 80, 25);
        panel.add(fullNameLabel);

        JTextField fullNameText = new JTextField(20);
        fullNameText.setBounds(100, 80, 160, 25);
        panel.add(fullNameText);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 110, 80, 25);
        panel.add(addressLabel);

        JTextField addressText = new JTextField(20);
        addressText.setBounds(100, 110, 160, 25);
        panel.add(addressText);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(10, 140, 100, 25);
        panel.add(phoneNumberLabel);

        JTextField phoneNumberText = new JTextField(20);
        phoneNumberText.setBounds(100, 140, 160, 25);
        panel.add(phoneNumberText);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 170, 80, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(20);
        emailText.setBounds(100, 170, 160, 25);
        panel.add(emailText);

        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(10, 200, 100, 25);
        panel.add(accountTypeLabel);

        String[] accountTypes = {"Savings", "Current"};
        JComboBox<String> accountTypeDropdown = new JComboBox<>(accountTypes);
        accountTypeDropdown.setBounds(100, 200, 160, 25);
        panel.add(accountTypeDropdown);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 260, 100, 25);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                String fullName = fullNameText.getText();
                String address = addressText.getText();
                String phoneNumber = phoneNumberText.getText();
                String email = emailText.getText();
                String accountType = (String) accountTypeDropdown.getSelectedItem();

                // Check if any field is empty
                if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || address.isEmpty() ||
                    phoneNumber.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit method
                }

                // Check password length
                if (password.length() < 5 || password.length() > 10) {
                    JOptionPane.showMessageDialog(frame, "Password must be 5-10 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit method
                }

                // Check if phone number is numeric
                if (!isNumeric(phoneNumber)) {
                    JOptionPane.showMessageDialog(frame, "Phone number must contain only digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit method
                }

                // Check if email is in proper format
                if (!isValidEmailAddress(email)) {
                    JOptionPane.showMessageDialog(frame, "Invalid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit method
                }

                // Now proceed with registration
                Bank bank = Main.getBank(); // Access bank object
                boolean registered = bank.register(username, password, fullName, address, phoneNumber, email, accountType);
                if (registered) {
                    User newUser = bank.getUser(username); // Retrieve the newly registered user
                    showAccountNumberDialog(frame, newUser.getAccountNumber());
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Checking Email
            private boolean isValidEmailAddress(String email) {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                return email.matches(emailRegex);
            }

            // Method to check if string is numeric
            private boolean isNumeric(String str) {
                return str.matches("\\d+");
            }
        });
    }

    private static void showAccountNumberDialog(JFrame parentFrame, String accountNumber) {
        JDialog dialog = new JDialog(parentFrame, "Registration Successful", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Registration successful. Your account number is:");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(messageLabel, BorderLayout.NORTH);

        JTextField accountNumberField = new JTextField(accountNumber);
        accountNumberField.setEditable(false);
        accountNumberField.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(accountNumberField, BorderLayout.CENTER);

        JButton copyButton = new JButton("Copy Account Number");
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(accountNumber);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(dialog, "Account number copied to clipboard.", "Copied", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        dialog.add(copyButton, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
}
