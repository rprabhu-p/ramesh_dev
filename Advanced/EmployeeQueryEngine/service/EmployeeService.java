package Advanced.EmployeeQueryEngine.service;

import Advanced.EmployeeQueryEngine.model.Employee;
import Advanced.EmployeeQueryEngine.repository.EmployeeRepository;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public void showHierarchy(Employee e) {
        List<String> chain = new ArrayList<>();
        Employee cur = e;
        chain.add(cur.name);
        Set<String> visited = new HashSet<>();
        visited.add(cur.name.toLowerCase());

        while (cur.reportingTo != null && !cur.reportingTo.trim().isEmpty()) {
            String mgrName = cur.reportingTo.trim();
            if (visited.contains(mgrName.toLowerCase())) {
                chain.add("(cycle detected -> " + mgrName + ")");
                break;
            }
            visited.add(mgrName.toLowerCase());
            Optional<Employee> mgr = repo.findByName(mgrName);
            if (!mgr.isPresent()) {
                chain.add(mgrName + "(unknown)");
                break;
            }
            chain.add(mgr.get().name);
            cur = mgr.get();
        }
        System.out.println(String.join(" -> ", chain));
    }

    public void showSummaries() {
        List<Employee> all = repo.findAll();
        System.out.println("Department summary:");
        all.stream()
            .collect(Collectors.groupingBy(e -> e.department, Collectors.counting()))
            .forEach((k,v) -> System.out.printf("  %s: %d\n", k, v));

        System.out.println("Designation summary:");
        all.stream()
            .collect(Collectors.groupingBy(e -> e.designation, Collectors.counting()))
            .forEach((k,v) -> System.out.printf("  %s: %d\n", k, v));

        System.out.println("ReportingTo summary:");
        all.stream()
            .collect(Collectors.groupingBy(e -> e.reportingTo == null || e.reportingTo.isEmpty() ? "(none)" : e.reportingTo, Collectors.counting()))
            .forEach((k,v) -> System.out.printf("  %s: %d\n", k, v));
    }
}
