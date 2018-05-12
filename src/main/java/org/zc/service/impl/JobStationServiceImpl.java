package org.zc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.Process;
import org.zc.repository.JobStationRepository;
import org.zc.service.JobStationService;

import java.util.List;

/**
 * Description:
 * Create by @author cpc
 * 2018年5月3日 下午5:27:55
 */
@Service
public class JobStationServiceImpl implements JobStationService {
	@Autowired
	private JobStationRepository jobStationRepository;
	
	/**
	 * 根据职能工位id查询其对应的质检项id列表
	 * @param jobStationId		职能工位id
	 */
	public List<Integer> findItemIdListByJobStationId(Integer jobStationId) {
		return jobStationRepository.findItemIdListByJobStationId(jobStationId);
	}

	/**
	 * 查询职能工位对应的质检流程id
	 * @param jobStationId
	 * @param processId
	 * @return
	 */
	public Process findProcessByJobStationId(Integer jobStationId, Integer processId) {
		List<Process> processListByJobStationId = jobStationRepository.findProcessByJobStationId(jobStationId, processId);
		if(processListByJobStationId != null && processListByJobStationId.size() > 0) {
			return processListByJobStationId.get(0);
		} else {
			return null;
		}
	}
}
