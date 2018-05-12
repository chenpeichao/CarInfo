package org.zc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zc.common.Constant;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.domain.JobStation;
import org.zc.domain.Process;
import org.zc.domain.TaskExecuteRecord;
import org.zc.dto.TaskListVehicleDTO;
import org.zc.dto.UserLoginInfoDTO;
import org.zc.repository.TaskExecuteRecordRepository;
import org.zc.service.JobStationService;
import org.zc.service.TaskExecuteRecordService;
import org.zc.service.UserService;
import org.zc.service.ZcTaskService;
import org.zc.vo.TaskExecuteRecordVO;
import org.zc.vo.TaskVO;

/**
 * Description:
 * Create by @author cpc
 * 2018年4月21日 下午3:50:18
 */
@Service
@Transactional
public class TaskExecuteRecordServiceImpl implements TaskExecuteRecordService {
	Logger logger = Logger.getLogger(TaskExecuteRecordServiceImpl.class);
	@Autowired
	private TaskExecuteRecordRepository taskExecuteRecordRepository;
	@Autowired
	private ZcTaskService zcTaskService;
	@Autowired
	private UserService userService;
	/**职能工位*/
	@Autowired
	private JobStationService jobStationService;

	/**
	 * 保存任务流转记录
	 * @param taskExecuteRecord
	 */
	public void saveTaskExecuteRecord(TaskExecuteRecord taskExecuteRecord) {
		taskExecuteRecordRepository.save(taskExecuteRecord);
	}
	
	/**
	 * 保存任务流转记录列表
	 * @param taskExecuteRecord
	 */
	public void saveTaskExecuteRecordList(List<TaskExecuteRecord> taskExecuteRecordList) {
		taskExecuteRecordRepository.save(taskExecuteRecordList);
	}

