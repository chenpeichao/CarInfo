package org.zc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.Station;
import org.zc.repository.StationRepository;
import org.zc.service.StationService;
import org.zc.vo.StationVO;

/**
 * 工位
 * Created by ceek on 2018-04-24 22:56.
 */
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationRepository stationRepository;

    /**
     * 根据工位id，获取工位VO
     * @param id
     * @return
     */
    public StationVO findStationVOById(Integer id) {
    	Station station = stationRepository.findOne(id);
    	if(station == null) {
    		return null;
    	}
    	StationVO stationVO = new StationVO();
    	stationVO.setId(station.getId());
    	stationVO.setName(station.getName());
    	stationVO.setCode(station.getCode());
    	stationVO.setSid(station.getSid());
    	stationVO.setPid(station.getPid());
        return stationVO;
    }
    
    /**
     * 根据职能工位id，查询物理工位信息
     * @param jobStationId		职能工位id
     * @param workAreaId		物理工区
     * @param typeId			质检类型
     */
    public Station findStationByJobStationId(Integer jobStationId, Integer workAreaId,  Integer typeId) {
    	List<Station> stations = stationRepository.findStationByJobStationId(jobStationId, workAreaId, typeId);
    	if(stations == null || stations.size() == 0) {
    		return null;
    	}
    	return stations.get(0);
    }
}
