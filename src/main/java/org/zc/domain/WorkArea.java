package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 产区
 * @author xuly
 *
 */

@Entity(name = "zc_work_area")
public class WorkArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;//产线名称
	@Column
	private String code; //产线编码
	@Column(name="workshopId")
	private int workshop_id; //车间id
	@Column (name = "is_deleted")
	private boolean isDeleted;//是否删除	
	@Column(name = "create_time")
	private int createTime;
	@Column(name = "update_time")
	private int updateTime;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getWorkshop_id() {
		return workshop_id;
	}
	public void setWorkshop_id(int workshop_id) {
		this.workshop_id = workshop_id;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	public int getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "WorkArea [id=" + id + ", name=" + name + ", code=" + code + ", workshop_id=" + workshop_id
				+ ", isDeleted=" + isDeleted + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + createTime;
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + updateTime;
		result = prime * result + workshop_id;
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
		WorkArea other = (WorkArea) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (createTime != other.createTime)
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
		if (updateTime != other.updateTime)
			return false;
		if (workshop_id != other.workshop_id)
			return false;
		return true;
	}
	
	
}
