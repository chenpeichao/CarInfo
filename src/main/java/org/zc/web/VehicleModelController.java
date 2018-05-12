package org.zc.web;

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
import org.zc.service.VehicleModelService;
import org.zc.service.VehicleTypeService;
import org.zc.utils.DateUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 车辆型号
 */
@Controller
@RequestMapping("/vehicleModel")
public class VehicleModelController {
	@Autowired
	private VehicleTypeService vehicleTypeService;
	@Autowired
	private VehicleModelService vehicleModelService;

	Logger logger = Logger.getLogger(VehicleModelController.class);

	/**
	 * 查询所有车辆类别，以及对应车辆型号
	 * @param response
	 * @param request
	 * @return
	 */
	@GetMapping("/findAll")
	@ResponseBody
	public Map<String, Object> findAll(HttpServletResponse response, HttpServletRequest request) {
		// 解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");

		JSONObject jsonObj = new JSONObject();
		try {
			String userId = request.getParameter("userId");// 用户id
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统

			logger.info("findVehicleInfoByplate接口调用，收到车辆登记信息数据：v=" + version + " userId:" + userId + " o: " + os + " time:" + DateUtils.getPatternDateTime(DateUtils.DATE_STR_WHOLE, null)
					+ "\r\n 车辆信息如下：");
			
			jsonObj.put("code", 1);
			//获取所有车辆类别及其下的质检类型和车辆型号列表信息
			jsonObj.put("datas", vehicleTypeService.findVehicleTypeAndModel());
			return jsonObj;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error");
			jsonObj.put("status", 0);
			jsonObj.put("msg", "查询车辆信息失败");
			return jsonObj;
		}
	}

	/**
	 * 根据指定车辆类别查询车辆型号信息
	 * @param response
	 * @param request
	 * @return
	 */
	@GetMapping("/findVehicleModelListByTypeId")
	@ResponseBody
	public String findVehicleModelListByTypeId(HttpServletResponse response, HttpServletRequest request) {
		// 解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");

		JSONObject jsonObj = new JSONObject();
		try {
			String userId = request.getParameter("userId");// 用户id
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统
			String vehicleTypeId = request.getParameter("vehicleTypeId");

			//验证车辆类别id是否为整数
			if(StringUtils.isNotBlank(vehicleTypeId)) {
				Integer.parseInt(vehicleTypeId.trim());
			}

			jsonObj.put("code", 1);
			//根据车辆类型id查询其下车辆型号列表
			jsonObj.put("datas", vehicleModelService.findVehicleModelListByTypeId(vehicleTypeId.trim()));
			return jsonObj.toJSONString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			jsonObj.put("code", 0);
			jsonObj.put("msg", "车辆型号id格式错误");
			return jsonObj.toJSONString();
		}catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("code", 0);
			jsonObj.put("msg", "查询车辆型号失败");
			return jsonObj.toJSONString();
		}
	}
}