	/**
	 * 获取用户任务列表
	 * @param userLoginInfoDTO	登录用户dto
	 * @param pageNumber		页码
	 * @param pageSize			每页显示记录数
	 * @return
	 */
	public Map<String, Object> getUserTask(UserLoginInfoDTO userLoginInfoDTO, Integer pageNumber, Integer pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//当前登录用户无相关角色
		if(userLoginInfoDTO.getRoleMap() == null) {
			resultMap.put("code", 0);
			resultMap.put("msg", "当前用户无任务列表!");
			return resultMap;
		}
		//分页信息封装--按照taskId倒序排列
		ArrayList<Order> sortOrder = new ArrayList<Sort.Order>();
		sortOrder.add(new Sort.Order(Direction.ASC, "executeStatus"));
		sortOrder.add(new Sort.Order(Direction.DESC, "taskId"));
    	Sort sort = new Sort(sortOrder);
		Pageable pageable = new PageRequest(pageNumber, pageSize, sort);  //分页信息

		//根据用户所有的角色id，来检查当前角色下需要执行的任务
		//遍历当前用户的角色列表
		List<TaskVO> taskVOList = new ArrayList<TaskVO>();
		for(Integer roleId : userLoginInfoDTO.getRoleMap().keySet()){
			switch (roleId) {
				case 4:	//入场鉴定员
						//在任务流转表中获取人场鉴定---qualityInspectionGroupType：1入厂鉴定，2工位质检，3整车质检
						Specification<TaskExecuteRecord> spec4 = new Specification<TaskExecuteRecord>() {        //查询条件构造
							@Override
				            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
								Path<Integer> qualityInspectionGroupType = taskExecuteRecord.get("qualityInspectionGroupType");
								Path<Integer> processId = taskExecuteRecord.get("processId");
								Predicate p1 = cb.equal(qualityInspectionGroupType, Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
								Predicate p2 = cb.isNull(processId);
								Predicate p = cb.and(p1, p2);
								return p;
				            }
						};
						//封装分页TaskExecuteRecord实体列表
				        Page<TaskExecuteRecord> taskExecuteRecordToEntranceCheckupPage = taskExecuteRecordRepository.findAll(spec4, pageable);
						
//						List<TaskExecuteRecord> entranceCheckUpList = taskExecuteRecordRepository.getTaskListByQualityInspectionGroupType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
						List<TaskExecuteRecord> entranceCheckUpList = taskExecuteRecordToEntranceCheckupPage.getContent();
						for(TaskExecuteRecord taskExecuteRecord : entranceCheckUpList) {
							//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
							TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
							TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
									taskListVehicleDTO.getVehiclePlate(),
									taskListVehicleDTO.getVehicleModel(),
									taskListVehicleDTO.getVehicleType());
							if(taskExecuteRecord.getExecuteStatus() <= 0) {
								taskVO.setMsg("您现在需要进行 入场鉴定");
								taskVO.setStatus(1);
							} else {
								taskVO.setMsg("工作已完成 ！");
								taskVO.setStatus(0);
							}
							taskVOList.add(taskVO);
						};
						resultMap.put("totalPages", taskExecuteRecordToEntranceCheckupPage.getTotalPages());
						resultMap.put("totalElements", taskExecuteRecordToEntranceCheckupPage.getTotalElements());
						break;
					case 11:	//入场鉴定企业代表
						Specification<TaskExecuteRecord> spec11 = new Specification<TaskExecuteRecord>() {        //查询条件构造
							@Override
				            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
								Path<Integer> qualityInspectionGroupType = taskExecuteRecord.get("qualityInspectionGroupType");
								Path<Integer> processId = taskExecuteRecord.get("processId");
								Predicate p1 = cb.equal(qualityInspectionGroupType, Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
								Predicate p2 = cb.equal(processId, Constant.SPECIAL_INSPECTION_PROCESS_ID);
								Predicate p = cb.and(p1, p2);
								return p;
				            }
						};
						//封装分页TaskExecuteRecord实体列表
				        Page<TaskExecuteRecord> toEntranceCheckupSpecialInspectionPage = taskExecuteRecordRepository.findAll(spec11, pageable);
				        List<TaskExecuteRecord> entranceCheckUpSpecialInspectionList = toEntranceCheckupSpecialInspectionPage.getContent();
						//在任务流转表中获取入场鉴定企业代表---qualityInspectionGroupType：1入厂鉴定，2工位质检，3整车质检，processId:3:专检、4:分解
//						List<TaskExecuteRecord> entranceCheckUpSpecialInspectionList = taskExecuteRecordRepository.getTaskListByQualityInspectionGroupType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
						for(TaskExecuteRecord taskExecuteRecord : entranceCheckUpSpecialInspectionList) {
							//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
							TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
							TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
									taskListVehicleDTO.getVehiclePlate(),
									taskListVehicleDTO.getVehicleModel(),
									taskListVehicleDTO.getVehicleType());
							if(taskExecuteRecord.getExecuteStatus() <= 0) {
								//查询未完成到此节点还有多少步
								Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
								if(step == 0) {
									taskVO.setMsg("您现在需要进行 入场鉴定企业代表操作");
									taskVO.setStatus(8);
								} else {
									taskVO.setMsg("还有"+step+"步需要您进行 入场鉴定企业代表操作");
									taskVO.setStatus(0);
								}
							} else {
								taskVO.setMsg("工作已完成 ！");
								taskVO.setStatus(0);
							}
							taskVOList.add(taskVO);
						};
						resultMap.put("totalPages", toEntranceCheckupSpecialInspectionPage.getTotalPages());
						resultMap.put("totalElements", toEntranceCheckupSpecialInspectionPage.getTotalElements());
						break;
					case 12:	//入场鉴定监造代表
						Specification<TaskExecuteRecord> spec12 = new Specification<TaskExecuteRecord>() {        //查询条件构造
							@Override
				            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
								Path<Integer> qualityInspectionGroupType = taskExecuteRecord.get("qualityInspectionGroupType");
								Path<Integer> processId = taskExecuteRecord.get("processId");
								Predicate p1 = cb.equal(qualityInspectionGroupType, Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
								Predicate p2 = cb.equal(processId, Constant.BUILD_INSPECTION_PROCESS_ID);
								Predicate p = cb.and(p1, p2);
								return p;
				            }
						};
						//封装分页TaskExecuteRecord实体列表
				        Page<TaskExecuteRecord> taskExecuteRecordToEntranceCheckupBuildInspectionPage = taskExecuteRecordRepository.findAll(spec12, pageable);
				        List<TaskExecuteRecord> entranceCheckUpBuildInspectionList = taskExecuteRecordToEntranceCheckupBuildInspectionPage.getContent();
						//在任务流转表中获取入场鉴定企业代表---qualityInspectionGroupType：1入厂鉴定，2工位质检，3整车质检，processId:3:专检、4:分解
//						List<TaskExecuteRecord> entranceCheckUpSpecialInspectionList = taskExecuteRecordRepository.getTaskListByQualityInspectionGroupType(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE);
						for(TaskExecuteRecord taskExecuteRecord : entranceCheckUpBuildInspectionList) {
							//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
							TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
							TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
									taskListVehicleDTO.getVehiclePlate(),
									taskListVehicleDTO.getVehicleModel(),
									taskListVehicleDTO.getVehicleType());
							if(taskExecuteRecord.getExecuteStatus() <= 0) {
								//查询未完成到此节点还有多少步
								Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
								if(step == 0) {
									taskVO.setMsg("您现在需要进行 入场鉴定监造代表操作");
									taskVO.setStatus(9);
								} else {
									taskVO.setMsg("还有"+step+"步需要您进行 入场鉴定监造代表操作");
									taskVO.setStatus(0);
								}
							} else {
								taskVO.setMsg("工作已完成 ！");
								taskVO.setStatus(0);
							}
							taskVOList.add(taskVO);
						};
						resultMap.put("totalPages", taskExecuteRecordToEntranceCheckupBuildInspectionPage.getTotalPages());
						resultMap.put("totalElements", taskExecuteRecordToEntranceCheckupBuildInspectionPage.getTotalElements());
						break;
				case 3:	//调度员
					//1、获取调度员对应的车间id
					Integer dispatchWorkShopId = userLoginInfoDTO.getWorkShopId();
					if(dispatchWorkShopId == null) {
						logger.error("登录用户：【" + userLoginInfoDTO + "】 的调度员信息中车间信息有误！");
						resultMap.put("code", 0);
						resultMap.put("msg", "登录用户的调度员信息中车间信息有误!");
						return resultMap;
					}
					
