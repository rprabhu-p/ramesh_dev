package BankManagement.model;

import java.util.*;

public class GiftCard {
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
