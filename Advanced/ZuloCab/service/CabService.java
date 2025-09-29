package ZuloCab.service;

import java.util.List;

import ZuloCab.model.Cab;
import ZuloCab.model.Location;
import ZuloCab.utils.DataInitializer;

public class CabService {
    public static Cab findNearestCab(Location source) {
        List<Cab> cabs = DataInitializer.getCabs();
        return cabs.stream()
                .filter(c -> !c.getDriver().isOnRest())
                .min((c1, c2) -> Math.abs(c1.getCurrentLocation().getDistance() - source.getDistance())
                        - Math.abs(c2.getCurrentLocation().getDistance() - source.getDistance()))
                .orElse(null);
    }
}
