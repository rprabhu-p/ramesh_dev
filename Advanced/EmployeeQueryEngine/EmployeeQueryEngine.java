package Advanced.EmployeeQueryEngine;

import Advanced.EmployeeQueryEngine.model.Employee;
import Advanced.EmployeeQueryEngine.repository.EmployeeRepository;
import Advanced.EmployeeQueryEngine.query.*;
import Advanced.EmployeeQueryEngine.service.EmployeeService;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeQueryEngine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Employee> sample = Arrays.asList(
                new Employee(1, "A", 58, "CEO", "Executive", ""),
                new Employee(2, "B", 50, "VP", "Finance", "A"),
                new Employee(3, "C", 45, "Director", "Finance", "B"),
                new Employee(4, "D", 42, "Manager", "Finance", "C"),
                new Employee(5, "E", 39, "Manager", "Engineering", "B"),
                new Employee(6, "F", 36, "Lead", "Engineering", "D"),
                new Employee(7, "G", 34, "Engineer", "Engineering", "F"),
                new Employee(8, "H", 31, "Engineer", "Engineering", "F"),
                new Employee(9, "I", 29, "Analyst", "Finance", "F"),
                new Employee(10, "J", 26, "Intern", "Finance", "I")
        );

        EmployeeRepository repo = new EmployeeRepository(sample);
        EmployeeService service = new EmployeeService(repo);

        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    repo.findAll().forEach(System.out::println);
                    break;
                case "2":
                    System.out.println("Enter query (type 'exit' to return). Example: age > 30 and department contains finance");
                    while (true) {
                        System.out.print("query> ");
                        String q = sc.nextLine().trim();
                        if (q.equalsIgnoreCase("exit")) break;
                        Optional<Condition> cond = ConditionParser.parseQuery(q);
                        if (!cond.isPresent()) {
                            System.out.println("Failed to parse query.");
                            continue;
                        }
                        List<Employee> res = repo.findAll().stream().filter(cond.get()).collect(Collectors.toList());
                        if (res.isEmpty()) System.out.println("No matching employees.");
                        else res.forEach(System.out::println);
                    }
                    break;
                case "3":
                    System.out.print("Enter employee name: ");
                    String name = sc.nextLine().trim();
                    repo.findByName(name).ifPresentOrElse(
                        e -> service.showHierarchy(e),
                        () -> System.out.println("Employee not found.")
                    );
                    break;
                case "4":
                    System.out.print("Enter manager name: ");
                    String m = sc.nextLine().trim();
                    List<Employee> dr = repo.finDirectReports(m);
                    if (dr.isEmpty()) System.out.println("No direct reports found.");
                    else dr.forEach(System.out::println);
                    break;
                case "5":
                    service.showSummaries();
                    break;
                case "6":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println();
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("---- Employee Query Engine ----");
        System.out.println("1) Show all employees");
        System.out.println("2) Process query");
        System.out.println("3) Show reporting hierarchy");
        System.out.println("4) Show direct reports");
        System.out.println("5) Show summaries");
        System.out.println("6) Exit");
        System.out.print("Select: ");
    }
}
