package org.zc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.domain.JobStation;
import org.zc.domain.User;

/**
 * Description:	用户
 * Create by @author cpc
 * 2018年4月24日 下午4:26:19
 */
public interface UserRepository extends JpaRepository<User, Integer>{
	/**
     * 根据用户组id查询其对应职能工位信息
     * @param userGroupId        用户组id
     * @return
     */
    @Query(" select zjs FROM org.zc.domain.StationUserGroup zsug, org.zc.domain.Station zs, org.zc.domain.JobStationRelateStation zjsrs, org.zc.domain.JobStation zjs WHERE zsug.stationId = zs.id and zjsrs.stationId = zs.id and zjsrs.jobStationId = zjs.id and zsug.userGroupId = :userGroupId ")
    public List<JobStation> getJobStationByUserGroupId(@Param("userGroupId")Integer userGroupId);
    
    /**
	 * 根据任务流转id获取工位普通员工列表
	 * @param jobStationId	职能工位id
	 * @return
	 */
    @Query(" select user FROM org.zc.domain.User user, org.zc.domain.StationUserGroup zsug, org.zc.domain.Station zs, org.zc.domain.JobStationRelateStation zjsrs, org.zc.domain.JobStation zjs WHERE user.groupId = zsug.userGroupId and zsug.stationId = zs.id and zjsrs.stationId = zs.id and zjsrs.jobStationId = zjs.id and zs.workshopId = :workshopId and zs.workAreaId = :workAreaId and zjs.id = :jobStationId and zjs.typeId = :typeId and user.isAdmin = 0 ")
	public List<User> getStaffListByJobStationIdAndTypeId(@Param("jobStationId")Integer jobStationId, @Param("typeId")Integer typeId, @Param("workshopId")Integer workshopId, @Param("workAreaId")Integer workAreaId);

    /**
     * 根据工位长用户组id，获取工位普通员工列表
     * @param staffZhangGroupId    工位长用户组id
     * @return
     */
    @Query(" select user FROM org.zc.domain.User user, org.zc.domain.UserRole userRole, org.zc.domain.Role role where user.id = userRole.userId and userRole.roleId = role.id and role.id = 5 and user.groupId = :userGroupId ")
    public List<User> getStaffListByStaffZhangGroupId(@Param("userGroupId")Integer staffZhangGroupId);

    /**
     * 查询指定用户id列表中包含的指定权限列表
     * @param userIdList
     * @return
     */
    @Query(" select user FROM org.zc.domain.User user, org.zc.domain.UserRole userRole, org.zc.domain.Role role where user.id = userRole.userId and userRole.roleId = role.id and role.id in :roleIdList and user.id in :userIdList ")
    public List<User> findStaffZhangByUserIdList(@Param("userIdList")List<Integer> userIdList, @Param("roleIdList")List<Integer> roleIdList);
    
    /**
     * 根据用户名查找是否存在用户--排除后台用户
     * @param username		登录用户名
     * @return
     */
    @Query(" FROM org.zc.domain.User user where user.username = :username and user.isAdmin = 0 ")
    public List<User> getUserByUsername(@Param("username")String username);
    
    /**
	 * 用户登录接口--排除后台登录权限以及普通用户
	 * @param username		登录用户名
	 * @param password		密码
	 * @return
	 */
    @Query(" SELECT user FROM org.zc.domain.User user, org.zc.domain.UserRole userRole, org.zc.domain.Role role  where user.id = userRole.userId and userRole.roleId = role.id and role.id != 5 and user.username = :username and user.passwordHash = :password and user.isAdmin = 0")
	public List<User> userLogin(@Param("username")String username, @Param("password")String password);
}
