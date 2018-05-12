package org.zc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zc.domain.Answer;
import org.zc.domain.InspectionItem;

import java.util.List;

/**
 * 
 * @author xuly
 */
public interface AnswerRepository  extends JpaRepository<Answer, Integer> {
	/**
     * 根据质检项id列表以及任务id，查询指定流程答题答案
     * @param qualityInspectionItemIds	质检项id
     * @param taskId					任务id
     * @param processId					流程id
     * @return
     */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.processId = :processId and answer.status = :status and answer.qualityItemId in :qualityInspectionItemIds  order by answer.id ")
	public List<Answer> findAnswerListByQualityInspectionItemIdList(@Param("qualityInspectionItemIds")List<Integer> qualityInspectionItemIds, @Param("taskId")Integer taskId, @Param("processId")Integer processId, @Param("status")Integer status);
	/**
	 * 根据上步质检项id列表，查询答题答案--包含失败的答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param processId					流程id
	 * @return
	 */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.processId = :processId and answer.qualityItemId in :qualityInspectionItemIds order by answer.id ")
	public List<Answer> findAnswerListByQualityInspectionItemIdList(@Param("qualityInspectionItemIds")List<Integer> qualityInspectionItemIds, @Param("taskId")Integer taskId, @Param("processId")Integer processId);
	/**
	 * 根据上步质检项id列表，查询答题答案--包含失败的答案--包括自检和专检
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param processId					流程id
	 * @return
	 */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.qualityItemId in :qualityInspectionItemIds order by answer.id ")
	public List<Answer> findAnswerListByQualityInspectionItemIdListProcessIdIsAll(@Param("qualityInspectionItemIds")List<Integer> qualityInspectionItemIds, @Param("taskId")Integer taskId);


	/**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @return
	 */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.itemType = :itemType and answer.status = :status and answer.qualityItemId in :qualityInspectionItemIds  order by answer.id ")
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemType(@Param("qualityInspectionItemIds")List<Integer> qualityInspectionItemIds, @Param("taskId")Integer taskId, @Param("itemType")Integer itemType, @Param("status")Integer status);
	/**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @return
	 */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.itemType = :itemType and answer.status = :status and answer.processId is null and answer.qualityItemId in :qualityInspectionItemIds  order by answer.id ")
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessIdIsNull(@Param("qualityInspectionItemIds")List<Integer> qualityInspectionItemIds, @Param("taskId")Integer taskId, @Param("itemType")Integer itemType, @Param("status")Integer status);
	/**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @return
	 */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.itemType = :itemType and answer.processId = :processId and answer.status = :status and answer.qualityItemId in :qualityInspectionItemIds  order by answer.id ")
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(@Param("qualityInspectionItemIds")List<Integer> qualityInspectionItemIds, @Param("taskId")Integer taskId, @Param("itemType")Integer itemType, @Param("processId")Integer processId, @Param("status")Integer status);
	/**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @return
	 */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.itemType = :itemType and answer.processId is null and answer.qualityItemId in :qualityInspectionItemIds  order by answer.id ")
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessIdIsNull(@Param("qualityInspectionItemIds")List<Integer> qualityInspectionItemIds, @Param("taskId")Integer taskId, @Param("itemType")Integer itemType);
	/**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @return
	 */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.itemType = :itemType and answer.processId = :processId and answer.qualityItemId in :qualityInspectionItemIds  order by answer.id ")
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(@Param("qualityInspectionItemIds")List<Integer> qualityInspectionItemIds, @Param("taskId")Integer taskId, @Param("itemType")Integer itemType, @Param("processId")Integer processId);

//	/**
//     * 根据任务id，和item_type类型，查询已经答题的质检项id列表
//     * @param taskId					任务id
//     * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
//     * @param status					答案成功状态0：失败、1：成功
//     * @return
//     */
//	@Query(" select answer.qualityItemId FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.itemType = :itemType and answer.status = :status ")
//	public List<Integer> findItemIdListByTaskIdAndItemType(@Param("taskId")Integer taskId, @Param("itemType")Integer itemType, @Param("status")Integer status);

