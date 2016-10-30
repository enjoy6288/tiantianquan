package support.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//后台是否登录拦截器
@Repository
public class BackLoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Object value = session.getAttribute("logined");
		if(value!=null){
			return true;
		}
		// 挑战到登录页面
		request.getRequestDispatcher("/WEB-INF/jsp/base/login.jsp").forward(request, response);
		return false;
	}

}
