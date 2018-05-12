package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 问题结果表
 * @author xuly
 */
@Entity(name = "zc_answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "process_id")
	private Integer processId;//质检流程(自检、互检、专检等)
	@Column(name = "item_type")
	private Integer itemType;//质检项组类型（1、入厂鉴定；2、工位；3、整车质检）
	@Column(name = "quality_item_id")
	private Integer qualityItemId;//质检项ID
	@Column(name = "quality_item_name")
	private String qualityItemName;//质检项名称（冗余质检名称）
	@Column
	private String content;//所填答案（自定义答案）
	@Column(name = "choose_content")
	private String chooseContent;//针对选择题判断（选择的答案）
	@Column(name = "user_id")
	private Integer userId;//操作人ID（注意不是工位长）
	@Column(name = "user_name")
	private String userName;//操作人名称
	@Column(name = "task_id")
	private Integer taskId;//任务ID
	@Column(name = "station_id")
	private Integer stationId;//工位ID
	@Column(name = "job_station_id")
	private Integer jobStationId;//职能工位ID
	@Column(name = "work_area_id")
	private Integer workAreaId;//产线ID
	@Column(name = "workshop_id")
	private Integer workshopId;//车间ID
	@Column
	private Integer times;//质检次数
	@Column
	private Integer status;//答案状态 0代表失败，1代表成功
	@Column(name = "create_time")
	private Long createTime;//
	@Column(name = "update_time")
	private Long updateTime;//
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProcessId() {
		return processId;
	}
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	public Integer getQualityItemId() {
		return qualityItemId;
	}
	public void setQualityItemId(Integer qualityItemId) {
		this.qualityItemId = qualityItemId;
	}
	public String getQualityItemName() {
		return qualityItemName;
	}
	public void setQualityItemName(String qualityItemName) {
		this.qualityItemName = qualityItemName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChooseContent() {
		return chooseContent;
	}
	public void setChooseContent(String chooseContent) {
		this.chooseContent = chooseContent;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public Integer getJobStationId() {
		return jobStationId;
	}
	public void setJobStationId(Integer jobStationId) {
		this.jobStationId = jobStationId;
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
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
		result = prime * result + ((chooseContent == null) ? 0 : chooseContent.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + ((jobStationId == null) ? 0 : jobStationId.hashCode());
		result = prime * result + ((processId == null) ? 0 : processId.hashCode());
		result = prime * result + ((qualityItemId == null) ? 0 : qualityItemId.hashCode());
		result = prime * result + ((qualityItemName == null) ? 0 : qualityItemName.hashCode());
		result = prime * result + ((stationId == null) ? 0 : stationId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result + ((times == null) ? 0 : times.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Answer other = (Answer) obj;
		if (chooseContent == null) {
			if (other.chooseContent != null)
				return false;
		} else if (!chooseContent.equals(other.chooseContent))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
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
		if (itemType == null) {
			if (other.itemType != null)
				return false;
		} else if (!itemType.equals(other.itemType))
			return false;
		if (jobStationId == null) {
			if (other.jobStationId != null)
				return false;
		} else if (!jobStationId.equals(other.jobStationId))
			return false;
		if (processId == null) {
			if (other.processId != null)
				return false;
		} else if (!processId.equals(other.processId))
			return false;
		if (qualityItemId == null) {
			if (other.qualityItemId != null)
				return false;
		} else if (!qualityItemId.equals(other.qualityItemId))
			return false;
		if (qualityItemName == null) {
			if (other.qualityItemName != null)
				return false;
		} else if (!qualityItemName.equals(other.qualityItemName))
			return false;
		if (stationId == null) {
			if (other.stationId != null)
				return false;
		} else if (!stationId.equals(other.stationId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (times == null) {
			if (other.times != null)
				return false;
		} else if (!times.equals(other.times))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
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
		return "Answer [id=" + id + ", processId=" + processId + ", itemType=" + itemType + ", qualityItemId="
				+ qualityItemId + ", qualityItemName=" + qualityItemName + ", content=" + content + ", chooseContent="
				+ chooseContent + ", userId=" + userId + ", userName=" + userName + ", taskId=" + taskId
				+ ", stationId=" + stationId + ", jobStationId=" + jobStationId + ", workAreaId=" + workAreaId
				+ ", workshopId=" + workshopId + ", times=" + times + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
}
