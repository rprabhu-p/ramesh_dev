package Advanced.EmployeeQueryEngine.query;

import Advanced.EmployeeQueryEngine.model.Employee;
import java.util.function.Predicate;

public interface Condition extends Predicate<Employee> {}