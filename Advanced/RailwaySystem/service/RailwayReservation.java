package Advanced.RailwaySystem.service;

import Advanced.RailwaySystem.model.*;
import java.util.*;

public class RailwayReservation {
    private int totalSeats;
    private List<Booking> bookings;
    private PriorityQueue<User> waitingList; // waiting users sorted by priority

    public RailwayReservation(int totalSeats) {
        this.totalSeats = totalSeats;
        this.bookings = new ArrayList<>();
        this.waitingList = new PriorityQueue<>((u1, u2) -> Integer.compare(u2.getPriority(), u1.getPriority()));
    }

    public int seatsLeft() {
        return totalSeats - bookings.size();
    }

    public boolean bookSeat(User user) {
        if (seatsLeft() > 0) {
            Booking booking = new Booking(user, bookings.size() + 1);
            bookings.add(booking);
            Collections.sort(bookings);
            System.out.println("✅ Seat booked for " + user.getName() + " | Seat Number: " + booking.getSeatNumber());
            return true;
        } else {
            waitingList.add(user);
            System.out.println("⚠️ No seats available! " + user.getName() + " added to waiting list.");
            return false;
        }
    }

    public boolean cancelSeat(String userName) {
        for (Iterator<Booking> it = bookings.iterator(); it.hasNext();) {
            Booking booking = it.next();
            if (booking.getUser().getName().equalsIgnoreCase(userName)) {
                it.remove();
                System.out.println("❌ Booking cancelled for " + userName);

                // Allocate seat to next waiting user (if any)
                if (!waitingList.isEmpty()) {
                    User nextUser = waitingList.poll();
                    Booking newBooking = new Booking(nextUser, bookings.size() + 1);
                    bookings.add(newBooking);
                    Collections.sort(bookings);
                    System.out.println("✅ Seat reallocated to " + nextUser.getName() + " from waiting list.");
                }
                return true;
            }
        }
        System.out.println("⚠️ No booking found for " + userName);
        return false;
    }

    public void showBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No current bookings.");
        } else {
            System.out.println("=== Current Bookings ===");
            for (Booking b : bookings) {
                System.out.println("Seat " + b.getSeatNumber() + " -> " + b.getUser().getName() + " (Priority: " + b.getUser().getPriority() + ")");
            }
        }
        System.out.println("Seats left: " + seatsLeft());
        showWaitingList();
    }

    public void showWaitingList() {
        if (waitingList.isEmpty()) {
            System.out.println("No one in waiting list.");
        } else {
            System.out.println("=== Waiting List ===");
            for (User u : waitingList) {
                System.out.println(u.getName() + " (Priority: " + u.getPriority() + ")");
            }
        }
    }
}
