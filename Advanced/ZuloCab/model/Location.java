package Advanced.ZuloCab.model;

public class Location {
    private int id;
    private String name;
    private int distance;

    public Location(int id, String name, int distance) {
        this.id = id;
        this.name = name;
        this.distance = distance;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getDistance() { return distance; }
}
