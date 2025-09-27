package Advanced.RailwaySystem;

import Advanced.RailwaySystem.model.*;
import Advanced.RailwaySystem.service.*;
import java.util.*;

public class Railway {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter total number of seats: ");
        int totalSeats = sc.nextInt();
        sc.nextLine();

        RailwayReservation reservation = new RailwayReservation(totalSeats);

        while (true) {
            System.out.println("\n=== Railway Reservation Menu ===");
            System.out.println("1. Book a Seat");
            System.out.println("2. Cancel a Booking");
            System.out.println("3. View Bookings & Waiting List");
            System.out.println("4. Seats Left");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter your priority (higher number = higher priority): ");
                    int priority = sc.nextInt();
                    sc.nextLine();
                    reservation.bookSeat(new User(name, priority));
                    break;

                case 2:
                    System.out.print("Enter name to cancel booking: ");
                    String cancelName = sc.nextLine();
                    reservation.cancelSeat(cancelName);
                    break;

                case 3:
                    reservation.showBookings();
                    break;

                case 4:
                    System.out.println("Seats left: " + reservation.seatsLeft());
                    break;

                case 5:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
