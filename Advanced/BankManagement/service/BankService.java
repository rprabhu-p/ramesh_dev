package Advanced.BankManagement.service;

import Advanced.BankManagement.model.*;
import Advanced.BankManagement.util.*;
import java.util.*;

public class BankService {
    private List<Customer> customers;

    public BankService() {
        customers = new ArrayList<>();
        customers.add(new Customer("C001", "Alice", "pass1", 5000));
        customers.add(new Customer("C002", "Bob", "pass2", 6000));
        customers.add(new Customer("C003", "Charlie", "pass3", 7000));
    }

    public List<Customer> getCustomers() { return customers; }

    public Customer login(String id, String password) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(id) &&
                c.getEncryptedPassword().equals(EncryptionUtility.encrypt(password))) {
                return c;
            }
        }
        return null;
    }

    public GiftCard createGiftCard(Customer c) {
        String cardNo = String.valueOf((int)(Math.random()*90000) + 10000);
        String pin = String.valueOf((int)(Math.random()*9000) + 1000);
        GiftCard card = new GiftCard(cardNo, pin);
        c.getGiftCards().add(card);
        System.out.println("Gift Card Created! CardNo: " + cardNo + " Pin: " + pin);
        return card;
    }

    public void topUp(Customer c, GiftCard card, double amount) {
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

    public void purchase(Customer c, GiftCard card, double amount) {
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

    public void blockGiftCard(Customer c, GiftCard card) {
        if (!card.isBlocked()) {
            card.setBlocked(true);
            c.setMainBalance(c.getMainBalance() + card.getBalance());
            card.setBalance(0);
            card.addTransaction(new Transaction("Block", 0));
            System.out.println("Card blocked & balance transferred back!");
        }
    }

    public void showHistory(GiftCard card) {
        System.out.println("Transaction History for Card " + card.getCardNumber());
        for (Transaction t : card.getHistory()) {
            System.out.println(t);
        }
    }

    public GiftCard selectGiftCard(Customer c, Scanner sc) {
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
