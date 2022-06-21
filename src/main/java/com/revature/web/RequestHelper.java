package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

/*
 * dispatcher is also called a RequestHelper 
 * 
 * IS NOT a servlet. the front controller IS A servlet. 
 * 
 * this class just has lots of methods. 
 */
public class RequestHelper {
	//employeeService
	private static EmployeeService eserv = new EmployeeService(new EmployeeDao());
	
	//object mapper (for frontend) from jackson 
	private static ObjectMapper om = new ObjectMapper();

	//even tho not a servlet, think about what this method does 
	//waht it do? 
	/*
	 * it extracts the params 
	 * from a request (username and password) from the UI 
	 * 
	 * It will call the confirmLogin() method from the EmployeeService and 
	 * see if a user with that username and password exists 
	 * 
	 * Q: who will provide the method with the HttpRequest? A: The UI 
	 * -> we will need to build an html doc with a form that will send these params to the method 
	 */
	public static void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		//1. Extract the parameters from the request (username and password)
		String username = request.getParameter("username");//name of param
		String password = request.getParameter("password");//name of param
		
		// 2. call the confirm login () method from the EmployeeService and see what it returns 
		Employee e = eserv.confirmLogin(username, password);
		
		//3. if user exists, let's print their info to screen for now 
			
		if(e.getId() > 0) {
			//grab the session 
			HttpSession ses = request.getSession();
			// add user to the session 
			ses.setAttribute("the-user", e);//set the Employee/User to The Employee object 
			// alternatively, you can redirect to another servlet 
			//print out user's data w print writer 
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<h1>Welcome "+ e.getFirstName()+"!</h1>");
			out.println("<h3>Welcome you have sucessfully logged in!</h3>");
			//you could print the obj out as a json string 
			String jsonStr = om.writeValueAsString(e);//pass in user object
			out.println(jsonStr);
		}
		else {
			//if user does not exist (id = 0)
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("no user found. sorry.");
			//response.setStatus(204);//204 means successful connection to server, but no content found 
		}
			
		
		
		
	}
}
