package Advanced.RailwaySystem.model;

public class User {
    private String name;
    private int priority; // Higher value = higher priority

    public User(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
