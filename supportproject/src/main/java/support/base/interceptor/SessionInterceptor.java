package support.base.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import support.base.util.RedisUtil;

@Repository
public class SessionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// 请求路径
		String path = request.getRequestURI();
		String clientToken = request.getParameter("token");
		// 获取redis里面的token
		String serverToken = RedisUtil.get("token:" + clientToken);
		if(StringUtils.isNotEmpty(clientToken)&&StringUtils.isEmpty(serverToken)){
			RedisUtil.set("token:" + clientToken, clientToken);
		}
		if (StringUtils.isEmpty(clientToken)) {
			boolean loginFlag = path.contains("queryTopicCollect") || path.contains("queryProductCollect");
			if (loginFlag) {
				JSONObject json = new JSONObject();
				json.put("code", 0);
				json.put("info", "请登录");
				try {
					response.setCharacterEncoding("UTF-8");
					System.out.println(json.toString());
					response.setContentType("application/json;charset=utf-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write(json.toString());
					response.getWriter().flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		}
		return true;
	}

}
