import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String email;
    private String accountType;
    private String accountNumber;
    private Account account;

    public User(String username, String password, String fullName, String address, String phoneNumber, String email, String accountType, String accountNumber) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.account = new Account(accountType, accountNumber); // Pass both accountType and accountNumber
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Account getAccount() {
        return account;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}