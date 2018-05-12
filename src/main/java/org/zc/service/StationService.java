package org.zc.service;

import org.zc.domain.Station;
import org.zc.vo.StationVO;

/**
 * 工位
 * Created by ceek on 2018-04-24 22:55.
 */
public interface StationService {
    /**
     * 根据工位id，获取工位详情
     * @param id
     * @return
     */
    public StationVO findStationVOById(Integer id);
    
    /**
     * 根据职能工位id，查询物理工位信息
     * @param jobStationId		职能工位id
     * @param workAreaId		物理工区
     * @param typeId			质检类型
     */
    public Station findStationByJobStationId(Integer jobStationId, Integer workAreaId, Integer typeId);
}
