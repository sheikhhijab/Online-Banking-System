import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private Bank bank;
    private JFrame parentFrame;
    private JList<String> userList;

    public AdminDashboard(Bank bank, JFrame parentFrame) {
        this.bank = bank;
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setTitle("Admin Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a scroll pane for the user list
        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        // Initialize the user list
        DefaultListModel<String> listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single selection
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedUsername = userList.getSelectedValue();
                if (selectedUsername != null) {
                    User selectedUser = bank.getUser(selectedUsername);
                    if (selectedUser != null) {
                        showUserDetails(selectedUser);
                    }
                }
            }
        });
        scrollPane.setViewportView(userList);
        updateUserList();

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Button to view all users
        JButton viewAllUsersButton = new JButton("View All Users");
        viewAllUsersButton.addActionListener(e -> viewAllUsers());
        buttonPanel.add(viewAllUsersButton);

        // Button to delete selected user
        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(e -> deleteUser());
        buttonPanel.add(deleteUserButton);

        // Button to logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
        buttonPanel.add(logoutButton);

        // Button to search for a user
        JButton searchUserButton = new JButton("Search User");
        searchUserButton.addActionListener(e -> searchUser());
        buttonPanel.add(searchUserButton);

        // Button to view transaction history
        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.addActionListener(e -> viewTransactionHistory());
        buttonPanel.add(transactionHistoryButton);

        // Button to add a new user
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(e -> addUser());
        buttonPanel.add(addUserButton);

        // Button to change user password
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> changeUserPassword());
        buttonPanel.add(changePasswordButton);

        add(panel);
        setVisible(true);
    }

    // Update the user list
    private void updateUserList() {
        DefaultListModel<String> listModel = (DefaultListModel<String>) userList.getModel();
        listModel.clear();
        for (String username : bank.getAllUsers().keySet()) {
            listModel.addElement(username);
        }
    }

    // View all users
    private void viewAllUsers() {
        JFrame userDetailsFrame = new JFrame("All Users");
        userDetailsFrame.setSize(500, 400);
        JPanel userDetailsPanel = new JPanel(new GridLayout(bank.getAllUsers().size(), 1));

        for (User user : bank.getAllUsers().values()) {
            userDetailsPanel.add(new JLabel("Username: " + user.getUsername()));
            userDetailsPanel.add(new JLabel("Full Name: " + user.getFullName()));
            userDetailsPanel.add(new JLabel("Address: " + user.getAddress()));
            userDetailsPanel.add(new JLabel("Phone Number: " + user.getPhoneNumber()));
            userDetailsPanel.add(new JLabel("Email: " + user.getEmail()));
            userDetailsPanel.add(new JLabel("Account Type: " + user.getAccount().getType()));
            userDetailsPanel.add(new JLabel("Balance: " + user.getAccount().getBalance()));
            userDetailsPanel.add(new JLabel("Transactions: " + user.getAccount().getTransactions()));
            userDetailsPanel.add(new JLabel("-------------------------------"));
        }

        userDetailsFrame.add(new JScrollPane(userDetailsPanel));
        userDetailsFrame.setVisible(true);
    }

    // Delete the selected user
    private void deleteUser() {
        String selectedUsername = userList.getSelectedValue();
        if (selectedUsername != null) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete the user " + selectedUsername + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                bank.removeUser(selectedUsername);
                updateUserList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to display user details
    private void showUserDetails(User user) {
        JFrame userDetailsFrame = new JFrame("User Details: " + user.getUsername());
        userDetailsFrame.setSize(400, 300);
        JPanel userDetailsPanel = new JPanel(new GridLayout(0, 1));

        userDetailsPanel.add(new JLabel("Username: " + user.getUsername()));
        userDetailsPanel.add(new JLabel("Full Name: " + user.getFullName()));
        userDetailsPanel.add(new JLabel("Account Number: " + user.getAccountNumber()));
        userDetailsPanel.add(new JLabel("Address: " + user.getAddress()));
        userDetailsPanel.add(new JLabel("Phone Number: " + user.getPhoneNumber()));
        userDetailsPanel.add(new JLabel("Email: " + user.getEmail()));
        userDetailsPanel.add(new JLabel("Account Type: " + user.getAccount().getType()));
        userDetailsPanel.add(new JLabel("Balance: " + user.getAccount().getBalance()));
        userDetailsPanel.add(new JLabel("Transactions: " + user.getAccount().getTransactions()));

        userDetailsFrame.add(new JScrollPane(userDetailsPanel));
        userDetailsFrame.setVisible(true);
    }

    // Method to search for a user
    private void searchUser() {
        String username = JOptionPane.showInputDialog(this, "Enter username to search:");
        if (username != null && !username.isEmpty()) {
            User user = bank.getUser(username);
            if (user != null) {
                showUserDetails(user);
            } else {
                JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to view transaction history
    private void viewTransactionHistory() {
        String username = userList.getSelectedValue();
        if (username != null) {
            User user = bank.getUser(username);
            if (user != null) {
                JFrame transactionFrame = new JFrame("Transaction History: " + user.getUsername());
                transactionFrame.setSize(400, 300);
                JPanel transactionPanel = new JPanel(new GridLayout(0, 1));

                for (String transaction : user.getAccount().getTransactions()) {
                    transactionPanel.add(new JLabel(transaction));
                }

                transactionFrame.add(new JScrollPane(transactionPanel));
                transactionFrame.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to view transactions.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to add a new user
    private void addUser() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField fullNameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField accountTypeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Account Type:"));
        panel.add(accountTypeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String fullName = fullNameField.getText();
            String address = addressField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String accountType = accountTypeField.getText();

            if (bank.register(username, password, fullName, address, phoneNumber, email, accountType)) {
                updateUserList();
                JOptionPane.showMessageDialog(this, "User added successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to change a user's password
    private void changeUserPassword() {
        String selectedUsername = userList.getSelectedValue();
        if (selectedUsername != null) {
            User user = bank.getUser(selectedUsername);
            if (user != null) {
                String newPassword = JOptionPane.showInputDialog(this, "Enter new password for user " + selectedUsername + ":");
                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(newPassword);
                    JOptionPane.showMessageDialog(this, "Password changed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to change their password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bank bank = new Bank();
            JFrame parentFrame = new JFrame("Parent Frame");
            parentFrame.setSize(600, 400);
            parentFrame.setLocationRelativeTo(null);
            parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            parentFrame.setVisible(true);

            AdminDashboard adminDashboard = new AdminDashboard(bank, parentFrame);
            adminDashboard.setVisible(true);
        });
    }
}