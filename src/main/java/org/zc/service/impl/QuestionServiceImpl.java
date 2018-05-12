package org.zc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zc.common.Constant;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.domain.Answer;
import org.zc.domain.InspectionItem;
import org.zc.domain.Process;
import org.zc.domain.Station;
import org.zc.domain.TaskExecuteRecord;
import org.zc.dto.UserLoginInfoDTO;
import org.zc.service.AnswerService;
import org.zc.service.InspectionItemService;
import org.zc.service.JobStationService;
import org.zc.service.QuestionService;
import org.zc.service.StationService;
import org.zc.service.TaskExecuteRecordService;
import org.zc.service.UserService;
import org.zc.service.WorkshopService;
import org.zc.utils.DateUtils;
import org.zc.vo.AnswerVO;
import org.zc.vo.CommitQuestionVO;
import org.zc.vo.QualityInspectionItemVO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 问题service实现类
 * Created by ceek on 2018-04-19 0:23.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private InspectionItemService inspectionItemService;  //质检项service
    @Autowired
    private TaskExecuteRecordService taskExecuteRecordService;  //任务流转记录service
    @Autowired
	private AnswerService answerService;  //答案service
    @Autowired
    private WorkshopService workshopService;  //车间service
    @Autowired
    private UserService userService;  //用户service
    @Autowired
    private StationService stationService;  //工位service
    @Autowired
    private JobStationService jobStationService;  //职能工位service

    /**
     * 根据任务流转记录id获取当前用户问题列表
     * @param taskRecordId     任务流转记录id
     * @param status           任务流转标识
     * @return
     */
    public Map<String, Object> getQuestionListByTaskRecordId(Integer taskRecordId, Integer status) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //1、根据任务流转记录id查找任务流转记录
        TaskExecuteRecord taskExecuteRecord = taskExecuteRecordService.findOneById(taskRecordId);
        if(taskExecuteRecord == null) {
            resultMap.put("code", 0);
            resultMap.put("msg", "未查找到该任务记录！");
            return resultMap;
        } else {
        	//数据库查询答案列表
        	List<Answer> answerList = new ArrayList<Answer>();
        	//界面显示答案列表
        	List<AnswerVO> answerVOList = new ArrayList<AnswerVO>();
        	//TODO:pcchen 应该设置任务流转到处理节点，才让看问题列表
        	Integer step = taskExecuteRecordService.getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskRecordId);
        	if(step != 0) {
        		resultMap.put("code", 0);
                resultMap.put("msg", "任务还未流转到您，请稍后再试！");
                return resultMap;
        	}
        	//这里不用根据用户权限来流转具体的问题列表，因其在任务列表接口中已经根据指定的用户权限就行了任务流转记录的读取，
        	//这里能读取到指定的任务流转id，并且登录用户有权限角色，则可定已经包含了此任务流转记录id的操作权限
            Integer qualityInspectionGroupId = taskExecuteRecord.getQualityInspectionGroupId();
            //入场鉴定以及整车质检问题列表---任务流转记录表中，质检项组id不为null
            List<QualityInspectionItemVO> inspectionItemVOList = new ArrayList<QualityInspectionItemVO>();
            Integer jobStationId = taskExecuteRecord.getJobStationId();   //只能工位id
            List<Integer> qualityInspectionItemIdList = new ArrayList<Integer>(); //质检项id集合
//			List<InspectionItem> solveInspectionItemList = new ArrayList<InspectionItem>(); //已完成答案的质检项集合
//			List<Answer> solveInspectionItemAnswerList = new ArrayList<Answer>();			//已完成答案的质检项答案集合
//			List<AnswerVO> solveInspectionItemAnswerVOList = new ArrayList<AnswerVO>();			//已完成答案的质检项答案集合-界面显示

			switch(status) {
            case 1:		//入场鉴定
            case 7: 	//整车质检
                //根据质检项组id查询出质检项列表
                //4、根据质检项组id，获取车辆质检质检项列表信息
                List<InspectionItem> inspectionItemList = inspectionItemService.findItemListByGroupId(qualityInspectionGroupId, 0);
				qualityInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(qualityInspectionGroupId, 0);	//查询入厂鉴定/整车质检需执行的质检项id列表

				//查询已完成答案的质检项集合
//				if(status == 1) {
//					solveInspectionItemList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), null, Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, 1);
//				} else if(status == 7) {
//					solveInspectionItemList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), null, Constant.ALL_CAR_ITEM_TYPE, 1);
//				}

				//对于已经答过题合格的质检项不显示
//				inspectionItemList.removeAll(solveInspectionItemList);
				//查询已完成答案的质检项答案集合
//				if(status == 1) {
//					solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, null, 1);
//				} else if(status == 7) {
//					solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.ALL_CAR_ITEM_TYPE, null, 1);
//				}
				
				//4.1、组装质检项组
                installItemListToVO(inspectionItemList, inspectionItemVOList);
                //4.2、组装已答题信息
//                installAnswerListToVO(solveInspectionItemAnswerList, solveInspectionItemAnswerVOList);
                 
                resultMap.put("code", 1);
                resultMap.put("taskRecordId", taskRecordId);
                resultMap.put("inspectionItemList", inspectionItemVOList);
//                resultMap.put("solveInspectionItemAnswerList", solveInspectionItemAnswerVOList);
                break;
            case 4:         //专检
            case 5:         //监造
            case 8:		//入场鉴定企业代表
            case 9:		//入场鉴定监造代表
            case 10:	//整车质检专检代表
            case 11:	//整车质检监造代表
                //1、根据质检项组id，查询入场鉴定所答质检项信息
//                List<InspectionItem> toEntranceCheckupSpecialInspectionItemList = inspectionItemService.findItemListByGroupId(qualityInspectionGroupId, 0);
                //根据质检项组id查询出质检项列表
                //1.1、获取质检项id集合
                qualityInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(qualityInspectionGroupId, 0);

                if(status == 4) {
                    //专检员---查看的是质检项组中工位长的答题列表
                	//1.2.1、查询出任务流转记录中的质检项组id，查询其质检项id列表
                    answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.EACH_INSPECTION_PROCESS_ID, 1);
					//专检员-已答题的答题列表
//					solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);
//
//					
//					
//					//专检员的答题列表---排除已经答过的题
//					answerList.removeAll(solveInspectionItemAnswerList);
                } else if(status == 5) {
					//监造员---查看的是专检的答题列表
					answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);

					//监造员-已答题的答题列表
