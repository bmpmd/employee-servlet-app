package com.revature.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {

	/*
	 * this method will be responsible for determining what resource the client is
	 * requesting
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. URI rewriting
		// http://localhost:8080/employee-servlet-app/login -- want to capture login
		// http://localhost:8080/employee-servlet-app/login --if they go here it returns
		// all employees in the DB
		final String URI = request.getRequestURI().replace("/employee-servlet-app/", "");// eliminate everyting but the
																							// uri

		// 2. set switch case statement in which we call the appropriate functionality
		// based on the URI returned
		// ie. login, register, depends on uri
		switch (URI) {
		case "login":
			// invoke some function from the RequestHelper
			RequestHelper.processLogin(request, response);//forwards request+response object to reqwuest helper which will do stuff w the requyest  
			break;
		case "employees":
			// ivoke some functionality from the RequestHelper which would return all
			RequestHelper.processEmployees(request, response);
			break;
		case "register":
			RequestHelper.processRegistration(request, response);
			break;
		default:
			// custom error page in case someone put garbage in uri here
			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
