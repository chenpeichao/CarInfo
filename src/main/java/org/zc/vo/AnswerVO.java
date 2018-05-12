package org.zc.vo;

/**
 * Description:	答案列表
 * Create by @author cpc
 * 2018年5月2日 下午5:29:31
 */
public class AnswerVO {
	private Integer id;
	private Integer processId;
	private Integer qualityItemId;
	private String qualityItemName;
	private String content;			//自定义答案
	private String chooseContent; //选择答案
	private Integer userId;		//操作人id
	private String userName;	//操作人名称
	private Integer taskId;	//任务id
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chooseContent == null) ? 0 : chooseContent.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((processId == null) ? 0 : processId.hashCode());
		result = prime * result + ((qualityItemId == null) ? 0 : qualityItemId.hashCode());
		result = prime * result + ((qualityItemName == null) ? 0 : qualityItemName.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		AnswerVO other = (AnswerVO) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
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
		return true;
	}
	@Override
	public String toString() {
		return "AnswerVO [id=" + id + ", processId=" + processId + ", qualityItemId=" + qualityItemId
				+ ", qualityItemName=" + qualityItemName + ", content=" + content + ", chooseContent=" + chooseContent
				+ ", userId=" + userId + ", userName=" + userName + ", taskId=" + taskId + "]";
	}
}
