package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Employee;
import com.revature.util.HibernateUtil;

//no interfaces necessary for this simple project

public class EmployeeDao {

	//CRUD methods 
	//create (think that in the service layer we'll have a register() method )
	public int insert(Employee e) {
		//import hibernate 
		//grab sess obj 
		Session ses = HibernateUtil.getSession();
		
		//begin tx 
		Transaction tx = ses.beginTransaction();
		//capture pk returned when the session method save() is called
		int pk = (int) ses.save(e);
		
		//AND COMMIT THE TRANSACTION!! 
		tx.commit();
		
		
		//return the pk 
		return pk;
	}
	
	
	//read 
	public List<Employee> findAll(){
		
		//grab session 
		Session ses = HibernateUtil.getSession();
		//make an HQL statement -- Hibernate Query Language, odd mix of OOP and native SQL 
		//aka, mixed one 
		List<Employee> employees = ses.createQuery("from Employee", Employee.class).list();//[SELECT] from [table, but object name].list() to ge tthe list 
		
		return employees;
	}
	
	//update
	public boolean update(Employee e) {
		return false;
	}
	
	//delete 
	public boolean delete(int id) {
		return false; 
	}
	
	
}
