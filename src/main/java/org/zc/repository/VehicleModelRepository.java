package org.zc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zc.domain.VehicleModel;

/**
 * DAO层
 * @author
 * @create 2018-03-06 15:32
 **/
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {
	/**
	 * 根据车辆类型查询其下所有车辆型号列表信息
	 * @param vehicleTypeId
	 * @return
	 */
	@Query("from org.zc.domain.VehicleModel o where o.vehicleTypeId = ?1")
	public List<VehicleModel> findvehicleTypeId(int vehicleTypeId);
	
	/**
	 * 根据车辆型号查询质检类别id
	 * @param vehicleModelId	车辆型号id
	 * @return
	 */
	@Query("select typeId from org.zc.domain.VehicleModel vehicleModel where vehicleModel.id = ?1")
	public Integer getQualityTypeIdByVehicleModelId(Integer vehicleModelId);

	/**
	 * 根据车辆型号id查询质检类别id
	 * @param vehicleModelId	车辆型号id
	 * @return
	 */
	@Query(" from org.zc.domain.VehicleModel vehicleModel where vehicleModel.typeId = ?1")
	public List<VehicleModel> findVehicleModelListByTypeId(Integer vehicleModelId);
}
