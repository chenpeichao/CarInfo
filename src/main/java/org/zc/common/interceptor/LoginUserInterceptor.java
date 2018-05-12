package org.zc.common.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zc.common.Constant;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.dto.UserLoginInfoDTO;
import org.zc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求用户注入
 * Created by ceek on 2018-04-23 2:45.
 */
public class LoginUserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            //根据当前传递的用户id查询用户的相关信息
            String userId = request.getParameter("userId");
            if(StringUtils.isBlank(userId)) {
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("code", -101);
                resultMap.put("msg", "未查找到登录用户信息，请重新登录！");
                String userJson = JSON.toJSONString(resultMap);
                OutputStream out = response.getOutputStream();
                out.write(userJson.getBytes("UTF-8"));
                return false;
            }
            UserLoginInfoDTO userLoginInfoDTO = userService.getUserLoginInfoDTO(userId);

            if(userLoginInfoDTO == null || userLoginInfoDTO.getRoleMap().size() <= 0) {
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("code", -101);
                resultMap.put("msg", "未查找到登录用户信息，请重新登录！");
                String userJson = JSON.toJSONString(resultMap);
                OutputStream out = response.getOutputStream();
                out.write(userJson.getBytes("UTF-8"));
                return false;
            }
            if(userLoginInfoDTO.getRoleMap().size() == 1 && userLoginInfoDTO.getRoleMap().containsKey(Constant.STAFF_ROLE_ID)) {
            	response.setContentType("application/json; charset=utf-8");
            	response.setCharacterEncoding("UTF-8");
            	Map<String, Object> resultMap = new HashMap<String, Object>();
            	resultMap.put("code", -101);
            	resultMap.put("msg", "普通员工，无权登录！");
            	String userJson = JSON.toJSONString(resultMap);
            	OutputStream out = response.getOutputStream();
            	out.write(userJson.getBytes("UTF-8"));
            	return false;
            }
            CacheCurrentUserThreadLocal.putUser(userLoginInfoDTO);
            return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
