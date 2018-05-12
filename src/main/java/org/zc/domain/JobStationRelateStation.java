package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Description:	物理工位和职能工位对应中间实体
 * Create by @author cpc
 * 2018年4月24日 下午4:18:46
 */
@Entity(name = "zc_job_station_relate_station")
public class JobStationRelateStation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "job_station_id")
	private Integer jobStationId;			//职能工位
	@Column(name = "type_id")
	private Integer typeId;					//质检类型
	@Column(name = "workshop_id")
	private Integer workshopId;				//车间ID
	@Column(name = "work_area_id")
	private Integer workAreaId;				//工区ID
	@Column(name = "station_id")
	private Integer stationId;				//工位ID
	@Column(name = "create_time")
	private Long createTime;				//创建时间
	@Column(name = "update_time")
	private Long updateTime;				//更新时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getJobStationId() {
		return jobStationId;
	}
	public void setJobStationId(Integer jobStationId) {
		this.jobStationId = jobStationId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getWorkshopId() {
		return workshopId;
	}
	public void setWorkshopId(Integer workshopId) {
		this.workshopId = workshopId;
	}
	public Integer getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(Integer workAreaId) {
		this.workAreaId = workAreaId;
	}
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobStationId == null) ? 0 : jobStationId.hashCode());
		result = prime * result + ((stationId == null) ? 0 : stationId.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((workAreaId == null) ? 0 : workAreaId.hashCode());
		result = prime * result + ((workshopId == null) ? 0 : workshopId.hashCode());
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
		JobStationRelateStation other = (JobStationRelateStation) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jobStationId == null) {
			if (other.jobStationId != null)
				return false;
		} else if (!jobStationId.equals(other.jobStationId))
			return false;
		if (stationId == null) {
			if (other.stationId != null)
				return false;
		} else if (!stationId.equals(other.stationId))
			return false;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (workAreaId == null) {
			if (other.workAreaId != null)
				return false;
		} else if (!workAreaId.equals(other.workAreaId))
			return false;
		if (workshopId == null) {
			if (other.workshopId != null)
				return false;
		} else if (!workshopId.equals(other.workshopId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JobStationRelateStation [id=" + id + ", jobStationId=" + jobStationId + ", typeId=" + typeId
				+ ", workshopId=" + workshopId + ", workAreaId=" + workAreaId + ", stationId=" + stationId
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}
