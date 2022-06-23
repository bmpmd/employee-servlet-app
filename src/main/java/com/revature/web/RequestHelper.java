package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

	/*
	 * this method will call the employee service's findAll() method 
	 *  -- use an object mapper to transform the list to a JSON string 
	 *  then use printwriter to print out json string to screen
	 *  
	 */
	public static void processEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		//http://localhost:8080/employee-servlet-app/employees
		// will return the entire list of employees in json ..
		
		//1 set content type of respones 
		//response.setContentType("text/html");
		response.setContentType("application/json");
		
		//2 call findall method from eserv 
		 List<Employee> emps = eserv.getAll();
		 
		//3 transform the lsit to a string 
		String jsonString = om.writeValueAsString(emps);
		//4 write it out (get printwriter b4 tho)
		PrintWriter out = response.getWriter();
		out.write(jsonString);// writes to response body rather than print on page
		
		
		
	}
	
	public static void processRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		//since we're forwarding the request from front ctrler, steps needed
		
		//1. Extract the parameters from the request (username and password, etc )
		String firstname = request.getParameter("firstname");//name of param
		String lastname = request.getParameter("lastname");//name of param
		String username = request.getParameter("username");//name of param
		String password = request.getParameter("password");//name of param
		
		//2 construct a new eployee object 
		Employee e = new Employee(firstname, lastname, username, password);
		
		//3 call the register() method from the service layer 
		int pk = eserv.register(e);//this retunrs pk 
		
		//4 check its id.. if > 0, then successful new user. 
		if(pk > 0) {
			//sucessful register 
			
			
			//add user to session 
			e.setId(pk);//make surte to set the pk from the one in the db gave it 
			HttpSession ses = request.getSession();
			ses.setAttribute("the-user", e);
			
			// and using the Request helper, forward the request and response to a new resource..
			//send the user to a new page -- welcome.html 
			request.getRequestDispatcher("welcome.html").forward(request, response);//have yet to make this. in webapp create this page 
			
			
		}
		else {
			
			//TODO: provide better logic in the Service layer to check for PSQL exceptions...
			//-> not catching it right, displays err 
			
			//fail to register new emp 
			//if it's -1 that means the register method failed (and there's probably a duplicate user)
			//diff msg for diff fails? like failed to register doesnt always mean dupe user 
			
			//use printwriter to print out 
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			//could geneerate page on the fly Or just make these pages and point them like we did above there lol 
			out.println("<h1> Regsitration failed. User already exists.</h1>");
			out.println("<a href=\"index.html\">Back</a>");

		}
			
		
		
		
	}
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