//					solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.BUILD_INSPECTION_PROCESS_ID, 1);
//
//					//专检员的答题列表---排除已经答过的题
//					answerList.removeAll(solveInspectionItemAnswerList);
                } else if(status == 8) {
                	//入场鉴定的专检---查看的是入厂鉴定的问题列表
					//由于入厂鉴定有自定义问题，规定itemId为-1，所以答案列表中要多查询itemId为-1的质检项
					qualityInspectionItemIdList.add(-1);
                	answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, null, 1);

					//入场鉴定的专检-已答题的答题列表
//					solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(),  Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);
//
//					//入场鉴定的专检的答题列表---排除已经答过的题
//					answerList.removeAll(solveInspectionItemAnswerList);
                } else if(status == 10) {
                    //整车质检的专检---查看的是整车质检的问题列表
					//由于整车质检有自定义问题，规定itemId为-1，所以答案列表中要多查询itemId为-1的质检项
					qualityInspectionItemIdList.add(-1);
                    answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.ALL_CAR_ITEM_TYPE, null, 1);

					//整车质检的专检-已答题的答题列表
//					solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(),  Constant.ALL_CAR_ITEM_TYPE, Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);
//
//					//整车质检的专检的答题列表---排除已经答过的题
//					answerList.removeAll(solveInspectionItemAnswerList);
                } else if(status == 9) {
                	//入场鉴定的监造--查看的是专检的答案列表
					//由于入厂鉴定有自定义问题，规定itemId为-1，所以答案列表中要多查询itemId为-1的质检项
					qualityInspectionItemIdList.add(-1);
                	answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(),  Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);

					//入场鉴定的监造-已答题的答题列表
//					solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(),  Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, Constant.BUILD_INSPECTION_PROCESS_ID, 1);
//					//入场鉴定的监造的答题列表---排除已经答过的题
//					answerList.removeAll(solveInspectionItemAnswerList);
            	} else if(status == 11) {
					//整车质检的监造--查看的是专检的答案列表
					//由于整车质检有自定义问题，规定itemId为-1，所以答案列表中要多查询itemId为-1的质检项
					qualityInspectionItemIdList.add(-1);
					answerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(),  Constant.ALL_CAR_ITEM_TYPE, Constant.SPECIAL_INSPECTION_PROCESS_ID, 1);

					//整车质检的监造-已答题的答题列表
//					solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(),  Constant.ALL_CAR_ITEM_TYPE, Constant.BUILD_INSPECTION_PROCESS_ID, 1);
//					//整车质检的监造的答题列表---排除已经答过的题
//					answerList.removeAll(solveInspectionItemAnswerList);
				}
                
                //封装界面显示问题列表
                installAnswerListToVO(answerList, answerVOList);
                //组装已答题信息
//                installAnswerListToVO(solveInspectionItemAnswerList, solveInspectionItemAnswerVOList);
                
                resultMap.put("code", 1);
                resultMap.put("taskRecordId", taskRecordId);
//                resultMap.put("datas", answerVOList);
                /****************** code by laogen*****************/
                List<QualityInspectionItemVO> tempList = new ArrayList<>();
                for(AnswerVO i:answerVOList) {
                	QualityInspectionItemVO itemTemp =new QualityInspectionItemVO();
                	itemTemp.setType(1);
                	itemTemp.setId(i.getQualityItemId());
                	StringBuffer sb = new StringBuffer(i.getQualityItemName());
            		if (StringUtils.isNotBlank(i.getChooseContent())) {
            			sb.append("\n").append(i.getChooseContent());
					}
            		if (StringUtils.isNotBlank(i.getContent())) {
            			sb.append("\n").append(i.getContent());
					}
                	itemTemp.setTitle(sb.toString());
                	itemTemp.getItemList().add("合格");
                	itemTemp.getItemList().add("不合格");
                	tempList.add(itemTemp);
                }
            	resultMap.put("inspectionItemList", tempList);
//				resultMap.put("inspectionItemAnswerList", answerVOList);
                /*********************over************************/
//				resultMap.put("solveInspectionItemAnswerList", solveInspectionItemAnswerVOList);
                break;
            case 2:
            	//表示工位长自检质检项列表
            	//任务流转记录表中职能工位id存在，即是工位长需要自检的问题列表
            	List<InspectionItem> inspectionItemStaffZhangList = inspectionItemService.getQualityInspectionItemListByJobStationId(jobStationId, 0);

				//查询职能工位id对应的已完成答案的质检项id集合
				qualityInspectionItemIdList = inspectionItemService.getQualityInspectionItemIdListByJobStationId(jobStationId, 0);
				//查询工位长对应的自检的答题列表
				answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.SELF_INSPECTION_PROCESS_ID, 1);

				//查询工位长已经答题的质检项列表
