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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zc.common.Constant;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.dto.UserLoginInfoDTO;
import org.zc.service.AnswerService;
import org.zc.utils.DateUtils;

/**
 * Description:	答题
 * Create by @author cpc
 * 2018年5月5日 下午2:20:31
 */
@Controller
@RequestMapping("/answer")
public class AnswerController {
	Logger logger = Logger.getLogger(AnswerController.class);

    @Autowired
    private AnswerService answerService;

	/**
     * 获取答题情况信息
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/getAnswerList")
    @ResponseBody
    public Map<String, Object> getAnswerList(HttpServletResponse response, HttpServletRequest request) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            UserLoginInfoDTO currentUser = CacheCurrentUserThreadLocal.getCurrentUser();
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统
            String taskRecordId = request.getParameter("taskRecordId");  //任务流转记录id
            String status = request.getParameter("status");         //status标识

            // 默认从第1页，pageSize为10
 			Integer pageNumber = StringUtils.isNotBlank(request.getParameter("pageNumber")) ? Integer.parseInt(request.getParameter("pageNumber").trim())
 							: Constant.DEFAULT_PAGE_NUMBER;
 			Integer pageSize = StringUtils.isNotBlank(request.getParameter("pageSize")) ? Integer.parseInt(request.getParameter("pageSize").trim())
 							: Constant.DEFAULT_PAGE_SIZE;
            
            if(currentUser == null) {
                resultMap.put("msg", "未查找到登录用户信息，请重新登录！");
                resultMap.put("code", -101);
                return resultMap;
            }

            if(StringUtils.isBlank(taskRecordId) || StringUtils.isBlank(status)) {
                resultMap.put("msg", "请求参数未传递！");
                resultMap.put("code", 0);
                return resultMap;
            }

            //记录操作日志
            logger.info("获取答题情况信息数据：v=" + version + " o: " + os + " time:" + DateUtils.getPatternDateTime(DateUtils.DATE_STR_WHOLE, null) + "\r\n 登录用户信息如下："
                    + " currentUser:" + currentUser);

            if(pageNumber < 1) {
            	resultMap.put("msg", "页码参数错误，最小为1！");
                resultMap.put("code", 0);
                return resultMap;
            }
            return answerService.getAnswerList(Integer.parseInt(taskRecordId), Integer.parseInt(status), pageNumber-1, pageSize);
        } catch (NumberFormatException e) {
        	logger.info("获取用户任务列表信息数据异常error-数据类型异常");
            resultMap.put("msg", "参数格式错误！");
            resultMap.put("code", 0);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取用户任务列表信息数据异常error");
            resultMap.put("msg", "获取用户任务列表信息数据异常！");
            resultMap.put("code", 0);
            return resultMap;
        }
    }
}
