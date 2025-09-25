package Advanced;

import java.util.*;

// ===== Customer Class =====
class Customer {
    private String customerId;
    private String name;
    private String encryptedPassword;
    private double mainBalance;
    private int rewardPoints;
    private List<GiftCard> giftCards;

    public Customer(String id, String name, String password, double balance) {
        this.customerId = id;
        this.name = name;
        this.encryptedPassword = EncryptionUtility.encrypt(password);
        this.mainBalance = balance;
        this.rewardPoints = 0;
        this.giftCards = new ArrayList<>();
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public double getMainBalance() { return mainBalance; }
    public int getRewardPoints() { return rewardPoints; }
    public List<GiftCard> getGiftCards() { return giftCards; }

    public void setMainBalance(double balance) { this.mainBalance = balance; }
    public void setRewardPoints(int points) { this.rewardPoints = points; }
}

// ===== Encryption Utility =====
class EncryptionUtility {
    public static String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    encrypted.append((char) ('A' + (c - 'A' + 1) % 26));
                } else {
                    encrypted.append((char) ('a' + (c - 'a' + 1) % 26));
                }
            } else if (Character.isDigit(c)) {
                encrypted.append((char) ('0' + (c - '0' + 1) % 10));
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }
}

// ===== Transaction Class =====
class Transaction {
    private static int counter = 1;
    private String transactionId;
    private Date date;
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.transactionId = "TXN" + counter++;
        this.date = new Date();
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return transactionId + " | " + date + " | " + type + " | " + amount;
    }
}

// ===== Gift Card Class =====
class GiftCard {
    private String cardNumber;
    private String pin;
    private double balance;
    private boolean blocked;
    private List<Transaction> history;

    public GiftCard(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = 0.0;
        this.blocked = false;
        this.history = new ArrayList<>();
    }

    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public boolean isBlocked() { return blocked; }

    public void setBalance(double balance) { this.balance = balance; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }

    public void addTransaction(Transaction t) { history.add(t); }
    public List<Transaction> getHistory() { return history; }
}

// ===== Bank Management System =====
public class BankManagement {
    private static List<Customer> customers = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    // ---- Initialize with 3 customers ----
    static {
        customers.add(new Customer("C001", "Alice", "pass1", 5000));
        customers.add(new Customer("C002", "Bob", "pass2", 6000));
        customers.add(new Customer("C003", "Charlie", "pass3", 7000));
    }