//				solveInspectionItemList = answerService.findItemIdListByTaskIdAndJobStationIdAndProcessId(taskExecuteRecord.getTaskId(), taskExecuteRecord.getJobStationId(), Constant.SELF_INSPECTION_PROCESS_ID, 1);
//				//工位长需要答题的质检项列表排除已经答题的质检项列表
//				inspectionItemStaffZhangList.removeAll(solveInspectionItemList);

				//组装需要答题的质检项组
                installItemListToVO(inspectionItemStaffZhangList, inspectionItemVOList);
                //组装已答题信息
//                installAnswerListToVO(answerList, solveInspectionItemAnswerVOList);
                
            	resultMap.put("code", 1);
            	resultMap.put("taskRecordId", taskRecordId);
//            	resultMap.put("datas", inspectionItemVOList);
				resultMap.put("inspectionItemList", inspectionItemVOList);
//				resultMap.put("solveInspectionItemAnswerList", solveInspectionItemAnswerVOList);
            	break;
            case 3:
            	//表示工位长互检答题列表
            	//任务流转记录表中工位id存在，即是工位长需要自检的问题列表
                qualityInspectionItemIdList = inspectionItemService.getQualityInspectionItemIdListByJobStationId(jobStationId, 0);
                //processId为自检的答案列表
                answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.SELF_INSPECTION_PROCESS_ID, 1);

				//查询已经答题的自检答案列表--互检答案列表
//				solveInspectionItemAnswerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.EACH_INSPECTION_PROCESS_ID, 1);
//
//				//工位长互检答题列表---排除已经答过的题
//				answerList.removeAll(solveInspectionItemAnswerList);

                //封装界面显示问题列表
                installAnswerListToVO(answerList, answerVOList);
                //组装已答题信息
//                installAnswerListToVO(solveInspectionItemAnswerList, solveInspectionItemAnswerVOList);

            	resultMap.put("code", 1);
            	resultMap.put("taskRecordId", taskRecordId);
            	/****************** code by laogen*****************/
                List<QualityInspectionItemVO> tempList1 = new ArrayList<>();
                for(AnswerVO i:answerVOList) {
                	QualityInspectionItemVO itemTemp =new QualityInspectionItemVO();
                	itemTemp.setType(1);
                	itemTemp.setId(i.getQualityItemId());
                	StringBuffer sb = new StringBuffer(i.getQualityItemName());
            		if (StringUtils.isNotBlank(i.getChooseContent())) {
            			sb.append("\n").append(i.getChooseContent());
					}
            		if (StringUtils.isNotBlank(i.getContent())) {
            			sb.append("\n").append(i.getContent());
					}
                	itemTemp.setTitle(sb.toString());
                	itemTemp.getItemList().add("合格");
                	itemTemp.getItemList().add("不合格");
                	tempList1.add(itemTemp);
                }
            	resultMap.put("inspectionItemList", tempList1);