					Specification<TaskExecuteRecord> spec3 = new Specification<TaskExecuteRecord>() {        //查询条件构造
						@Override
			            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
							Path<Integer> typeId = taskExecuteRecord.get("typeId");
							Path<Integer> workshopId = taskExecuteRecord.get("workshopId");
							Predicate p1 = cb.equal(typeId, Constant.DISPATCH_TYPE_ID);
							Predicate p2 = cb.equal(workshopId, dispatchWorkShopId);
							Predicate p = cb.and(p1, p2);
							return p;
			            }
					};
					//封装分页TaskExecuteRecord实体列表
			        Page<TaskExecuteRecord> taskExecuteRecordDispatchPage = taskExecuteRecordRepository.findAll(spec3, pageable);
					
					//2、在任务流转表中获取调度员的任务列表
//					List<TaskExecuteRecord> dispatchTaskLists = taskExecuteRecordRepository.getTaskListByQualityInspectionType(Constant.DISPATCH_QUALITY_INSPECTION_TYPE, dispatchWorkShopId);
					//3、对结果进行封装
					for(TaskExecuteRecord taskExecuteRecord : taskExecuteRecordDispatchPage) {
						//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
						TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
						TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
								taskListVehicleDTO.getVehiclePlate(),
								taskListVehicleDTO.getVehicleModel(),
								taskListVehicleDTO.getVehicleType());
						if(taskExecuteRecord.getExecuteStatus() <= 0) {
							//查询未完成到此节点还有多少步
							Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
							if(step == 0) {
								taskVO.setMsg("您现在需要进行 产线选择");
								taskVO.setStatus(6);
							} else {
								taskVO.setMsg("还有"+step+"步需要您进行 产线选择");
								taskVO.setStatus(0);
							}
						} else {
							taskVO.setMsg("工作已完成 ！");
							taskVO.setStatus(0);
						}
						taskVOList.add(taskVO);
					};
					resultMap.put("totalPages", taskExecuteRecordDispatchPage.getTotalPages());
					resultMap.put("totalElements", taskExecuteRecordDispatchPage.getTotalElements());
					break;
				case 6:	//员工长
					//如果员工长没有组信息，则是错误数据
					if(userLoginInfoDTO.getUserGroupId() == null) {
						logger.error("登录用户：【" + userLoginInfoDTO + "】 的员工长员工组信息有误！");
						resultMap.put("code", 0);
						resultMap.put("msg", "登录用户的员工组信息有误!");
						return resultMap;
					}
					//1、获取员工长所在职能工位列表
					List<JobStation> jobStationList = userService.getJobStationByUserGroupId(userLoginInfoDTO.getUserGroupId());
					
					if(jobStationList == null || jobStationList.size() <= 0) {
						logger.error("登录用户：【" + userLoginInfoDTO + "】 无对应的职能工位信息！");
						resultMap.put("totalPages", 0);
						resultMap.put("msg", "员工长未找到对应的职能工位！");
						resultMap.put("code", -401);
						resultMap.put("totalElements", 0);
						break;
					}
					
					Specification<TaskExecuteRecord> spec6 = new Specification<TaskExecuteRecord>() {        //查询条件构造
						@Override
			            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
							Path<Integer> jobStationId = taskExecuteRecord.get("jobStationId");
							Predicate p = null;
							if (jobStationList != null && jobStationList.size() > 0) {
								//根据id查询-----in查询
								In<Integer> p1 = cb.in(jobStationId);
								for(JobStation jobStation : jobStationList) {
									p1.value(jobStation.getId());
								}
								p = cb.and(p1);
								return p;
							}
							return null;
			            }
					};
					//封装分页TaskExecuteRecord实体列表
			        Page<TaskExecuteRecord> taskExecuteRecordStaffPage = taskExecuteRecordRepository.findAll(spec6, pageable);
					
					List<TaskExecuteRecord> staffTaskLists = taskExecuteRecordStaffPage.getContent();
					//查询具体职能工位对应的任务流转记录信息
					for(TaskExecuteRecord taskExecuteRecord : staffTaskLists) {
						//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
						TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
						TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
								taskListVehicleDTO.getVehiclePlate(),
								taskListVehicleDTO.getVehicleModel(),
								taskListVehicleDTO.getVehicleType());
						//查询此职能工位是否有自检或互检
						Process selfProcess = jobStationService.findProcessByJobStationId(taskExecuteRecord.getJobStationId(), Constant.SELF_INSPECTION_PROCESS_ID);
						Process eachProcess = jobStationService.findProcessByJobStationId(taskExecuteRecord.getJobStationId(), Constant.EACH_INSPECTION_PROCESS_ID);
						if(taskExecuteRecord.getExecuteStatus() <= 0) {
							//查询未完成到此节点还有多少步
							Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
							//step=0 表示执行到当前任务节点了
							if(step == 0) {
								if(taskExecuteRecord.getUserId() == null && selfProcess != null && taskExecuteRecord.getUserId() == null) {
									//自检
									taskVO.setMsg("您现在需要进行 自检");
									taskVO.setStatus(2);
								} else if(taskExecuteRecord.getUserId() != null && eachProcess != null && taskExecuteRecord.getUserId() != null) {
									//互检
									taskVO.setMsg("您现在需要进行 互检");
									taskVO.setStatus(3);
								} else {
									//不触发自检和互检
									taskVOList.add(taskVO);
									break;
								}
							} else {
								taskVO.setMsg("还有"+step+"步需要您进行 自检");
								taskVO.setStatus(0);
							}
						} else {
							taskVO.setMsg("工作已完成 ！");
							taskVO.setStatus(0);
						}
						taskVOList.add(taskVO);
					};
					resultMap.put("totalPages", taskExecuteRecordStaffPage.getTotalPages());
					resultMap.put("totalElements", taskExecuteRecordStaffPage.getTotalElements());
					break;
