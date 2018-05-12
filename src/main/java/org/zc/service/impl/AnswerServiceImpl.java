package org.zc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.common.Constant;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.domain.Answer;
import org.zc.domain.InspectionItem;
import org.zc.domain.TaskExecuteRecord;
import org.zc.domain.User;
import org.zc.dto.UserLoginInfoDTO;
import org.zc.repository.AnswerRepository;
import org.zc.service.AnswerService;
import org.zc.service.InspectionItemService;
import org.zc.service.TaskExecuteRecordService;
import org.zc.service.UserService;
import org.zc.service.WorkshopService;
import org.zc.vo.AnswerVO;
import org.zc.vo.ChoseWorkshopVO;

@Service
public  class AnswerServiceImpl implements AnswerService {
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private TaskExecuteRecordService taskExecuteRecordService;  //任务流转记录service
	@Autowired
	private AnswerService answerService;  //答案service
	@Autowired
	private InspectionItemService inspectionItemService;  //质检项service
	@Autowired
	private WorkshopService workshopService;  //车间service
	@Autowired
	private UserService userService;;  //用户service

	@Override
	public void saveAnswer(Answer answer) {
		answerRepository.save(answer);
	}
	
	public void saveAnswerList(List<Answer> answerList) {
		answerRepository.save(answerList);
	}
	
	/**
     * 根据上步质检项id列表，查询答题答案
     * @param qualityInspectionItemIds	质检项id
     * @param taskId					任务id
     * @param processId					流程id
     * @param status					答案成功状态0：失败、1：成功
     * @return
     */
    public List<Answer> findAnswerListByQualityInspectionItemIdList(List<Integer> qualityInspectionItemIds, Integer taskId, Integer processId, Integer status) {
    	return answerRepository.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIds, taskId, processId, status);
    }
	/**
	 * 根据上步质检项id列表，查询答题答案--包含失败的答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param processId					流程id
	 * @return
	 */
	public List<Answer> findAnswerListByQualityInspectionItemIdList(List<Integer> qualityInspectionItemIds, Integer taskId, Integer processId) {
		if(processId == null) {
			return answerRepository.findAnswerListByQualityInspectionItemIdListProcessIdIsAll(qualityInspectionItemIds, taskId);
		} else {
			return answerRepository.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIds, taskId, processId);
		}
    }

//	/**
//	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
//	 * @param qualityInspectionItemIds	质检项id
//	 * @param taskId					任务id
//	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
//	 * @param status					答案成功状态0：失败、1：成功
//	 * @return
//	 */
//	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemType(List<Integer> qualityInspectionItemIds, Integer taskId, Integer itemType, Integer status) {
//		return answerRepository.findAnswerListByQualityInspectionItemIdListAndItemType(qualityInspectionItemIds, taskId, itemType, status);
//	}
	/**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param processId					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param status					答案成功状态0：失败、1：成功
	 * @return
	 */
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(List<Integer> qualityInspectionItemIds, Integer taskId, Integer itemType, Integer processId, Integer status) {
		if(processId == null) {
			return answerRepository.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessIdIsNull(qualityInspectionItemIds, taskId, itemType, status);
		} else{
			return answerRepository.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIds, taskId, itemType, processId, status);
		}
	}
	/**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案--包含失败的答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param processId					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @return
	 */
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(List<Integer> qualityInspectionItemIds, Integer taskId, Integer itemType, Integer processId) {
		if(processId == null) {
				return answerRepository.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessIdIsNull(qualityInspectionItemIds, taskId, itemType);
		} else{
			return answerRepository.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIds, taskId, itemType, processId);
		}
	}
//	/**
//     * 根据任务id，和item_type类型，查询已经答题的质检项id列表
//     * @param taskId					任务id
//     * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
//     * @param status					答案成功状态0：失败、1：成功
//     * @return
//     */
//    public List<Integer> findItemIdListByTaskIdAndItemType(Integer taskId, Integer itemType, Integer status) {
//    	return answerRepository.findItemIdListByTaskIdAndItemType(taskId, itemType, status);
//    }

	/**
	 * 根据任务id，和item_type，以及processId查询质检项id列表
	 * @param taskId                    任务id
	 * @param processId                 流程id
	 * @param itemType                  质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param status                    答案成功状态0：失败、1：成功
	 * @return
	 */
	public List<InspectionItem> findItemIdListByTaskIdAndProcessIdAndItemType(Integer taskId, Integer processId, Integer itemType, Integer status) {
		if(processId == null) {
			return answerRepository.findItemIdListByTaskIdAndProcessIdIsNullAndItemType(taskId, itemType, status);
		} else if(itemType == null) {
			return answerRepository.findItemIdListByTaskIdAndProcessIdAndItemTypeIsNull(taskId, processId, status);
		} else {
			return answerRepository.findItemIdListByTaskIdAndProcessIdAndItemType(taskId, processId, itemType, status);
		}
	}

