package org.zc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zc.domain.Station;

/**
 * 工位
 * Created by ceek on 2018-04-24 22:57.
 */
public interface StationRepository extends JpaRepository<Station, Integer> {
	/**
     * 根据职能工位id，查询物理工位信息
     * @param jobStationId		职能工位id
     * @param workAreaId		物理工区
     * @param typeId			质检类型
     */
	@Query(" SELECT station FROM org.zc.domain.JobStation jobStation, org.zc.domain.JobStationRelateStation jobStationRelateStation, org.zc.domain.Station station WHERE jobStation.id = jobStationRelateStation.jobStationId and jobStationRelateStation.workAreaId = :workAreaId and jobStationRelateStation.stationId = station.id and jobStation.typeId = :typeId AND jobStation.id = :jobStationId ")
    public List<Station> findStationByJobStationId(@Param("jobStationId")Integer jobStationId, @Param("workAreaId")Integer workAreaId, @Param("typeId")Integer typeId);
}
