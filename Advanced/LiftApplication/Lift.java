package LiftApplication;


public class Lift {
    String name;
    int currentFloor;
    int minFloor;
    int maxFloor;
    int capacity;
    boolean underMaintenance;
    int passengers;

    public Lift(String name, int minFloor, int maxFloor, int capacity) {
        this.name = name;
        this.currentFloor = 0;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.capacity = capacity;
        this.passengers = 0;
        this.underMaintenance = false;
    }

    public boolean canServe(int from, int to) {
        if (underMaintenance) return false;
        return from >= minFloor && to <= maxFloor;
    }

    @Override
    public String toString() {
        return name + "(" + (underMaintenance ? "-1" : currentFloor) + ")";
    }
}