//    /**
//     * 根据任务id，和职能工位id类型，查询已经答题成功的质检项id列表
//     * @param taskId					任务id
//     * @param jobStationId				职能工位id
//     * @param status					答案成功状态0：失败、1：成功
//     * @return
//     */
//    public List<Integer> findItemIdListByTaskIdAndJobStationId(Integer taskId, Integer jobStationId, Integer status) {
//    	return answerRepository.findItemIdListByTaskIdAndJobStationId(taskId, jobStationId, status);
//    }
    
    /**
     * 根据任务id，和职能工位id类型，查询已经答题成功的质检项id列表
     * @param taskId						任务id
     * @param jobStationId				职能工位id
     * @param processId					流程任务
     * @param status						答案成功状态0：失败、1：成功
     * @return
     */
    public List<InspectionItem> findItemIdListByTaskIdAndJobStationIdAndProcessId(Integer taskId, Integer jobStationId, Integer processId, Integer status) {
    	return answerRepository.findItemIdListByTaskIdAndJobStationIdAndProcessId(taskId, jobStationId, processId, status);
    }

	/**
	 * 根据任务流转记录id和流转status标识，获取
	 * @param taskRecordId		任务流转记录id
	 * @param status				任务流转状态标识
	 * @param pageNumber			页码
	 * @param pageSize				每页显示记录数
	 * @return
	 */
	public Map<String, Object> getAnswerList(Integer taskRecordId, Integer status, Integer pageNumber, Integer pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//登录用户信息
		UserLoginInfoDTO loginUser = CacheCurrentUserThreadLocal.getCurrentUser();

		//1、根据任务流转记录id查找任务流转记录
		TaskExecuteRecord taskExecuteRecord = taskExecuteRecordService.findOneById(taskRecordId);
		if(taskExecuteRecord == null) {
			resultMap.put("code", 0);
			resultMap.put("msg", "未查找到该任务记录！");
			return resultMap;
		}

		List<Integer> qualityInspectionItemIdList = new ArrayList<Integer>(); //质检项id集合
		List<Answer> answerList = new ArrayList<Answer>();			//已完成答案的质检项答案集合
		List<AnswerVO> answerVOList = new ArrayList<AnswerVO>();			//已完成答案的质检项答案集合-界面显示

		Integer qualityInspectionGroupId = taskExecuteRecord.getQualityInspectionGroupId();

		switch(status) {
			case 1:	//入场鉴定
			case 7:	//整车质检
				//1、根据质检项组id，获取车辆质检质检项id列表信息
				qualityInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(qualityInspectionGroupId, 0);	//查询入厂鉴定/整车质检需执行的质检项id列表

				//2、查询已完成答案的质检项答案集合--包括不成功的答案列表
				//2.1、由于入厂鉴定和整车质检中包含自定义问题(item_id为-1)，所以答案答案列表中应包含自定义质检项答案列表
				qualityInspectionItemIdList.add(-1);
				if(status == 1) {
					answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, null);
				} else if(status == 7) {
					answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.ALL_CAR_ITEM_TYPE, null);
				}

				//4.2、组装已答题信息
				installAnswerListToVO(answerList, answerVOList);
				resultMap.put("code", 1);
				resultMap.put("taskRecordId", taskRecordId);
				resultMap.put("inspectionItemAnswerList", answerVOList);
				break;
			case 4:         //专检
			case 5:         //监造
			case 8:		//入场鉴定企业代表
			case 9:		//入场鉴定监造代表
			case 10:	//整车质检专检代表
			case 11:	//整车质检监造代表
				//1.1、获取质检项id集合
				qualityInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(qualityInspectionGroupId, 0);

				if(status == 4) {
					//专检员--
					//1.2.1、查询出任务流转记录中的质检项组id，查询其质检项id列表
					answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);
				} else if(status == 5) {
					//监造员---
					answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.BUILD_INSPECTION_PROCESS_ID, 1);
				} else if(status == 8) {
					//入场鉴定的专检---
					//由于入厂鉴定有自定义问题，规定itemId为-1，所以答案列表中要多查询itemId为-1的质检项
					qualityInspectionItemIdList.add(-1);
					answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);
				} else if(status == 10) {
					//整车质检的专检---
					//由于整车质检有自定义问题，规定itemId为-1，所以答案列表中要多查询itemId为-1的质检项
					qualityInspectionItemIdList.add(-1);
					answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.ALL_CAR_ITEM_TYPE, Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);
				} else if(status == 9) {
					//入场鉴定的监造--
					//由于入厂鉴定有自定义问题，规定itemId为-1，所以答案列表中要多查询itemId为-1的质检项
					qualityInspectionItemIdList.add(-1);
					answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(),  Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, Constant.BUILD_INSPECTION_PROCESS_ID, 1);
				} else if(status == 11) {
					//整车质检的监造--
					//由于整车质检有自定义问题，规定itemId为-1，所以答案列表中要多查询itemId为-1的质检项
					qualityInspectionItemIdList.add(-1);
					answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(),  Constant.ALL_CAR_ITEM_TYPE, Constant.BUILD_INSPECTION_PROCESS_ID, 1);
				}

				//封装界面显示问题列表
				installAnswerListToVO(answerList, answerVOList);

				resultMap.put("code", 1);
				resultMap.put("taskRecordId", taskRecordId);
				resultMap.put("inspectionItemAnswerList", answerVOList);
				break;
			case 2:		//工位长自检
				//查询职能工位id对应的已完成答案的质检项id集合
				qualityInspectionItemIdList = inspectionItemService.getQualityInspectionItemIdListByJobStationId(taskExecuteRecord.getJobStationId(), 0);
				//查询工位长对应的自检的答题列表
				answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.SELF_INSPECTION_PROCESS_ID);

				//组装已答题信息
				installAnswerListToVO(answerList, answerVOList);

				resultMap.put("code", 1);
				resultMap.put("taskRecordId", taskRecordId);
				resultMap.put("inspectionItemAnswerList", answerVOList);
				break;
			case 3:		//工位长互检的答案列表
				//表示工位长互检答题列表
				//任务流转记录表中工位id存在，即是工位长需要自检的问题列表
				qualityInspectionItemIdList = inspectionItemService.getQualityInspectionItemIdListByJobStationId(taskExecuteRecord.getJobStationId(), 0);
				//processId为自检的答案列表
				answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.EACH_INSPECTION_PROCESS_ID);

				//封装界面显示问题列表
				installAnswerListToVO(answerList, answerVOList);

				resultMap.put("code", 1);
				resultMap.put("taskRecordId", taskRecordId);
				resultMap.put("inspectionItemAnswerList", answerVOList);
				break;
			case 6:
				//表示车间调度员-调度车间
				List<ChoseWorkshopVO> choseWorkshopVOList = (List<ChoseWorkshopVO>)workshopService.workAreaChoseList(taskRecordId).get("datas");
				List<ChoseWorkshopVO> choseWorkshopVOListResult = new ArrayList<ChoseWorkshopVO>();
				for(ChoseWorkshopVO choseWorkshopVO : choseWorkshopVOList) {
					if(choseWorkshopVO.getWorkAreaId() == taskExecuteRecord.getWorkAreaId()) {
						choseWorkshopVOListResult.add(choseWorkshopVO);
					}
				}
				User opreateUser = userService.findOneByUserId(taskExecuteRecord.getUserId());
				resultMap.put("opreateUserName", opreateUser.getName());
				resultMap.put("chooseStationList", choseWorkshopVOListResult);
				resultMap.put("taskRecordId", taskRecordId);
				resultMap.put("code", 1);
				break;
		}
		return resultMap;
	}

	/**
	 * 查询指定任务下的质检项对应的有效自检答题详情
	 * @param taskId		任务id
	 * @param itemId		质检项id
	 * @param status		成功标识，0：失败；1：成功
	 * @return
	 */
	public List<Answer> findAnswerByTaskIdAndItemId(Integer taskId, Integer itemId, Integer status) {
		return answerRepository.findAnswerByTaskIdAndItemId(taskId, itemId, status);
	}

	/**
	 * 组装数据库中的答案为页面展示的答案列表
	 * @param answerList		    数据库中查询的问题列表
	 * @param answerVOList		组装完成后的界面问题列表
	 */
	private void installAnswerListToVO(List<Answer> answerList, List<AnswerVO> answerVOList) {
		for(Answer answer : answerList) {
			AnswerVO answerVO = new AnswerVO();
			answerVO.setId(answer.getId());
			answerVO.setProcessId(answer.getProcessId());
			answerVO.setQualityItemId(answer.getQualityItemId());
			answerVO.setQualityItemName(answer.getQualityItemName());
			answerVO.setTaskId(answer.getTaskId());
			answerVO.setUserId(answer.getUserId());
			answerVO.setUserName(answer.getUserName());
			answerVO.setContent(answer.getContent());
			answerVO.setChooseContent(answer.getChooseContent());
			answerVOList.add(answerVO);
		}
	}
}
