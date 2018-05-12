package org.zc.service;

import java.util.List;
import java.util.Map;

import org.zc.domain.JobStation;
import org.zc.domain.User;
import org.zc.dto.UserLoginInfoDTO;

/**
 * Description:	用户service
 * Create by @author cpc
 * 2018年4月22日 下午1:23:19
 */
public interface UserService {
    /**
     * 查询登录用户的登录信息--用户/权限/用户组
     * @param userId    用户id
     * @return
     */
	public UserLoginInfoDTO getUserLoginInfoDTO(String userId);
	
	/**
	 * 根据用户组id获取用户对应职能工位信息
	 * @return
	 */
	public List<JobStation> getJobStationByUserGroupId(Integer userGroupId);
	
	/**
	 * 根据用户id查询用户信息
	 * @param userId		用户id
	 * @return
	 */
	public User findOneByUserId(Integer userId);
	
	/**
	 * 根据任务流转id获取工位普通员工列表
	 * @param taskRecordId	任务流转id
	 * @return
	 */
	public Map<String, Object> getStaffListInStationByTaskRecordId(Integer taskRecordId);
	
	/**
	 * 用户登录接口
	 * @param username		登录用户名
	 * @param password		密码
	 * @return
	 */
	public Map<String, Object> userLogin(String username, String password);
}
