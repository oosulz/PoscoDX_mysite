package mysite.security;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sun.jdi.Type;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.vo.UserVo;
public class AuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		Auth auth = null;
		
		//1. Handler 종류 확인 (들어온 요청이 실제 컨트롤러로 가는 요청인지, 아니면 단순히 이미지나 CSS 같은 정적 파일을 요청하는 건지 구분)
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletRequestHandler 타입인 경우
			// DefaultServletHandler가 처리하는 경우(정적자원, /assets/**, mapping이 안되어 있는 URL)
			return true;
		}
		
		//2. casting (실제 어떤 컨트롤러의 메소드가 요청을 처리하는지 확인)
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Handler에서 @Auth 가져오기
		auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		if(auth == null) {
			auth = handlerMethod
					.getMethod()
					.getDeclaringClass()
					.getAnnotation(Auth.class);
		}
		
		//5. type 이나 method에 @Auth가 없는 경우
		if(auth == null) {
			return true;
		}
		
		//6. @Auth가 붙어 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		//7.권한(Authorization) 체크를 위해 role("USER", "ADMIN") 가져오기
		String role = auth.role();

		//8. @Auth의 role이 "USER"인 경우, authUser의 role은 "USER" 또는 "ADMIN" 이던 상관없다.
		if("USER".equals(role)) {
			return true;
		}
		
		//9. @Auth의 role이 "ADMIN"인 경우, authUser의 role은 반드시 "ADMIN" 이어야 한다.
		if(!"ADMIN".equals(authUser.getRole())) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		//10. 옳은 관리자 권한 [@Auth(role="ADMIN") && authUser.role="ADMIN"] 
		return true;
	}
	
}