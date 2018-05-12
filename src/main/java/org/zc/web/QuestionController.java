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
import org.zc.service.QuestionService;
import org.zc.utils.DateUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 获取问题和提交问题
 *
 * @author xuly
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
	Logger logger = Logger.getLogger(QuestionController.class);

	@Autowired
	private QuestionService questionService;

	/**
	 * 根据任务id和获取当前登录用户问题列表
	 * @param response
	 * @param request
	 * @return
	 */
	@GetMapping("/getQuestionListByTaskRecordId")
	@ResponseBody
	public Map<String, Object> getEntranceQuestionListByVehiclePlate(HttpServletResponse response, HttpServletRequest request) {
		// 解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserLoginInfoDTO currentUser = CacheCurrentUserThreadLocal.getCurrentUser();
		String version = request.getParameter("version");// version name
		String os = request.getParameter("os");// os		//操作系统
		String taskRecordId = request.getParameter("taskRecordId");// 任务流转记录id
		String status = request.getParameter("status");			//任务流转状态标识
		try {

			logger.info("getQuestionListByTaskRecordId接口调用，收到任务信息数据：version=" + version + " currentUser:" + currentUser + " os: " + os + " time:" + DateUtils.getPatternDateTime(DateUtils.DATE_STR_WHOLE, null)
					+ "\r\n 任务id：" + "taskRecordId:" + taskRecordId);

			if(StringUtils.isBlank(taskRecordId)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "任务流转记录id为必填项");
				return resultMap;
			}
			if(currentUser == null) {
				resultMap.put("msg", "未查找到登录用户信息，请重新登录！");
				resultMap.put("code", -101);
				return resultMap;
			}
			if(StringUtils.isBlank(status)) {
				resultMap.put("msg", "任务流转状态记录错误！");
				resultMap.put("code", 0);
				return resultMap;
			}

			return questionService.getQuestionListByTaskRecordId(Integer.parseInt(taskRecordId), Integer.parseInt(status));
		} catch (NumberFormatException e) {
			logger.info("获取问题列表信息数据异常error-数据类型转换异常");
			resultMap.put("msg", "参数格式错误！");
			resultMap.put("code", 0);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", 0);
			resultMap.put("msg", "查询失败，请重试");
			return resultMap;
		}
	}
	
	/**
	 * 提交问题
	 * @param response
	 * @param request
	 * @return
	 */
	@PostMapping("/commitQuestion")
	@ResponseBody
	public Map<String, Object> commitQuestion(HttpServletResponse response, HttpServletRequest request) {
		// 解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String version = request.getParameter("version");// version name
		String os = request.getParameter("os");// os		//操作系统
		String taskRecordId = request.getParameter("taskRecordId");// 任务流转记录id
		String status = request.getParameter("status");			//任务流转状态标识
		String itemId = request.getParameter("itemId");			//质检项id
		String itemTitle = request.getParameter("itemTitle");			//质检项问题
		String content = request.getParameter("content");			//自定义答案
		String chooseContent = request.getParameter("chooseContent");			//选择答案
		String type = request.getParameter("type");			//答题类型0为判断题，1为选择题，2为填空题 ，3为多选题
		String userId = request.getParameter("userId");			//操作人id
		String isCorrect = request.getParameter("isCorrect");			//针对专检和监造
		
		if(StringUtils.isBlank(taskRecordId) || StringUtils.isBlank(itemId) || StringUtils.isBlank(status) 
				 || StringUtils.isBlank(userId)) {
			resultMap.put("msg", "请填写必填项！！！");
			resultMap.put("code", 0);
			return resultMap;
		}
		
		try {
			return questionService.commitQuestionList(Integer.parseInt(userId.trim()), Integer.parseInt(taskRecordId.trim()), Integer.parseInt(itemId.trim()), itemTitle, content, chooseContent, type, Integer.parseInt(status.trim()));
		} catch (NumberFormatException e) {
			logger.error("答题数据异常error-数据类型转换异常");
			resultMap.put("msg", "参数格式错误！");
			resultMap.put("code", 0);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", 0);
			resultMap.put("msg", "查询失败，请重试");
			return resultMap;
		}
	}
	
	/**
	 * 提交问题-batch
	 * @param response
	 * @param request
	 * @return
	 */
	@PostMapping("/batchCommitQuestion")
	@ResponseBody
	public Map<String, Object> batchCommitQuestion(HttpServletResponse response, HttpServletRequest request) {
		// 解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String version = request.getParameter("version");// version name
		String os = request.getParameter("os");// os		//操作系统
		String taskRecordId = request.getParameter("taskRecordId");// 任务流转记录id
		String status = request.getParameter("status");			//任务流转状态标识
		
		String answersList = request.getParameter("answersList");
		
		if(StringUtils.isBlank(taskRecordId) || StringUtils.isBlank(status)) {
			resultMap.put("msg", "请填写必填项！！！");
			resultMap.put("code", 0);
			return resultMap;
		}
		
		JSONArray platformList = JSON.parseArray(answersList);
		try {
			return questionService.batchCommitQuestionList(platformList, Integer.parseInt(taskRecordId), Integer.parseInt(status));
		} catch (NumberFormatException e) {
			logger.error("答题数据异常error-数据类型转换异常");
			resultMap.put("msg", "参数格式错误！");
			resultMap.put("code", 0);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", 0);
			resultMap.put("msg", "查询失败，请重试");
			return resultMap;
		}
	}
}
