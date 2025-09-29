package Advanced;

import java.util.*;

// -------------------- Core Classes --------------------

class Vehicle {
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

class PaymentRecord {
    Vehicle vehicle;
    double amountPaid;

    public PaymentRecord(Vehicle vehicle, double amountPaid) {
        this.vehicle = vehicle;
        this.amountPaid = amountPaid;
    }
}

class TollBooth {
    String tollName;
    Map<String, Double> chargeScheme = new HashMap<>(); // VehicleType -> Rate
    List<PaymentRecord> payments = new ArrayList<>();

    public TollBooth(String tollName) {
        this.tollName = tollName;
    }

    public void setCharge(String vehicleType, double rate) {
        chargeScheme.put(vehicleType, rate);
    }

    public double calculateCharge(Vehicle v) {
        double base = chargeScheme.getOrDefault(v.type, 0.0);
        if (v.isVIP) return base * 0.8; // 20% discount
        return base;
    }

    public void recordPayment(Vehicle v, double amount) {
        payments.add(new PaymentRecord(v, amount));
    }

    @Override
    public String toString() {
        return tollName;
    }
}

class Journey {
    Vehicle vehicle;
    String startPoint;
    String endPoint;
    List<TollBooth> tollsPassed = new ArrayList<>();
    double totalAmount;

    public Journey(Vehicle vehicle, String startPoint, String endPoint) {
        this.vehicle = vehicle;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}

class Highway {
    List<TollBooth> tolls = new ArrayList<>();

    public void addToll(TollBooth toll) {
        tolls.add(toll);
    }

    // Circular route calculation
    public List<TollBooth> findRoute(int startIndex, int endIndex, Vehicle v) {
        List<TollBooth> clockwise = new ArrayList<>();
        List<TollBooth> counterClockwise = new ArrayList<>();

        // Clockwise
        for (int i = startIndex; i != endIndex; i = (i + 1) % tolls.size()) {
            clockwise.add(tolls.get(i));
        }
        clockwise.add(tolls.get(endIndex));

        // Counter-Clockwise
        for (int i = startIndex; i != endIndex; i = (i - 1 + tolls.size()) % tolls.size()) {
            counterClockwise.add(tolls.get(i));
        }
        counterClockwise.add(tolls.get(endIndex));

        // Compare actual costs for this vehicle
        double clockwiseCost = sumRoute(clockwise, v);
        double counterCost = sumRoute(counterClockwise, v);

        return clockwiseCost <= counterCost ? clockwise : counterClockwise;
    }

    private double sumRoute(List<TollBooth> route, Vehicle v) {
        double sum = 0;
        for (TollBooth t : route) {
            sum += t.calculateCharge(v);
        }
        return sum;
    }
}

// -------------------- Main Application --------------------

public class TollAppSingle {
    static Scanner sc = new Scanner(System.in);
    static Highway highway = new Highway();
    static Map<String, Vehicle> vehicles = new HashMap<>();

    public static void main(String[] args) {
        setupSampleData(); // preload tolls & charges

        while (true) {
            System.out.println("\n====== Toll Payment System ======");
            System.out.println("1. Register Vehicle & Start Journey");
            System.out.println("2. Display Toll-wise Report");
            System.out.println("3. Display Vehicle-wise Report");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: handleJourney(); break;
                case 2: tollReport(); break;
                case 3: vehicleReport(); break;
                case 4: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ---------------- Setup ----------------
    static void setupSampleData() {
        TollBooth t1 = new TollBooth("Toll1");
        t1.setCharge("Car", 100);
        t1.setCharge("Truck", 200);
        t1.setCharge("Bike", 50);

        TollBooth t2 = new TollBooth("Toll2");
        t2.setCharge("Car", 60);
        t2.setCharge("Truck", 150);
        t2.setCharge("Bike", 40);

        TollBooth t3 = new TollBooth("Toll3");
        t3.setCharge("Car", 80);
        t3.setCharge("Truck", 180);
        t3.setCharge("Bike", 30);

        highway.addToll(t1);
        highway.addToll(t2);
        highway.addToll(t3);
    }

    // ---------------- Module 1 ----------------
    static void handleJourney() {
        System.out.print("Enter Vehicle Number: ");
        String vno = sc.nextLine();

        Vehicle v = vehicles.get(vno);
        if (v == null) {
            System.out.print("Enter Vehicle Type (Car/Truck/Bike): ");
            String type = sc.nextLine();
            System.out.print("Is VIP? (yes/no): ");
            boolean vip = sc.nextLine().equalsIgnoreCase("yes");
            v = new Vehicle(vno, type, vip);
            vehicles.put(vno, v);
        }

        System.out.println("Available Tolls:");
        for (int i = 0; i < highway.tolls.size(); i++) {
            System.out.println(i + " - " + highway.tolls.get(i).tollName);
        }

        System.out.print("Enter Start Toll Index: ");
        int startIndex = sc.nextInt();
        System.out.print("Enter End Toll Index: ");
        int endIndex = sc.nextInt();
        sc.nextLine();

        Journey j = new Journey(v, highway.tolls.get(startIndex).tollName, highway.tolls.get(endIndex).tollName);

        List<TollBooth> route = highway.findRoute(startIndex, endIndex, v);

        double total = 0;
        for (TollBooth toll : route) {
            double charge = toll.calculateCharge(v);
            total += charge;
            toll.recordPayment(v, charge);
            j.tollsPassed.add(toll);
        }
        j.totalAmount = total;
        v.journeys.add(j);

        System.out.println("Journey Completed! Total Paid: " + total);
    }

    // ---------------- Module 2 ----------------
    static void tollReport() {
        for (TollBooth t : highway.tolls) {
            System.out.println("\nToll: " + t.tollName);
            double collected = 0;
            for (PaymentRecord pr : t.payments) {
                System.out.println("  Vehicle: " + pr.vehicle.vehicleNumber + " Paid: " + pr.amountPaid);
                collected += pr.amountPaid;
            }
            System.out.println("Total Collected at " + t.tollName + ": " + collected);
        }
    }

    // ---------------- Module 3 ----------------
    static void vehicleReport() {
        for (Vehicle v : vehicles.values()) {
            System.out.println("\nVehicle: " + v);
            double totalSpent = 0;
            for (Journey j : v.journeys) {
                System.out.println("  Journey: " + j.startPoint + " -> " + j.endPoint);
                System.out.print("   Tolls: ");
                for (TollBooth tb : j.tollsPassed) {
                    System.out.print(tb.tollName + " ");
                }
                System.out.println("\n   Amount Paid: " + j.totalAmount);
                totalSpent += j.totalAmount;
            }
            System.out.println("Total Paid by Vehicle: " + totalSpent);
        }
    }
}
