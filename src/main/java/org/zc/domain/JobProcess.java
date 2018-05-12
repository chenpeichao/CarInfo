package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 工位流程所需要的质检流程
 * @author xuly
 *
 */

@Entity(name = "zc_job_process")
public class JobProcess {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "job_station_id")
	private int jobStationId;//职能工位Id
	@Column(name = "process_id")
	private int processId;//职能工位Id
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
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
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
		result = prime * result + jobStationId;
		result = prime * result + processId;
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
		JobProcess other = (JobProcess) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id != other.id)
			return false;
		if (jobStationId != other.jobStationId)
			return false;
		if (processId != other.processId)
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
		return "JobProcess [id=" + id + ", jobStationId=" + jobStationId + ", processId=" + processId + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
	
	
	

}
