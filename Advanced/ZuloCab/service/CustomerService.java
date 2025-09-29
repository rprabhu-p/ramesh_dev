package ZuloCab.service;

import java.util.List;

import ZuloCab.model.Customer;
import ZuloCab.utils.DataInitializer;

public class CustomerService {
    public static Customer login(String name, String password) {
        List<Customer> customers = DataInitializer.getCustomers();
        return customers.stream()
                .filter(c -> c.getName().equals(name) && c.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
