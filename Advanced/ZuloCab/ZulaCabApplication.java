package Advanced.ZuloCab;

import Advanced.ZuloCab.model.Cab;
import Advanced.ZuloCab.model.Customer;
import Advanced.ZuloCab.model.Driver;
import Advanced.ZuloCab.model.Location;
import Advanced.ZuloCab.model.Ride;
import Advanced.ZuloCab.service.CabService;
import Advanced.ZuloCab.service.CustomerService;
import Advanced.ZuloCab.service.DriverService;
import Advanced.ZuloCab.utils.DataInitializer;

import java.util.Scanner;

public class ZulaCabApplication {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DataInitializer.initialize();
        while (true) {
            System.out.println("\nWelcome to ZULA!!");
            System.out.println("1. Driver login");
            System.out.println("2. Customer login");
            System.out.println("3. ZULA Administrator");
            System.out.println("4. Exit");
            System.out.print("Please choose a service: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> driverLogin();
                case 2 -> customerLogin();
                case 3 -> adminModule();
                case 4 -> {
                    System.out.println("Thank you for using ZULA!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void driverLogin() {
        System.out.print("Enter Driver Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        Driver driver = DriverService.login(name, pass);
        if (driver == null) {
            System.out.println("Invalid credentials!");
            return;
        }
        System.out.println("Welcome " + driver.getName());
        driver.printRideHistory();
    }

    static void customerLogin() {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        Customer customer = CustomerService.login(name, pass);
        if (customer == null) {
            System.out.println("Invalid credentials!");
            return;
        }

        System.out.println("Welcome " + customer.getName());

        while (true) {
            System.out.println("\n1. Book a Ride");
            System.out.println("2. View Ride History");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1 -> bookRide(customer);
                case 2 -> customer.printRideHistory();
                case 3 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    static void bookRide(Customer customer) {
        var locations = CabService.getLocations();
        System.out.println("Available Locations:");
        for (Location loc : locations) {
            System.out.println(loc.getId() + ". " + loc.getName());
        }

        System.out.print("Enter source location id: ");
        int srcId = sc.nextInt();
        System.out.print("Enter destination location id: ");
        int destId = sc.nextInt();
        sc.nextLine();

        Location source = locations.stream().filter(l -> l.getId() == srcId).findFirst().orElse(null);
        Location destination = locations.stream().filter(l -> l.getId() == destId).findFirst().orElse(null);

        if (source == null || destination == null) {
            System.out.println("Invalid locations!");
            return;
        }

        Cab cab = CabService.findNearestCab(source);
        if (cab == null) {
            System.out.println("No available cabs nearby!");
            return;
        }

        int fare = Math.abs(destination.getDistance() - source.getDistance()) * 10;
        Ride ride = new Ride(customer, cab.getDriver(), source, destination, fare);

        // Update histories
        customer.addRide(ride);
        cab.getDriver().addRide(ride);
        cab.getDriver().incrementTrip();
        cab.setCurrentLocation(destination);

        System.out.println("Ride booked successfully with driver " + cab.getDriver().getName() +
                ". Fare: $" + fare + " (Zula commission: $" + ride.getZulaCommission() + ")");
    }

    static void adminModule() {
        System.out.println("=== ZULA ADMINISTRATOR MODULE ===");
        while (true) {
            System.out.println("\n1. View all drivers");
            System.out.println("2. View all customers");
            System.out.println("3. View total ZULA commission");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> viewAllDrivers();
                case 2 -> viewAllCustomers();
                case 3 -> viewTotalCommission();
                case 4 -> {
                    System.out.println("Logging out of admin...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void viewAllDrivers() {
        System.out.println("=== DRIVERS LIST ===");
        for (Driver d : DataInitializer.getDrivers()) {
            System.out.println("Driver: " + d.getName() + " | Age: " + d.getPassword());
            d.printRideHistory();
        }
    }

    static void viewAllCustomers() {
        System.out.println("=== CUSTOMERS LIST ===");
        for (Customer c : DataInitializer.getCustomers()) {
            System.out.println("Customer: " + c.getName());
            c.printRideHistory();
        }
    }

    static void viewTotalCommission() {
        int total = 0;
        for (Driver d : DataInitializer.getDrivers()) {
            for (Ride r : d.getRideHistory()) {
                total += r.getZulaCommission();
            }
        }
        System.out.println("Total ZULA commission earned: $" + total);
    }
}
