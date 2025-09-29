package EmployeeQueryEngine.model;

public class Employee {
    public int id;
    public int age;
    public String name;
    public String designation;
    public String department;
    public String reportingTo;

    public Employee(int id, String name, int age, String designation, String department, String reportingTo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.department = department;
        this.reportingTo = reportingTo;
    }

    @Override
    public String toString(){
        return String.format("ID=%d, Name=%s, Age=%d, Designation=%s, Department=%s, ReportingTo=%s",
                    id, name, age, designation, department, reportingTo);
    }
}
