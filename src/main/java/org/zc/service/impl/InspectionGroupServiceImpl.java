package org.zc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.InspectionGroup;
import org.zc.domain.InspectionGroupProcess;
import org.zc.repository.InspectionGroupRepository;
import org.zc.service.InspectionGroupService;

/**
 * VehicleInfoService处理
 *
 * @author xly
 * @create 2018-03-06 15:28
 **/
@Service
public  class InspectionGroupServiceImpl implements InspectionGroupService {

	 @Autowired
	  private InspectionGroupRepository inspectionGroupRepository;

	@Override
	public int findGroup_id(int type_id, int item_type) {
		// TODO Auto-generated method stub
		return inspectionGroupRepository.findGroup_id(type_id, item_type);
	}

	/**
	 * 根据质检项名称和质检类型id获取质检项组信息
	 * @param name			质检项名称
	 * @param typeId		质检类型id
	 * @return
	 */
	public InspectionGroup findInspectionGroupByTypeIdAndName(String name, Integer typeId) {
		return inspectionGroupRepository.findInspectionGroupByTypeIdAndName(name, typeId);
	}
	
	/**
	 * 根据质检项类型标识和质检类型id获取质检项组信息
	 * @param itemType			质检项类型
	 * @param typeId			质检类型id
	 * @return
	 */
	public InspectionGroup findInspectionGroupByItemTypeAndTypeId(Integer itemType, Integer typeId) {
		return inspectionGroupRepository.findInspectionGroupByItemTypeAndTypeId(itemType, typeId);
	}
	
	/**
	 * 根据质检类型以及质检项id，来唯一确定一个质检项组id
	 * @param qualityTypeId					质检类型id
	 * @param qualityInspectionItemId		质检项id
	 * @return
	 */
	public List<InspectionGroup> findInspectionGroupByTypeIdAndInspectionItemId(Integer qualityTypeId, Integer qualityInspectionItemId) {
		return inspectionGroupRepository.findInspectionGroupByTypeIdAndInspectionItemId(qualityTypeId, qualityInspectionItemId);
	}
	
	/**
	 * 查询当前质检项组是否触发专检和监造
	 * @param inspectionGroupId		质检项组id
	 * @return
	 */
	public InspectionGroupProcess findSpecialOrBuildInspectionProcessByInspectionGroupId(Integer inspectionGroupId, Integer processId) {
		return inspectionGroupRepository.findSpecialOrBuildInspectionProcessByInspectionGroupId(inspectionGroupId, processId);
	}

	/**
	 * 根据质检项组id查询质检项组信息
	 * @param inspectionGroupId		质检项组id
	 * @return
	 */
	public InspectionGroup findInspectionGroupById(Integer inspectionGroupId) {
		return inspectionGroupRepository.findOne(inspectionGroupId);
	}
}
