package TollApplication;

import java.util.*;

public class Highway {
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
