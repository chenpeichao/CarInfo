package org.zc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zc.domain.InspectionItem;

/**
 * DAO层
 * 
 * @author
 * @create 2018-03-06 15:32
 **/
public interface InspectionItemRepository extends JpaRepository<InspectionItem, Integer> {
	/**
	 * 根据质检项组id查找质检项列表
	 * @param groupId	质检项组id
	 * @param isDeleted	是否删除
	 * @return
	 */
	@Query(" SELECT inspectionItem  From org.zc.domain.InspectionGroupItem inspectionGroupItem, org.zc.domain.InspectionItem inspectionItem  where inspectionGroupItem.itemId = inspectionItem.id and inspectionGroupItem.groupId = :groupId and inspectionItem.isDeleted = :isDeleted ")
	public List<InspectionItem> findItemListByGroupId(@Param("groupId")Integer groupId, @Param("isDeleted")Integer isDeleted);

	/**
	 * 根据质检项组id查找质检项id列表
	 * @param groupId	质检项组id
	 * @param isDeleted	是否删除
	 * @return
	 */
	@Query(" SELECT inspectionItem.id  From org.zc.domain.InspectionGroupItem inspectionGroupItem, org.zc.domain.InspectionItem inspectionItem  where inspectionGroupItem.itemId = inspectionItem.id and inspectionGroupItem.groupId = :groupId and inspectionItem.isDeleted = :isDeleted ")
	public List<Integer> findItemIdListByGroupId(@Param("groupId")Integer groupId, @Param("isDeleted")Integer isDeleted);

	/**
	 * 根据职能工位id查找其对应的所有质检项
	 * @param jobStationId		职能工位id
	 * @param isDeleted			是否删除1:是，0:否
	 * @return
	 */
	@Query(" SELECT zqii  FROM org.zc.domain.JobStation zjs, org.zc.domain.JobStationItem zjsi, org.zc.domain.InspectionItem zqii where zjsi.itemId = zqii.id and zjs.id = zjsi.jobStationId and zjs.id = :jobStationId  and zqii.isDeleted = :isDeleted ")
	public List<InspectionItem> getQualityInspectionItemListByJobStationId(@Param("jobStationId")Integer jobStationId, @Param("isDeleted")Integer isDeleted);

	/**
	 * 根据职能工位id查找其对应的所有质检项id集合
	 * @param jobStationId		职能工位id
	 * @param isDeleted			是否删除1:是，0:否
	 * @return
	 */
	@Query(" SELECT zqii.id  FROM org.zc.domain.JobStation zjs, org.zc.domain.JobStationItem zjsi, org.zc.domain.InspectionItem zqii where zjsi.itemId = zqii.id and zjs.id = zjsi.jobStationId and zjs.id = :jobStationId  and zqii.isDeleted = :isDeleted ")
	public List<Integer> getQualityInspectionItemIdListByJobStationId(@Param("jobStationId")Integer jobStationId, @Param("isDeleted")Integer isDeleted);
}
