import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank implements Serializable {
    private Map<String, User> users = new HashMap<>();

    // Register a new user
    public boolean register(String username, String password, String fullName, String address, String phoneNumber, String email, String accountType) {
        if (users.containsKey(username)) {
            return false;
        }
        String accountNumber = generateAccountNumber();
        User newUser = new User(username, password, fullName, address, phoneNumber, email, accountType, accountNumber);
        users.put(username, newUser);
        return true;
    }

    // Generate a random 14-digit account number
    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    // Retrieve a user by username
    public User getUser(String username) {
        return users.get(username);
    }
    
    // Retrieve a user by account number
    public User getUserByAccountNumber(String accountNumber) {
        for (User user : users.values()) {
            if (user.getAccount().getAccountNumber().equals(accountNumber)) {
                return user;
            }
        }
        return null; // If no user found with the given account number
    }

    // Remove a user by username
    public void removeUser(String username) {
        users.remove(username);
    }

    // Retrieve all users
    public Map<String, User> getAllUsers() {
        return users;
    }

    // Save the bank data to a file
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    // Load the bank data from a file
    public static Bank loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Bank) in.readObject();
        }
    }
}
