package ZuloCab.model;

public class Cab {
    private int id;
    private Driver driver;
    private Location currentLocation;

    public Cab(int id, Driver driver, Location loc) {
        this.id = id;
        this.driver = driver;
        this.currentLocation = loc;
    }

    public int getId() { return id; }
    public Driver getDriver() { return driver; }
    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location loc) { this.currentLocation = loc; }
}
