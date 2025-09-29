package Advanced;

import java.util.*;

class Lift {
    String name;
    int currentFloor;
    int capacity;
    boolean underMaintenance;
    int minFloor;
    int maxFloor;
    Queue<Integer> stops; // keeps track of scheduled stops

    public Lift(String name, int minFloor, int maxFloor, int capacity) {
        this.name = name;
        this.currentFloor = 0; // initially all lifts at 0
        this.capacity = capacity;
        this.underMaintenance = false;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.stops = new LinkedList<>();
    }

    public boolean canServe(int from, int to) {
        if (underMaintenance) return false;
        return (from >= minFloor && to <= maxFloor);
    }

    public void moveTo(int floor) {
        this.currentFloor = floor;
    }

    public int numberOfStops(int from, int to) {
        int stops = 0;
        if (from > to) {
            stops = from - to;
        } else {
            stops = to - from;
        }
        return stops;
    }

    @Override
    public String toString() {
        return name + "(" + (underMaintenance ? "-1" : currentFloor) + ")";
    }
}

class LiftSystem {
    List<Lift> lifts;

    public LiftSystem() {
        lifts = new ArrayList<>();
        // L1 & L2 -> 0-5
        lifts.add(new Lift("L1", 0, 5, 5));
        lifts.add(new Lift("L2", 0, 5, 5));
        // L3 & L4 -> 6-10
        lifts.add(new Lift("L3", 6, 10, 6));
        lifts.add(new Lift("L4", 6, 10, 6));
        // L5 -> 0-10
        lifts.add(new Lift("L5", 0, 10, 10));
    }

    public void display() {
        System.out.println("Lift   : " + String.join(" ", lifts.stream().map(l -> l.name).toList()));
        System.out.println("Floor  : " + String.join("   ", lifts.stream().map(l -> l.underMaintenance ? "-1" : String.valueOf(l.currentFloor)).toList()));
    }

    public void request(int from, int to) {
        Lift chosen = chooseLift(from, to);
        if (chosen == null) {
            System.out.println("No available lift!");
            return;
        }
        System.out.println(chosen.name + " is assigned");
        chosen.moveTo(to);
        display();
    }

    private Lift chooseLift(int from, int to) {
        Lift best = null;
        int bestDistance = Integer.MAX_VALUE;
        int userDirection = Integer.compare(to, from);

        for (Lift lift : lifts) {
            if (!lift.canServe(from, to)) continue;

            int distance = Math.abs(lift.currentFloor - from);

            if (distance < bestDistance) {
                best = lift;
                bestDistance = distance;
            } else if (distance == bestDistance) {
                // Rule 4: choose based on direction
                int liftDirection = Integer.compare(to, lift.currentFloor);
                if (liftDirection == userDirection) {
                    best = lift;
                } else {
                    // Rule 6: least number of stops
                    if (best != null && lift.numberOfStops(from, to) < best.numberOfStops(from, to)) {
                        best = lift;
                    }
                }
            }
        }
        return best;
    }

    public void setMaintenance(String liftName, boolean status) {
        for (Lift lift : lifts) {
            if (lift.name.equals(liftName)) {
                lift.underMaintenance = status;
                if (status) lift.currentFloor = -1;
                else lift.currentFloor = 0;
            }
        }
    }
}

public class LiftSystemMain {
    public static void main(String[] args) {
        LiftSystem system = new LiftSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Lift System ---");
            system.display();
            System.out.println("1. Request Lift");
            System.out.println("2. Set Lift Under Maintenance");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.print("Enter source floor: ");
                int from = sc.nextInt();
                System.out.print("Enter destination floor: ");
                int to = sc.nextInt();
                system.request(from, to);
            } else if (ch == 2) {
                System.out.print("Enter lift name (L1-L5): ");
                String liftName = sc.next();
                System.out.print("Enter 1 for Maintenance, 0 to Restore: ");
                boolean status = sc.nextInt() == 1;
                system.setMaintenance(liftName, status);
            } else {
                break;
            }
        }
        sc.close();
    }
}
