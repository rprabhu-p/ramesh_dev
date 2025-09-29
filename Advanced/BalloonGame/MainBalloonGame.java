package BalloonGame;

import java.util.*;

public class MainBalloonGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a Balloon Game Case:");
        System.out.println("1. Basic balloon drop");
        System.out.println("2. Stop when a row is full");
        System.out.println("3. Drop in first free column if chosen column is full");
        System.out.println("4. Burst vertically if 3 in a column");
        System.out.println("5. Burst horizontally if 3 in a row");
        System.out.print("Enter choice (1-5): ");

        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> BalloonGame1.run();
            case 2 -> BalloonGame2.run();
            case 3 -> BalloonGame3.run();
            case 4 -> BalloonGame4.run();
            case 5 -> BalloonGame5.run();
            default -> System.out.println("Invalid choice!");
        }

        sc.close();
    }
}
