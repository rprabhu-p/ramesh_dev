package TollApplication;

import java.util.*;

public class Journey {
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
