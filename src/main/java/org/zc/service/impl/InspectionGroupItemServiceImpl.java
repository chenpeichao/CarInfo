package org.zc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.InspectionGroupItem;
import org.zc.repository.InspectionGroupItemRepository;
import org.zc.service.InspectionGroupItemService;

/**
 * InspectionGroupItemService处理
 *
 * @author xly
 * @create 2018-03-06 15:28
 **/
@Service
public class InspectionGroupItemServiceImpl implements InspectionGroupItemService {

	@Autowired
	private InspectionGroupItemRepository inspectionGroupItemRepository;

	@Override
	public List<InspectionGroupItem> findItemBygroupid(int group_id) {
		// TODO Auto-generated method stub
		return inspectionGroupItemRepository.findItemBygroupid(group_id);
	}
}