//				case 7:	//分解员
					//TODO:pcchen 未进行分解员的工作划分
				case 8:	//专检员
					//在任务流转表中获取专检--processId：4:专检、5:监造
					//1、获取专检员对应的车间id
					Integer userWorkShopId = userLoginInfoDTO.getWorkShopId();
					if(userWorkShopId == null) {
						logger.error("登录用户：【" + userLoginInfoDTO + "】 的专检员信息中车间信息有误！");
						resultMap.put("code", 0);
						resultMap.put("msg", "登录用户的车间信息有误!");
						return resultMap;
					}
					
					Specification<TaskExecuteRecord> spec8 = new Specification<TaskExecuteRecord>() {        //查询条件构造
						@Override
			            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
							Path<Integer> processId = taskExecuteRecord.get("processId");
							Path<Integer> workshopId = taskExecuteRecord.get("workshopId");
							Path<Integer> jobStationId = taskExecuteRecord.get("jobStationId");
							Predicate p1 = cb.equal(processId, Constant.SPECIAL_INSPECTION_PROCESS_ID);
							Predicate p2 = cb.equal(workshopId, userWorkShopId);
							Predicate p3 = cb.isNull(jobStationId);
							Predicate p = cb.and(p1, p2, p3);
							return p;
			            }
					};
					//封装分页TaskExecuteRecord实体列表
			        Page<TaskExecuteRecord> taskExecuteSpecialInspectionPage = taskExecuteRecordRepository.findAll(spec8, pageable);
			        List<TaskExecuteRecord> specialInspectionTaskList = taskExecuteSpecialInspectionPage.getContent();
					
					//2、查询专检员的任务记录列表
//					List<TaskExecuteRecord> specialInspectionTaskList = taskExecuteRecordRepository.getTaskListByProcessIdAndWorkshopId(Constant.SPECIAL_INSPECTION_PROCESS_ID, userWorkShopId);
					for(TaskExecuteRecord taskExecuteRecord : specialInspectionTaskList) {
						//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
						TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
						TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
								taskListVehicleDTO.getVehiclePlate(),
								taskListVehicleDTO.getVehicleModel(),
								taskListVehicleDTO.getVehicleType());
						if(taskExecuteRecord.getExecuteStatus() <= 0) {
							//查询未完成到此节点还有多少步
							Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
							if(step == 0) {
								taskVO.setMsg("您现在需要进行 专检");
								taskVO.setStatus(4);
							} else {
								taskVO.setMsg("还有"+step+"步需要您进行 专检");
								taskVO.setStatus(0);
							}
						} else {
							taskVO.setMsg("工作已完成 ！");
							taskVO.setStatus(0);
						}
						taskVOList.add(taskVO);
					};
					resultMap.put("totalPages", taskExecuteSpecialInspectionPage.getTotalPages());
					resultMap.put("totalElements", taskExecuteSpecialInspectionPage.getTotalElements());
					break;
				case 10://监造员
					//在任务流转表中获取专检--processId：4:专检、5:监造
					//1、获取专检员对应的车间id
					Integer checkWorkShopId = userLoginInfoDTO.getWorkShopId();
					if(checkWorkShopId == null) {
						logger.error("登录用户：【" + userLoginInfoDTO + "】 的监造员信息中车间信息有误！");
						resultMap.put("code", 0);
						resultMap.put("msg", "登录用户的车间信息有误!");
						return resultMap;
					}
					
					Specification<TaskExecuteRecord> spec10 = new Specification<TaskExecuteRecord>() {        //查询条件构造
						@Override
			            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
							Path<Integer> processId = taskExecuteRecord.get("processId");
							Path<Integer> workshopId = taskExecuteRecord.get("workshopId");
							Path<Integer> jobStationId = taskExecuteRecord.get("jobStationId");
							Predicate p1 = cb.equal(processId, Constant.BUILD_INSPECTION_PROCESS_ID);
							Predicate p2 = cb.equal(workshopId, checkWorkShopId);
							Predicate p3 = cb.isNull(jobStationId);
							Predicate p = cb.and(p1, p2, p3);
							return p;
			            }
					};
					//封装分页TaskExecuteRecord实体列表
			        Page<TaskExecuteRecord> taskExecuteBuildInspectionPage = taskExecuteRecordRepository.findAll(spec10, pageable);
					List<TaskExecuteRecord> buildInspectionTaskList = taskExecuteBuildInspectionPage.getContent();
					
					//2、查询专检员的任务记录列表
