package support.base.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import redis.clients.jedis.Jedis;
import support.base.util.CommonUse;
import support.base.util.RedisUtil;

@Repository
public class SessionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// 请求路径
		String path = request.getRequestURI();
		String clientToken = request.getParameter("token");
		if (StringUtils.isEmpty(clientToken)) {
			if (path.contains("queryTopicCollect") || path.contains("queryProductCollect")) {
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
		} else {
			Jedis jedis = RedisUtil.getJedis();
			String serverToken = jedis.get(clientToken);
			RedisUtil.closeRedis();
			if (serverToken!=null) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

}
