package org.zc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.VehicleModel;
import org.zc.domain.VehicleType;
import org.zc.repository.VehicleTypeRepository;
import org.zc.service.VehicleModelService;
import org.zc.service.VehicleTypeService;
import org.zc.vo.VehicleTypeVO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 车辆类型
 * Description:
 * Create by @author cpc
 * 2018年4月15日 下午6:19:58 <br/>
 */
@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {
	@Autowired
	private VehicleModelService vehicleModelService;

	@Autowired
	private VehicleTypeRepository vehicleTypeRepository;

	/**
	 * 获取所有车辆类别及其下的质检类型和车辆编号列表信息
	 */
	@Override
	public JSONArray findVehicleTypeAndModel() {
		JSONArray datas = new JSONArray();
		// 车辆类型
		List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
		// 车辆型号-质检类型集合
		List<VehicleModel> vehicleModelList = new ArrayList<VehicleModel>();

		// 循环大类型 查找所在大类型的小类型
		for (VehicleType type : vehicleTypeList) {
			JSONArray modelarray = new JSONArray();
			JSONObject typeObj = new JSONObject();
			typeObj.put("id", type.getId());// int 车辆类别id
			typeObj.put("name", type.getName());// String 车辆类别名称
			typeObj.put("modelarray", modelarray);// arraylist<> 车辆型号,必填
			datas.add(typeObj);
			// 根据车辆类型获取其下所有车辆型号列表
			vehicleModelList = vehicleModelService.findVehicleModelByvehicleTypeId(type.getId());
			// 循环每一种大类型 把小类型写到json中
			for (int i = 1; i < vehicleModelList.size(); i++) {
				JSONObject modelObj = new JSONObject();
				modelObj.put("name", vehicleModelList.get(i).getName());// 小类型名称
				modelObj.put("id", vehicleModelList.get(i).getId());// 小类型ID
				modelarray.add(modelObj);
			}
		}
		return datas;
	}

	/**
	 * 获取所有车辆类型信息
	 * @return
	 */
	public List<VehicleTypeVO> findAllVehicleType() {
		List<VehicleType> vehicleTypeListDB = vehicleTypeRepository.findAll();
		//对返回车辆类型列表数据，进行前台显示字段封装
		List<VehicleTypeVO> vehicleTypeVOList = new ArrayList<VehicleTypeVO>();
		if(vehicleTypeListDB != null) {
			for(VehicleType vehicleType : vehicleTypeListDB) {
				VehicleTypeVO vehicleTypeVO = new VehicleTypeVO(vehicleType.getId(), vehicleType.getName());
				vehicleTypeVOList.add(vehicleTypeVO);
			}
		}
		// 车辆类型
		return vehicleTypeVOList;
	}
}
