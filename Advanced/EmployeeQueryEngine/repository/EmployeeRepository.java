package Advanced.EmployeeQueryEngine.repository;

import Advanced.EmployeeQueryEngine.model.Employee;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeRepository {
    private final List<Employee> employees;

    public EmployeeRepository(List<Employee> employee) {
        this.employees = new ArrayList<>(employee);
    }

    public List<Employee> findAll(){
        return Collections.unmodifiableList(employees);
    }

    public Optional<Employee> findByName(String name) {
        return employees.stream()
                .filter(e -> e.name.equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Employee> finDirectReports(String managerName) {
        return employees.stream()
                .filter(e -> managerName.equalsIgnoreCase(e.reportingTo))
                .collect(Collectors.toList());
    }
}
