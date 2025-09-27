package Advanced.BankManagement.model;

import java.util.*;

public class Transaction {
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
