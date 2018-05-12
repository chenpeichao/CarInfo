package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Description:	任务流转记录表
 * Create by @author cpc
 * 2018年4月21日 下午1:29:31
 */
@Entity(name = "zc_task_execute_record")
public class TaskExecuteRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;					//主键标识
	@Column(name = "task_id")
	private Integer taskId;             //任务id
	@Column(name = "workshop_id")
	private Integer workshopId;         //车间id
	@Column(name = "work_area_id")
	private Integer workAreaId;         //工区id
	@Column(name = "job_station_id")
	private Integer jobStationId;          //职能工位id
	@Column(name = "user_id")
	private Integer userId;          //员工id
	@Column(name = "user_check_id")
	private Integer userCheckId;          //当为职能工位时，表示互检人id
	@Column(name = "default_work_area")
	private Integer defaultWorkArea;          //是否是相同工区默认禅心
	@Column(name = "type_id")
	private Integer typeId;             //质检类型id
	@Column(name = "process_id")
	private Integer processId;          //质检级别(zc_process)id(自检、互检、专检、分解、监造等)
	@Column(name = "quality_inspection_group_type")
	private Integer qualityInspectionGroupType;          //质检项组类型：1入厂鉴定，2工位质检，3整车质检
	@Column(name = "quality_inspection_group_id")
	private Integer qualityInspectionGroupId;          //质检项组id
	@Column(name = "execute_status")
	private Integer executeStatus;      //执行状态-0:未执行，1:执行完成
	@Column(name = "mark")
	private String mark;      //标识记录

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

	public Integer getJobStationId() {
		return jobStationId;
	}

	public void setJobStationId(Integer jobStationId) {
		this.jobStationId = jobStationId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserCheckId() {
		return userCheckId;
	}

	public void setUserCheckId(Integer userCheckId) {
		this.userCheckId = userCheckId;
	}

	public Integer getDefaultWorkArea() {
		return defaultWorkArea;
	}

	public void setDefaultWorkArea(Integer defaultWorkArea) {
		this.defaultWorkArea = defaultWorkArea;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getQualityInspectionGroupType() {
		return qualityInspectionGroupType;
	}

	public void setQualityInspectionGroupType(Integer qualityInspectionGroupType) {
		this.qualityInspectionGroupType = qualityInspectionGroupType;
	}

	public Integer getQualityInspectionGroupId() {
		return qualityInspectionGroupId;
	}

	public void setQualityInspectionGroupId(Integer qualityInspectionGroupId) {
		this.qualityInspectionGroupId = qualityInspectionGroupId;
	}

	public Integer getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(Integer executeStatus) {
		this.executeStatus = executeStatus;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultWorkArea == null) ? 0 : defaultWorkArea.hashCode());
		result = prime * result + ((executeStatus == null) ? 0 : executeStatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobStationId == null) ? 0 : jobStationId.hashCode());
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((processId == null) ? 0 : processId.hashCode());
		result = prime * result + ((qualityInspectionGroupId == null) ? 0 : qualityInspectionGroupId.hashCode());
		result = prime * result + ((qualityInspectionGroupType == null) ? 0 : qualityInspectionGroupType.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		result = prime * result + ((userCheckId == null) ? 0 : userCheckId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		TaskExecuteRecord other = (TaskExecuteRecord) obj;
		if (defaultWorkArea == null) {
			if (other.defaultWorkArea != null)
				return false;
		} else if (!defaultWorkArea.equals(other.defaultWorkArea))
			return false;
		if (executeStatus == null) {
			if (other.executeStatus != null)
				return false;
		} else if (!executeStatus.equals(other.executeStatus))
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
		if (mark == null) {
			if (other.mark != null)
				return false;
		} else if (!mark.equals(other.mark))
			return false;
		if (processId == null) {
			if (other.processId != null)
				return false;
		} else if (!processId.equals(other.processId))
			return false;
		if (qualityInspectionGroupId == null) {
			if (other.qualityInspectionGroupId != null)
				return false;
		} else if (!qualityInspectionGroupId.equals(other.qualityInspectionGroupId))
			return false;
		if (qualityInspectionGroupType == null) {
			if (other.qualityInspectionGroupType != null)
				return false;
		} else if (!qualityInspectionGroupType.equals(other.qualityInspectionGroupType))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		if (userCheckId == null) {
			if (other.userCheckId != null)
				return false;
		} else if (!userCheckId.equals(other.userCheckId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		return "TaskExecuteRecord [id=" + id + ", taskId=" + taskId + ", workshopId=" + workshopId + ", workAreaId="
				+ workAreaId + ", jobStationId=" + jobStationId + ", userId=" + userId + ", userCheckId=" + userCheckId
				+ ", defaultWorkArea=" + defaultWorkArea + ", typeId=" + typeId + ", processId=" + processId
				+ ", qualityInspectionGroupType=" + qualityInspectionGroupType + ", qualityInspectionGroupId="
				+ qualityInspectionGroupId + ", executeStatus=" + executeStatus + ", mark=" + mark + "]";
	}
}
