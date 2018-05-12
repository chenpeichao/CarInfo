package org.zc.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.dto.UserLoginInfoDTO;
import org.zc.service.UserService;
import org.zc.utils.DateUtils;

/**
 * Description:	用户
 * Create by @author cpc
 * 2018年5月5日 上午10:03:57
 */
@Controller
@RequestMapping("/user")
public class UserController {
	Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
     * 获取员工列表
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/userLogin")
    @ResponseBody
    public Map<String, Object> userLogin(HttpServletResponse response, HttpServletRequest request) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        String version = request.getParameter("version");// 版本
        String os = request.getParameter("os");// 操作系统
        String username = request.getParameter("username");	// 任务流转记录id
        String password = request.getParameter("password");	// 任务流转记录id
        if(StringUtils.isBlank(username)) {
        	resultMap.put("msg", "用户名为必填项！");
            resultMap.put("code", 0);
            return resultMap;
        }
        if(StringUtils.isBlank(password)) {
        	resultMap.put("msg", "密码为必填项!");
        	resultMap.put("code", 0);
        	return resultMap;
        }
        //记录操作日志
        logger.info("用户登录信息数据：v=" + version + " o: " + os + " time:" + DateUtils.getPatternDateTime(DateUtils.DATE_STR_WHOLE, null) + "\r\n 登录用户信息如下："
                + " username:" + username + "----password:" + password);
        try {
        	return userService.userLogin(username.trim(), password.trim());
        } catch(Exception e) {
        	e.printStackTrace();
            logger.info("用户登录信息数据异常error");
            resultMap.put("msg", "用户登录信息数据异常，请稍后再试！");
            resultMap.put("code", 0);
            return resultMap;
        }
    }
	
	/**
     * 获取员工列表
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/getStaffList")
    @ResponseBody
    public Map<String, Object> getStaffList(HttpServletResponse response, HttpServletRequest request) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            UserLoginInfoDTO currentUser = CacheCurrentUserThreadLocal.getCurrentUser();
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统
            String taskRecordId = request.getParameter("taskRecordId");	// 任务流转记录id

            if(currentUser == null) {
                resultMap.put("msg", "未查找到登录用户信息，请重新登录！");
                resultMap.put("code", -101);
                return resultMap;
            }

            //记录操作日志
            logger.info("获取员工列表信息数据：v=" + version + " o: " + os + " time:" + DateUtils.getPatternDateTime(DateUtils.DATE_STR_WHOLE, null) + "\r\n 登录用户信息如下："
                    + " currentUser:" + currentUser);
            if(StringUtils.isBlank(taskRecordId)) {
            	resultMap.put("msg", "未查找到任务流转记录id！");
                resultMap.put("code", 0);
                return resultMap;
            }
            
            return userService.getStaffListInStationByTaskRecordId(Integer.parseInt(taskRecordId.trim()));
        } catch (NumberFormatException e) {
        	logger.info("获取员工列表信息数据异常error-数据类型异常");
            resultMap.put("msg", "参数格式错误！");
            resultMap.put("code", 0);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取员工列表信息数据异常error");
            resultMap.put("msg", "获取员工列表信息数据异常！");
            resultMap.put("code", 0);
            return resultMap;
        }
    }
}
