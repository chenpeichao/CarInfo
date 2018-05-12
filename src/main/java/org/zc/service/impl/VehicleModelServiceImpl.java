package org.zc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.VehicleModel;
import org.zc.repository.VehicleModelRepository;
import org.zc.service.VehicleModelService;
import org.zc.vo.VehicleModelVO;

@Service
public class VehicleModelServiceImpl implements VehicleModelService {
	@Autowired
    private VehicleModelRepository vehicleModelRepository;

	/**
	 * 根据车辆类型id获取车辆型号列表
	 * @param vehicleTypeId
	 * @return
	 */
	@Override
	public List<VehicleModel> findVehicleModelByvehicleTypeId(int vehicleTypeId) {
		return vehicleModelRepository.findvehicleTypeId(vehicleTypeId);
	}

	/**
	 * 根据指定车辆类型id查询车辆型号列表
	 * @param vehicleTypeIdStr     车辆类型id
	 * @return
	 */
	public List<VehicleModelVO> findVehicleModelListByTypeId(String vehicleTypeIdStr) {
		List<VehicleModelVO> vehicleModelVOList = new ArrayList<VehicleModelVO>();
		if(StringUtils.isNotBlank(vehicleTypeIdStr)) {
			Integer vehicleTypeId = Integer.parseInt(vehicleTypeIdStr);
			List<VehicleModel> vehicleModelList = vehicleModelRepository.findVehicleModelListByTypeId(vehicleTypeId);
			for(VehicleModel vehicleModel : vehicleModelList) {
				vehicleModelVOList.add(new VehicleModelVO(vehicleModel.getId(), vehicleModel.getName()));
			}
		} else {
			List<VehicleModel> vehicleModelList = vehicleModelRepository.findAll();
			for(VehicleModel vehicleModel : vehicleModelList) {
				vehicleModelVOList.add(new VehicleModelVO(vehicleModel.getId(), vehicleModel.getName()));
			}
		}
		return vehicleModelVOList;
	}

	/**
	 * 根据车辆型号id获取车辆型号信息
	 * @param vehicleModelId		车辆型号id
	 * @return
	 */
	public VehicleModel finVehicleModelById(Integer vehicleModelId) {
		return vehicleModelRepository.findOne(vehicleModelId);
	}
}
