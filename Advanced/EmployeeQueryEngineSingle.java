package Advanced;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeQueryEngine {

    // -------------------- Model --------------------
    static class Employee {
        int id;
        String name;
        int age;
        String designation;
        String department;
        String reportingTo; // manager name or id (we will use name in sample data)

        //Constructor
        Employee(int id, String name, int age, String designation, String department, String reportingTo) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.designation = designation;
            this.department = department;
            this.reportingTo = reportingTo;
        }

        @Override
        public String toString() {
            return String.format("ID=%d, Name=%s, Age=%d, Designation=%s, Department=%s, ReportingTo=%s",
                    id, name, age, designation, department, reportingTo);
        }
    }

    // -------------------- Repository / Data Source --------------------
    static class EmployeeRepository {
        private final List<Employee> employees;

        EmployeeRepository(List<Employee> employees) {
            this.employees = new ArrayList<>(employees);
        }

        List<Employee> findAll() {
            return Collections.unmodifiableList(employees);
        }

        Optional<Employee> findByName(String name) {
            return employees.stream().filter(e -> e.name.equalsIgnoreCase(name)).findFirst();
        }

        List<Employee> findDirectReports(String managerName) {
            return employees.stream()
                    .filter(e -> managerName.equalsIgnoreCase(e.reportingTo))
                    .collect(Collectors.toList());
        }
    }

    // -------------------- Query / Condition System --------------------
    interface Condition extends Predicate<Employee> {}

    static class AgeCondition implements Condition {
        enum Option {GT, LT, EQ, NEQ} // >, <, ==, !=
        private final Option op;
        private final int value;

        AgeCondition(Option op, int value) {
            this.op = op; 
            this.value = value;
        }

        public boolean test(Employee e) {
            switch (op) {
                case GT: return e.age > value;
                case LT: return e.age < value;
                case EQ: return e.age == value;
                case NEQ: return e.age != value;
                default: return false;
            }
        }
    }

    static class StringCondition implements Condition {
        enum Option {STARTS_WITH, CONTAINS, ENDS_WITH, NOT_CONTAINS, EQUALS, NOT_EQUALS}
        private final Option op;
        private final String fieldName;
        private final String value;

        StringCondition(Option op, String fieldName, String value) {
            this.op = op; 
            this.fieldName = fieldName.toLowerCase(); 
            this.value = value.toLowerCase();
        }

        private String getFieldValue(Employee e) {
            switch (fieldName) {
                case "name": return e.name == null ? "" : e.name.toLowerCase();
                case "designation": return e.designation == null ? "" : e.designation.toLowerCase();
                case "department": return e.department == null ? "" : e.department.toLowerCase();
                case "reportingto": return e.reportingTo == null ? "" : e.reportingTo.toLowerCase();
                default: return "";
            }
        }

        public boolean test(Employee e) {
            String fv = getFieldValue(e);
            switch (op) {
                case STARTS_WITH: return fv.startsWith(value);
                case CONTAINS: return fv.contains(value);
                case ENDS_WITH: return fv.endsWith(value);
                case NOT_CONTAINS: return !fv.contains(value);
                case EQUALS: return fv.equals(value);
                case NOT_EQUALS: return !fv.equals(value);
                default: return false;
            }
        }
    }

    // Factory/parser for conditions. This is where extensibility is concentrated.
    static class ConditionParser {
        private static final Map<String, StringCondition.Option> STRING_OPS = new HashMap<>();
        static {
            STRING_OPS.put("startswith", StringCondition.Option.STARTS_WITH);
            STRING_OPS.put("contains", StringCondition.Option.CONTAINS);
            STRING_OPS.put("endswith", StringCondition.Option.ENDS_WITH);
            STRING_OPS.put("notcontains", StringCondition.Option.NOT_CONTAINS);
            STRING_OPS.put("equals", StringCondition.Option.EQUALS);
            STRING_OPS.put("notequals", StringCondition.Option.NOT_EQUALS);
        }

        // parse a single clause, e.g. "age > 30" or "department contains finance" or "reporting to A"
        static Optional<Condition> parseClause(String clause) {
            clause = clause.trim();
            // make clause lower for detecting ops but keep raw tokens for values
            String lower = clause.toLowerCase();

            // numeric comparators
            if (lower.matches(".*\\bage\\b.*")) {
                if (lower.contains(">")) {
                    String[] parts = clause.split(">", 2);
                    int v = Integer.parseInt(parts[1].trim());
                    return Optional.of(new AgeCondition(AgeCondition.Option.GT, v));
                } else if (lower.contains("<")) {
                    String[] parts = clause.split("<", 2);
                    int v = Integer.parseInt(parts[1].trim());
                    return Optional.of(new AgeCondition(AgeCondition.Option.LT, v));
                } else if (lower.contains("!=")) {
                    String[] parts = clause.split("!=", 2);
                    int v = Integer.parseInt(parts[1].trim());
                    return Optional.of(new AgeCondition(AgeCondition.Option.NEQ, v));
                } else if (lower.contains("==") || lower.contains(" = ") || lower.contains("=")) {
                    String[] parts = clause.split("=", 2);
                    int v = Integer.parseInt(parts[1].trim());
                    return Optional.of(new AgeCondition(AgeCondition.Option.EQ, v));
                } else {
                    System.out.println("Unknown age comparator in clause: " + clause);
                    return Optional.empty();
                }
            }

            // string comparators
            for (String token : STRING_OPS.keySet()) {
                if (lower.contains(" " + token + " ") || lower.contains(token + " ") || lower.contains(" " + token)) {
                    // split on the token (first occurrence)
                    int idx = lower.indexOf(token);
                    String fieldPart = clause.substring(0, idx).trim();
                    String valuePart = clause.substring(idx + token.length()).trim();
                    
                    valuePart = valuePart.replaceFirst("^[:=\\s]+", "").trim(); // remove optional leading colon/equals
                    
                    String field = fieldPart.replaceAll("\\s+","").toLowerCase(); // field normalization
                    
                    field = field.replace("reportingto", "reportingto"); // special-case "reportingto" matches "reporting to"
                    StringCondition.Option op = STRING_OPS.get(token);
                    return Optional.of(new StringCondition(op, field, stripQuotes(valuePart)));
                }
            }

            // if no explicit operator, treat as equals on the last token after field name
            // e.g. "reporting to A" or "department finance" -> equals
            String[] parts = clause.split("\\s+");
            if (parts.length >= 2) {
                String field = parts[0].toLowerCase();
                // support "reporting to" two-word field
                if (field.equals("reporting") && parts.length >= 3 && parts[1].equalsIgnoreCase("to")) {
                    field = "reportingto";
                    String value = join(parts, 2);
                    return Optional.of(new StringCondition(StringCondition.Option.EQUALS, field, stripQuotes(value)));
                }
                // otherwise: field value
                String value = join(parts, 1);
                field = field.replaceAll("\\s+","");
                return Optional.of(new StringCondition(StringCondition.Option.EQUALS, field, stripQuotes(value)));
            }

            System.out.println("Could not parse clause: " + clause);
            return Optional.empty();
        }

        private static String stripQuotes(String s) {
            s = s.trim();
            if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
                return s.substring(1, s.length()-1);
            }
            return s;
        }

        private static String join(String[] arr, int start) {
            StringBuilder sb = new StringBuilder();
            for (int i = start; i < arr.length; i++) {
                if (i > start) sb.append(' ');
                sb.append(arr[i]);
            }
            return sb.toString();
        }

        // parse an entire query with default AND joining e.g. "age > 30 and department contains finance"
        static Optional<Condition> parseQuery(String query) {
            String[] clauses = query.split("(?i)\\s+and\\s+"); // split by case-insensitive 'and'
            List<Condition> conditions = new ArrayList<>();
            for (String c : clauses) {
                Optional<Condition> oc = parseClause(c);
                if (!oc.isPresent()) return Optional.empty();
                conditions.add(oc.get());
            }
            // compose conditions with AND
            return Optional.of(e -> {
                for (Condition cond : conditions) if (!cond.test(e)) return false;
                return true;
            });
        }
    }

    // -------------------- Application (Menu + Actions) --------------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // sample data: 10 employees (A is top)
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

        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": // Show all employee data
                    repo.findAll().forEach(System.out::println);
                    break;
                case "2": // interactive query mode
                    System.out.println("Enter query clauses (type 'exit' to return to menu). Example: age > 30 and department contains finance and reporting to A");
                    while (true) {
                        System.out.print("query> ");
                        String q = sc.nextLine().trim();
                        if (q.equalsIgnoreCase("exit")) break;
                        Optional<Condition> cond = ConditionParser.parseQuery(q);
                        if (!cond.isPresent()) {
                            System.out.println("Failed to parse query. Please try again.");
                            continue;
                        }
                        List<Employee> res = repo.findAll().stream().filter(cond.get()).collect(Collectors.toList());
                        if (res.isEmpty()) System.out.println("No matching employees.");
                        else res.forEach(System.out::println);
                    }
                    break;
                case "3": // show reporting hierarchy for a given employee name
                    System.out.print("Enter employee name: ");
                    String name = sc.nextLine().trim();
                    Optional<Employee> eOpt = repo.findByName(name);
                    if (!eOpt.isPresent()) {
                        System.out.println("Employee not found: " + name);
                        break;
                    }
                    showHierarchy(repo, eOpt.get());
                    break;
                case "4": // show employees reporting to given manager
                    System.out.print("Enter manager name: ");
                    String m = sc.nextLine().trim();
                    List<Employee> dr = repo.findDirectReports(m);
                    if (dr.isEmpty()) System.out.println("No direct reports found for " + m);
                    else dr.forEach(System.out::println);
                    break;
                case "5": // show summary counts
                    showSummaries(repo);
                    break;
                case "6":
                    System.out.println("Exiting application. Bye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Unknown option. Please choose 1..6");
            }
            System.out.println();
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("---------------- Employee Query Engine ----------------");
        System.out.println("1) Show all employee data");
        System.out.println("2) Process query (interactive; type 'exit' to return)");
        System.out.println("3) Show reporting hierarchy for an employee");
        System.out.println("4) Show employees reporting to a manager");
        System.out.println("5) Show summary of Department, Designation, ReportingTo");
        System.out.println("6) Exit");
        System.out.print("Select an option: ");
    }

    private static void showHierarchy(EmployeeRepository repo, Employee e) {
        // climb reporting chain
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
        // print J -> I -> F -> ... style
        System.out.println(String.join(" -> ", chain));
    }

    private static void showSummaries(EmployeeRepository repo) {
        List<Employee> all = repo.findAll();
        System.out.println("Department summary:");
        Map<String, Long> deptCounts = all.stream().collect(Collectors.groupingBy(e -> e.department, Collectors.counting()));
        deptCounts.forEach((k,v) -> System.out.printf("  %s: %d\n", k, v));

        System.out.println("Designation summary:");
        Map<String, Long> desigCounts = all.stream().collect(Collectors.groupingBy(e -> e.designation, Collectors.counting()));
        desigCounts.forEach((k,v) -> System.out.printf("  %s: %d\n", k, v));

        System.out.println("ReportingTo summary (direct reports count):");
        Map<String, Long> mgrCounts = all.stream().collect(Collectors.groupingBy(e -> e.reportingTo == null || e.reportingTo.isEmpty() ? "(none)" : e.reportingTo, Collectors.counting()));
        mgrCounts.forEach((k,v) -> System.out.printf("  %s: %d\n", k, v));
    }
}

