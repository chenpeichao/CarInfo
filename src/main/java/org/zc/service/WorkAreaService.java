package org.zc.service;

import org.zc.domain.WorkArea;

/**
 * 工区
 * Created by ceek on 2018-04-24 22:47.
 */
public interface WorkAreaService {
    /**
     * 根据工区id，查询指定工区信息
     * @param id        工区id
     * @return
     */
    public WorkArea findOneById(Integer id);
}