//					List<TaskExecuteRecord> buildInspectionTaskList = taskExecuteRecordRepository.getTaskListByProcessIdAndWorkshopId(Constant.BUILD_INSPECTION_PROCESS_ID, checkWorkShopId);
					for(TaskExecuteRecord taskExecuteRecord : buildInspectionTaskList) {
						//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
						TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
						TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
								taskListVehicleDTO.getVehiclePlate(),
								taskListVehicleDTO.getVehicleModel(),
								taskListVehicleDTO.getVehicleType());
						if(taskExecuteRecord.getExecuteStatus() <= 0) {
							//查询未完成到此节点还有多少步
							Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
							if(step == 0) {
								taskVO.setMsg("您现在需要进行 监造");
								taskVO.setStatus(5);
							} else {
								taskVO.setMsg("还有"+step+"步需要您进行 监造");
								taskVO.setStatus(0);
							}
						} else {
							taskVO.setMsg("工作已完成 ！");
							taskVO.setStatus(0);
						}
						taskVOList.add(taskVO);
					};
					resultMap.put("totalPages", taskExecuteBuildInspectionPage.getTotalPages());
					resultMap.put("totalElements", taskExecuteBuildInspectionPage.getTotalElements());
					break;
				case 9:	//整车质检员
					Specification<TaskExecuteRecord> spec9 = new Specification<TaskExecuteRecord>() {        //查询条件构造
						@Override
			            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
							Path<Integer> qualityInspectionGroupType = taskExecuteRecord.get("qualityInspectionGroupType");
							Path<Integer> processId = taskExecuteRecord.get("processId");
							Predicate p1 = cb.equal(qualityInspectionGroupType, Constant.ALL_CAR_ITEM_TYPE);
							Predicate p2 = cb.isNull(processId);
							Predicate p = cb.and(p1, p2);
							return p;
			            }
					};
					//封装分页PageSearchVO实体列表
			        Page<TaskExecuteRecord> allCarQualityInspectionPage = taskExecuteRecordRepository.findAll(spec9, pageable);
					List<TaskExecuteRecord> allCarQualityInspectionTaskList = allCarQualityInspectionPage.getContent();
//					//在任务流转表中获取整车质检--qualityInspectionGroupType：1入厂鉴定，2工位质检，3整车质检
//					List<TaskExecuteRecord> allCarQualityInspectionTaskList = taskExecuteRecordRepository.getTaskListByQualityInspectionGroupType(Constant.ALL_CAR_ITEM_TYPE);
					for(TaskExecuteRecord taskExecuteRecord : allCarQualityInspectionTaskList) {
						//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
						TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
						TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
								taskListVehicleDTO.getVehiclePlate(),
								taskListVehicleDTO.getVehicleModel(),
								taskListVehicleDTO.getVehicleType());
						if(taskExecuteRecord.getExecuteStatus() <= 0) {
							//查询未完成到此节点还有多少步
							Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
							if(step == 0) {
								taskVO.setMsg("您现在需要进行 整车质检");
								taskVO.setStatus(7);
							} else {
								taskVO.setMsg("还有"+step+"步需要您进行 整车质检");
								taskVO.setStatus(0);
							}
						} else {
							taskVO.setMsg("工作已完成 ！");
							taskVO.setStatus(0);
						}
						taskVOList.add(taskVO);
					};
					resultMap.put("totalPages", allCarQualityInspectionPage.getTotalPages());
					resultMap.put("totalElements", allCarQualityInspectionPage.getTotalElements());
					break;
				case 13:	//整车质检专检人员
					Specification<TaskExecuteRecord> spec13 = new Specification<TaskExecuteRecord>() {        //查询条件构造
						@Override
			            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
							Path<Integer> qualityInspectionGroupType = taskExecuteRecord.get("qualityInspectionGroupType");
							Path<Integer> processId = taskExecuteRecord.get("processId");
							Predicate p1 = cb.equal(qualityInspectionGroupType, Constant.ALL_CAR_ITEM_TYPE);
							Predicate p2 = cb.equal(processId, Constant.SPECIAL_INSPECTION_PROCESS_ID);
							Predicate p = cb.and(p1, p2);
							return p;
			            }
					};
					//封装分页PageSearchVO实体列表
			        Page<TaskExecuteRecord> allCarQualityInspectionSpecialInspectionPage = taskExecuteRecordRepository.findAll(spec13, pageable);
					List<TaskExecuteRecord> allCarQualityInspectionSpecialInspectionTaskList = allCarQualityInspectionSpecialInspectionPage.getContent();
