package org.zc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.InspectionItem;
import org.zc.repository.InspectionItemRepository;
import org.zc.service.InspectionItemService;

/**
 * 处理
 *
 * @author xly
 * @create 2018-03-06 15:28
 **/
@Service
public  class InspectionItemServiceImpl implements InspectionItemService {
    @Autowired
    private InspectionItemRepository inspectionItemRepository;

    /**
	 * 根据质检项id查询质检项信息
	 * @param itemId		质检项id
	 * @return
	 */
	public InspectionItem findItemByItemid(int itemId) {
		return inspectionItemRepository.findOne(itemId);
	}


	/**
	 * 根据质检项组id查找质检项列表
	 * @param groupId	质检项组id
	 * @param isDeleted	是否删除	1:是，0:否
	 * @return
	 */
	public List<InspectionItem> findItemListByGroupId(Integer groupId, Integer isDeleted) {
		return inspectionItemRepository.findItemListByGroupId(groupId, isDeleted);
	}

	/**
	 * 根据质检项组id查找质检项id列表
	 * @param groupId	质检项组id
	 * @param isDeleted	是否删除	1:是，0:否
	 * @return
	 */
	public List<Integer> findItemIdListByGroupId(Integer groupId, Integer isDeleted) {
		return inspectionItemRepository.findItemIdListByGroupId(groupId, isDeleted);
	}

	/**
	 * 根据职能工位id查找其对应的所有质检项
	 * @param jobStationId		职能工位id
	 * @param isDeleted			是否删除1:是，0:否
	 * @return
	 */
	public List<InspectionItem> getQualityInspectionItemListByJobStationId(Integer jobStationId, Integer isDeleted) {
		return inspectionItemRepository.getQualityInspectionItemListByJobStationId(jobStationId, isDeleted);
	}

	/**
	 * 根据职能工位id查找其对应的所有质检项id集合
	 * @param jobStationId		职能工位id
	 * @param isDeleted			是否删除1:是，0:否
	 * @return
	 */
	public List<Integer> getQualityInspectionItemIdListByJobStationId(Integer jobStationId, Integer isDeleted) {
		return inspectionItemRepository.getQualityInspectionItemIdListByJobStationId(jobStationId, isDeleted);
	}
}
