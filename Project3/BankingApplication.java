import java.io.*;
import java.util.*;

// Main Banking Application Class
public class BankingApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   WELCOME TO JAVA BANK");
        System.out.println("=================================\n");
        
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Create New Account");
            System.out.println("2. Login to Account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("\nThank you for using Java Bank. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void createAccount() {
        System.out.println("\n--- CREATE NEW ACCOUNT ---");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter account number (6 digits): ");
        String accountNumber = scanner.nextLine();
        
        if (!accountNumber.matches("\\d{6}")) {
            System.out.println("Error: Account number must be 6 digits.");
            return;
        }
        
        System.out.print("Create a PIN (4 digits): ");
        String pin = scanner.nextLine();
        
        if (!pin.matches("\\d{4}")) {
            System.out.println("Error: PIN must be 4 digits.");
            return;
        }
        
        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = getDoubleInput();
        
        if (initialDeposit < 0) {
            System.out.println("Error: Initial deposit cannot be negative.");
            return;
        }
        
        Account account = new Account(accountNumber, name, pin, initialDeposit);
        if (bank.addAccount(account)) {
            System.out.println("\nAccount created successfully!");
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Account Holder: " + name);
            System.out.println("Initial Balance: $" + String.format("%.2f", initialDeposit));
        } else {
            System.out.println("Error: Account number already exists.");
        }
    }
    
    private static void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        
        Account account = bank.authenticate(accountNumber, pin);
        
        if (account != null) {
            System.out.println("\nLogin successful! Welcome, " + account.getAccountHolderName());
            accountMenu(account);
        } else {
            System.out.println("Error: Invalid account number or PIN.");
        }
    }
    
    private static void accountMenu(Account account) {
        while (true) {
            System.out.println("\n--- ACCOUNT MENU ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Transaction History");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    checkBalance(account);
                    break;
                case 2:
                    deposit(account);
                    break;
                case 3:
                    withdraw(account);
                    break;
                case 4:
                    viewTransactionHistory(account);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void checkBalance(Account account) {
        System.out.println("\n--- BALANCE INQUIRY ---");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder: " + account.getAccountHolderName());
        System.out.println("Current Balance: $" + String.format("%.2f", account.getBalance()));
    }
    
    private static void deposit(Account account) {
        System.out.println("\n--- DEPOSIT MONEY ---");
        System.out.print("Enter amount to deposit: ");
        double amount = getDoubleInput();
        
        if (account.deposit(amount)) {
            System.out.println("Deposit successful!");
            System.out.println("New Balance: $" + String.format("%.2f", account.getBalance()));
            bank.saveAccounts();
        } else {
            System.out.println("Error: Invalid deposit amount.");
        }
    }
    
    private static void withdraw(Account account) {
        System.out.println("\n--- WITHDRAW MONEY ---");
        System.out.print("Enter amount to withdraw: ");
        double amount = getDoubleInput();
        
        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful!");
            System.out.println("New Balance: $" + String.format("%.2f", account.getBalance()));
            bank.saveAccounts();
        } else {
            System.out.println("Error: Insufficient funds or invalid amount.");
        }
    }
    
    private static void viewTransactionHistory(Account account) {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        List<String> history = account.getTransactionHistory();
        
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : history) {
                System.out.println(transaction);
            }
        }
    }
    
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private static double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid amount: ");
            }
        }
    }
}

// Account Class
class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String accountHolderName;
    private String pin;
    private double balance;
    private List<String> transactionHistory;
    
    public Account(String accountNumber, String accountHolderName, String pin, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created with initial deposit: $" + String.format("%.2f", initialDeposit));
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }
    
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        addTransaction("Deposited: $" + String.format("%.2f", amount) + " | Balance: $" + String.format("%.2f", balance));
        return true;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        addTransaction("Withdrew: $" + String.format("%.2f", amount) + " | Balance: $" + String.format("%.2f", balance));
        return true;
    }
    
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
    
    private void addTransaction(String transaction) {
        String timestamp = new Date().toString();
        transactionHistory.add("[" + timestamp + "] " + transaction);
    }
}

// Bank Class
class Bank {
    private Map<String, Account> accounts;
    private static final String DATA_FILE = "bank_data.dat";
    
    public Bank() {
        accounts = new HashMap<>();
        loadAccounts();
    }
    
    public boolean addAccount(Account account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            return false;
        }
        accounts.put(account.getAccountNumber(), account);
        saveAccounts();
        return true;
    }
    
    public Account authenticate(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        if (account != null && account.verifyPin(pin)) {
            return account;
        }
        return null;
    }
    
    public void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error saving account data: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadAccounts() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                accounts = (Map<String, Account>) ois.readObject();
                System.out.println("Loaded " + accounts.size() + " existing account(s).");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Starting with fresh account database.");
                accounts = new HashMap<>();
            }
        }
    }
}