package Advanced.ZuloCab.model;

public class Ride {
    private Customer customer;
    private Driver driver;
    private Location source;
    private Location destination;
    private int fare;
    private int zulaCommission;

    public Ride(Customer customer, Driver driver, Location source, Location destination, int fare) {
        this.customer = customer;
        this.driver = driver;
        this.source = source;
        this.destination = destination;
        this.fare = fare;
        this.zulaCommission = fare * 30 / 100; // 30% commission
    }

    public Customer getCustomer() { return customer; }
    public Driver getDriver() { return driver; }
    public Location getSource() { return source; }
    public Location getDestination() { return destination; }
    public int getFare() { return fare; }
    public int getZulaCommission() { return zulaCommission; }
}
