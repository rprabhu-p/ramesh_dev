package LiftApplication;

import java.util.Scanner;

public class LiftSystemMain {
    public static void main(String[] args) {
        LiftSystem system = new LiftSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Lift System ---");
            system.display();
            System.out.println("1. Request Lift");
            System.out.println("2. Set Maintenance");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.print("Enter source floor: ");
                int from = sc.nextInt();
                System.out.print("Enter destination floor: ");
                int to = sc.nextInt();
                system.request(from, to);
            } else if (ch == 2) {
                System.out.print("Enter lift name (L1-L5): ");
                String name = sc.next();
                System.out.print("1 = Maintenance, 0 = Restore: ");
                boolean status = sc.nextInt() == 1;
                system.setMaintenance(name, status);
            } else break;
        }
        sc.close();
    }
}