//				resultMap.put("inspectionItemAnswerList", answerVOList);
//				resultMap.put("solveInspectionItemAnswerList", solveInspectionItemAnswerVOList);
            	break;
            case 6:
                //表示车间调度员-调度车间
                resultMap = workshopService.workAreaChoseList(taskRecordId);
                resultMap.put("taskRecordId", taskRecordId);
                break;
            }
            return resultMap;
        }
    }
    
    /**
     * 批量提交问题
     * @param platformList		问题答案封装json数组
     * @param taskRecordId		任务流转id
     * @param status			任务流转状态标识
     * @return
     */
    public Map<String, Object> batchCommitQuestionList(JSONArray platformList, Integer taskRecordId, Integer status) {
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
        
        //1.1、判断之前的任务已经流转完成才能进行答题
		//TODO:pcchen 应该设置任务流转到处理节点，才让看问题列表
        Integer step = taskExecuteRecordService.getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
        if(step != null && step > 0) {
        	resultMap.put("code", 0);
        	resultMap.put("msg", "任务还未流转到您处，请稍后质检！");
        	return resultMap;
        }
        //是否有不合格的问题
        Boolean executeSuccess = true;
        
        List<Answer> answerList = new ArrayList<Answer>();
        for (Object jsonObject : platformList) {
			CommitQuestionVO commitQuestionVO = JSONObject.parseObject(jsonObject.toString(), CommitQuestionVO.class);
			if(commitQuestionVO.getOperateUserId() == null || commitQuestionVO.getItemId() == null) {
				resultMap.put("msg", "请填写必填项！！！");
				resultMap.put("code", 0);
				return resultMap;
			}
			if(-1 == commitQuestionVO.getItemId()) {
				if(StringUtils.isBlank(commitQuestionVO.getContent()) && StringUtils.isBlank(commitQuestionVO.getChooseContent())) {
					resultMap.put("msg", "自定义问题有未填写答案的选项！！！");
					resultMap.put("code", 0);
					return resultMap;
				}
			}
			Answer answer = new Answer();
			if(executeSuccess) {
				executeSuccess = switchAnswer(answer, commitQuestionVO.getOperateUserId(), taskExecuteRecord, taskExecuteRecord.getId(), commitQuestionVO.getItemId(), 
						commitQuestionVO.getTitle(), commitQuestionVO.getContent(), commitQuestionVO.getChooseContent(), status, resultMap);
			} else {
				switchAnswer(answer, commitQuestionVO.getOperateUserId(), taskExecuteRecord, taskExecuteRecord.getId(), commitQuestionVO.getItemId(), 
						commitQuestionVO.getTitle(), commitQuestionVO.getContent(), commitQuestionVO.getChooseContent(), status, resultMap);
			}
			if(resultMap != null && resultMap.get("code") != null && ((Integer)resultMap.get("code")) == 0) {
				return resultMap;
			}
			answerList.add(answer);
		}
        
        if(!executeSuccess) {
        	//当有不合格答案时
        	//将所有答案status设置为0：失效
        	for(Answer answer : answerList) {
        		answer.setStatus(0);
        	}
        } else {
        	//当没有不合格时
        	switch(status) {
            case 2:			//员工长自检
				Process eachProcess = jobStationService.findProcessByJobStationId(taskExecuteRecord.getJobStationId(), Constant.EACH_INSPECTION_PROCESS_ID);
				//判断当前职能工位有互检没有，如果没有，则次任务流转完成
            	if(eachProcess == null) {
            		taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, loginUser.getUserId(), 1);
            	} else {
            		taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, loginUser.getUserId(), 0);
            	}
            	break;
            case 3:			//员工长互检
            	taskExecuteRecordService.updateRecordExecuteStatusAndEachCheckUserId(taskRecordId, loginUser.getUserId(), 1);
            	break;
        	default:
        		taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, loginUser.getUserId(), 1);
        		break;
        	} 
        }
        
		resultMap.put("code", 1);
		resultMap.put("msg", "保存成功！");
        answerService.saveAnswerList(answerList);
        return resultMap;
    }
    
    
    private Boolean switchAnswer(Answer answer, Integer operateUserId, TaskExecuteRecord taskExecuteRecord, Integer taskRecordId, Integer itemId, 
    		String itemTitle, String content, String chooseContent, Integer status, Map<String, Object> resultMap) {
    	Boolean executeSuccess = true;
    	
    	Long currentDateTime = DateUtils.getSecondRankLongNumberFromDate(new Date());
    	//登录用户信息
    	
    	switch(status) {
        case 1:	//入场鉴定
        case 7:	//整车质检
			//当itemId为-1时，表示自定义问题
        	if(status == 1) {
        		//入场鉴定item_type=1
        		answer.setItemType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
        	} else if(status == 7) {
        		//整车质检item_type=3
        		answer.setItemType(Constant.ALL_CAR_ITEM_TYPE);
        	}
        	answer.setQualityItemId(itemId);
        	if(itemId == -1) {
        		//表示自定义问题
        		answer.setQualityItemName(itemTitle);
        	} else {
        		answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	}
        	if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	answer.setStatus(1);
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);
        	
        	break;
        case 2://表示工位长自检
        	answer.setProcessId(Constant.SELF_INSPECTION_PROCESS_ID);
        	answer.setQualityItemId(itemId);
        	answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	//查询职能工位对应的物理工位
        	Station station = stationService.findStationByJobStationId(taskExecuteRecord.getJobStationId(), taskExecuteRecord.getWorkAreaId(), taskExecuteRecord.getTypeId());
        	answer.setJobStationId(taskExecuteRecord.getJobStationId());
        	answer.setStationId(station.getId());
        	answer.setWorkAreaId(taskExecuteRecord.getWorkAreaId());
        	answer.setWorkshopId(taskExecuteRecord.getWorkshopId());
        	answer.setStatus(1);
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);
        	
			break;
        case 3://表示工位长互检
			//5.1、再有自检流程下，自检人和互检人不应该相同
			Process eachProcess = jobStationService.findProcessByJobStationId(taskExecuteRecord.getJobStationId(), Constant.EACH_INSPECTION_PROCESS_ID);
			if(eachProcess != null) {
				//查询指定任务下的质检项对应的有效自检答题详情
				List<Answer> answerListDB = answerService.findAnswerByTaskIdAndItemId(taskExecuteRecord.getTaskId(), itemId, 1);
				if(answerListDB != null && answerListDB.size() > 0 && answerListDB.get(0).getUserId() == operateUserId) {
					resultMap.put("code", 0);
					resultMap.put("msg", "自检人和互检人不能相同！");
					break;
				}
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.EACH_INSPECTION_PROCESS_ID);
        	answer.setQualityItemId(itemId);
        	answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	//查询职能工位对应的物理工位
        	Station stationEach = stationService.findStationByJobStationId(taskExecuteRecord.getJobStationId(), taskExecuteRecord.getWorkAreaId(), taskExecuteRecord.getTypeId());
        	answer.setJobStationId(taskExecuteRecord.getJobStationId());
        	answer.setStationId(stationEach.getId());
        	answer.setWorkAreaId(taskExecuteRecord.getWorkAreaId());
        	answer.setWorkshopId(taskExecuteRecord.getWorkshopId());
        	answer.setStatus(1);
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);

        	break;
        case 4://表示专检
        	if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				break;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		TaskExecuteRecord findOneById = taskExecuteRecordService.findOneById(taskRecordId);
        		if("合格".equals(chooseContent.trim())) {
        			answer.setStatus(1);
        		} else {
        			executeSuccess = false;
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
        	answer.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);
        	answer.setQualityItemId(itemId);
        	answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);

        	break;
        case 5://表示监造
        	if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				break;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			answer.setStatus(1);
        		} else {
        			executeSuccess = false;
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
        	answer.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);
        	answer.setQualityItemId(itemId);
        	answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);

        	break;
		case 8:		//入场鉴定企业代表
			if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				break;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			answer.setStatus(1);
        		} else {
        			executeSuccess = false;
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);
			answer.setItemType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
			answer.setQualityItemId(itemId);
			if(itemId == -1) {
				//表示自定义问题
				answer.setQualityItemName(itemTitle);
			} else {
				answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
			}
			answer.setUserId(operateUserId);
			answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
			answer.setTaskId(taskExecuteRecord.getTaskId());
			answer.setCreateTime(currentDateTime);
			answer.setUpdateTime(currentDateTime);

			break;
		case 9:		//入场鉴定监造代表
			if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				break;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			answer.setStatus(1);
        		} else {
        			executeSuccess = false;
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);
			answer.setItemType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
			answer.setQualityItemId(itemId);
			if(itemId == -1) {
				//表示自定义问题
				answer.setQualityItemName(itemTitle);
			} else {
				answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
			}
			answer.setUserId(operateUserId);
			answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
			answer.setTaskId(taskExecuteRecord.getTaskId());
			answer.setCreateTime(currentDateTime);
			answer.setUpdateTime(currentDateTime);

			break;
		case 10:	//整车质检专检代表
			if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				break;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			answer.setStatus(1);
        		} else {
        			executeSuccess = false;
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);
			answer.setItemType(Constant.ALL_CAR_ITEM_TYPE);
			answer.setQualityItemId(itemId);
			if(itemId == -1) {
				//表示自定义问题
				answer.setQualityItemName(itemTitle);
			} else {
				answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
			}
			answer.setUserId(operateUserId);
			answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
			answer.setTaskId(taskExecuteRecord.getTaskId());
			answer.setCreateTime(currentDateTime);
			answer.setUpdateTime(currentDateTime);

			break;
		case 11:	//整车质检监造代表
			if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				break;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			answer.setStatus(1);
        		} else {
        			executeSuccess = false;
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);
			answer.setItemType(Constant.ALL_CAR_ITEM_TYPE);
			answer.setQualityItemId(itemId);
			if(itemId == -1) {
				//表示自定义问题
				answer.setQualityItemName(itemTitle);
			} else {
				answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
			}
			answer.setUserId(operateUserId);
			answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
			answer.setTaskId(taskExecuteRecord.getTaskId());
			answer.setStatus(1);
			answer.setCreateTime(currentDateTime);
			answer.setUpdateTime(currentDateTime);

			break;
        }
    	return executeSuccess;
    }
    
    /**
     * 问题提交
     * @param operateUserId			操作人id
     * @param taskRecordId			任务流转id
     * @param itemId				质检项id
     * @param itemTitle				质检项问题(当itemId为-1时，为自定义问题)
     * @param content				所填答案（自定义答案
     * @param chooseContent			针对选择题判断（选择的答案）
     * @param typeStr				题目类型	0为判断题，1为选择题，2为填空题 ，3为多选题
     * @param status				任务流转状态标识
     * @return
     */
    public Map<String, Object> commitQuestionList(Integer operateUserId, Integer taskRecordId, Integer itemId, 
    		String itemTitle, String content, String chooseContent, String typeStr, Integer status) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	//登录用户信息
        UserLoginInfoDTO loginUser = CacheCurrentUserThreadLocal.getCurrentUser();
    	