    // ---- Main Menu ----
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== BANK MENU ====");
            System.out.println("1. Account Login");
            System.out.println("2. Purchase (Gift Card Login)");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> accountLoginMenu();
                case 2 -> giftCardPurchaseMenu();
                case 3 -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ---- Customer Account Menu ----
    public static void accountLoginMenu() {
        System.out.print("Enter Customer ID: ");
        String id = sc.next();
        System.out.print("Enter Password: ");
        String pwd = sc.next();

        Customer c = login(id, pwd);
        if (c == null) {
            System.out.println("Login failed!");
            return;
        }

        System.out.println("Welcome " + c.getName() + "! Balance: " + c.getMainBalance());

        while (true) {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1. Create Gift Card");
            System.out.println("2. TopUp Gift Card");
            System.out.println("3. Transaction History");
            System.out.println("4. Block Gift Card");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> createGiftCard(c);
                case 2 -> {
                    GiftCard card = selectGiftCard(c);
                    if (card != null) {
                        System.out.print("Enter amount: ");
                        double amt = sc.nextDouble();
                        topUp(c, card, amt);
                    }
                }
                case 3 -> {
                    GiftCard card = selectGiftCard(c);
                    if (card != null) showHistory(card);
                }
                case 4 -> {
                    GiftCard card = selectGiftCard(c);
                    if (card != null) blockGiftCard(c, card);
                }
                case 5 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ---- Gift Card Purchase Menu ----
    public static void giftCardPurchaseMenu() {
        System.out.print("Enter Customer ID: ");
        String id = sc.next();
        Customer c = null;
        for (Customer cust : customers) {
            if (cust.getCustomerId().equals(id)) {
                c = cust;
                break;
            }
        }
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }

        GiftCard card = selectGiftCard(c);
        if (card == null) return;

        System.out.print("Enter PIN: ");
        String pin = sc.next();
        if (!card.getPin().equals(pin)) {
            System.out.println("Wrong PIN!");
            return;
        }

        System.out.print("Enter purchase amount: ");
        double amt = sc.nextDouble();
        purchase(c, card, amt);
    }

        // ---- Login ----
    public static Customer login(String id, String password) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(id) &&
                c.getEncryptedPassword().equals(EncryptionUtility.encrypt(password))) {
                return c;
            }
        }
        return null;
    }

    // ---- Create Gift Card ----
    public static GiftCard createGiftCard(Customer c) {
        String cardNo = String.valueOf((int)(Math.random()*90000) + 10000);
        String pin = String.valueOf((int)(Math.random()*9000) + 1000);
        GiftCard card = new GiftCard(cardNo, pin);
        c.getGiftCards().add(card);
        System.out.println("Gift Card Created! CardNo: " + cardNo + " Pin: " + pin);
        return card;
    }

    // ---- TopUp ----
    public static void topUp(Customer c, GiftCard card, double amount) {
        if (card.isBlocked()) {
            System.out.println("Card is blocked!");
            return;
        }
        if (c.getMainBalance() >= amount) {
            c.setMainBalance(c.getMainBalance() - amount);
            card.setBalance(card.getBalance() + amount);
            card.addTransaction(new Transaction("TopUp", amount));
            System.out.println("TopUp Successful!");
        } else {
            System.out.println("Insufficient main balance!");
        }
    }

    // ---- Purchase ----
    public static void purchase(Customer c, GiftCard card, double amount) {
        if (card.isBlocked()) {
            System.out.println("Card is blocked!");
            return;
        }
        if (card.getBalance() >= amount) {
            card.setBalance(card.getBalance() - amount);
            card.addTransaction(new Transaction("Purchase", amount));
            System.out.println("Purchase successful! Remaining: " + card.getBalance());

            int earned = (int)(amount / 100);
            c.setRewardPoints(c.getRewardPoints() + earned);

            if (c.getRewardPoints() >= 10) {
                c.setMainBalance(c.getMainBalance() + 10);
                c.setRewardPoints(c.getRewardPoints() - 10);
                System.out.println("Redeemed 10 reward points. ₹10 added to main balance.");
            }
        } else {
            System.out.println("Not enough balance in gift card!");
        }
    }

    // ---- Block Gift Card ----
    public static void blockGiftCard(Customer c, GiftCard card) {
        if (!card.isBlocked()) {
            card.setBlocked(true);
            c.setMainBalance(c.getMainBalance() + card.getBalance());
            card.setBalance(0);
            card.addTransaction(new Transaction("Block", 0));
            System.out.println("Card blocked & balance transferred back!");
        }
    }

    // ---- Transaction History ----
    public static void showHistory(GiftCard card) {
        System.out.println("Transaction History for Card " + card.getCardNumber());
        for (Transaction t : card.getHistory()) {
            System.out.println(t);
        }
    }

    // ---- Helper to select Gift Card ----
    public static GiftCard selectGiftCard(Customer c) {
        if (c.getGiftCards().isEmpty()) {
            System.out.println("No gift cards found!");
            return null;
        }
        System.out.println("Select Gift Card:");
        for (int i = 0; i < c.getGiftCards().size(); i++) {
            GiftCard card = c.getGiftCards().get(i);
            System.out.println((i+1) + ". CardNo: " + card.getCardNumber() + 
                               " | Balance: " + card.getBalance() + 
                               " | Blocked: " + card.isBlocked());
        }
        int ch = sc.nextInt();
        if (ch < 1 || ch > c.getGiftCards().size()) return null;
        return c.getGiftCards().get(ch-1);
    }
}
