package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 职能工位所对应的质检项关系表
 * @author xuly
 *
 */
@Entity(name = "zc_job_station_item")
public class JobStationItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "job_station_id")
	private int jobStationId;//职能工位ID
	@Column(name = "item_id")
	private int itemId;//质检项id
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
	public int getJobStationId() {
		return jobStationId;
	}
	public void setJobStationId(int jobStationId) {
		this.jobStationId = jobStationId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + id;
		result = prime * result + itemId;
		result = prime * result + jobStationId;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		JobStationItem other = (JobStationItem) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id != other.id)
			return false;
		if (itemId != other.itemId)
			return false;
		if (jobStationId != other.jobStationId)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JobStationItem [id=" + id + ", jobStationId=" + jobStationId + ", itemId=" + itemId + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
	
	
}
