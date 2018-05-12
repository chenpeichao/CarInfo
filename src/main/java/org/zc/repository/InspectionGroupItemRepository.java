package org.zc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zc.domain.InspectionGroupItem;

/**
 * 质检组和质检项关系表
 * @author xuly
 *
 */
public interface InspectionGroupItemRepository extends JpaRepository<InspectionGroupItem, Integer> {

	/**
	 * 根据group_id 去查找zc_quality_inspection_group_item的item_id （一对多）
	 */
	@Query("select o  from org.zc.domain.InspectionGroupItem o where o.groupId = ?1")
	public List<InspectionGroupItem> findItemBygroupid(int group_id); //按照质检项组查询出相关的itemid


}
