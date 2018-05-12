package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 质检组和质检项关系表
 * @author xuly
 *
 */

@Entity(name = "zc_quality_inspection_group_item")
public class InspectionGroupItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "group_id")
	private int groupId;//质检组id
	@Column(name = "item_id")
	private int itemId;//质检项ID
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
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + createTime;
		result = prime * result + groupId;
		result = prime * result + id;
		result = prime * result + itemId;
		result = prime * result + updateTime;
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
		InspectionGroupItem other = (InspectionGroupItem) obj;
		if (createTime != other.createTime)
			return false;
		if (groupId != other.groupId)
			return false;
		if (id != other.id)
			return false;
		if (itemId != other.itemId)
			return false;
		if (updateTime != other.updateTime)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InspectionGroupItem [id=" + id + ", groupId=" + groupId + ", itemId=" + itemId + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
}