/*
 * How to run:
Menu-driven console app (options):
    1. Show all employee data
    2. Interactive query mode (type exit to return to menu)
    3. Show reporting hierarchy for an employee (prints chain like J -> I -> F -> ... )
    4. Show employees reporting to a manager (direct reports)
    5. Show summary counts of Department, Designation, ReportingTo
    6. Exit

    * Query parser supports combined clauses joined by default AND. 
    * Example qery: age > 30 and age < 50 and department contains finance and reporting to A
    * Numeric comparators for age: >, <, !=, == (or =).
    * String comparators: startswith, contains, endswith, notcontains, equals, notequals.
    * If a clause omits an explicit operator (e.g., reporting to A), it defaults to equality.
    * Hierarchy traversal detects cycles and handles missing manager entries gracefully.
    * Extensible Condition factory: adding new operators or data types is localized to the parser and new Condition implementations.

Design & System-Design Notes (why it’s extensible / scales):
============================
Although this is an in-memory single-process implementation for the exercise, the code is structured with patterns that make it easy to scale:
1. Separation of concerns:
    * Repository layer (EmployeeRepository) isolates data access so you can swap in a DB (SQL/NoSQL), remote API, or file-based store without changing core logic.
    * Condition/Comparator classes encapsulate comparison logic; adding new operations or types requires new Condition implementations and parser entries only.
2. Extensible Query Engine:
    * The ConditionParser centralizes parsing; add new tokens (operators) and map them to new Condition classes to support additional comparisons or types (dates, floats, enums).
    * Conditions are Predicate<Employee> and composed with AND. Adding OR or grouped parentheses is straightforward (introduce AST nodes and an evaluator).
3. Scaling out (horizontal):
    * For larger datasets, move data to a dedicated datastore:
    * Read-heavy: use a replicated read-optimized store (Elasticsearch, read replicas) to support fast text queries (contains, startsWith).
    * 8 Write-heavy: use sharded databases and asynchronous indexing.
    * Expose the engine via a microservice API (REST/gRPC). Deploy multiple service instances behind a load balancer (Kubernetes). Use shared datastore and consistent caching.
    * Add caching layer (Redis or in-memory LRU) for common queries or hierarchy lookups.
4. Feature addition / backward compatibility:
    * New operators: add to parser map and implement Condition class — no change to menu or consumers.
    * New fields: add to model + repository + condition field mapping. Parser remains the same.
    * Complex queries (grouping, parentheses): enhance parser to produce AST and evaluator, still reusing Condition implementations.
5. Performance & reliability:
    * For fast hierarchical queries (reporting chains, transitive closure), maintain an adjacency list and precompute ancestor chains or use graph DB (Neo4j) for very large orgs.
    * Monitoring: instrument endpoints and background jobs with metrics (Prometheus) and logs (ELK).
    * Resilience: circuit breakers, retries, and graceful degradation.
6. Security & multi-tenancy:
    * Add authentication/authorization at API gateway.
    * Tenant separation via schema or row-level security in DB.

How to extend (practical examples)
    => Add OR: parse query into tokens and build an AST where internal nodes are AND/OR; evaluate accordingly.
    => Add dateOfJoining filter: add field to Employee, create DateCondition and parse date tokens (ISO format).
    => Swap in DB: implement EmployeeRepository methods to translate Conditions into SQL/DSL or pass queries to Elasticsearch.

Sample queries to try in option 2:
    age > 30 and department contains finance
    department equals Engineering and age < 40
    reporting to B
    name startswith J
 */