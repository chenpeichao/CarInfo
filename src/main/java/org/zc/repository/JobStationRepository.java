package org.zc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zc.domain.JobStation;
import org.zc.domain.Process;

/**
 * Description:职能工位
 * Create by @author cpc
 * 2018年5月3日 下午5:30:37
 */
public interface JobStationRepository extends JpaRepository<JobStation, Integer> {
	/**
	 * 根据职能工位id查询其对应的质检项id列表
	 * @param jobStationId		职能工位id
	 */
	@Query(" SELECT jobStationItem.itemId FROM org.zc.domain.JobStation jobStation, org.zc.domain.JobStationItem jobStationItem where jobStation.id = jobStationItem.jobStationId and jobStation.id = :jobStationId ")
	public List<Integer> findItemIdListByJobStationId(@Param("jobStationId")Integer jobStationId);

	/**
	 * 查询职能工位对应的质检流程id
	 * @param jobStationId
	 * @param processId
	 * @return
	 */
	@Query(" SELECT process FROM org.zc.domain.JobStation jobStation, org.zc.domain.JobProcess jobProcess, org.zc.domain.Process process where jobStation.id = jobProcess.jobStationId and jobProcess.processId = process.id and jobStation.id = :jobStationId and process.id = :processId ")
	public List<Process> findProcessByJobStationId(@Param("jobStationId")Integer jobStationId, @Param("processId")Integer processId);
}
