package org.zc.service;

import java.util.List;
import java.util.Map;

import org.zc.domain.TaskExecuteRecord;
import org.zc.dto.UserLoginInfoDTO;

/**
 * Description:任务流转记录
 * Create by @author cpc
 * 2018年4月21日 下午3:48:32
 */
public interface TaskExecuteRecordService {
	/**
	 * 保存任务流转记录
	 * @param taskExecuteRecord
	 */
	public void saveTaskExecuteRecord(TaskExecuteRecord taskExecuteRecord);
	/**
	 * 保存任务流转记录列表
	 * @param taskExecuteRecord
	 */
	public void saveTaskExecuteRecordList(List<TaskExecuteRecord> taskExecuteRecordList);
	
	/**
	 * 获取用户任务列表
	 * @param userLoginInfoDTO	登录用户dto
	 * @param pageNumber			页码
	 * @param pageSize				每页显示记录数
	 * @return
	 */
	public Map<String, Object> getUserTask(UserLoginInfoDTO userLoginInfoDTO, Integer pageNumber, Integer pageSize);

	/**
	 * 获取前序任务流转记录列表
	 * @param taskRecordId		任务流转记录id
	 * @param pageNumber			页码
	 * @param pageSize				每页显示记录数
	 * @return
	 */
	public Map<String, Object> getBeforeTaskRecordList(Integer taskRecordId, Integer pageNumber, Integer pageSize);

	/**
	 * 根据任务流转记录id，查询指定记录
	 * @param id
	 * @return
	 */
	public TaskExecuteRecord findOneById(Integer id);
	
	/**
	 * 查询此节点之前节点还有多少未完成
	 * @param taskId					任务id
	 * @param taskExecuteRecordId	当前节点id
	 * @return
	 */
	public Integer getNotExecuteToCurrentStep(Integer taskId, Integer taskExecuteRecordId);
	
	/**
	 * 根据任务id和车间id修改职能工位对应的工区，以便确定当前职能工位对应的物理工位
	 * @param taskId			任务id
	 * @param workshopId		车间id
	 * @param workAreaId		工区id
	 */
	public void updateDispatchWorkshopWorkAreaByTaskIdAndWorkshopId(Integer taskId, Integer workshopId, Integer workAreaId);
	
	/**
	 * 更新指定任务流转记录的执行状态-为完成
	 * @param taskRecordId		任务流转记录id
	 */
	public void updateRecordExecuteStatus(Integer taskRecordId);
	
	/**
	 * 更新指定任务流转记录的操作人
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 */
	public void updateExecuteUserInTaskExecuteRecord(Integer taskRecordId, Integer userId);
	
	/**
	 * 更新指定任务流转记录的操作人
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 * @param executeStatus		任务流转记录成功与否1：成功；0：失败
	 */
	public void updateRecordExecuteStatusAndUserIdByTaskRecordId(Integer taskRecordId, Integer userId, Integer executeStatus);

	/**
	 * 针对工位长互检--更新指定任务流转记录的执行状态-为完成，以及互检人id信息
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 * @param executeStatus		任务流转记录成功与否1：成功；0：失败
	 */
	public void updateRecordExecuteStatusAndEachCheckUserId(Integer taskRecordId, Integer userId, Integer executeStatus);
}
