package Advanced.TollApplication;

import java.util.*;

public class Vehicle {
    String vehicleNumber;
    String type; // Car, Truck, Bike
    boolean isVIP;
    List<Journey> journeys = new ArrayList<>();

    public Vehicle(String vehicleNumber, String type, boolean isVIP) {
        this.vehicleNumber = vehicleNumber;
        this.type = type;
        this.isVIP = isVIP;
    }

    @Override
    public String toString() {
        return vehicleNumber + " (" + type + (isVIP ? ", VIP" : "") + ")";
    }
}
