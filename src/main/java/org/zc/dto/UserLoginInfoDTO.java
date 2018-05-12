package org.zc.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录相关信息
 * Created by ceek on 2018-04-22 23:23.
 */
public class UserLoginInfoDTO {
    private Integer userId; //用户id
    private String name; //员工姓名
    private String userName;//用户名
    private Integer workShopId; //用户所属车间id
    private Map<Integer, String> roleMap = new HashMap<Integer, String>();   //角色map<角色id，角色名称>
    private Integer userGroupId; //用户组id
    private String userGroupName; //用户组名称
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(Integer workShopId) {
		this.workShopId = workShopId;
	}
	public Map<Integer, String> getRoleMap() {
		return roleMap;
	}
	public void setRoleMap(Map<Integer, String> roleMap) {
		this.roleMap = roleMap;
	}
	public Integer getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roleMap == null) ? 0 : roleMap.hashCode());
		result = prime * result + ((userGroupId == null) ? 0 : userGroupId.hashCode());
		result = prime * result + ((userGroupName == null) ? 0 : userGroupName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((workShopId == null) ? 0 : workShopId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserLoginInfoDTO other = (UserLoginInfoDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roleMap == null) {
			if (other.roleMap != null)
				return false;
		} else if (!roleMap.equals(other.roleMap))
			return false;
		if (userGroupId == null) {
			if (other.userGroupId != null)
				return false;
		} else if (!userGroupId.equals(other.userGroupId))
			return false;
		if (userGroupName == null) {
			if (other.userGroupName != null)
				return false;
		} else if (!userGroupName.equals(other.userGroupName))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (workShopId == null) {
			if (other.workShopId != null)
				return false;
		} else if (!workShopId.equals(other.workShopId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserLoginInfoDTO [userId=" + userId + ", name=" + name + ", userName=" + userName + ", workShopId="
				+ workShopId + ", roleMap=" + roleMap + ", userGroupId=" + userGroupId + ", userGroupName="
				+ userGroupName + "]";
	}
}