//					//在任务流转表中获取整车质检--qualityInspectionGroupType：1入厂鉴定，2工位质检，3整车质检
//					List<TaskExecuteRecord> allCarQualityInspectionTaskList = taskExecuteRecordRepository.getTaskListByQualityInspectionGroupType(Constant.ALL_CAR_ITEM_TYPE);
					for(TaskExecuteRecord taskExecuteRecord : allCarQualityInspectionSpecialInspectionTaskList) {
						//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
						TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
						TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
								taskListVehicleDTO.getVehiclePlate(),
								taskListVehicleDTO.getVehicleModel(),
								taskListVehicleDTO.getVehicleType());
						if(taskExecuteRecord.getExecuteStatus() <= 0) {
							//查询未完成到此节点还有多少步
							Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
							if(step == 0) {
								taskVO.setMsg("您现在需要进行 整车质检专检");
								taskVO.setStatus(10);
							} else {
								taskVO.setMsg("还有"+step+"步需要您进行 整车质检专检");
								taskVO.setStatus(0);
							}
						} else {
							taskVO.setMsg("工作已完成 ！");
							taskVO.setStatus(0);
						}
						taskVOList.add(taskVO);
					};
					resultMap.put("totalPages", allCarQualityInspectionSpecialInspectionPage.getTotalPages());
					resultMap.put("totalElements", allCarQualityInspectionSpecialInspectionPage.getTotalElements());
					break;
				case 14:	//整车质检监造人员
					Specification<TaskExecuteRecord> spec14 = new Specification<TaskExecuteRecord>() {        //查询条件构造
						@Override
			            public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
							Path<Integer> qualityInspectionGroupType = taskExecuteRecord.get("qualityInspectionGroupType");
							Path<Integer> processId = taskExecuteRecord.get("processId");
							Predicate p1 = cb.equal(qualityInspectionGroupType, Constant.ALL_CAR_ITEM_TYPE);
							Predicate p2 = cb.equal(processId, Constant.BUILD_INSPECTION_PROCESS_ID);
							Predicate p = cb.and(p1, p2);
							return p;
			            }
					};
					//封装分页PageSearchVO实体列表
			        Page<TaskExecuteRecord> allCarQualityInspectionBuildInspectionPage = taskExecuteRecordRepository.findAll(spec14, pageable);
					List<TaskExecuteRecord> allCarQualityInspectionBuildInspectionTaskList = allCarQualityInspectionBuildInspectionPage.getContent();
