package org.zc.service;

import org.zc.domain.VehicleInfo;
import org.zc.vo.VehicleInfoVO;

/**
 * 车辆信息
 * 
 * @author xuly
 *
 */
public interface VehicleInfoServiceBak {
	/**
	 * 保存车辆信息
	 * @param plate				车牌号
	 * @param weight				车重
	 * @param fullWeight			载重
	 * @param vehicleTypeId		车辆类型id
	 * @param vehicleModelId		车辆型号id
	 */
	public void saveVehicleInfo(String plate, String weight, String fullWeight, String vehicleTypeId,
			String vehicleModelId);

	/**
	 * 根据车牌号查询车辆信息
	 * @param plate			车牌号
	 * @return
	 */
	public VehicleInfoVO findVehicleInfoByplate(String plate);



	public boolean vehicleInfoexists(int id);

	public VehicleInfo findVehicleInfoByid(int id);

}
