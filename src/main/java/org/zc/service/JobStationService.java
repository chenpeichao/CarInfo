package org.zc.service;

import org.zc.domain.Process;

import java.util.List;

/**
 * Description:职能工位service
 * Create by @author cpc
 * 2018年5月3日 下午5:27:22
 */
public interface JobStationService {
	/**
	 * 根据职能工位id查询其对应的质检项id列表
	 * @param jobStationId		职能工位id
	 */
	public List<Integer> findItemIdListByJobStationId(Integer jobStationId);

	/**
	 * 查询职能工位对应的质检流程id
	 * @param jobStationId
	 * @param processId
	 * @return
	 */
	public Process findProcessByJobStationId(Integer jobStationId, Integer processId);

}
