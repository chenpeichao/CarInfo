package org.zc.service;

import org.zc.vo.VehicleInfoVO;

import java.util.Map;

/**
 * ${DESCRIPTION}
 * Created by ceek on 2018-04-17 22:01.
 */
public interface VehicleInfoService {
    /**
     * 保存车辆信息
     * @param plate				车牌号
     * @param weight				车重
     * @param fullWeight			载重
     * @param vehicleTypeId		车辆类型id
     * @param vehicleModelId		车辆型号id
     */
    public Map<String, Object> saveVehicleInfo(String plate, String weight, String fullWeight, String vehicleTypeId,
                                               String vehicleModelId)  throws Exception;

    /**
     * 根据车牌号查询车辆信息
     * @param plate			车牌号
     * @return
     */
    public VehicleInfoVO findVehicleInfoByplate(String plate);
}
