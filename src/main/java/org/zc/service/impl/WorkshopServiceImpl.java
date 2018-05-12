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
import org.zc.common.Constant;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.domain.Answer;
import org.zc.domain.TaskExecuteRecord;
import org.zc.domain.TypeWorkArea;
import org.zc.domain.WorkArea;
import org.zc.domain.WorkShop;
import org.zc.domain.ZcTask;
import org.zc.dto.UserLoginInfoDTO;
import org.zc.repository.WorkshopRepository;
import org.zc.service.AnswerService;
import org.zc.service.StationService;
import org.zc.service.TaskExecuteRecordService;
import org.zc.service.TypeWorkAreaService;
import org.zc.service.UserService;
import org.zc.service.WorkAreaService;
import org.zc.service.WorkshopService;
import org.zc.service.ZcTaskService;
import org.zc.utils.DateUtils;
import org.zc.vo.ChoseWorkshopVO;
import org.zc.vo.StationVO;

/**
 * 车间
 * Created by ceek on 2018-04-24 21:54.
 */
@Service
public class WorkshopServiceImpl implements WorkshopService {
	Logger logger = Logger.getLogger(WorkshopServiceImpl.class);
	
    @Autowired
    private WorkshopRepository workshopRepository;
    @Autowired
    private TaskExecuteRecordService taskExecuteRecordService;
    @Autowired
    private ZcTaskService zcTaskService;
    @Autowired
    private TypeWorkAreaService typeWorkAreaService;
    @Autowired
    private WorkAreaService workAreaService;
    @Autowired
    private StationService stationService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;

    /**
     * 根据流转记录id获取可以选择的工区以及对应的工位列表
     * @param taskRecordId
     * @return
     */
    public Map<String, Object> workAreaChoseList(Integer taskRecordId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //1、根据流转记录id获取指定流转记录
        TaskExecuteRecord taskExecuteRecord = taskExecuteRecordService.findOneById(taskRecordId);
        if(taskExecuteRecord == null || taskExecuteRecord.getWorkshopId() == null) {
            resultMap.put("msg", "未找到该流转记录！");
            resultMap.put("code", 0);
            return resultMap;
        }
        //1.1用户必须有任务流转记录车间的调度权限
//        UserLoginInfoDTO loginUser = CacheCurrentUserThreadLocal.getCurrentUser();
    	//用户必须有调度权限
//    	if(!loginUser.getRoleMap().containsKey(Constant.DISPATCH_ROLE_ID) || loginUser.getWorkShopId() != taskExecuteRecord.getWorkshopId()) {
//    		logger.error("登录用户：【" + loginUser + "】 没有指定车间"+taskExecuteRecord.getWorkshopId()+"的调度权限！");
//    		resultMap.put("msg", "您没有操作权限，请联系管理员！");
//            resultMap.put("code", -301);
//            return resultMap;
//    	}
        //2、获取车间id以及质检类别id
        Integer workshopId = taskExecuteRecord.getWorkshopId();
        Integer taskId = taskExecuteRecord.getTaskId();
        //2.1由于在任务流转表中对于调度员不用关心质检类型，所以设置为默认值-1，所以通过taskid在任务表中获取质检类型
        ZcTask zcTask = zcTaskService.findByTaskid(taskId);
        Integer typeId = zcTask.getTypeId();

        //3、根据车间以及质检类型来获取可选工区以及对应的工位信息
        //3.1、根据质检类型以及车间获取可选择工区列表
        List<TypeWorkArea> workAreaList = typeWorkAreaService.findWorkAreaByTypeIdAndWorkshopId(workshopId, typeId);
        Collections.sort(workAreaList, new Comparator<TypeWorkArea>() {
            @Override
            public int compare(TypeWorkArea o1, TypeWorkArea o2) {
                return o1.getWorkAreaId().compareTo(o2.getWorkAreaId());
            }
        });
        List<ChoseWorkshopVO> choseWorkshopVOList = new ArrayList<ChoseWorkshopVO>();
        int temChange = 0;
        ChoseWorkshopVO choseWorkshopVO = new ChoseWorkshopVO();
        for(int i = 0; i < workAreaList.size(); i++) {
            TypeWorkArea typeWorkArea = workAreaList.get(i);

            if(temChange != workAreaList.get(i).getWorkAreaId()) {
                choseWorkshopVO = new ChoseWorkshopVO();
                //根据工区id查询工区详情
                WorkArea workArea = workAreaService.findOneById(workAreaList.get(i).getWorkAreaId());
                if(workArea == null) {
                    resultMap.put("msg", "未找到可选工区信息！");
                    resultMap.put("code", 0);
                    return resultMap;
                }
                choseWorkshopVO.setWorkshopId(taskExecuteRecord.getWorkshopId());
                choseWorkshopVO.setWorkAreaId(workArea.getId());
                choseWorkshopVO.setWorkAreaName(workArea.getName());
                choseWorkshopVOList.add(choseWorkshopVO);
            }
            //通过工位id，获取工位详情
            StationVO stationVO = stationService.findStationVOById(typeWorkArea.getStationId());
            if(stationVO == null) {
                continue;
            }
            choseWorkshopVO.getStationList().add(stationVO);

            temChange = workAreaList.get(i).getWorkAreaId();//用来只保存一个工区信息
        }
        //对于没有可选的工区所对应的工位列表按照pid升序排序
        for(ChoseWorkshopVO choseWorkshopVO2 : choseWorkshopVOList) {
        	Collections.sort(choseWorkshopVO2.getStationList(), new Comparator<StationVO>() {
                @Override
                public int compare(StationVO o1, StationVO o2) {
                    return o1.getPid().compareTo(o2.getPid());
                }
            });
        }
        
        
        resultMap.put("datas", choseWorkshopVOList);
        resultMap.put("code", 1);
        return resultMap;
    }
    
