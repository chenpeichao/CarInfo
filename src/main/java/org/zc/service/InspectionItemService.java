package org.zc.service;

import java.util.List;

import org.zc.domain.InspectionItem;



public interface InspectionItemService {
    
	/**
	 * 根据质检项id查询质检项信息
	 * @param itemId		质检项id
	 * @return
	 */
	public InspectionItem findItemByItemid(int itemId);

	/**
	 * 根据质检项组id查找质检项列表
	 * @param groupId	质检项组id
	 * @param isDeleted	是否删除	1:是，0:否
	 * @return
	 */
	public List<InspectionItem> findItemListByGroupId(Integer groupId, Integer isDeleted);
	
	/**
	 * 根据质检项组id查找质检项id列表
	 * @param groupId	质检项组id
	 * @param isDeleted	是否删除	1:是，0:否
	 * @return
	 */
	public List<Integer> findItemIdListByGroupId(Integer groupId, Integer isDeleted);

	/**
	 * 根据职能工位id查找其对应的所有质检项
	 * @param jobStationId		职能工位id
	 * @param isDeleted			是否删除1:是，0:否
	 * @return
	 */
	public List<InspectionItem> getQualityInspectionItemListByJobStationId(Integer jobStationId, Integer isDeleted);

	/**
	 * 根据职能工位id查找其对应的所有质检项id集合
	 * @param jobStationId		职能工位id
	 * @param isDeleted			是否删除1:是，0:否
	 * @return
	 */
	public List<Integer> getQualityInspectionItemIdListByJobStationId(Integer jobStationId, Integer isDeleted);
}
