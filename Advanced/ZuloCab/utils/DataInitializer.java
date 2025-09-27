package Advanced.ZuloCab.utils;

import Advanced.ZuloCab.model.*;

import java.util.ArrayList;
import java.util.List;

public class DataInitializer {
    private static final List<Driver> drivers = new ArrayList<>();
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<Location> locations = new ArrayList<>();
    private static final List<Cab> cabs = new ArrayList<>();

    public static void initialize() {
        // Drivers
        drivers.add(new Driver(1, "aaa", "111", 25));
        drivers.add(new Driver(2, "bbb", "222", 36));
        drivers.add(new Driver(3, "ccc", "333", 31));
        drivers.add(new Driver(4, "ddd", "444", 28));

        // Customers
        customers.add(new Customer(1, "WW", "55", 25));
        customers.add(new Customer(2, "XX", "66", 36));
        customers.add(new Customer(3, "YY", "77", 31));
        customers.add(new Customer(4, "ZZ", "88", 28));

        // Locations
        locations.add(new Location(1, "A", 0));
        locations.add(new Location(2, "B", 15));
        locations.add(new Location(3, "C", 4));
        locations.add(new Location(4, "D", 7));
        locations.add(new Location(5, "R", 23));
        locations.add(new Location(6, "F", 9));
        locations.add(new Location(7, "G", 18));
        locations.add(new Location(8, "H", 20));

        // Cabs
        cabs.add(new Cab(1, drivers.get(0), locations.get(3)));
        cabs.add(new Cab(2, drivers.get(1), locations.get(6)));
        cabs.add(new Cab(3, drivers.get(2), locations.get(7)));
        cabs.add(new Cab(4, drivers.get(3), locations.get(0)));
    }

    public static List<Driver> getDrivers() { return drivers; }
    public static List<Customer> getCustomers() { return customers; }
    public static List<Location> getLocations() { return locations; }
    public static List<Cab> getCabs() { return cabs; }
}
