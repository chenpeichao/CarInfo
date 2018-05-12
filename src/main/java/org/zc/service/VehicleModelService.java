package org.zc.service;


import java.util.List;

import org.zc.domain.VehicleModel;
import org.zc.vo.VehicleModelVO;


/**
 * 
 * @author xuly
 *
 */
public interface VehicleModelService {
    
   /**
    * 根据车辆大类型 查询所有有的小类型 得到实体后 讲model表的id和name返回给前端 
    * @param vehicleTypeId
    * @return
    */
	public List<VehicleModel>  findVehicleModelByvehicleTypeId(int vehicleTypeId);

    /**
     * 根据指定车辆类型id查询车辆型号列表
     * @param vehicleTypeId     车辆类型id
     * @return
     */
    public List<VehicleModelVO> findVehicleModelListByTypeId(String vehicleTypeId);

    /**
     * 根据车辆型号id获取车辆型号信息
     * @param vehicleModelId		车辆型号id
     * @return
     */
    public VehicleModel finVehicleModelById(Integer vehicleModelId);
}