    /**
     * 流转记录中保存车间对应多个工区，选择指定工区
     * @param taskRecordId		任务流转记录id
     * @param taskRecordId		工区id
     * @return
     */
    public Map<String, Object> commitWorkAreaChoseByTaskRecordIdAndWorkAreaId(Integer taskRecordId, Integer workAreaId) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	Long currentDateTime = DateUtils.getSecondRankLongNumberFromDate(new Date());

        //1、根据流转记录id获取指定流转记录
        TaskExecuteRecord taskExecuteRecord = taskExecuteRecordService.findOneById(taskRecordId);
        if(taskExecuteRecord == null || taskExecuteRecord.getWorkshopId() == null) {
            resultMap.put("msg", "未找到该流转记录！");
            resultMap.put("code", 0);
            return resultMap;
        }
        //1.1用户必须有任务流转记录车间的调度权限
        UserLoginInfoDTO loginUser = CacheCurrentUserThreadLocal.getCurrentUser();
    	//用户必须有调度权限
    	if(!loginUser.getRoleMap().containsKey(Constant.DISPATCH_ROLE_ID) || loginUser.getWorkShopId() != taskExecuteRecord.getWorkshopId()) {
    		logger.error("登录用户：【" + loginUser + "】 没有指定车间"+taskExecuteRecord.getWorkshopId()+"的调度权限！");
    		resultMap.put("msg", "您没有操作权限，请联系管理员！");
            resultMap.put("code", -301);
            return resultMap;
    	}
    	
    	//2、获取到所有该调度员需要调度修改或确认的任务流转下的职能工位列表
    	//2.1、获取当前任务之前的节点是否执行完成
    	if(taskExecuteRecordService.getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskRecordId) >= 1) {
    		resultMap.put("msg", "当前任务还未执行到此步骤！请稍后再试！");
            resultMap.put("code", 0);
            return resultMap;
    	}
    	//3、查询工区id是否正确
    	//由于调度员没有质检类型，所以查询任务表查询当前任务的质检类型
    	ZcTask zcTask = zcTaskService.findByTaskid(taskExecuteRecord.getTaskId());
    	List<TypeWorkArea> workAreaList = typeWorkAreaService.findWorkAreaByTypeIdAndWorkshopId(taskExecuteRecord.getWorkshopId(), zcTask.getTypeId());
    	
    	//3.1、查看传递的工区参数，是否在制定车间存在
    	boolean flag = false;
    	for(TypeWorkArea typeWorkArea : workAreaList) {
    		if(typeWorkArea.getWorkAreaId().equals(workAreaId)) {
    			flag = true;
    		}
    	}

    	if(!flag) {
    		resultMap.put("msg", "当前车间所选择工区错误！");
            resultMap.put("code", 0);
        	return resultMap;
    	}
    	//4、更新任务流转表中的可选车间中的工区，以使职能工位和物理工位对应
    	taskExecuteRecordService.updateDispatchWorkshopWorkAreaByTaskIdAndWorkshopId(taskExecuteRecord.getTaskId(), taskExecuteRecord.getWorkshopId(), workAreaId);
    	//4.1、保存操作人信息
    	taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskExecuteRecord.getId(), loginUser.getUserId(), 1);
    	resultMap.put("msg", "工区选择成功！");
        resultMap.put("code", 1);
        
    		//表示自定义问题
        Answer answer = new Answer();
        answer.setQualityItemId(-2);
        answer.setWorkshopId(taskExecuteRecord.getWorkshopId());
		answer.setQualityItemName(workshopRepository.findOne(taskExecuteRecord.getWorkshopId()).getName()+"车间工区选择");
		answer.setChooseContent(workAreaId + "");
		answer.setContent("");
    	answer.setUserId(loginUser.getUserId());
    	answer.setUserName(userService.findOneByUserId(loginUser.getUserId()).getUsername());
    	answer.setTaskId(taskExecuteRecord.getTaskId());
    	answer.setStatus(1);
    	answer.setCreateTime(currentDateTime);
    	answer.setUpdateTime(currentDateTime);
    	
    	answerService.saveAnswer(answer);
    	
    	return resultMap;
    }

    /**
     * 根据车间id查询车间信息
     * @param workshopId    车间id
     * @return
     */
    public WorkShop findWorkshopById(Integer workshopId) {
        return workshopRepository.findOne(workshopId);
    }
}
