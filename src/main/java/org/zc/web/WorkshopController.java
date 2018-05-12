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
import org.zc.service.WorkshopService;

/**
 * 车间
 * Created by ceek on 2018-04-24 21:50.
 */
@Controller
@RequestMapping("/workshop")
public class WorkshopController {
    Logger logger = Logger.getLogger(WorkshopController.class);

    @Autowired
    private WorkshopService workshopService;

    /**
     * 车间工区调度-可选工区列表
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/workAreaChoseList")
    @ResponseBody
    public Map<String, Object> workAreaChoseList(HttpServletResponse response, HttpServletRequest request) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统
            String taskRecordId = request.getParameter("taskRecordId");// 任务流转记录id

            if(StringUtils.isBlank(taskRecordId)) {
                resultMap.put("code", 0);
                resultMap.put("msg", "任务流转记录id为必填项");
                return resultMap;
            }

            return workshopService.workAreaChoseList(Integer.parseInt(taskRecordId));
        } catch (NumberFormatException e) {
            logger.info("获取车间工区列表数据异常error-数据类型转换异常");
            resultMap.put("msg", "参数格式错误！");
            resultMap.put("code", 0);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 0);
            resultMap.put("msg", "获取车间工区列表失败");
            return resultMap;
        }
    }
    
    /**
     * 车间工区选择记录提交
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/commitWorkAreaChose")
    @ResponseBody
    public Map<String, Object> commitWorkAreaChose(HttpServletResponse response, HttpServletRequest request) {
    	// 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统
            String taskRecordId = request.getParameter("taskRecordId");// 任务流转记录id
            String workAreaId = request.getParameter("workAreaId");// 选择的工区id

            if(StringUtils.isBlank(taskRecordId)) {
                resultMap.put("code", 0);
                resultMap.put("msg", "任务流转记录id为必填项");
                return resultMap;
            }
            if(StringUtils.isBlank(workAreaId)) {
            	resultMap.put("code", 0);
            	resultMap.put("msg", "工区id为必填项");
            	return resultMap;
            }
            
            return workshopService.commitWorkAreaChoseByTaskRecordIdAndWorkAreaId(Integer.parseInt(taskRecordId), Integer.parseInt(workAreaId));
        } catch (NumberFormatException e) {
            logger.info("车间工区选择记录提交异常error-数据类型转换异常");
            resultMap.put("msg", "参数格式错误！");
            resultMap.put("code", 0);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 0);
            resultMap.put("msg", "车间工区选择记录提交失败");
            return resultMap;
        }
    }
}
