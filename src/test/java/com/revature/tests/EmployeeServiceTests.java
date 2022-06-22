package com.revature.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class EmployeeServiceTests {

	private EmployeeService eserv;
	private EmployeeDao mockdao;

	@Before
	public void setup() {
		mockdao = mock(EmployeeDao.class);
		eserv = new EmployeeService(mockdao);
	}

	@After
	public void teardown() {
		mockdao = null;
		eserv = null;
	}

	/*
	 * happy/fail path for confirmLogin
	 */
	@Test
	public void testConfirmLogin_Success() {
		// 1 create a fake list of emps
		// this is the dummy data we feed to Mockito
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green");
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows");

		List<Employee> emps = new LinkedList<Employee>();
		emps.add(e1);
		emps.add(e2);

		// 2 setup the mockdao's behavior
		// findAll() method's data to provide fake data
		when(mockdao.findAll()).thenReturn(emps);

		// 3 capture acutal output of the method
		// capture expected ouytput of the methods

		// login as bruce, mockdao will mock the behjavior of findall
		Employee actual = eserv.confirmLogin("thehulk", "green");
		Employee expected = e1;
		// asset that they're equal
		assertEquals(expected, actual);

	}

	@Test
	public void testConfirmLogin_Fail() {
		// returns an empty employee obj if fails
		// 1 create a fake list of emps
		// this is the dummy data we feed to Mockito
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green");
		Employee e2 = new Employee(21, "Clint", "Barton", "hawkeye", "arrows");

		List<Employee> emps = new LinkedList<Employee>();
		emps.add(e1);
		emps.add(e2);
		// 2 setup the mockdao's behavior
		// findAll() method's data to provide fake data
		when(mockdao.findAll()).thenReturn(emps);
		// 3 capture acutal output of the method
		// capture expected ouytput of the methods

		// login as bruce, mockdao will mock the behjavior of findall
		Employee actual = eserv.confirmLogin("thehulk", "blue");//bad password, so login fails 
		Employee expected = new Employee();//expected to be empty 
		
		//assrert that it's an empty employee
		assertEquals(actual, expected);

	}
	
}
