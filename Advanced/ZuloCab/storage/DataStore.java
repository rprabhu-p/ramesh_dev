package ZuloCab.storage;


import java.util.HashMap;
import java.util.Map;

import ZuloCab.model.*;

public class DataStore {
    public static Map<Integer, Driver> drivers = new HashMap<>();
    public static Map<Integer, Customer> customers = new HashMap<>();
    public static Map<Integer, Location> locations = new HashMap<>();
    public static Map<Integer, Cab> cabs = new HashMap<>();
}
