package org.zc.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zc.service.VehicleTypeService;

/**
 * 车辆类型
 * Created by ceek on 2018-04-17 21:22.
 */
@Controller
@RequestMapping("/vehicleType")
public class VehicleTypeController {
    Logger logger = Logger.getLogger(VehicleInfoController.class);

    @Autowired
    private VehicleTypeService vehicleTypeService;

    /**
     * 查询所有车辆类别
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/findAllVehicleType")
    @ResponseBody
    public Map<String, Object> findAll(HttpServletResponse response, HttpServletRequest request) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            String userId = request.getParameter("userId");// 用户id
            String version = request.getParameter("version");// 版本
            String os = request.getParameter("os");// 操作系统

            resultMap.put("code", 1);
            //获取所有车辆类别信息
            resultMap.put("datas", vehicleTypeService.findAllVehicleType());
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 0);
            resultMap.put("msg", "查询所有车辆类别失败");
            return resultMap;
        }
    }
}
