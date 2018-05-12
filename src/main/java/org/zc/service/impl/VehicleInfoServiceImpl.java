package org.zc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zc.common.Constant;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.domain.InspectionGroup;
import org.zc.domain.InspectionGroupProcess;
import org.zc.domain.InspectionItem;
import org.zc.domain.TaskExecuteRecord;
import org.zc.domain.TypeWorkArea;
import org.zc.domain.VehicleInfo;
import org.zc.domain.ZcTask;
import org.zc.dto.TypeWorkAreaJobStationDTO;
import org.zc.repository.VehicleInfoRepository;
import org.zc.repository.VehicleModelRepository;
import org.zc.service.*;
import org.zc.utils.DateUtils;
import org.zc.vo.VehicleInfoVO;

/**
 * 车辆信息service
 * Created by ceek on 2018-04-17 22:03.
 */
@Service
@Transactional
public class VehicleInfoServiceImpl  implements VehicleInfoService {
	Logger logger = Logger.getLogger(VehicleInfoServiceImpl.class);
	
    /**车辆基本信息*/
    @Autowired
    private VehicleInfoRepository vehicleInfoRepository;
    /**车辆型号*/
    @Autowired
    private VehicleModelRepository vehicleModelRepository;
    /**任务task*/
    @Autowired
    private ZcTaskService zcTaskService;
    /**质检项组*/
    @Autowired
    private InspectionGroupService inspectionGroupService;
    /**质检项组*/
    @Autowired
    private InspectionItemService inspectionItemService;
    /**车间、工区、产线信息*/
    @Autowired
    private TypeWorkAreaService typeWorkAreaService;
    /**任务流转记录*/
    @Autowired
    private TaskExecuteRecordService taskExecuteRecordService;
    /**物理工位*/
    @Autowired
    private StationService stationService;
    /**车间*/
    @Autowired
    private WorkshopService workshopService;

    /**
     * 保存车辆信息
     * @param plate				车牌号
     * @param weight			    车重
     * @param fullWeight		    载重
     * @param vehicleTypeId		车辆类型id
     * @param vehicleModelId	    车辆型号id
     */
    @Transactional
    public Map<String, Object> saveVehicleInfo(String plate, String weight, String fullWeight, String vehicleTypeId,
                                String vehicleModelId) throws Exception{
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<Integer, String> roleMap = CacheCurrentUserThreadLocal.getCurrentUser().getRoleMap();
        if(roleMap == null || !roleMap.keySet().contains(Constant.ENTRANCE_CHECKUP_ROLE_ID)) {
            resultMap.put("msg", "您没有操作权限，请联系管理员！");
            resultMap.put("code", -301);
            return resultMap;
        }

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
            vehicleInfo.setUpdateTime(DateUtils.getSecondRankLongNumberFromDate(currentTime));
            vehicleInfoRepository.saveAndFlush(vehicleInfo);
        } else {
            vehicleInfoDB.setWeight(Double.valueOf(weight));
            vehicleInfoDB.setFullWeight(Double.valueOf(fullWeight));
            vehicleInfoDB.setVehicleTypeId(Integer.parseInt(vehicleTypeId));
            vehicleInfoDB.setVehicleModelId(Integer.parseInt(vehicleModelId));
            vehicleInfoDB.setUpdateTime(DateUtils.getSecondRankLongNumberFromDate(currentTime));
            vehicleInfoRepository.saveAndFlush(vehicleInfoDB);
        }

        // 保存成功 同时获取到车辆表的id--用于插入zc_task任务表
        int vehicleInfoId = vehicleInfo.getId() == null ? vehicleInfoDB.getId() : vehicleInfo.getId();

        //根据车辆型号id(车辆型号表)获取车辆质检类别id
        Integer qualityTypeId = vehicleModelRepository.getQualityTypeIdByVehicleModelId(Integer.parseInt(vehicleModelId));
        if(qualityTypeId == null) {
            vehicleInfoRepository.delete(vehicleInfo);
        	resultMap.put("msg", "系统位录入该车型的质检类型！");
            resultMap.put("code", 0);
            return resultMap;
        }

