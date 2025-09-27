package Advanced.ZuloCab.model;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private int id;
    private String name;
    private String password;
    private int age;
    private boolean onRest = false;
    private List<Ride> rideHistory = new ArrayList<>();
    private int tripsCompleted = 0;

    public Driver(int id, String name, String password, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public boolean isOnRest() { return onRest; }
    public void incrementTrip() { 
        tripsCompleted++; 
        onRest = tripsCompleted % 1 == 0; // driver rests after 1 trip
    }

    public void addRide(Ride ride) { rideHistory.add(ride); }
    public List<Ride> getRideHistory() { return rideHistory; }

    public void printRideHistory() {
        if (rideHistory.isEmpty()) {
            System.out.println("No trips yet.");
            return;
        }
        System.out.println("Source\tDestination\tCustomer\tFare\tCommission");
        for (Ride r : rideHistory) {
            System.out.println(r.getSource().getName() + "\t" + 
                               r.getDestination().getName() + "\t" + 
                               r.getCustomer().getName() + "\t" + 
                               r.getFare() + "\t" + 
                               r.getZulaCommission());
        }
    }
}