	/**
	 * 根据任务id，和item_type，以及processId查询质检项id列表
	 * @param taskId                    任务id
	 * @param processId                 流程id
	 * @param itemType                  质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param status                    答案成功状态0：失败、1：成功
	 * @return
	 */
	@Query(" select inspectionItem FROM org.zc.domain.Answer answer, org.zc.domain.InspectionItem inspectionItem WHERE answer.qualityItemId = inspectionItem.id and answer.taskId = :taskId and answer.processId = :processId and answer.itemType = :itemType and answer.status = :status ")
	public List<InspectionItem> findItemIdListByTaskIdAndProcessIdAndItemType(@Param("taskId")Integer taskId, @Param("processId")Integer processId, @Param("itemType")Integer itemType, @Param("status")Integer status);
	/**
	 * 根据任务id，和item_type，以及processId为null查询质检项id列表
	 * @param taskId                    任务id
	 * @param itemType                  质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param status                    答案成功状态0：失败、1：成功
	 * @return
	 */
	@Query(" select inspectionItem FROM org.zc.domain.Answer answer, org.zc.domain.InspectionItem inspectionItem WHERE answer.qualityItemId = inspectionItem.id and answer.taskId = :taskId and answer.processId is null and answer.itemType = :itemType and answer.status = :status ")
	public List<InspectionItem> findItemIdListByTaskIdAndProcessIdIsNullAndItemType(@Param("taskId")Integer taskId, @Param("itemType")Integer itemType, @Param("status")Integer status);
	/**
	 * 根据任务id，和item_type为null，以及processId查询质检项id列表
	 * @param taskId                    任务id
	 * @param processId                 流程id
	 * @param status                    答案成功状态0：失败、1：成功
	 * @return
	 */
	@Query(" select inspectionItem FROM org.zc.domain.Answer answer, org.zc.domain.InspectionItem inspectionItem WHERE answer.qualityItemId = inspectionItem.id and answer.taskId = :taskId and answer.processId = :processId and answer.itemType is null and answer.status = :status ")
	public List<InspectionItem> findItemIdListByTaskIdAndProcessIdAndItemTypeIsNull(@Param("taskId")Integer taskId, @Param("processId")Integer processId, @Param("status")Integer status);

//	/**
//     * 根据任务id，和职能工位id类型，查询已经答题成功的质检项id列表
//     * @param taskId					任务id
//     * @param jobStationId				职能工位id
//     * @param status					答案成功状态0：失败、1：成功
//     * @return
//     */
//	@Query(" select inspectionItem FROM org.zc.domain.Answer answer, org.zc.domain.InspectionItem inspectionItem WHERE answer.qualityItemId = inspectionItem.id and answer.taskId = :taskId and answer.jobStationId = :jobStationId and answer.status = :status ")
//    public List<InspectionItem> findItemIdListByTaskIdAndJobStationId(@Param("taskId")Integer taskId, @Param("jobStationId")Integer jobStationId, @Param("status")Integer status);

	/**
	 * 根据任务id，和职能工位id类型，查询已经答题成功的质检项id列表
	 * @param taskId						任务id
	 * @param jobStationId				职能工位id
	 * @param processId					流程任务
	 * @param status						答案成功状态0：失败、1：成功
	 * @return
	 */
	@Query(" select inspectionItem FROM org.zc.domain.Answer answer, org.zc.domain.InspectionItem inspectionItem WHERE answer.qualityItemId = inspectionItem.id and answer.taskId = :taskId and answer.processId = :processId and answer.jobStationId = :jobStationId and answer.status = :status ")
	public List<InspectionItem> findItemIdListByTaskIdAndJobStationIdAndProcessId(@Param("taskId")Integer taskId, @Param("jobStationId")Integer jobStationId, @Param("processId")Integer processId, @Param("status")Integer status);

	/**
	 * 查询指定任务下的质检项对应的有效自检答题详情
	 * @param taskId				任务id
	 * @param qualityItemId		质检项id
	 * @param status				成功标识，0：失败；1：成功
	 * @return
	 */
	@Query(" FROM org.zc.domain.Answer answer WHERE answer.taskId = :taskId and answer.qualityItemId = :qualityItemId and answer.status = :status")
	public List<Answer> findAnswerByTaskIdAndItemId(@Param("taskId")Integer taskId, @Param("qualityItemId")Integer qualityItemId, @Param("status")Integer status);
}
