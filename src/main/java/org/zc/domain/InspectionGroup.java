package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 质检组
 * @author xuly
 *
 */

@Entity(name = "zc_quality_inspection_group")
public class InspectionGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;//质检组名称
	@Column(name = "type_id")
	private int typeId;//质检类别ID
	@Column(name = "is_deleted")
	private boolean isDeleted;//是否删除
	@Column(name = "create_time")
	private int createTime;
	@Column(name = "update_time")
	private int updateTime;
	@Column(name = "item_type")
	private int itemType;//质检项类型 1入厂鉴定，2工位质检，3整车质检
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
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + createTime;
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + itemType;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + typeId;
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
		InspectionGroup other = (InspectionGroup) obj;
		if (createTime != other.createTime)
			return false;
		if (id != other.id)
			return false;
		if (isDeleted != other.isDeleted)
			return false;
		if (itemType != other.itemType)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (typeId != other.typeId)
			return false;
		if (updateTime != other.updateTime)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InspectionGroup [id=" + id + ", name=" + name + ", typeId=" + typeId + ", isDeleted=" + isDeleted
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", itemType=" + itemType + "]";
	}
}