//    	Integer type = 0;
//		if(StringUtils.isNotBlank(typeStr)) {
//			type = Integer.parseInt(typeStr.trim());
//		}

    	Long currentDateTime = DateUtils.getSecondRankLongNumberFromDate(new Date());
    	
        //1、根据任务流转记录id查找任务流转记录
        TaskExecuteRecord taskExecuteRecord = taskExecuteRecordService.findOneById(taskRecordId);
        if(taskExecuteRecord == null) {
            resultMap.put("code", 0);
            resultMap.put("msg", "未查找到该任务记录！");
            return resultMap;
        }
        
        
        //1.1、判断之前的任务已经流转完成才能进行答题
		//TODO:pcchen 应该设置任务流转到处理节点，才让看问题列表
        Integer step = taskExecuteRecordService.getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
        if(step != null && step > 0) {
        	resultMap.put("code", 0);
        	resultMap.put("msg", "任务还未流转到您处，请稍后质检！");
        	return resultMap;
        }
        Answer answer = new Answer();
        switch(status) {
        case 1:	//入场鉴定
        case 7:	//整车质检
			//当itemId为-1时，表示自定义问题
        	if(status == 1) {
        		//入场鉴定item_type=1
        		answer.setItemType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
        	} else if(status == 7) {
        		//整车质检item_type=3
        		answer.setItemType(Constant.ALL_CAR_ITEM_TYPE);
        	}
        	answer.setQualityItemId(itemId);
        	if(itemId == -1) {
        		//表示自定义问题
        		answer.setQualityItemName(itemTitle);
        	} else {
        		answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	}
        	if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	answer.setStatus(1);
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);
        	
        	//判断质检项组是否执行完成---完成保存任务流转记录
        	if(status == 1) {
				List<Integer> entranceItemIdList = inspectionItemService.findItemIdListByGroupId(taskExecuteRecord.getQualityInspectionGroupId(), 0);
				List<InspectionItem> entranceAnswerItemIdList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), null, Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, 1);
        		if(entranceItemIdList.size() > 1 && entranceAnswerItemIdList.size() + 1 == entranceItemIdList.size()) {
					taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, 1);
        		}
        	} else if(status == 7) {
        		List<Integer> allCarItemIdList = inspectionItemService.findItemIdListByGroupId(taskExecuteRecord.getQualityInspectionGroupId(), 0);
                List<InspectionItem> allCarAnswerItemIdList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), null, Constant.ALL_CAR_ITEM_TYPE, 1);
                //当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
				if(allCarItemIdList.size() > 1 && allCarAnswerItemIdList.size() + 1 == allCarItemIdList.size()) {
					taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, 1);
				}
			}
        	break;
        case 2://表示工位长自检
        	answer.setProcessId(Constant.SELF_INSPECTION_PROCESS_ID);
        	answer.setQualityItemId(itemId);
        	answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	//查询职能工位对应的物理工位
        	Station station = stationService.findStationByJobStationId(taskExecuteRecord.getJobStationId(), taskExecuteRecord.getWorkAreaId(), taskExecuteRecord.getTypeId());
        	answer.setJobStationId(taskExecuteRecord.getJobStationId());
        	answer.setStationId(station.getId());
        	answer.setWorkAreaId(taskExecuteRecord.getWorkAreaId());
        	answer.setWorkshopId(taskExecuteRecord.getWorkshopId());
        	answer.setStatus(1);
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);
        	
        	//查询职能工位对应质检项id列表
        	List<Integer> staffItemIdList = jobStationService.findItemIdListByJobStationId(taskExecuteRecord.getJobStationId());
        	//查询工位长已自检答题的质检项id列表--查询某一任务的指定职能工位的成功的答案中的质检项id列表
        	List<InspectionItem> staffAnswerItemIdList = answerService.findItemIdListByTaskIdAndJobStationIdAndProcessId(taskExecuteRecord.getTaskId(), taskExecuteRecord.getJobStationId(), Constant.SELF_INSPECTION_PROCESS_ID, 1);
    		//当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
    		if(staffItemIdList.size() > 1 && staffAnswerItemIdList.size() + 1 == staffItemIdList.size()) {
				//工位长自检，只更新任务流转表的user_id字段，不更新状态，当互检完成时更新check_user_id并更新状态
				//user_id和check_user_id用于用户任务列表中自检和互检的区别显示；自检user_id为null，互检user_id有值，check_user_id为null
				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, loginUser.getUserId(), 1);
			}
			break;
        case 3://表示工位长互检
			//5.1、再有自检流程下，自检人和互检人不应该相同
			Process selfProcess = jobStationService.findProcessByJobStationId(taskExecuteRecord.getJobStationId(), Constant.SELF_INSPECTION_PROCESS_ID);
			if(selfProcess != null) {
				//查询指定任务下的质检项对应的有效自检答题详情
				List<Answer> answerListDB = answerService.findAnswerByTaskIdAndItemId(taskExecuteRecord.getTaskId(), itemId, 1);
				if(answerListDB != null && answerListDB.size() > 0 && answerListDB.get(0).getUserId() == operateUserId) {
					resultMap.put("code", 0);
					resultMap.put("msg", "自检人和互检人不能相同！");
					return resultMap;
				}
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.EACH_INSPECTION_PROCESS_ID);
        	answer.setQualityItemId(itemId);
        	answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	answer.setStatus(1);
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);

			//查询职能工位对应质检项id列表
			List<Integer> staffEachItemIdList = jobStationService.findItemIdListByJobStationId(taskExecuteRecord.getJobStationId());
			//查询工位长已互检答题的质检项id列表--查询某一任务的指定职能工位的成功的答案中的质检项id列表
			List<InspectionItem> staffEachAnswerItemIdList = answerService.findItemIdListByTaskIdAndJobStationIdAndProcessId(taskExecuteRecord.getTaskId(), taskExecuteRecord.getJobStationId(), Constant.EACH_INSPECTION_PROCESS_ID, 1);
			//当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
			if(staffEachItemIdList.size() > 1 && staffEachAnswerItemIdList.size() + 1 == staffEachItemIdList.size()) {
				//保存工位长互检人信息
				taskExecuteRecordService.updateRecordExecuteStatusAndEachCheckUserId(taskRecordId, loginUser.getUserId(), 1);
			}
        	break;
        case 4://表示专检
        	if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				return resultMap;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		TaskExecuteRecord findOneById = taskExecuteRecordService.findOneById(taskRecordId);
        		if("合格".equals(chooseContent.trim())) {
        			//只有当之前保存的质检项问题为非失败时，更新执行状态
        			if(findOneById.getExecuteStatus() != -1) {
        				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, 1);
        			}
        			answer.setStatus(1);
        		} else {
        			taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, -1);
        			answer.setStatus(0);
