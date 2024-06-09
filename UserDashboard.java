import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDashboard {
    public static void show(User user) {
        JFrame frame = new JFrame("User Menu");
        frame.setSize(400, 300);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeUserMenuComponents(panel, user, frame);
        frame.setVisible(true);
    }

    private static void placeUserMenuComponents(JPanel panel, User user, JFrame frame) {
        panel.setLayout(null);

        JButton transferButton = new JButton("Transfer Money");
        transferButton.setBounds(10, 20, 150, 25);
        panel.add(transferButton);
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTransferMoney(user);
            }
        });

        JButton viewStatementsButton = new JButton("View Statements");
        viewStatementsButton.setBounds(10, 60, 150, 25);
        panel.add(viewStatementsButton);
        viewStatementsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showViewStatements(user);
            }
        });

        JButton enterBalanceButton = new JButton("Enter Balance");
        enterBalanceButton.setBounds(10, 100, 150, 25);
        panel.add(enterBalanceButton);
        enterBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterBalance(user);
            }
        });

        JButton utilityPaymentButton = new JButton("Utility Payment");
        utilityPaymentButton.setBounds(10, 140, 150, 25);
        panel.add(utilityPaymentButton);
        utilityPaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUtilityPayment(user);
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 180, 150, 25);
        panel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    private static void showTransferMoney(User user) {
        JFrame frame = new JFrame("Transfer Money");
        frame.setSize(300, 250); // Adjusted size to fit all components
        JPanel panel = new JPanel();
        frame.add(panel);
        placeTransferMoneyComponents(panel, user, frame);
        frame.setVisible(true);
    }

    private static void placeTransferMoneyComponents(JPanel panel, User user, JFrame frame) {
        panel.setLayout(null);

        JLabel bankLabel = new JLabel("Select Bank:");
        bankLabel.setBounds(10, 20, 150, 25);
        panel.add(bankLabel);

        String[] banks = {"HabibMetropolitan", "Meezan Bank", "Al Habib Bank"};
        JComboBox<String> bankDropdown = new JComboBox<>(banks);
        bankDropdown.setBounds(160, 20, 120, 25);
        panel.add(bankDropdown);

        JLabel recipientAccountLabel = new JLabel("Recipient Account No:");
        recipientAccountLabel.setBounds(10, 60, 150, 25);
        panel.add(recipientAccountLabel);

        JTextField recipientAccountText = new JTextField(20);
        recipientAccountText.setBounds(160, 60, 120, 25);
        panel.add(recipientAccountText);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 100, 80, 25);
        panel.add(amountLabel);

        JTextField amountText = new JTextField(20);
        amountText.setBounds(160, 100, 120, 25);
        panel.add(amountText);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(10, 140, 100, 25);
        panel.add(transferButton);
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedBank = (String) bankDropdown.getSelectedItem();
                String recipientAccountNo = recipientAccountText.getText();
                double amount;
                try {
                    amount = Double.parseDouble(amountText.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                User recipient = Main.getBank().getUserByAccountNumber(recipientAccountNo);
                if (recipient != null) {
                    if (user.getAccount().withdraw(amount)) {
                        recipient.getAccount().deposit(amount);
                        JOptionPane.showMessageDialog(frame, "Transfer to " + selectedBank + " successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Recipient not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static void showViewStatements(User user) {
        JFrame frame = new JFrame("View Statements");
        frame.setSize(300, 200);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeViewStatementsComponents(panel, user, frame);
        frame.setVisible(true);
    }

    private static void placeViewStatementsComponents(JPanel panel, User user, JFrame frame) {
        panel.setLayout(null);

        JTextArea statementsArea = new JTextArea();
        statementsArea.setBounds(10, 20, 260, 130);
        panel.add(statementsArea);

        StringBuilder statements = new StringBuilder();
        for (String transaction : user.getAccount().getTransactions()) {
            statements.append(transaction).append("\n");
        }
        statementsArea.setText(statements.toString());
    }

    private static void enterBalance(User user) {
        JFrame frame = new JFrame("Enter Balance");
        frame.setSize(300, 200);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeEnterBalanceComponents(panel, user, frame);
        frame.setVisible(true);
    }

    private static void placeEnterBalanceComponents(JPanel panel, User user, JFrame frame) {
        panel.setLayout(null);

        JLabel balanceLabel = new JLabel("Enter Balance:");
        balanceLabel.setBounds(10, 20, 100, 25);
        panel.add(balanceLabel);

        JTextField balanceText = new JTextField(20);
        balanceText.setBounds(120, 20, 150, 25);
        panel.add(balanceText);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(10, 60, 100, 25);
        panel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double balance;
                try {
                    balance = Double.parseDouble(balanceText.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid balance amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                user.getAccount().setBalance(balance);
                JOptionPane.showMessageDialog(frame, "Balance updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });
    }

    private static void showUtilityPayment(User user) {
        JFrame frame = new JFrame("Utility Payment");
        frame.setSize(350, 250);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeUtilityPaymentComponents(panel, user, frame);
        frame.setVisible(true);
    }

    private static void placeUtilityPaymentComponents(JPanel panel, User user, JFrame frame) {
        panel.setLayout(null);

        JLabel typeLabel = new JLabel("Payment Type:");
        typeLabel.setBounds(10, 20, 150, 25);
        panel.add(typeLabel);

        String[] types = {"Educational", "Utilities Bill"};
        JComboBox<String> typeDropdown = new JComboBox<>(types);
        typeDropdown.setBounds(160, 20, 150, 25);
        panel.add(typeDropdown);

        JLabel accountLabel = new JLabel("Account No:");
        accountLabel.setBounds(10, 60, 150, 25);
        panel.add(accountLabel);

        JTextField accountText = new JTextField(20);
        accountText.setBounds(160, 60, 150, 25);
        panel.add(accountText);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 100, 150, 25);
        panel.add(amountLabel);

        JTextField amountText = new JTextField(20);
        amountText.setBounds(160, 100, 150, 25);
        panel.add(amountText);

        JButton payButton = new JButton("Pay");
        payButton.setBounds(10, 140, 100, 25);
        panel.add(payButton);
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String paymentType = (String) typeDropdown.getSelectedItem();
                String accountNo = accountText.getText();
                double amount;
                try {
                    amount = Double.parseDouble(amountText.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Assuming no specific recipient for utility payments in this context.
                if (user.getAccount().withdraw(amount)) {
                    JOptionPane.showMessageDialog(frame, paymentType + " payment of " + amount + " to account " + accountNo + " successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
