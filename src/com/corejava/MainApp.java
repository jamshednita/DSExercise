package com.corejava;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Employee emp1 = new Employee(101, "XYZ");
		Employee emp2 = new Employee(102, "XYZ");
		
		Set<Employee> sortedEmp= new TreeSet<Employee>();
		
		sortedEmp.add(emp1);
		sortedEmp.add(emp2);
		
		Set<Employee> emps= new HashSet<Employee>();
		
		emps.add(emp1);
		emps.add(emp2);
		
		System.out.println(sortedEmp.size());
		System.out.println(emps.size());
	}

}
