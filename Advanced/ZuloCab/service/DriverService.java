package Advanced.ZuloCab.service;

import Advanced.ZuloCab.model.Driver;
import Advanced.ZuloCab.utils.DataInitializer;

import java.util.List;

public class DriverService {
    public static Driver login(String name, String password) {
        List<Driver> drivers = DataInitializer.getDrivers();
        return drivers.stream()
                .filter(d -> d.getName().equals(name) && d.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
