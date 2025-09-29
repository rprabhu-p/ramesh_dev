package ZuloCab.service;

import java.util.List;

import ZuloCab.model.Driver;
import ZuloCab.utils.DataInitializer;

public class DriverService {
    public static Driver login(String name, String password) {
        List<Driver> drivers = DataInitializer.getDrivers();
        return drivers.stream()
                .filter(d -> d.getName().equals(name) && d.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
