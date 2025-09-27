package Advanced.EmployeeQueryEngine.query;

import Advanced.EmployeeQueryEngine.model.Employee;

public class StringCondition implements Condition {
    public enum Option {STARTS_WITH, CONTAINS, ENDS_WITH, NOT_CONTAINS, EQUALS, NOT_EQUALS}
    private final Option op;
    private final String fieldName;
    private final String value;

    public StringCondition(Option op, String fieldName, String value) {
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

    @Override
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
