package org.zc.service;

import java.util.List;

import org.zc.domain.InspectionGroupItem;


/**
 * @author 
 * @create 2018-03-06 15:28
 **/
public interface InspectionGroupItemService {
    
	/**
	 * 根据group_id 去查找zc_quality_inspection_group_item的item_id （一对多）
	 */
	 public List<InspectionGroupItem> findItemBygroupid(int group_id); //按照质检项组查询出相关的itemid
}