//        			//对于次任务的专检问题全部设置为失效
//        			//1、根据质检项组id查到质检项id集合
//        			List<Integer> qualityInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(findOneById.getQualityInspectionGroupId(), 0);
//        			List<Answer> answerList = answerService.findAnswerListByQualityInspectionItemIdList(qualityInspectionItemIdList, taskExecuteRecord.getTaskId(), Constant.EACH_INSPECTION_PROCESS_ID, 1);
//        			
//        			for( : answerList) {
//        				
//        			}
//        			answerService.updateAnswerDisableByItemIdAndTaskIdAndProcessId();
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
        	answer.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);
        	answer.setQualityItemId(itemId);
        	answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);

//			查询专检质检项组的质检项id列表
//			List<Integer> specialInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(taskExecuteRecord.getQualityInspectionGroupId(), 0);
//			//查询专检答题的质检项id列表--查询某一人物的指定processId为专检，itemType为null的答案中的质检项id列表
//			List<InspectionItem> specialInspectionAnswerItemIdList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), Constant.SPECIAL_INSPECTION_PROCESS_ID, null, 1);
//
//			//当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
//			if(specialInspectionItemIdList.size() > 1 && specialInspectionAnswerItemIdList.size() + 1== specialInspectionItemIdList.size()) {
//				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId);
//			}

        	break;
        case 5://表示监造
        	if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				return resultMap;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			TaskExecuteRecord findOneById = taskExecuteRecordService.findOneById(taskRecordId);
        			//只有当之前保存的质检项问题为非失败时，更新执行状态
        			if(findOneById.getExecuteStatus() != -1) {
        				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, 1);
        			}
        			answer.setStatus(1);
        		} else {
        			taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, -1);
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
        	answer.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);
        	answer.setQualityItemId(itemId);
        	answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
        	answer.setUserId(operateUserId);
        	answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
        	answer.setTaskId(taskExecuteRecord.getTaskId());
        	answer.setCreateTime(currentDateTime);
        	answer.setUpdateTime(currentDateTime);

