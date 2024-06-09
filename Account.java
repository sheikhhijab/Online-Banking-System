import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private String type;
    private double balance;
    private String accountNumber; // New field for account number
    private List<String> transactions;

    public Account(String type, String accountNumber) {
        this.type = type;
        this.accountNumber = accountNumber; // Initialize account number
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        addTransaction("Set Balance: " + balance);
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void addTransaction(String transaction) {
        transactions.add(transaction);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            addTransaction("Withdraw: " + amount);
            return true;
        } else {
            return false;
        }
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposit: " + amount);
    }

    // Getter and Setter for accountNumber
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
