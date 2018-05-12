package org.zc.service;

import java.util.List;

import org.zc.vo.VehicleTypeVO;

import com.alibaba.fastjson.JSONArray;


/**
 * 车辆类型
 * @author xuly
 *
 */
public interface VehicleTypeService {
    
    /**
     * 获取所有车辆类型及其下的车辆型号列表信息
     * @return
     */
    public JSONArray findVehicleTypeAndModel();

    /**
     * 获取所有车辆类型信息
     * @return
     */
    public List<VehicleTypeVO> findAllVehicleType();
}
