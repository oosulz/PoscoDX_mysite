package mysite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.service.UserService;
import mysite.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		System.out.println("LoginInterceptor triggered for path: " + request.getRequestURI());
		System.out.println("email :"+ email+" || password :"+ password);
		
		UserVo authUser = userService.getUser(email, password); 
		if(authUser == null) {
			request.setAttribute("email", email);
			request.setAttribute("result", "fail");
			request
				.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp")
				.forward(request, response);
			
			return false;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("authUser", authUser);
		
		response.sendRedirect(request.getContextPath());
		
		System.out.println("[authUser] : " +authUser);
		
		return false;
	}

	

}
