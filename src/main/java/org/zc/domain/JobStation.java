package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 职能工位
 * @author xuly
 *
 */
@Entity(name = "zc_job_station")
public class JobStation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;//虚拟工位名称（职能工位）
	@Column
	private int typeId;//质检类别id
	@Column(name = "workshop_id")
	private int workshopId;//车间id
	@Column
	private int sid;//下一工位id
	@Column
	private int pid;//上一工位id
	@Column(name = "is_deleted")
	private int isDeleted;//是否删除
	@Column(name = "create_time")
	private Long createTime;//
	@Column(name = "update_time")
	private Long updateTime;//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
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
	public int getWorkshopId() {
		return workshopId;
	}
	public void setWorkshopId(int workshopId) {
		this.workshopId = workshopId;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + id;
		result = prime * result + isDeleted;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + pid;
		result = prime * result + sid;
		result = prime * result + typeId;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		JobStation other = (JobStation) obj;
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
		if (typeId != other.typeId)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (workshopId != other.workshopId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JobStation [id=" + id + ", name=" + name + ", typeId=" + typeId + ", workshopId=" + workshopId
				+ ", sid=" + sid + ", pid=" + pid + ", isDeleted=" + isDeleted + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
}
