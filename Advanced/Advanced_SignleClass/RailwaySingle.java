package Advanced_SignleClass;

import java.util.*;

class User {
    String name;
    int priority; // Higher value = higher priority

    public User(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}

class Booking implements Comparable<Booking> {
    User user;
    int seatNumber;

    public Booking(User user, int seatNumber) {
        this.user = user;
        this.seatNumber = seatNumber;
    }

    @Override
    public int compareTo(Booking other) {
        // Higher priority first
        return Integer.compare(other.user.priority, this.user.priority);
    }
}

class RailwayReservation {
    private int totalSeats;
    private List<Booking> bookings;

    public RailwayReservation(int totalSeats) {
        this.totalSeats = totalSeats;
        this.bookings = new ArrayList<>();
    }

    public int seatsLeft() {
        return totalSeats - bookings.size();
    }

    public boolean bookSeat(User user) {
        if (seatsLeft() <= 0) {
            System.out.println("No seats available for " + user.name);
            return false;
        }
        Booking booking = new Booking(user, bookings.size() + 1);
        bookings.add(booking);
        // Sort bookings based on user priority
        Collections.sort(bookings);
        System.out.println("Seat booked for " + user.name + " | Seat Number: " + booking.seatNumber);
        return true;
    }

    public boolean cancelSeat(String userName) {
        for (Iterator<Booking> it = bookings.iterator(); it.hasNext();) {
            Booking booking = it.next();
            if (booking.user.name.equalsIgnoreCase(userName)) {
                it.remove();
                System.out.println("Booking cancelled for " + userName);
                return true;
            }
        }
        System.out.println("No booking found for " + userName);
        return false;
    }

    public void showBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No current bookings.");
            return;
        }
        System.out.println("Current Bookings:");
        for (Booking b : bookings) {
            System.out.println("Seat " + b.seatNumber + " -> " + b.user.name + " (Priority: " + b.user.priority + ")");
        }
        System.out.println("Seats left: " + seatsLeft());
    }
}

public class RailwaySingle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter total number of seats: ");
        int totalSeats = sc.nextInt();
        sc.nextLine(); // consume newline

        RailwayReservation reservation = new RailwayReservation(totalSeats);

        while (true) {
            System.out.println("\n=== Railway Reservation Menu ===");
            System.out.println("1. Book a Seat");
            System.out.println("2. Cancel a Booking");
            System.out.println("3. View Bookings");
            System.out.println("4. Seats Left");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter your priority (higher number = higher priority): ");
                    int priority = sc.nextInt();
                    sc.nextLine(); // consume newline
                    User user = new User(name, priority);
                    reservation.bookSeat(user);
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
