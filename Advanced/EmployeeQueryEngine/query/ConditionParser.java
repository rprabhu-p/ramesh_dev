package Advanced.EmployeeQueryEngine.query;


import java.util.*;
//import Advanced.EmployeeQueryEngine.model.Employee;

public class ConditionParser {
    private static final Map<String, StringCondition.Option> STRING_OPS = new HashMap<>();
    static {
        STRING_OPS.put("startswith", StringCondition.Option.STARTS_WITH);
        STRING_OPS.put("contains", StringCondition.Option.CONTAINS);
        STRING_OPS.put("endswith", StringCondition.Option.ENDS_WITH);
        STRING_OPS.put("notcontains", StringCondition.Option.NOT_CONTAINS);
        STRING_OPS.put("equals", StringCondition.Option.EQUALS);
        STRING_OPS.put("notequals", StringCondition.Option.NOT_EQUALS);
    }

    public static Optional<Condition> parseClause(String clause) {
        clause = clause.trim();
        String lower = clause.toLowerCase();

        if (lower.contains("age")) {
            if (lower.contains(">")) {
                int v = Integer.parseInt(clause.split(">", 2)[1].trim());
                return Optional.of(new AgeCondition(AgeCondition.Option.GT, v));
            } else if (lower.contains("<")) {
                int v = Integer.parseInt(clause.split("<", 2)[1].trim());
                return Optional.of(new AgeCondition(AgeCondition.Option.LT, v));
            } else if (lower.contains("!=")) {
                int v = Integer.parseInt(clause.split("!=", 2)[1].trim());
                return Optional.of(new AgeCondition(AgeCondition.Option.NEQ, v));
            } else if (lower.contains("=")) {
                int v = Integer.parseInt(clause.split("=", 2)[1].trim());
                return Optional.of(new AgeCondition(AgeCondition.Option.EQ, v));
            }
        }

        for (String token : STRING_OPS.keySet()) {
            if (lower.contains(" " + token + " ")) {
                int idx = lower.indexOf(token);
                String field = clause.substring(0, idx).trim().replaceAll("\\s+","").toLowerCase();
                String value = clause.substring(idx + token.length()).trim();
                return Optional.of(new StringCondition(STRING_OPS.get(token), field, stripQuotes(value)));
            }
        }

        String[] parts = clause.split("\\s+");
        if (parts.length >= 2) {
            String field = parts[0].toLowerCase();
            if (field.equals("reporting") && parts[1].equalsIgnoreCase("to")) {
                String value = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
                return Optional.of(new StringCondition(StringCondition.Option.EQUALS, "reportingto", stripQuotes(value)));
            }
            String value = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
            return Optional.of(new StringCondition(StringCondition.Option.EQUALS, field, stripQuotes(value)));
        }
        return Optional.empty();
    }

    public static Optional<Condition> parseQuery(String query) {
        String[] clauses = query.split("(?i)\\s+and\\s+");
        List<Condition> conditions = new ArrayList<>();
        for (String c : clauses) {
            Optional<Condition> oc = parseClause(c);
            if (!oc.isPresent()) return Optional.empty();
            conditions.add(oc.get());
        }
        return Optional.of(e -> conditions.stream().allMatch(cond -> cond.test(e)));
    }

    private static String stripQuotes(String s) {
        s = s.trim();
        if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
            return s.substring(1, s.length()-1);
        }
        return s;
    }
}
