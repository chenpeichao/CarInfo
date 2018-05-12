package org.zc.domain;

import javax.persistence.*;

/**
 * 质检种类所拥有的产线
 * @author xuly
 *
 */
@Entity(name = "zc_type_work_area")
public class TypeWorkArea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "workshop_id")
	private Integer workshopId;//车间ID
	@Column(name = "work_area_id")
	private Integer workAreaId;//物理产线id
	@Column(name = "station_id")
	private Integer stationId;//工位id
	@Column(name = "type_id")
	private Integer typeId;//质检类别id
	@Column(name = "is_standard")
	private Integer isStandard;//是否为标准产线(暂时废弃)
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
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getIsStandard() {
		return isStandard;
	}
	public void setIsStandard(Integer isStandard) {
		this.isStandard = isStandard;
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
	public String toString() {
		return "TypeWorkArea [id=" + id + ", workshopId=" + workshopId + ", workAreaId=" + workAreaId + ", stationId="
				+ stationId + ", typeId=" + typeId + ", isStandard=" + isStandard + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (workshopId != null ? workshopId.hashCode() : 0);
		result = 31 * result + (workAreaId != null ? workAreaId.hashCode() : 0);
		result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
		result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
		result = 31 * result + (isStandard != null ? isStandard.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
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
		TypeWorkArea other = (TypeWorkArea) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id != other.id)
			return false;
		if (isStandard != other.isStandard)
			return false;
		if (stationId != other.stationId)
			return false;
		if (typeId != other.typeId)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (workAreaId != other.workAreaId)
			return false;
		if (workshopId != other.workshopId)
			return false;
		return true;
	}
	
	
	
}