//					//在任务流转表中获取整车质检--qualityInspectionGroupType：1入厂鉴定，2工位质检，3整车质检
//					List<TaskExecuteRecord> allCarQualityInspectionTaskList = taskExecuteRecordRepository.getTaskListByQualityInspectionGroupType(Constant.ALL_CAR_ITEM_TYPE);
					for(TaskExecuteRecord taskExecuteRecord : allCarQualityInspectionBuildInspectionTaskList) {
						//TaskVO(String vehiclePlate, String vehicleModel, String vehicleType)
						TaskListVehicleDTO taskListVehicleDTO = zcTaskService.getVehiclePlateModelTypeByTaskid(taskExecuteRecord.getTaskId());
						TaskVO taskVO = new TaskVO(taskExecuteRecord.getId(),
								taskListVehicleDTO.getVehiclePlate(),
								taskListVehicleDTO.getVehicleModel(),
								taskListVehicleDTO.getVehicleType());
						if(taskExecuteRecord.getExecuteStatus() <= 0) {
							//查询未完成到此节点还有多少步
							Integer step = getNotExecuteToCurrentStep(taskExecuteRecord.getTaskId(), taskExecuteRecord.getId());
							if(step == 0) {
								taskVO.setMsg("您现在需要进行 整车质检监造");
								taskVO.setStatus(11);
							} else {
								taskVO.setMsg("还有"+step+"步需要您进行 整车质检监造");
								taskVO.setStatus(0);
							}
						} else {
							taskVO.setMsg("工作已完成 ！");
							taskVO.setStatus(0);
						}
						taskVOList.add(taskVO);
					};
					resultMap.put("totalPages", allCarQualityInspectionBuildInspectionPage.getTotalPages());
					resultMap.put("totalElements", allCarQualityInspectionBuildInspectionPage.getTotalElements());
					break;
				default:
					//未找到用户的权限
					resultMap.put("code", 0);
					resultMap.put("msg", "用户权限错误，请联系管理员!");
					return resultMap;
			}
		}
		resultMap.put("code", 1);
		resultMap.put("datas", taskVOList);
		resultMap.put("pageNumber", pageNumber+1);
		resultMap.put("pageSize", pageSize);
		return resultMap;
	}

	/**
	 * 获取前序任务流转记录列表
	 * @param taskRecordId		任务流转记录id
	 * @param pageNumber			页码
	 * @param pageSize				每页显示记录数
	 * @return
	 */
	public Map<String, Object> getBeforeTaskRecordList(Integer taskRecordId, Integer pageNumber, Integer pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserLoginInfoDTO userLoginInfoDTO = CacheCurrentUserThreadLocal.getCurrentUser();
		//当前登录用户无相关角色
		if(userLoginInfoDTO.getRoleMap() == null) {
			resultMap.put("code", 0);
			resultMap.put("msg", "当前用户无任务列表!");
			return resultMap;
		}

		//获取当前任务流转记录的taskId
		TaskExecuteRecord taskExecuteRecord = taskExecuteRecordRepository.findOne(taskRecordId);
		if(taskExecuteRecord == null) {
			resultMap.put("code", 0);
			resultMap.put("msg", "未查找到之前的流转记录!");
			return resultMap;
		}
		Integer taskIdDB = taskExecuteRecord.getTaskId();
		//分页信息封装--按照id升序排列
		ArrayList<Order> sortOrder = new ArrayList<Sort.Order>();
		sortOrder.add(new Sort.Order(Direction.ASC, "id"));
		Sort sort = new Sort(sortOrder);
		Pageable pageable = new PageRequest(pageNumber, pageSize, sort);  //分页信息

		Integer taskExecuteRecordId = taskExecuteRecord.getId();
		Specification<TaskExecuteRecord> taskExecuteRecordSpec = new Specification<TaskExecuteRecord>() {        //查询条件构造
			@Override
			public Predicate toPredicate(Root<TaskExecuteRecord> taskExecuteRecord, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Integer> taskId = taskExecuteRecord.get("taskId");
				Path<Integer> executeStatus = taskExecuteRecord.get("executeStatus");
				Path<Integer> id = taskExecuteRecord.get("id");
				Predicate p1 = cb.equal(taskId, taskIdDB);
				Predicate p2 = cb.equal(executeStatus, 1);
				Predicate p3 = cb.le(id, taskExecuteRecordId);
				Predicate p = cb.and(p1, p2, p3);
				return p;
			}
		};

		Page<TaskExecuteRecord> taskExecuteRecordPage = taskExecuteRecordRepository.findAll(taskExecuteRecordSpec, pageable);
		List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordPage.getContent();
		List<TaskExecuteRecordVO> taskExecuteRecordVOList = new ArrayList<TaskExecuteRecordVO>();
		for(TaskExecuteRecord taskExecuteRecordTmp : taskExecuteRecordList) {
			TaskExecuteRecordVO taskExecuteRecordVO = new TaskExecuteRecordVO();
			taskExecuteRecordVO.setTaskRecordId(taskExecuteRecordTmp.getId());
			taskExecuteRecordVO.setTaskId(taskExecuteRecordTmp.getTaskId());
			taskExecuteRecordVO.setUserId(taskExecuteRecordTmp.getUserId());
			taskExecuteRecordVO.setMark(taskExecuteRecordTmp.getMark());
			if(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE.equals(taskExecuteRecordTmp.getQualityInspectionGroupType())  && taskExecuteRecordTmp.getProcessId() == null) {
				taskExecuteRecordVO.setStatus(1);
			} else if(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE.equals(taskExecuteRecordTmp.getQualityInspectionGroupType()) && Constant.SPECIAL_INSPECTION_PROCESS_ID.equals(taskExecuteRecordTmp.getProcessId())) {
				taskExecuteRecordVO.setStatus(8);
			} else if(Constant.TO_ENTRANCE_CHECKUP_ITEM_TYPE.equals(taskExecuteRecordTmp.getQualityInspectionGroupType()) && Constant.BUILD_INSPECTION_PROCESS_ID.equals(taskExecuteRecordTmp.getProcessId())) {
				taskExecuteRecordVO.setStatus(9);
			} else if(Constant.ALL_CAR_ITEM_TYPE.equals(taskExecuteRecordTmp.getQualityInspectionGroupType()) && taskExecuteRecordTmp.getProcessId() == null) {
				taskExecuteRecordVO.setStatus(7);
			} else if(Constant.ALL_CAR_ITEM_TYPE.equals(taskExecuteRecordTmp.getQualityInspectionGroupType()) && Constant.SPECIAL_INSPECTION_PROCESS_ID.equals(taskExecuteRecordTmp.getProcessId())) {
				taskExecuteRecordVO.setStatus(10);
			} else if(Constant.ALL_CAR_ITEM_TYPE.equals(taskExecuteRecordTmp.getQualityInspectionGroupType()) && Constant.BUILD_INSPECTION_PROCESS_ID.equals(taskExecuteRecordTmp.getProcessId())) {
				taskExecuteRecordVO.setStatus(11);
			} else if(taskExecuteRecordTmp.getTypeId() == Constant.DISPATCH_TYPE_ID) {
				taskExecuteRecordVO.setStatus(6);	//车间调度
			} else if(taskExecuteRecordTmp.getQualityInspectionGroupType() == null && Constant.SPECIAL_INSPECTION_PROCESS_ID.equals(taskExecuteRecordTmp.getProcessId())) {
				taskExecuteRecordVO.setStatus(4);
			} else if(taskExecuteRecordTmp.getQualityInspectionGroupType() == null && Constant.BUILD_INSPECTION_PROCESS_ID.equals(taskExecuteRecordTmp.getProcessId())) {
				taskExecuteRecordVO.setStatus(5);
			} else if(taskExecuteRecordTmp.getJobStationId() != null) {
				//查询此职能工位是否有自检或互检
				Process selfProcess = jobStationService.findProcessByJobStationId(taskExecuteRecordTmp.getJobStationId(), Constant.SELF_INSPECTION_PROCESS_ID);
				Process eachProcess = jobStationService.findProcessByJobStationId(taskExecuteRecordTmp.getJobStationId(), Constant.EACH_INSPECTION_PROCESS_ID);

				if(taskExecuteRecordTmp.getUserId() != null && selfProcess != null) {
					taskExecuteRecordVO.setStatus(2);//保存自检前序工作
					taskExecuteRecordVO.setMark(taskExecuteRecordTmp.getMark() + "自检");
				} 
				if(taskExecuteRecordTmp.getUserCheckId() != null && eachProcess != null) {
					//保存互检前序任务
					TaskExecuteRecordVO taskExecuteRecordVO2 = new TaskExecuteRecordVO();
					taskExecuteRecordVO2.setTaskRecordId(taskExecuteRecordTmp.getId());
					taskExecuteRecordVO2.setTaskId(taskExecuteRecordTmp.getTaskId());
					taskExecuteRecordVO2.setUserId(taskExecuteRecordTmp.getUserId());
					taskExecuteRecordVO2.setMark(taskExecuteRecordTmp.getMark() + "互检");
					taskExecuteRecordVO2.setStatus(3);//保存互检前序工作
					taskExecuteRecordVOList.add(taskExecuteRecordVO2);
				}
			}
			taskExecuteRecordVOList.add(taskExecuteRecordVO);
		}
		resultMap.put("code", 1);
		resultMap.put("datas", taskExecuteRecordVOList);
		resultMap.put("totalPages", taskExecuteRecordPage.getTotalPages());
		resultMap.put("totalElements", taskExecuteRecordPage.getTotalElements());
		resultMap.put("pageNumber", pageNumber+1);
		resultMap.put("pageSize", pageSize);
		return resultMap;
	}
	
	/**
	 * 根据任务流转记录id，查询指定记录
	 * @param id
	 * @return
	 */
	public TaskExecuteRecord findOneById(Integer id) {
		return taskExecuteRecordRepository.findOne(id);
	}

	/**
	 * 查询此节点之前节点还有多少未完成
	 * @param taskId					任务id
	 * @param taskExecuteRecordId	当前节点id
	 * @return
	 */
	public Integer getNotExecuteToCurrentStep(Integer taskId, Integer taskExecuteRecordId) {
		return taskExecuteRecordRepository.getNotExecuteToCurrentStep(taskId, taskExecuteRecordId);
	}
	
	/**
	 * 根据任务id和车间id修改职能工位对应的工区，以便确定当前职能工位对应的物理工位
	 * @param taskId			任务id
	 * @param workshopId		车间id
	 * @param workAreaId		工区id
	 */
	public void updateDispatchWorkshopWorkAreaByTaskIdAndWorkshopId(Integer taskId, Integer workshopId, Integer workAreaId) {
		taskExecuteRecordRepository.updateDispatchWorkshopWorkAreaByTaskIdAndWorkshopId(taskId, workshopId, workAreaId);
	}
	
	/**
	 * 更新指定任务流转记录的执行状态-为完成
	 * @param taskRecordId		任务执行记录id
	 */
	public void updateRecordExecuteStatus(Integer taskRecordId) {
		taskExecuteRecordRepository.updateRecordExecuteStatus(taskRecordId);
	}
	
	/**
	 * 更新指定任务流转记录的操作人
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 */
	public void updateExecuteUserInTaskExecuteRecord(Integer taskRecordId, Integer userId) {
		taskExecuteRecordRepository.updateExecuteUserInTaskExecuteRecord(taskRecordId, userId);
	}
	
	/**
	 * 更新指定任务流转记录的操作人
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 * @param executeStatus		任务流转记录成功与否1：成功；0：失败
	 */
	public void updateRecordExecuteStatusAndUserIdByTaskRecordId(Integer taskRecordId, Integer userId, Integer executeStatus) {
		taskExecuteRecordRepository.updateRecordExecuteStatusAndUserIdByTaskRecordId(taskRecordId, userId, executeStatus);
	}

	/**
	 * 针对工位长互检--更新指定任务流转记录的执行状态-为完成，以及互检人id信息
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 * @param executeStatus		任务流转记录成功与否1：成功；0：失败
	 */
	public void updateRecordExecuteStatusAndEachCheckUserId(Integer taskRecordId, Integer userId, Integer executeStatus) {
		taskExecuteRecordRepository.updateRecordExecuteStatusAndEachCheckUserId(taskRecordId, userId, executeStatus);
	}
}
