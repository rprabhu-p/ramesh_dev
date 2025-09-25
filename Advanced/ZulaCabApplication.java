package Advanced;

import java.util.*;

class ZulaCabApplication {

    static Scanner sc = new Scanner(System.in);

    // Driver, Customer, Location, Cab Maps
    static Map<Integer, Driver> drivers = new HashMap<>();
    static Map<Integer, Customer> customers = new HashMap<>();
    static Map<Integer, Location> locations = new HashMap<>();
    static Map<Integer, Cab> cabs = new HashMap<>();

    public static void main(String[] args) {
        initializeData();
        while (true) {
            System.out.println("\nWelcome to ZULA!!");
            System.out.println("1. Driver login");
            System.out.println("2. Customer login");
            System.out.println("3. ZULA Administrator");
            System.out.println("4. Exit");
            System.out.print("Please choose a service: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            switch (choice) {
                case 1 -> driverLogin();
                case 2 -> customerLogin();
                case 3 -> adminLogin();
                case 4 -> {
                    System.out.println("Thank you for using ZULA!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Initialize data
    static void initializeData() {
        // Drivers
        drivers.put(1, new Driver(1, "aaa", "111", 25));
        drivers.put(2, new Driver(2, "bbb", "222", 36));
        drivers.put(3, new Driver(3, "ccc", "333", 31));
        drivers.put(4, new Driver(4, "ddd", "444", 28));

        // Customers
        customers.put(1, new Customer(1, "WW", "55", 25));
        customers.put(2, new Customer(2, "XX", "66", 36));
        customers.put(3, new Customer(3, "YY", "77", 31));
        customers.put(4, new Customer(4, "ZZ", "88", 28));

        // Locations
        locations.put(1, new Location(1, "A", 0));
        locations.put(2, new Location(2, "B", 15));
        locations.put(3, new Location(3, "C", 4));
        locations.put(4, new Location(4, "D", 7));
        locations.put(5, new Location(5, "R", 23));
        locations.put(6, new Location(6, "F", 9));
        locations.put(7, new Location(7, "G", 18));
        locations.put(8, new Location(8, "H", 20));

        // Cabs
        cabs.put(1, new Cab(1, drivers.get(1), locations.get(4)));
        cabs.put(2, new Cab(2, drivers.get(2), locations.get(7)));
        cabs.put(3, new Cab(3, drivers.get(3), locations.get(8)));
        cabs.put(4, new Cab(4, drivers.get(4), locations.get(1)));
    }

    // Driver Login
    static void driverLogin() {
        System.out.print("Enter Driver Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        Driver driver = drivers.values().stream()
                .filter(d -> d.name.equals(name) && d.password.equals(pass))
                .findFirst()
                .orElse(null);

        if (driver == null) {
            System.out.println("Invalid credentials!");
            return;
        }

        System.out.println("Welcome " + driver.name);
        System.out.println("Your ride history:");
        driver.printRideHistory();
    }

    // Customer Login
    static void customerLogin() {
        System.out.println("1. Existing Customer Login");
        System.out.println("2. New Customer Registration");
        System.out.print("Choice: ");
        int ch = sc.nextInt();
        sc.nextLine();

        Customer customer = null;

        if (ch == 1) {
            System.out.print("Enter Customer Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            customer = customers.values().stream()
                    .filter(c -> c.name.equals(name) && c.password.equals(pass))
                    .findFirst()
                    .orElse(null);

            if (customer == null) {
                System.out.println("Invalid credentials!");
                return;
            }
        } else if (ch == 2) {
            System.out.print("Enter ID: ");
            int id = sc.nextInt(); sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Password: ");
            String pass = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt(); sc.nextLine();

            customer = new Customer(id, name, pass, age);
            customers.put(id, customer);
            System.out.println("Customer registered successfully!");
        }

        // Customer Menu
        while (true) {
            System.out.println("\n1. Hail a Cab");
            System.out.println("2. View Ride History");
            System.out.println("3. Logout");
            System.out.print("Choice: ");
            int choice = sc.nextInt(); sc.nextLine();
            if (choice == 1) {
                hailCab(customer);
            } else if (choice == 2) {
                customer.printRideHistory();
            } else {
                break;
            }
        }
    }

    // Admin Login
    static void adminLogin() {
        System.out.print("Enter Admin Password: ");
        String pass = sc.nextLine();
        if (!pass.equals("admin")) {
            System.out.println("Wrong password!");
            return;
        }

        System.out.println("ZULA Cab Summary:");
        for (Cab cab : cabs.values()) {
            cab.printSummary();
        }
    }

    // Hail Cab
    static void hailCab(Customer customer) {
        System.out.println("Available Locations:");
        locations.values().forEach(l -> System.out.println(l.id + ". " + l.name));
        System.out.print("Enter Source Location ID: ");
        int srcId = sc.nextInt();
        System.out.print("Enter Destination Location ID: ");
        int destId = sc.nextInt();
        sc.nextLine();

        Location source = locations.get(srcId);
        Location dest = locations.get(destId);

        if (source == null || dest == null) {
            System.out.println("Invalid locations!");
            return;
        }

        Cab cab = findNearestCab(source);
        if (cab == null) {
            System.out.println("No cabs available!");
            return;
        }

        int distance = Math.abs(dest.distance - source.distance);
        int fare = distance * 10;
        int commission = (int)(fare * 0.3);

        System.out.println("Nearest Cab: " + cab.driver.name);
        System.out.println("Fare Estimate: Rs." + fare);
        System.out.print("Confirm ride? (y/n): ");
        String confirm = sc.nextLine();
        if (!confirm.equalsIgnoreCase("y")) return;

        Ride ride = new Ride(source, dest, customer, cab.driver, fare, commission);
        cab.addRide(ride);
        customer.addRide(ride);
        cab.driver.incrementTrip();
        System.out.println("Ride confirmed!");
    }

    static Cab findNearestCab(Location source) {
        return cabs.values().stream()
                .filter(c -> !c.driver.onRest)
                .min(Comparator.comparingInt(c -> Math.abs(c.currentLocation.distance - source.distance)))
                .orElse(null);
    }
}

// Driver Class
class Driver {
    int id;
    String name;
    String password;
    int age;
    boolean onRest = false;
    int tripsCompleted = 0;
    List<Ride> rideHistory = new ArrayList<>();

    public Driver(int id, String name, String password, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    void incrementTrip() {
        tripsCompleted++;
        onRest = true; // driver rests after 1 trip
    }

    void addRide(Ride ride) {
        rideHistory.add(ride);
    }

    void printRideHistory() {
        if (rideHistory.isEmpty()) {
            System.out.println("No trips yet.");
            return;
        }
        System.out.println("Source\tDestination\tCustomer\tFare\tCommission");
        for (Ride r : rideHistory) {
            System.out.println(r.source.name + "\t" + r.destination.name + "\t" + r.customer.name + "\t" + r.fare + "\t" + r.zulaCommission);
        }
    }
}

// Customer Class
class Customer {
    int id;
    String name;
    String password;
    int age;
    List<Ride> rideHistory;

    public Customer(int id, String name, String password, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        rideHistory = new ArrayList<>();
    }

    void addRide(Ride ride) {
        rideHistory.add(ride);
    }

    void printRideHistory() {
        if (rideHistory.isEmpty()) {
            System.out.println("No trips yet.");
            return;
        }
        System.out.println("Source\tDestination\tCab\tFare");
        for (Ride r : rideHistory) {
            System.out.println(r.source.name + "\t" + r.destination.name + "\t" + r.driver.name + "\t" + r.fare);
        }
    }
}

// Location Class
class Location {
    int id;
    String name;
    int distance;

    public Location(int id, String name, int distance) {
        this.id = id;
        this.name = name;
        this.distance = distance;
    }
}

// Cab Class
class Cab {
    int id;
    Driver driver;
    Location currentLocation;
    List<Ride> rideHistory = new ArrayList<>();
    int totalFare = 0;
    int totalCommission = 0;

    public Cab(int id, Driver driver, Location loc) {
        this.id = id;
        this.driver = driver;
        this.currentLocation = loc;
    }

    void addRide(Ride ride) {
        rideHistory.add(ride);
        driver.addRide(ride);
        currentLocation = ride.destination;
        totalFare += ride.fare;
        totalCommission += ride.zulaCommission;
    }

    void printSummary() {
        System.out.println("\nCab ID: " + id + " | Driver: " + driver.name);
        System.out.println("Total Trips: " + rideHistory.size());
        System.out.println("Total Fare Collected: " + totalFare);
        System.out.println("Total ZULA Commission: " + totalCommission);
        if (rideHistory.isEmpty()) {
            System.out.println("Trip Details: No trips yet.");
        } else {
            System.out.println("Source\tDestination\tCustomer\tFare\tCommission");
            for (Ride r : rideHistory) {
                System.out.println(r.source.name + "\t" + r.destination.name + "\t" + r.customer.name + "\t" + r.fare + "\t" + r.zulaCommission);
            }
        }
    }
}

// Ride Class
class Ride {
    Location source;
    Location destination;
    Customer customer;
    Driver driver;
    int fare;
    int zulaCommission;

    public Ride(Location src, Location dest, Customer cust, Driver drv, int fare, int commission) {
        this.source = src;
        this.destination = dest;
        this.customer = cust;
        this.driver = drv;
        this.fare = fare;
        this.zulaCommission = commission;
    }
}
