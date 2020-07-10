package smp.ytr.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("============================ START ================================");
		HttpSession session  = request.getSession(false);

//		if(session == null) {
//			response.sendRedirect(request.getContextPath()+"/");
//			return false;
//		}

		return true;
	}
}
