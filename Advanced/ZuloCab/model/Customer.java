package ZuloCab.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String password;
    private int age;
    private List<Ride> rideHistory = new ArrayList<>();

    public Customer(int id, String name, String password, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public void addRide(Ride ride) { rideHistory.add(ride); }
    public List<Ride> getRideHistory() { return rideHistory; }

    public void printRideHistory() {
        if (rideHistory.isEmpty()) {
            System.out.println("No trips yet.");
            return;
        }
        System.out.println("Source\tDestination\tDriver\tFare");
        for (Ride r : rideHistory) {
            System.out.println(r.getSource().getName() + "\t" + 
                               r.getDestination().getName() + "\t" + 
                               r.getDriver().getName() + "\t" + 
                               r.getFare());
        }
    }
}
