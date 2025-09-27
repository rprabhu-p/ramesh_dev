package Advanced.EmployeeQueryEngine.query;

import Advanced.EmployeeQueryEngine.model.Employee;

public class AgeCondition implements Condition {
    public enum Option {GT, LT, EQ, NEQ}
    private final Option op;
    private final int value;

    public AgeCondition(Option op, int value) {
        this.op = op;
        this.value = value;
    }

    @Override
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