//			//查询监造质检项组的质检项id列表
//			List<Integer> buildInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(taskExecuteRecord.getQualityInspectionGroupId(), 0);
//			//查询监造答题的质检项id列表--查询某一任务的指定processId为监造，itemType为null的答案中的质检项id列表
//			List<InspectionItem> buildInspectionAnswerItemIdList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), Constant.BUILD_INSPECTION_PROCESS_ID, null, 1);
//
//			//当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
//			if(buildInspectionItemIdList.size() > 1 && buildInspectionAnswerItemIdList.size() + 1 == buildInspectionItemIdList.size()) {
//				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId);
//			}

        	break;
		case 8:		//入场鉴定企业代表
			if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				return resultMap;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			TaskExecuteRecord findOneById = taskExecuteRecordService.findOneById(taskRecordId);
        			//只有当之前保存的质检项问题为非失败时，更新执行状态
        			if(findOneById.getExecuteStatus() != -1) {
        				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, 1);
        			}
        			answer.setStatus(1);
        		} else {
        			taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, -1);
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);
			answer.setItemType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
			answer.setQualityItemId(itemId);
			if(itemId == -1) {
				//表示自定义问题
				answer.setQualityItemName(itemTitle);
			} else {
				answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
			}
			answer.setUserId(operateUserId);
			answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
			answer.setTaskId(taskExecuteRecord.getTaskId());
			answer.setCreateTime(currentDateTime);
			answer.setUpdateTime(currentDateTime);

//			//查询入场鉴定企业代表质检项组的质检项id列表
//			List<Integer> toEntranceSpecialInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(taskExecuteRecord.getQualityInspectionGroupId(), 0);
//			//查询入场鉴定企业代表答题的质检项id列表--查询某一任务的指定processId为专检，itemType为入厂鉴定的答案中的质检项id列表
//			List<InspectionItem> toEntranceSpecialInspectionAnswerItemIdList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), Constant.SPECIAL_INSPECTION_PROCESS_ID, Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, 1);
//
//			//TODO:pcchen 由于此处中入场鉴定有自定义问题，所以此处的更新操作人只是对于质检项问题答完(未包含自定义问题)后就报错任务流转记录状态，操作人信息---待优化，
//			//当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
//			if(toEntranceSpecialInspectionItemIdList.size() > 1 && toEntranceSpecialInspectionAnswerItemIdList.size() + 1 == toEntranceSpecialInspectionItemIdList.size()) {
//				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId);
//			}

			break;
		case 9:		//入场鉴定监造代表
			if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				return resultMap;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			TaskExecuteRecord findOneById = taskExecuteRecordService.findOneById(taskRecordId);
        			//只有当之前保存的质检项问题为非失败时，更新执行状态
        			if(findOneById.getExecuteStatus() != -1) {
        				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, 1);
        			}
        			answer.setStatus(1);
        		} else {
        			taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, -1);
        			answer.setStatus(0);
        			resultMap.put("code", 0);
    				resultMap.put("msg", "问题必须选择是否合格！");
    				return resultMap;
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);
			answer.setItemType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
			answer.setQualityItemId(itemId);
			if(itemId == -1) {
				//表示自定义问题
				answer.setQualityItemName(itemTitle);
			} else {
				answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
			}
			answer.setUserId(operateUserId);
			answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
			answer.setTaskId(taskExecuteRecord.getTaskId());
			answer.setCreateTime(currentDateTime);
			answer.setUpdateTime(currentDateTime);

//			//查询监造质检项组的质检项id列表
//			List<Integer> toEntranceBuildInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(taskExecuteRecord.getQualityInspectionGroupId(), 0);
//			//查询监造答题的质检项id列表--查询某一任务的指定processId为监造，itemType为入厂鉴定的答案中的质检项id列表
//			List<InspectionItem> toEntranceBuildInspectionAnswerItemIdList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), Constant.BUILD_INSPECTION_PROCESS_ID, Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE, 1);
//
//			//当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
//			if(toEntranceBuildInspectionItemIdList.size() > 1 && toEntranceBuildInspectionAnswerItemIdList.size() + 1 == toEntranceBuildInspectionItemIdList.size()) {
//				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId);
//			}

			break;
		case 10:	//整车质检专检代表
			if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				return resultMap;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			TaskExecuteRecord findOneById = taskExecuteRecordService.findOneById(taskRecordId);
        			//只有当之前保存的质检项问题为非失败时，更新执行状态
        			if(findOneById.getExecuteStatus() != -1) {
        				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, 1);
        			}
        			answer.setStatus(1);
        		} else {
        			taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, -1);
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.SPECIAL_INSPECTION_PROCESS_ID);
			answer.setItemType(Constant.ALL_CAR_ITEM_TYPE);
			answer.setQualityItemId(itemId);
			if(itemId == -1) {
				//表示自定义问题
				answer.setQualityItemName(itemTitle);
			} else {
				answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
			}
			answer.setUserId(operateUserId);
			answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
			answer.setTaskId(taskExecuteRecord.getTaskId());
			answer.setCreateTime(currentDateTime);
			answer.setUpdateTime(currentDateTime);

