package com.revature.service;



import java.util.List;
import java.util.Optional;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;

public class EmployeeService {

	//remmeber: service layer relies on a DAO object w constructor injection into every service class 
	
	private EmployeeDao edao;
	
	/*
	 * dependency injection via constructor injection 
	 * 
	 * constructor injection is a sophisticated way of ensuring 
	 * that the EmployeeService object ALWAYS has an EmployeeDao object 
	 * 
	 *  brilliant way of ensuring it has what it neds 
	 */
	public EmployeeService(EmployeeDao edao) {
		this.edao = edao;
	}
	
	/*
	 * this is an idea of an implementation 
	 * 
	 * who is suppplying params?: servlet will
	 */
	public Employee confirmLogin(String username, String password){
		//let's stream thru all employees that are returned
		//
		Optional<Employee> possibleEmp = edao.findAll().stream()
				.filter(e->(e.getUsername().equals(username)) && (e.getPassword().equals(password)))
				.findFirst(); //open stream. optional because we might not find it. the predicate: for every employee e,
								//if the username matches and password matches, find first of those and set possibleEmp to that. 
		
		
		//if emp is present, return it, otherwise reutrn (null) or (empty employee obj)
		return (possibleEmp.isPresent()? possibleEmp.get() : new Employee());
		//ideally you should optimize this and setup a custom exception 
		
	}
	
	public List<Employee> getAll(){
		return edao.findAll();
	}
	
	
	
}