        //保存生成任务
        ZcTask zcTask = new ZcTask();
        zcTask.setVehicleId(vehicleInfoId); //车辆id
        zcTask.setTypeId(qualityTypeId);    //质检类型id
        zcTask.setVehicleWeight(Double.valueOf(weight));		//冗余车辆自重
        zcTask.setVehicleFullWeight(Double.valueOf(fullWeight));//冗余车辆满载重量
        zcTask.setVehicleModelId(Integer.parseInt(vehicleModelId));
        zcTask.setCreateTime(DateUtils.getSecondRankLongNumberFromDate(currentTime));
        zcTask.setUpdateTime(DateUtils.getSecondRankLongNumberFromDate(currentTime));
        zcTaskService.saveZcTask(zcTask);
        
        //根据车辆类型以及入场鉴定标识查询出质检项组(item_type:1入厂鉴定，2工位质检，3整车质检)标识
        InspectionGroup toEntranceCheckupItemTypeInspectionGroup = inspectionGroupService.findInspectionGroupByItemTypeAndTypeId(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, qualityTypeId);
//        //根据车辆类型以及整车质检标识查询出质检项组(item_type:1入厂鉴定，2工位质检，3整车质检)标识
        InspectionGroup allCarItemTypeInspectionGroup = inspectionGroupService.findInspectionGroupByItemTypeAndTypeId(Constant.ALL_CAR_ITEM_TYPE, qualityTypeId);
        if(toEntranceCheckupItemTypeInspectionGroup == null || allCarItemTypeInspectionGroup == null) {
            vehicleInfoRepository.delete(vehicleInfo);
            zcTaskService.deleteByTaskId(zcTask.getId());
        	resultMap.put("msg", "系统位未录入该车型的质检类型的质检项组！");
        	resultMap.put("code", 0);
        	return resultMap;
        }
        try {
            //记录任务流转记录,对于整条产线生成记录，对于车间可选多条工区情况，每个工区均做记录
            Integer taskId = zcTask.getId();        //任务id
            //=========================任务流转记录数据添加==开始======================================================
            List<TaskExecuteRecord> taskExecuteRecordList = new ArrayList<TaskExecuteRecord>();
            //1、生成入厂鉴定任务流转记录
            TaskExecuteRecord taskExecuteRecordEntry = new TaskExecuteRecord();
            taskExecuteRecordEntry.setTaskId(taskId);
            taskExecuteRecordEntry.setQualityInspectionGroupId(toEntranceCheckupItemTypeInspectionGroup.getId());//指定typeId的入厂鉴定质检项组id
            taskExecuteRecordEntry.setQualityInspectionGroupType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);        //1入厂鉴定，2工位质检，3整车质检
            taskExecuteRecordEntry.setExecuteStatus(0);        //标识次任务未执行
            taskExecuteRecordEntry.setTypeId(qualityTypeId);        //质检类型id
            taskExecuteRecordEntry.setMark("入厂鉴定");        //标识记录
            taskExecuteRecordList.add(taskExecuteRecordEntry);
            //1.1 入场鉴定企业代表任务流转记录
            TaskExecuteRecord taskExecuteCompanyRecordEntry = new TaskExecuteRecord();
            taskExecuteCompanyRecordEntry.setTaskId(taskId);
            taskExecuteCompanyRecordEntry.setQualityInspectionGroupType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);        //1入厂鉴定，2工位质检，3整车质检
            taskExecuteCompanyRecordEntry.setQualityInspectionGroupId(toEntranceCheckupItemTypeInspectionGroup.getId());//指定typeId的入厂鉴定企业代表质检项组id
            taskExecuteCompanyRecordEntry.setExecuteStatus(0);        //标识次任务未执行
            taskExecuteCompanyRecordEntry.setTypeId(qualityTypeId);        //质检类型id
            taskExecuteCompanyRecordEntry.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);                //相当于入场鉴定专检
            taskExecuteCompanyRecordEntry.setMark("入厂鉴定企业代表");        //标识记录
            taskExecuteRecordList.add(taskExecuteCompanyRecordEntry);
            //1.2 入场鉴定监造代表任务流转记录
            TaskExecuteRecord taskExecuteInspectionRecordEntry = new TaskExecuteRecord();
            taskExecuteInspectionRecordEntry.setTaskId(taskId);
            taskExecuteInspectionRecordEntry.setQualityInspectionGroupType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);        //1入厂鉴定，2工位质检，3整车质检
            taskExecuteInspectionRecordEntry.setExecuteStatus(0);        //标识次任务未执行
            taskExecuteInspectionRecordEntry.setQualityInspectionGroupId(toEntranceCheckupItemTypeInspectionGroup.getId());//指定typeId的入厂鉴定监造代表质检项组id
            taskExecuteInspectionRecordEntry.setTypeId(qualityTypeId);        //质检类型id
            taskExecuteInspectionRecordEntry.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);                //相当于入场鉴定监造
            taskExecuteInspectionRecordEntry.setMark("入厂鉴定监造代表");        //标识记录
            taskExecuteRecordList.add(taskExecuteInspectionRecordEntry);

            //2.1、根据指定质检类别，查询所有车间、工区、职能工位情况、并细粒度到工位的任务流转记录
            List<TypeWorkAreaJobStationDTO> typeWorkAreaJobStationList = typeWorkAreaService.findWorkShopAreaAndJobStationByTypeId(qualityTypeId);
            //2.2、根据质检类别id找到当前类别对应的车间、工区信息
            Map<Integer, List<Integer>> workShopAndAreaMap = getWorkshopAndAreaByTypeId(qualityTypeId);

            //优化车间和工区对应不是按序排列
            for (Integer key : workShopAndAreaMap.keySet()) {
                Collections.sort(workShopAndAreaMap.get(key), new Comparator<Integer>() {
                    @Override
                    public int compare(Integer b1, Integer b2) {
                        return b1.compareTo(b2);
                    }
                });
            }

            //3.1、生成车间、工区以及默认工区任务记录生成
            //车间标识---用来生成监造和专检任务
            Integer workShop = 0;
            Integer inspectionGroupFlag = -1;    //质检项组标识，在职能工位流转时，当职能工位对应的质检项对应的质检项组切换时的标识
            for (TypeWorkAreaJobStationDTO typeWorkAreaJobStationDTO : typeWorkAreaJobStationList) {
                //3.1、当一个质检项组完成后，看是否触发专检和监造
                if (typeWorkAreaJobStationDTO != null) {
                    //根据职能工位查询出其所对应的质检项组id，并且判断是否与前一个质检项组id记录的标识是否相同，如果不同，则查看是否触发专检和监造
                    //3.1.1、查询职能工位的一条质检项
                    List<InspectionItem> qualityInspectionItemList = inspectionItemService.getQualityInspectionItemListByJobStationId(typeWorkAreaJobStationDTO.getJobStationId(), 0);
                    if (qualityInspectionItemList == null || qualityInspectionItemList.size() < 1) {
                        vehicleInfoRepository.delete(vehicleInfo);
                        zcTaskService.deleteByTaskId(zcTask.getId());
                        //如果职能工位的质检项为null，则返回异常
                        logger.error("保存车辆信息错误----职能工位【" + typeWorkAreaJobStationDTO.getJobStationId() + "】未查找到其质检项信息！！");
                        resultMap.put("msg", "保存失败，请联系管理员！");
                        resultMap.put("code", 0);
                        return resultMap;
                    }
                    //3.1.2、根据质检类型以及一个质检项id，来唯一确定一个质检项组
                    List<InspectionGroup> inspectionGroupListByTypeIdAndInspectionItemId = inspectionGroupService.findInspectionGroupByTypeIdAndInspectionItemId(qualityTypeId, qualityInspectionItemList.get(0).getId());
                    if (inspectionGroupListByTypeIdAndInspectionItemId != null && inspectionGroupListByTypeIdAndInspectionItemId.size() != 1) {
                        vehicleInfoRepository.delete(vehicleInfo);
                        zcTaskService.deleteByTaskId(zcTask.getId());
                        //如果职能工位的质检项为null，则返回异常
                        logger.error("保存车辆信息错误----指定质检类型的质检项对应质检项组为" + inspectionGroupListByTypeIdAndInspectionItemId == null ? 0 : inspectionGroupListByTypeIdAndInspectionItemId.size() + "个！！");
                        resultMap.put("msg", "保存失败，质检项组异常！");
                        resultMap.put("code", 0);
                        return resultMap;
                    }
                    //3.1.3 如果当前职能工位对应的质检项组与之前的质检项组不是同一个，则查看是否记录专检和监造
                    if (inspectionGroupFlag != -1 && inspectionGroupFlag != inspectionGroupListByTypeIdAndInspectionItemId.get(0).getId()) {
                        //3.1.4、查询之前的质检项组是否需要触发专检和监造
                        InspectionGroupProcess specialInspectionProcess = inspectionGroupService.findSpecialOrBuildInspectionProcessByInspectionGroupId(inspectionGroupFlag, Constant.SPECIAL_INSPECTION_PROCESS_ID);
                        if (specialInspectionProcess != null && specialInspectionProcess.getId() != null) {
                            //说明需要触发专检
                            //保存专检的任务记录
                            TaskExecuteRecord taskExecuteRecordZhuanJian = new TaskExecuteRecord();
                            taskExecuteRecordZhuanJian.setTaskId(taskId);
                            taskExecuteRecordZhuanJian.setTypeId(qualityTypeId);            //
                            taskExecuteRecordZhuanJian.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);                //3:专检，4:监造
                            //由于在if条件中已经对第一次进来过滤,所以workShopId不可能为0
                            taskExecuteRecordZhuanJian.setWorkshopId(workShop);    //车间id
                            taskExecuteRecordZhuanJian.setExecuteStatus(0);        //标识次任务未执行
                            taskExecuteRecordZhuanJian.setQualityInspectionGroupId(inspectionGroupFlag);        //保存专检以及监造所需要控制的质检项组
                            InspectionGroup inspectionGroupById = inspectionGroupService.findInspectionGroupById(inspectionGroupFlag);
                            taskExecuteRecordZhuanJian.setMark(inspectionGroupById.getName() + "专检");        //标识记录
                            taskExecuteRecordList.add(taskExecuteRecordZhuanJian);
                        }
                        InspectionGroupProcess buildInspectionProcess = inspectionGroupService.findSpecialOrBuildInspectionProcessByInspectionGroupId(inspectionGroupFlag, Constant.BUILD_INSPECTION_PROCESS_ID);
                        if (buildInspectionProcess != null && buildInspectionProcess.getId() != null) {
                            //保存监造的任务记录
                            TaskExecuteRecord taskExecuteRecordJianZao = new TaskExecuteRecord();
                            taskExecuteRecordJianZao.setTaskId(taskId);
                            taskExecuteRecordJianZao.setTypeId(qualityTypeId);            //
                            taskExecuteRecordJianZao.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);                //3:专检，4:监造
                            taskExecuteRecordJianZao.setWorkshopId(workShop);    //车间id
                            taskExecuteRecordJianZao.setExecuteStatus(0);        //标识次任务未执行
                            taskExecuteRecordJianZao.setQualityInspectionGroupId(inspectionGroupFlag);        //保存专检以及监造所需要控制的质检项组
                            InspectionGroup inspectionGroupById = inspectionGroupService.findInspectionGroupById(inspectionGroupFlag);
                            taskExecuteRecordJianZao.setMark(inspectionGroupById.getName() + "监造");        //标识记录
                            taskExecuteRecordList.add(taskExecuteRecordJianZao);
                        }
                    }

                    inspectionGroupFlag = inspectionGroupListByTypeIdAndInspectionItemId.get(0).getId();
                }

                //3.2、车间调度
                if (workShopAndAreaMap.get(typeWorkAreaJobStationDTO.getWorkShopId()).size() > 1 && workShop != typeWorkAreaJobStationDTO.getWorkShopId()) {
                    //调度员任务列表中只有车间信息
                    TaskExecuteRecord taskExecuteRecordDiaoDu = new TaskExecuteRecord();
                    taskExecuteRecordDiaoDu.setTaskId(taskId);
                    taskExecuteRecordDiaoDu.setTypeId(Constant.DISPATCH_TYPE_ID);            //调度员质检类型为-1
                    taskExecuteRecordDiaoDu.setWorkshopId(typeWorkAreaJobStationDTO.getWorkShopId());    //车间id
                    taskExecuteRecordDiaoDu.setExecuteStatus(0);        //标识次任务未执行
                    taskExecuteRecordDiaoDu.setMark(workshopService.findWorkshopById(typeWorkAreaJobStationDTO.getWorkShopId()).getName() + "车间调度");        //标识记录
                    taskExecuteRecordList.add(taskExecuteRecordDiaoDu);
                }
                if (workShop != typeWorkAreaJobStationDTO.getWorkShopId()) {
                    workShop = typeWorkAreaJobStationDTO.getWorkShopId();
                }
                //3.3、保存工位的任务记录
                TaskExecuteRecord taskExecuteRecordStation = new TaskExecuteRecord();
                taskExecuteRecordStation.setTaskId(taskId);
                taskExecuteRecordStation.setTypeId(qualityTypeId);
                taskExecuteRecordStation.setJobStationId(typeWorkAreaJobStationDTO.getJobStationId());
                //不需要调度的车间，职能工位才需要工区
                if (workShopAndAreaMap.get(typeWorkAreaJobStationDTO.getWorkShopId()).size() <= 1) {
                    taskExecuteRecordStation.setWorkAreaId(typeWorkAreaJobStationDTO.getWorkAreaId());
                }
                taskExecuteRecordStation.setWorkshopId(typeWorkAreaJobStationDTO.getWorkShopId());
                //根据物理工位id查询物理工位信息
                taskExecuteRecordStation.setMark(stationService.findStationVOById(typeWorkAreaJobStationDTO.getStationId()).getName());        //标识记录
                taskExecuteRecordStation.setQualityInspectionGroupType(2);   //1入厂鉴定，2工位质检，3整车质检
                taskExecuteRecordStation.setExecuteStatus(0);        //标识次任务未执行
                taskExecuteRecordList.add(taskExecuteRecordStation);
            }

            //4、整车质检
            TaskExecuteRecord taskExecuteRecordZhengCheZhiJian = new TaskExecuteRecord();
            taskExecuteRecordZhengCheZhiJian.setTaskId(taskId);
            taskExecuteRecordZhengCheZhiJian.setTypeId(qualityTypeId);
            taskExecuteRecordZhengCheZhiJian.setQualityInspectionGroupType(Constant.ALL_CAR_ITEM_TYPE);//1入厂鉴定，2工位质检，3整车质检
            taskExecuteRecordZhengCheZhiJian.setQualityInspectionGroupId(allCarItemTypeInspectionGroup.getId());//指定typeId的整车质检质检项组id
            taskExecuteRecordZhengCheZhiJian.setExecuteStatus(0);        //标识次任务未执行
            taskExecuteRecordZhengCheZhiJian.setMark("整车质检");        //标识记录
            taskExecuteRecordList.add(taskExecuteRecordZhengCheZhiJian);
            //4.1、整车质检专检任务流转记录
            TaskExecuteRecord taskExecuteRecordZhengCheZhiJianSpecialInspection = new TaskExecuteRecord();
            taskExecuteRecordZhengCheZhiJianSpecialInspection.setTaskId(taskId);
            taskExecuteRecordZhengCheZhiJianSpecialInspection.setTypeId(qualityTypeId);
            taskExecuteRecordZhengCheZhiJianSpecialInspection.setQualityInspectionGroupType(Constant.ALL_CAR_ITEM_TYPE);//1入厂鉴定，2工位质检，3整车质检
            taskExecuteRecordZhengCheZhiJianSpecialInspection.setQualityInspectionGroupId(allCarItemTypeInspectionGroup.getId());//指定typeId的整车质检专检任务质检项组id
            taskExecuteRecordZhengCheZhiJianSpecialInspection.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);
            taskExecuteRecordZhengCheZhiJianSpecialInspection.setExecuteStatus(0);        //标识次任务未执行
            taskExecuteRecordZhengCheZhiJianSpecialInspection.setMark("整车质检专检");        //整车质检专检标识记录
            taskExecuteRecordList.add(taskExecuteRecordZhengCheZhiJianSpecialInspection);
            //4.1、整车质检监造任务流转记录
            TaskExecuteRecord taskExecuteRecordZhengCheZhiJianBuildInspection = new TaskExecuteRecord();
            taskExecuteRecordZhengCheZhiJianBuildInspection.setTaskId(taskId);
            taskExecuteRecordZhengCheZhiJianBuildInspection.setTypeId(qualityTypeId);
            taskExecuteRecordZhengCheZhiJianBuildInspection.setQualityInspectionGroupType(Constant.ALL_CAR_ITEM_TYPE);//1入厂鉴定，2工位质检，3整车质检
            taskExecuteRecordZhengCheZhiJianBuildInspection.setQualityInspectionGroupId(allCarItemTypeInspectionGroup.getId());//指定typeId的整车质检监造任务质检项组id
            taskExecuteRecordZhengCheZhiJianBuildInspection.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);
            taskExecuteRecordZhengCheZhiJianBuildInspection.setExecuteStatus(0);        //标识次任务未执行
            taskExecuteRecordZhengCheZhiJianBuildInspection.setMark("整车质检监造");        //整车质检监造标识记录
            taskExecuteRecordList.add(taskExecuteRecordZhengCheZhiJianBuildInspection);

            //5、保存
