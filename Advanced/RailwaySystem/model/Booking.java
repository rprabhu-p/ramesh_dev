package Advanced.RailwaySystem.model;

public class Booking implements Comparable<Booking> {
    private User user;
    private int seatNumber;

    public Booking(User user, int seatNumber) {
        this.user = user;
        this.seatNumber = seatNumber;
    }

    public User getUser() {
        return user;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public int compareTo(Booking other) {
        // Higher priority first
        return Integer.compare(other.getUser().getPriority(), this.user.getPriority());
    }
}
