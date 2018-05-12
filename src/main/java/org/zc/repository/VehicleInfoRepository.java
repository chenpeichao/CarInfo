package org.zc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zc.domain.VehicleInfo;

/**
 * DAO层 
 * @author 
 * @create 2018-03-06 15:32
 **/
public interface VehicleInfoRepository  extends JpaRepository<VehicleInfo, Integer> {
	/**
	 * 通过车牌号查询车辆信息
	 * @param plate		车牌号
	 * @return
	 */
	@Query("from org.zc.domain.VehicleInfo vehicleInfo where vehicleInfo.plate = ?1") 
	public VehicleInfo findByPlate(String plate);
}
