package EmployeeQueryEngine.query;

import java.util.function.Predicate;

import EmployeeQueryEngine.model.Employee;

public interface Condition extends Predicate<Employee> {}