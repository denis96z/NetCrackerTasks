package ru.ncedu.java.tasks;

public class EmployeeImpl implements Employee {
	private String firstName;
	private String lastName;
	private Employee manager;
	private int salary;
	
	public EmployeeImpl() {
		salary = 1000;
	}
	
	public int getSalary() {
		return salary;
	}
	
	public void increaseSalary(int value) {
		salary += value;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
	public String getManagerName() {
		return manager == null ? "No manager" : manager.getFullName();
	}
	
	public Employee getTopManager() {
		return manager == null ? this : manager.getTopManager();
	}
}
