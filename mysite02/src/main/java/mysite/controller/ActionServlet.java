package mysite.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("unused")
public abstract class ActionServlet extends HttpServlet {
	
	//factoryMethod
	abstract Action getAction(String actionName);
	
	//operation
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.setCharacterEncoding("utf-8");
		Optional<String> optionalActionName = Optional.ofNullable(request.getParameter("a"));
	
		Action action = getAction(optionalActionName.orElse(""));
		//Action action  =getAction(optionalActionName.isEmpty() ? "" : optionalActionName.get());
		action.execute(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	public static interface Action {
		void execute(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException;
		
	}

}
