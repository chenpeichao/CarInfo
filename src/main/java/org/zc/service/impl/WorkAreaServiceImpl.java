package org.zc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.WorkArea;
import org.zc.repository.WorkAreaRepository;
import org.zc.service.WorkAreaService;

/**
 * 工区
 * Created by ceek on 2018-04-24 22:48.
 */
@Service
public class WorkAreaServiceImpl implements WorkAreaService {
    @Autowired
    private WorkAreaRepository workAreaRepository;

    /**
     * 根据工区id，查询指定工区信息
     * @param id        工区id
     * @return
     */
    public WorkArea findOneById(Integer id) {
        return workAreaRepository.findOne(id);
    }
}
