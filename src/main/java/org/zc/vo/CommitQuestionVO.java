package org.zc.vo;

/**
 * Description:
 * Create by @author cpc
 * 2018年5月5日 下午4:13:12
 */
public class CommitQuestionVO {
	private Integer itemId;			//质检项id
	private String title;			//质检项题目
	private String content;			//自定义答案
	private String chooseContent;			//选择答案
	private String type;			//答题类型0为判断题，1为选择题，2为填空题 ，3为多选题
	private Integer operateUserId;			//操作人id
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(Integer operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chooseContent == null) ? 0 : chooseContent.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((operateUserId == null) ? 0 : operateUserId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CommitQuestionVO other = (CommitQuestionVO) obj;
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
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (operateUserId == null) {
			if (other.operateUserId != null)
				return false;
		} else if (!operateUserId.equals(other.operateUserId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CommitQuestionVO [itemId=" + itemId + ", title=" + title + ", content=" + content
				+ ", chooseContent=" + chooseContent + ", type=" + type + ", operateUserId=" + operateUserId + "]";
	}
}
