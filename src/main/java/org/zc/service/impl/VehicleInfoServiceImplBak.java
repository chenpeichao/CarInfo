package org.zc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.VehicleInfo;
import org.zc.domain.ZcTask;
import org.zc.repository.VehicleInfoRepository;
import org.zc.repository.VehicleModelRepository;
import org.zc.service.VehicleInfoServiceBak;
import org.zc.service.ZcTaskService;
import org.zc.utils.DateUtils;
import org.zc.vo.VehicleInfoVO;

/**
 * 车辆基本信息表
 *
 * @author xly
 * @create 2018-03-06 15:28
 **/
@Service
public  class VehicleInfoServiceImplBak implements VehicleInfoServiceBak {
	/**车辆基本信息*/
    @Autowired
    private VehicleInfoRepository vehicleInfoRepository;
    /**车辆型号*/
    @Autowired
    private VehicleModelRepository vehicleModelRepository;
    /**任务task*/
    @Autowired
    private ZcTaskService zcTaskService;

	
    /**
	 * 保存车辆信息
	 * @param plate				车牌号
	 * @param weight			车重
	 * @param fullWeight		载重
	 * @param vehicleTypeId		车辆类型id
	 * @param vehicleModelId	车辆型号id
	 */
	public void saveVehicleInfo(String plate, String weight, String fullWeight, String vehicleTypeId,
			String vehicleModelId) {
		//获取当前时间
		Date currentTime = new Date();
		VehicleInfo vehicleInfo = new VehicleInfo();
		
		//根据用户拍照查询是否有车辆信息
		VehicleInfo vehicleInfoDB = vehicleInfoRepository.findByPlate(plate);
		//保存或修改车辆信息
		if(vehicleInfoDB == null) {
			vehicleInfo.setPlate(plate);
			vehicleInfo.setWeight(Double.valueOf(weight));
			vehicleInfo.setFullWeight(Double.valueOf(fullWeight));
			vehicleInfo.setDeleted(false);
			vehicleInfo.setVehicleTypeId(Integer.parseInt(vehicleTypeId));
			vehicleInfo.setVehicleModelId(Integer.parseInt(vehicleModelId));
			vehicleInfo.setCreateTime(DateUtils.getSecondRankLongNumberFromDate(currentTime));
			vehicleInfoRepository.saveAndFlush(vehicleInfo);
		} else {
			//TODO:pcchen 当数据库中有车辆信息时，保存哪些字段
			vehicleInfoDB.setWeight(Double.valueOf(weight));
			vehicleInfoDB.setFullWeight(Double.valueOf(fullWeight));
			vehicleInfoDB.setVehicleTypeId(Integer.parseInt(vehicleTypeId));
			vehicleInfoDB.setVehicleModelId(Integer.parseInt(vehicleModelId));
			vehicleInfo.setUpdateTime(DateUtils.getSecondRankLongNumberFromDate(currentTime));
			vehicleInfoRepository.saveAndFlush(vehicleInfoDB);
		}
		
		// 保存成功 同时获取到车辆表的id--用于插入zc_task任务表
		int vehicleInfoId = vehicleInfo.getId() == null ? vehicleInfoDB.getId() : vehicleInfo.getId();
		
		//根据车辆型号id(车辆型号表)获取车辆质检类别id
		Integer qualityTypeId = vehicleModelRepository.getQualityTypeIdByVehicleModelId(Integer.parseInt(vehicleModelId));
		
		//保存生成任务记录
		ZcTask zcTask = new ZcTask();
		zcTask.setVehicleId(vehicleInfoId);
		zcTask.setTypeId(qualityTypeId);		
		zcTask.setCreateTime(DateUtils.getSecondRankLongNumberFromDate(currentTime));
		zcTaskService.saveZcTask(zcTask);
		
//		Process mProcess = new Process();
//		String process = setProcess(mProcess);// 入库用的String
//
//		ZcTaskExplorer zcTaskExplorer = new ZcTaskExplorer();
//		zcTaskExplorer.setCreate_time(Long.parseLong(time));
//		zcTaskExplorer.setProcess(process);
//		zcTaskExplorer.setTaskId(taskid);
//		zcTaskExplorer.setVehicleInfoId(vehicleInfo.getId());
		
		// TODO:pcchen 保存任务后，对于质检项的封装
//		zcTaskExplorerRepository.save(zcTaskExplorer);
	}


	/**
	 * 根据id查询一条记录
	 */
	@Override
	public VehicleInfo findVehicleInfoByid(int id) {
		// TODO Auto-generated method stub
		return vehicleInfoRepository.findOne(id);
	}


	/**
	 * 判断是否存在
	 */
	@Override
	public boolean vehicleInfoexists(int id) {
		// TODO Auto-generated method stub
		return  vehicleInfoRepository.exists(id);
	}


	/**
     * 根据车牌号查询车辆信息
     * @param plate
     * @return
     */
	@Override
	public VehicleInfoVO findVehicleInfoByplate(String plate) {
		VehicleInfo vehicleInfo = vehicleInfoRepository.findByPlate(plate);
		if(vehicleInfo == null) {
			return null;
		}
		VehicleInfoVO vehicleInfoVO = new VehicleInfoVO();
		vehicleInfoVO.setPlate(vehicleInfo.getPlate());
		vehicleInfoVO.setFullWeight(vehicleInfo.getFullWeight());
		vehicleInfoVO.setVehicleModelId(vehicleInfo.getVehicleModelId());
		vehicleInfoVO.setVehicleTypeId(vehicleInfo.getVehicleTypeId());
		vehicleInfoVO.setWeight(vehicleInfoVO.getWeight());
		return vehicleInfoVO;
	}



	
	
	



	
	
}