//            for (TaskExecuteRecord taskExecuteRecord : taskExecuteRecordList) {
//                taskExecuteRecordService.saveTaskExecuteRecord(taskExecuteRecord);
//            }
            taskExecuteRecordService.saveTaskExecuteRecordList(taskExecuteRecordList);
            //6、返回保存成功消息
            resultMap.put("code", 1);
            return resultMap;
        } catch(Exception e) {
            vehicleInfoRepository.delete(vehicleInfo);
            zcTaskService.deleteByTaskId(zcTask.getId());
            throw new Exception(e);
        }
    }
    
    /**
     * 根据质检类别id找到当前类别对应的车间、工区信息
     * @param qualityTypeId
     * @return
     */
    public Map<Integer, List<Integer>> getWorkshopAndAreaByTypeId(Integer qualityTypeId) {
    	//2.1、根据指定质检类别，查询所有车间、工区、工位情况、并细粒度到工位的任务流转记录
        List<TypeWorkArea> typeWorkAreaList = typeWorkAreaService.findWorkAndAreaByTypeId(qualityTypeId);

        //2.2、定义对于同一个车间有多个工区，默认选择第一个工区
        //2.2.1、定义车间以及对应工区map
        Map<Integer, List<Integer>> workShopAndAreaMap = new HashMap<Integer, List<Integer>>(); 
        for(TypeWorkArea typeWorkArea : typeWorkAreaList) {
        	//如果车间以及可选工区map没有当前车间key，则创建一个新的map，并存放一个没有工区的车间map
        	if(!workShopAndAreaMap.containsKey(typeWorkArea.getWorkshopId())) {
        		workShopAndAreaMap.put(typeWorkArea.getWorkshopId(), null);
        	}
        	//如果车间以及可选工区map的当前key对应的车间没有工区，则向其中添加可选工区, 替换原先存储的null可选工区
        	if(workShopAndAreaMap.get(typeWorkArea.getWorkshopId()) == null) {
        		List<Integer> workAreaList = new ArrayList<Integer>();
        		workAreaList.add(typeWorkArea.getWorkAreaId());
        		workShopAndAreaMap.put(typeWorkArea.getWorkshopId(), workAreaList);
        	} else {
        		//如果车间以及可选工区map的当前key对应的车间有工区，则向可选工区list添加一个数据
        		workShopAndAreaMap.get(typeWorkArea.getWorkshopId()).add(typeWorkArea.getWorkAreaId());
        	}
        }
        return workShopAndAreaMap;
    }

    /**
     * 根据车牌号查询车辆信息
     * @param plate         车牌号
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
