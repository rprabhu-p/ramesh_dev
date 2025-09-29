package BankManagement.model;

import java.util.*;

import BankManagement.util.EncryptionUtility;

public class Customer {
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
