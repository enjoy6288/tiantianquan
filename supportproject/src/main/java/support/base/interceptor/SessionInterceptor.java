package support.base.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

@Repository
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	String loginName = (String) WebUtils.getSessionAttribute(request, "loginName");
        if(StringUtils.isEmpty(loginName)){
            JSONObject json = new JSONObject();
            json.put("code", 0);
            json.put("info", "未登录或登陆超时");
            try {
            response.setCharacterEncoding("UTF-8");  
            System.out.println(json.toString());
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(json.toString());
            response.getWriter().flush();
            response.sendRedirect("https://www.baidu.com");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
    
}
