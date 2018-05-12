package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 质检项
 *
 * @author xuly
 */

@Entity(name = "zc_quality_inspection_item")
public class InspectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int pid;//一个问题分为俩个问题（暂时未用）
    @Column
    private String title;//问题描述
    @Column
    public String standard;//参考标准可以为json
    @Column
    private int type;//0为判断题，1为选择题，2为填空题 3为自定义问题
    @Column(name = "is_deleted")
    private Integer isDeleted;//是否删除
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + createTime;
		result = prime * result + id;
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + pid;
		result = prime * result + ((standard == null) ? 0 : standard.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + type;
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
		InspectionItem other = (InspectionItem) obj;
		if (createTime != other.createTime)
			return false;
		if (id != other.id)
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (pid != other.pid)
			return false;
		if (standard == null) {
			if (other.standard != null)
				return false;
		} else if (!standard.equals(other.standard))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		if (updateTime != other.updateTime)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InspectionItem [id=" + id + ", pid=" + pid + ", title=" + title + ", standard=" + standard + ", type="
				+ type + ", isDeleted=" + isDeleted + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}
