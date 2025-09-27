package Advanced.ZuloCab.service;

import Advanced.ZuloCab.model.Customer;
import Advanced.ZuloCab.utils.DataInitializer;

import java.util.List;

public class CustomerService {
    public static Customer login(String name, String password) {
        List<Customer> customers = DataInitializer.getCustomers();
        return customers.stream()
                .filter(c -> c.getName().equals(name) && c.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
