package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 工位
 * @author xuly
 *
 */
@Entity(name = "zc_station")
public class Station {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String name;//工位名称
	@Column
	private String code;//工位编码
	@Column
	private Integer pid;//上一工位id
	@Column
	private Integer sid;//下一工位id
	@Column(name = "work_area_id")
	private Integer workAreaId;//产线ID
	@Column(name = "workshop_id")
	private Integer workshopId;//车间id
	@Column(name = "is_deleted")
	private Integer isDeleted;
	@Column(name = "create_time")
	private Long createTime;//
	@Column(name = "update_time")
	private Long updateTime;//
	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + id;
		result = prime * result + isDeleted;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + pid;
		result = prime * result + sid;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + workAreaId;
		result = prime * result + workshopId;
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
		Station other = (Station) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id != other.id)
			return false;
		if (isDeleted != other.isDeleted)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pid != other.pid)
			return false;
		if (sid != other.sid)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (workAreaId != other.workAreaId)
			return false;
		if (workshopId != other.workshopId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Station [id=" + id + ", name=" + name + ", code=" + code + ", pid=" + pid + ", sid=" + sid + "" +
				", workAreaId=" + workAreaId + ", workshopId=" + workshopId + ", isDeleted=" + isDeleted
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(Integer workAreaId) {
		this.workAreaId = workAreaId;
	}
	public Integer getWorkshopId() {
		return workshopId;
	}
	public void setWorkshopId(Integer workshopId) {
		this.workshopId = workshopId;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	
}