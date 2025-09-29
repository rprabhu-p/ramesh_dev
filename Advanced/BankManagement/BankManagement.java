package BankManagement;

import java.util.*;

import BankManagement.model.*;
import BankManagement.service.BankService;

public class BankManagement {
    private static Scanner sc = new Scanner(System.in);
    private static BankService bankService = new BankService();

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

    private static void accountLoginMenu() {
        System.out.print("Enter Customer ID: ");
        String id = sc.next();
        System.out.print("Enter Password: ");
        String pwd = sc.next();

        Customer c = bankService.login(id, pwd);
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
                case 1 -> bankService.createGiftCard(c);
                case 2 -> {
                    GiftCard card = bankService.selectGiftCard(c, sc);
                    if (card != null) {
                        System.out.print("Enter amount: ");
                        double amt = sc.nextDouble();
                        bankService.topUp(c, card, amt);
                    }
                }
                case 3 -> {
                    GiftCard card = bankService.selectGiftCard(c, sc);
                    if (card != null) bankService.showHistory(card);
                }
                case 4 -> {
                    GiftCard card = bankService.selectGiftCard(c, sc);
                    if (card != null) bankService.blockGiftCard(c, card);
                }
                case 5 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void giftCardPurchaseMenu() {
        System.out.print("Enter Customer ID: ");
        String id = sc.next();

        Customer c = bankService.getCustomers().stream()
                        .filter(cust -> cust.getCustomerId().equals(id))
                        .findFirst().orElse(null);

        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }

        GiftCard card = bankService.selectGiftCard(c, sc);
        if (card == null) return;

        System.out.print("Enter PIN: ");
        String pin = sc.next();
        if (!card.getPin().equals(pin)) {
            System.out.println("Wrong PIN!");
            return;
        }

        System.out.print("Enter purchase amount: ");
        double amt = sc.nextDouble();
        bankService.purchase(c, card, amt);
    }
}
