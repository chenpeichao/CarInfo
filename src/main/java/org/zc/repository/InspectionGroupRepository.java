package org.zc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zc.domain.InspectionGroup;
import org.zc.domain.InspectionGroupProcess;

/**
 * DAO层
 * 
 * @author
 * @create 2018-03-06 15:32
 **/
public interface InspectionGroupRepository extends JpaRepository<InspectionGroup, Integer> {

	/**
	 * 根据Type_id ，item_type 拿到zc_quality_inspection_group表的id
	 */
	@Query("select id from org.zc.domain.InspectionGroup o where o.typeId = ?1 and o.itemType = ?2")
	public int findGroup_id(int type_id,int item_type); //

	/**
	 * 根据质检项名称和质检类型id获取质检项组信息
	 * @param name			质检项名称
	 * @param typeId		质检类型id
	 * @return
	 */
	@Query(" from org.zc.domain.InspectionGroup inspectionGroup where inspectionGroup.name = ?1 and inspectionGroup.typeId = ?2" )
	public InspectionGroup findInspectionGroupByTypeIdAndName(String name, Integer typeId);
	
	/**
	 * 根据质检项类型标识和质检类型id获取质检项组信息
	 * @param itemType			质检项类型
	 * @param typeId			质检类型id
	 * @return
	 */
	@Query(" from org.zc.domain.InspectionGroup inspectionGroup where inspectionGroup.itemType = :itemType and inspectionGroup.typeId = :typeId and inspectionGroup.isDeleted = 0 " )
	public InspectionGroup findInspectionGroupByItemTypeAndTypeId(@Param("itemType")Integer itemType, @Param("typeId")Integer typeId);
	
	/**
	 * 根据质检类型以及质检项id，来唯一确定一个质检项组id
	 * @param qualityTypeId					质检类型id
	 * @param qualityInspectionItemId		质检项id
	 * @return
	 */
	@Query(" select inspectionGroup FROM org.zc.domain.InspectionGroup inspectionGroup ,org.zc.domain.InspectionGroupItem inspectionGroupItem where inspectionGroup.id = inspectionGroupItem.groupId and inspectionGroupItem.itemId = :itemId AND inspectionGroup.typeId = :typeId" )
	public List<InspectionGroup> findInspectionGroupByTypeIdAndInspectionItemId(@Param("typeId")Integer qualityTypeId, @Param("itemId")Integer qualityInspectionItemId);
	
	/**
	 * 查询当前质检项组是否触发专检和监造
	 * @param inspectionGroupId		质检项组id
	 * @return
	 */
	@Query(" FROM org.zc.domain.InspectionGroupProcess inspectionGroupProcess WHERE inspectionGroupProcess.qualityGroupId = :qualityGroupId AND inspectionGroupProcess.processId = :processId" )
	public InspectionGroupProcess findSpecialOrBuildInspectionProcessByInspectionGroupId(@Param("qualityGroupId")Integer inspectionGroupId, @Param("processId")Integer processId);
}
