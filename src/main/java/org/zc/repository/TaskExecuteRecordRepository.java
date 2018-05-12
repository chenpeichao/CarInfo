package org.zc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zc.domain.TaskExecuteRecord;

/**
 * Description:
 * Create by @author cpc
 * 2018年4月21日 下午3:50:58extends JpaRepository<PaperSearchView, Integer>, JpaSpecificationExecutor<PaperSearchView>
 */
public interface TaskExecuteRecordRepository extends JpaRepository<TaskExecuteRecord, Integer>, JpaSpecificationExecutor<TaskExecuteRecord> {
//	/**
//	 * 根据质检组类型查询任务列表--qualityInspectionGroupType：1入厂鉴定，2工位质检，3整车质检
//	 * @return
//	 */
//	//TODO:pcchen 此处应该按照未完成和id排序
//	@Query(" SELECT taskExecuteRecord FROM org.zc.domain.TaskExecuteRecord taskExecuteRecord where taskExecuteRecord.qualityInspectionGroupType = :qualityInspectionGroupType  ")
//	public List<TaskExecuteRecord> getTaskListByQualityInspectionGroupType(@Param("qualityInspectionGroupType")Integer qualityInspectionGroupType);

	/**
	 * 查询此节点之前节点还有多少未完成
	 * @param taskId					任务id
	 * @param taskExecuteRecordId	当前节点id
	 * @return
	 */
	@Query(" SELECT count(taskExecuteRecord.id) FROM org.zc.domain.TaskExecuteRecord taskExecuteRecord where taskExecuteRecord.taskId = :taskId and taskExecuteRecord .executeStatus = 0 and  taskExecuteRecord.id < :taskExecuteRecordId ")
	public Integer getNotExecuteToCurrentStep(@Param("taskId")Integer taskId, @Param("taskExecuteRecordId")Integer taskExecuteRecordId);

	/**
	 * 获取指定质检类别的车间的任务记录
	 * @param dispatchQualityInspectionType		质检类别特殊标识
	 * @param workshopId					车间id
	 * @return
	 */
	@Query(" SELECT taskExecuteRecord FROM org.zc.domain.TaskExecuteRecord taskExecuteRecord where taskExecuteRecord.typeId = :dispatchQualityInspectionType and taskExecuteRecord.workshopId = :workshopId ")
	public List<TaskExecuteRecord> getTaskListByQualityInspectionType(@Param("dispatchQualityInspectionType")Integer dispatchQualityInspectionType, @Param("workshopId")Integer workshopId);
	
	/**
	 * 获取指定职能工位的任务列表
	 * @param jobStationId		职能工位id
	 * @return
	 */
	@Query(" SELECT taskExecuteRecord FROM org.zc.domain.TaskExecuteRecord taskExecuteRecord where taskExecuteRecord.jobStationId = :jobStationId ")
	public List<TaskExecuteRecord> getTaskListByJobStationId(@Param("jobStationId")Integer jobStationId);
	
	/**
	 * 根据processId以及车间id查询任务记录列表
	 * @param processId			用于区别专检、监造、(分解)员
	 * @param userWorkshopId	车间id
	 * @return
	 */
	@Query(" SELECT taskExecuteRecord FROM org.zc.domain.TaskExecuteRecord taskExecuteRecord where taskExecuteRecord.processId = :processId and taskExecuteRecord.workshopId = :userWorkshopId ")
	public List<TaskExecuteRecord> getTaskListByProcessIdAndWorkshopId(@Param("processId")Integer processId, @Param("userWorkshopId")Integer userWorkshopId);
	
	/**
	 * 根据任务id和车间id修改职能工位对应的工区，以便确定当前职能工位对应的物理工位
	 * @param taskId			任务id
	 * @param workshopId		车间id
	 * @param workAreaId		工区id
	 */
	@Modifying
	@Query(" update org.zc.domain.TaskExecuteRecord taskExecuteRecord set taskExecuteRecord.workAreaId = :workAreaId where taskExecuteRecord.taskId = :taskId and taskExecuteRecord.workshopId = :workshopId")
	public void updateDispatchWorkshopWorkAreaByTaskIdAndWorkshopId(@Param("taskId")Integer taskId, @Param("workshopId")Integer workshopId, @Param("workAreaId")Integer workAreaId);
	
	/**
	 * 更新指定任务流转记录的执行状态-为完成
	 * @param taskRecordId		任务执行记录id
	 */
	@Modifying
	@Query(" update org.zc.domain.TaskExecuteRecord taskExecuteRecord set taskExecuteRecord.executeStatus = 1 where taskExecuteRecord.id = :taskRecordId ")
	public void updateRecordExecuteStatus(@Param("taskRecordId")Integer taskRecordId);
	
	/**
	 * 更新指定任务流转记录的操作人
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 */
	@Modifying
	@Query(" update org.zc.domain.TaskExecuteRecord taskExecuteRecord set taskExecuteRecord.userId = :userId where taskExecuteRecord.id = :taskRecordId ")
	public void updateExecuteUserInTaskExecuteRecord(@Param("taskRecordId")Integer taskRecordId, @Param("userId")Integer userId);
	
	/**
	 * 更新指定任务流转记录的操作人
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 * @param executeStatus		任务流转记录成功与否1：成功；0：失败
	 */
	@Modifying
	@Query(" update org.zc.domain.TaskExecuteRecord taskExecuteRecord set taskExecuteRecord.userId = :userId, taskExecuteRecord.executeStatus = :executeStatus  where taskExecuteRecord.id = :taskRecordId ")
	public void updateRecordExecuteStatusAndUserIdByTaskRecordId(@Param("taskRecordId")Integer taskRecordId, @Param("userId")Integer userId, @Param("executeStatus")Integer executeStatus);
	
	/**
	 * 针对工位长互检--更新指定任务流转记录的执行状态-为完成，以及互检人id信息
	 * @param taskRecordId		任务流转记录id
	 * @param userId			操作人id
	 * @param executeStatus		任务流转记录成功与否1：成功；0：失败
	 */
	@Modifying
	@Query(" update org.zc.domain.TaskExecuteRecord taskExecuteRecord set taskExecuteRecord.userCheckId = :userId, taskExecuteRecord.executeStatus = :executeStatus where taskExecuteRecord.id = :taskRecordId ")
	public void updateRecordExecuteStatusAndEachCheckUserId(@Param("taskRecordId")Integer taskRecordId, @Param("userId")Integer userId, @Param("executeStatus")Integer executeStatus);
}