//			//查询整车质检专检代表质检项组的质检项id列表
//			List<Integer> allCarSpecialInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(taskExecuteRecord.getQualityInspectionGroupId(), 0);
//			//查询整车质检专检代表答题的质检项id列表--查询某一任务的指定processId为专检，itemType为整车质检的答案中的质检项id列表
//			List<InspectionItem> allCarSpecialInspectionAnswerItemIdList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), Constant.SPECIAL_INSPECTION_PROCESS_ID, Constant.ALL_CAR_ITEM_TYPE, 1);
//
//			//当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
//			if(allCarSpecialInspectionItemIdList.size() > 1 && allCarSpecialInspectionAnswerItemIdList.size() + 1 == allCarSpecialInspectionItemIdList.size()) {
//				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId);
//			}

			break;
		case 11:	//整车质检监造代表
			if(StringUtils.isBlank(chooseContent)) {
				resultMap.put("code", 0);
				resultMap.put("msg", "问题必须选择是否合格！");
				return resultMap;
			}
			if(StringUtils.isNotBlank(chooseContent)) {
        		answer.setChooseContent(chooseContent);
        		if("合格".equals(chooseContent.trim())) {
        			TaskExecuteRecord findOneById = taskExecuteRecordService.findOneById(taskRecordId);
        			//只有当之前保存的质检项问题为非失败时，更新执行状态
        			if(findOneById.getExecuteStatus() != -1) {
        				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, 1);
        			}
        			answer.setStatus(1);
        		} else {
        			taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId, -1);
        			answer.setStatus(0);
        		}
        	}
        	if(StringUtils.isNotBlank(content)) {
        		answer.setContent(content);
        	}
			answer.setProcessId(Constant.BUILD_INSPECTION_PROCESS_ID);
			answer.setItemType(Constant.ALL_CAR_ITEM_TYPE);
			answer.setQualityItemId(itemId);
			if(itemId == -1) {
				//表示自定义问题
				answer.setQualityItemName(itemTitle);
			} else {
				answer.setQualityItemName(inspectionItemService.findItemByItemid(itemId).getTitle());
			}
			answer.setUserId(operateUserId);
			answer.setUserName(userService.findOneByUserId(operateUserId).getUsername());
			answer.setTaskId(taskExecuteRecord.getTaskId());
			answer.setStatus(1);
			answer.setCreateTime(currentDateTime);
			answer.setUpdateTime(currentDateTime);

//			//查询整车质检监造代表质检项组的质检项id列表
//			List<Integer> allCarBuildInspectionItemIdList = inspectionItemService.findItemIdListByGroupId(taskExecuteRecord.getQualityInspectionGroupId(), 0);
//			//查询整车质检监造代表答题的质检项id列表--查询某一任务的指定processId为监造，itemType为整车质检的答案中的质检项id列表
//			List<InspectionItem> allCarBuildInspectionAnswerItemIdList = answerService.findItemIdListByTaskIdAndProcessIdAndItemType(taskExecuteRecord.getTaskId(), Constant.BUILD_INSPECTION_PROCESS_ID, Constant.ALL_CAR_ITEM_TYPE, 1);
//
//			//当数据库中的质检项组包含质检项与答题列表中的成功答案的质检项的个数相同时，说明该节点流转完成-更新任务流转状态
//			if(allCarBuildInspectionItemIdList.size() > 1 && allCarBuildInspectionAnswerItemIdList.size() + 1 == allCarBuildInspectionItemIdList.size()) {
//				taskExecuteRecordService.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, operateUserId);
//			}

			break;
        }
		resultMap.put("code", 1);
		resultMap.put("msg", "保存成功！");
        answerService.saveAnswer(answer);
        return resultMap;
    }
    
    /**
     * 组装数据库中的质检项为页面展示的质检项列表
     * @param inspectionItemList		数据库中查询的质检项
     * @param inspectionItemVOList		组装完成后的界面质检项
     */
    private void installItemListToVO(List<InspectionItem> inspectionItemList, List<QualityInspectionItemVO> inspectionItemVOList) {
    	for(InspectionItem inspectionItem : inspectionItemList) {
            QualityInspectionItemVO qualityInspectionItemVO = new QualityInspectionItemVO();
            qualityInspectionItemVO.setId(inspectionItem.getId());
            qualityInspectionItemVO.setTitle(inspectionItem.getTitle());
            JSONArray jsonArray = JSONArray.parseArray(inspectionItem.getStandard());
            Object[] array = jsonArray.toArray();
           	 for(Object tmp : array) {
           		 qualityInspectionItemVO.getItemList().add(((String)tmp).trim());
           	 }
            qualityInspectionItemVO.setType(inspectionItem.getType());
            inspectionItemVOList.add(qualityInspectionItemVO);
        }
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
