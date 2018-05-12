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
import org.zc.service.VehicleInfoService;
import org.zc.utils.DateUtils;
import org.zc.vo.VehicleInfoVO;

/**
 * 车辆信息
 * Created by ceek on 2018-04-17 20:52.
 */
@Controller
@RequestMapping("/vehicleInfo")
public class VehicleInfoController {
    Logger logger = Logger.getLogger(VehicleInfoController.class);

    @Autowired
    private VehicleInfoService vehicleInfoService;

    /**
     * 保存车辆信息
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/saveVehicleInfo")
    @ResponseBody
    public Map<String, Object> saveVehicleInfo(HttpServletResponse response, HttpServletRequest request) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	UserLoginInfoDTO currentUser = CacheCurrentUserThreadLocal.getCurrentUser();
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统
            String plate = request.getParameter("plate");// 车牌号
            String weight = request.getParameter("weight");// 自重
            String fullWeight = request.getParameter("fullWeight");// 载重
            String vehicleTypeId = request.getParameter("vehicleTypeId");// 车辆类别id
            String vehicleModelId = request.getParameter("vehicleModelId");// 车辆型号id

            if(StringUtils.isBlank(plate)) {
                resultMap.put("msg", "车牌号不能为空！！");
                resultMap.put("code", 0);
                return resultMap;
            }
            if(StringUtils.isBlank(weight)) {
            	resultMap.put("msg", "自重不能为空！！");
            	resultMap.put("code", 0);
            	return resultMap;
            }
            if(StringUtils.isBlank(fullWeight)) {
            	resultMap.put("msg", "载重不能为空！！");
            	resultMap.put("code", 0);
            	return resultMap;
            }
            if(StringUtils.isBlank(vehicleTypeId)) {
            	resultMap.put("msg", "车辆类别不能为空！！");
            	resultMap.put("code", 0);
            	return resultMap;
            }
            if(StringUtils.isBlank(vehicleModelId)) {
            	resultMap.put("msg", "车辆型号不能为空！！");
            	resultMap.put("code", 0);
            	return resultMap;
            }

            //记录操作日志
            logger.info("收到车辆登记信息数据：v=" + version + " userId:" + currentUser.getUserId() + " o: " + os + " time:" + DateUtils.getPatternDateTime(DateUtils.DATE_STR_WHOLE, null) + "\r\n 车辆信息如下："
                    + " plate:" + plate + " weight:" + weight + " full_weight:" + fullWeight + " vehicle_type_id:"
                    + vehicleTypeId + " vehicle_model_id:" + vehicleModelId);

            return vehicleInfoService.saveVehicleInfo(plate.trim(), weight.trim(), fullWeight.trim(), vehicleTypeId.trim(), vehicleModelId.trim());
        } catch (NumberFormatException e) {
        	logger.info("保存车辆录入信息异常error");
            resultMap.put("msg", "车辆信息格式错误！");
            resultMap.put("code", 0);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("保存车辆录入信息异常error");
            resultMap.put("msg", "保存车辆录入信息失败！");
            resultMap.put("code", 0);
            return resultMap;
        }
    }

    /**
     * 通过车牌号查询车辆信息
     *
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/findVehicleInfoByplate")
    @ResponseBody
    public Map<String, Object> findVehicleInfoByplate(HttpServletResponse response, HttpServletRequest request) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	UserLoginInfoDTO currentUser = CacheCurrentUserThreadLocal.getCurrentUser();
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统
            String plate = request.getParameter("plate");// 车牌号

            logger.info("findVehicleInfoByplate接口调用，收到车辆登记信息数据：version=" + version + " userId:" + currentUser.getUserId() + " os: " + os + " time:" + DateUtils.getPatternDateTime(DateUtils.DATE_STR_WHOLE, null)
                    + "\r\n 车辆信息如下：" + "plate:" + plate);
            VehicleInfoVO vehicleInfoVO = vehicleInfoService.findVehicleInfoByplate(plate);

            if(vehicleInfoVO == null) {
                resultMap.put("code", 0);
                resultMap.put("msg", "查询失败，请检查车牌号码");
                return resultMap;
            }
            resultMap.put("code", 1);
            resultMap.put("datas", vehicleInfoVO);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 0);
            resultMap.put("msg", "查询失败，请重试");
            return resultMap;
        }
    }
}
