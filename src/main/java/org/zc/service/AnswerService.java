package org.zc.service;

import java.util.List;
import java.util.Map;

import org.zc.domain.Answer;
import org.zc.domain.InspectionItem;


/**
 * 问题
 * @author xuly
 *
 */
public interface AnswerService {
    
    public void saveAnswer(Answer answer);
    public void saveAnswerList(List<Answer> answerList);
    /**
     * 根据上步质检项id列表，查询答题答案
     * @param qualityInspectionItemIds	质检项id
     * @param taskId					任务id
     * @param processId					流程id
     * @param status					答案成功状态0：失败、1：成功
     * @return
     */
    public List<Answer> findAnswerListByQualityInspectionItemIdList(List<Integer> qualityInspectionItemIds, Integer taskId, Integer processId, Integer status);
    /**
     * 根据上步质检项id列表，查询答题答案--包含失败的答案
     * @param qualityInspectionItemIds	质检项id
     * @param taskId					任务id
     * @param processId					流程id
     * @return
     */
    public List<Answer> findAnswerListByQualityInspectionItemIdList(List<Integer> qualityInspectionItemIds, Integer taskId, Integer processId);

//    /**
//	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
//	 * @param qualityInspectionItemIds	质检项id
//	 * @param taskId					任务id
//	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
//	 * @param status					答案成功状态0：失败、1：成功
//	 * @return
//	 */
//	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemType(List<Integer> qualityInspectionItemIds, Integer taskId, Integer itemType, Integer status);
    /**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param processId					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param status					答案成功状态0：失败、1：成功
	 * @return
	 */
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(List<Integer> qualityInspectionItemIds, Integer taskId, Integer itemType, Integer processId, Integer status);

	/**
	 * 根据质检项id列表，任务id，和item_type类型，查询答题答案--包含失败的答案
	 * @param qualityInspectionItemIds	质检项id
	 * @param taskId					任务id
	 * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @param processId					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	 * @return
	 */
	public List<Answer> findAnswerListByQualityInspectionItemIdListAndItemTypeAndProcessId(List<Integer> qualityInspectionItemIds, Integer taskId, Integer itemType, Integer processId);

//    /**
//     * 根据任务id，和item_type类型，查询已经答题的质检项id列表
//     * @param taskId					任务id
//     * @param itemType					质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
//     * @param status					答案成功状态0：失败、1：成功
//     * @return
//     */
//    public List<Integer> findItemIdListByTaskIdAndItemType(Integer taskId, Integer itemType, Integer status);

    /**
     * 根据任务id，和item_type，以及processId查询质检项id列表
     * @param taskId                    任务id
     * @param processId                 流程id    自检、互检、专检、分解、监造
     * @param itemType                  质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
     * @param status                    答案成功状态0：失败、1：成功
     * @return
     */
    public List<InspectionItem> findItemIdListByTaskIdAndProcessIdAndItemType(Integer taskId, Integer processId, Integer itemType, Integer status);
    
    /**
     * 根据任务id，和职能工位id类型，查询已经答题成功的质检项id列表
     * @param taskId					任务id
     * @param jobStationId			职能工位id
     * @param processId				流程任务    自检、互检、专检、分解、监造
     * @param status					答案成功状态0：失败、1：成功
     * @return
     */
    public List<InspectionItem> findItemIdListByTaskIdAndJobStationIdAndProcessId(Integer taskId, Integer jobStationId, Integer processId, Integer status);


	/**
	 * 根据任务流转记录id和流转status标识，获取
 	 * @param taskRecordId		任务流转记录id
	 * @param status				任务流转状态标识
	 * @param pageNumber			页码
	 * @param pageSize				每页显示记录数
	 * @return
	 */
	public Map<String, Object> getAnswerList(Integer taskRecordId, Integer status, Integer pageNumber, Integer pageSize);

	/**
	 * 查询指定任务下的质检项对应的有效自检答题详情
	 * @param taskId		任务id
	 * @param itemId		质检项id
	 * @param status		成功标识，0：失败；1：成功
	 * @return
	 */
	public List<Answer> findAnswerByTaskIdAndItemId(Integer taskId, Integer itemId, Integer status);
}
