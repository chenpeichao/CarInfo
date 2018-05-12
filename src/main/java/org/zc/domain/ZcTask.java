package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity(name = "zc_task")
public class ZcTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "vehicle_id")
	private Integer vehicleId;//车辆id
	@Column(name = "type_id")
	private Integer typeId; //检查种类
	@Column(name = "vehicle_weight")
	private Double vehicleWeight; //车辆重量(单位：吨)
	@Column(name = "vehicle_full_weight")
	private Double vehicleFullWeight; //车辆满载重量(单位：吨)
	@Column(name = "create_time")
	private Long createTime;
	@Column(name = "update_time")
	private Long updateTime;
	@Column(name = "vehicle_model_id")
	private Integer vehicleModelId;		//车辆型号id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Double getVehicleWeight() {
		return vehicleWeight;
	}
	public void setVehicleWeight(Double vehicleWeight) {
		this.vehicleWeight = vehicleWeight;
	}
	public Double getVehicleFullWeight() {
		return vehicleFullWeight;
	}
	public void setVehicleFullWeight(Double vehicleFullWeight) {
		this.vehicleFullWeight = vehicleFullWeight;
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
	public Integer getVehicleModelId() {
		return vehicleModelId;
	}
	public void setVehicleModelId(Integer vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((vehicleFullWeight == null) ? 0 : vehicleFullWeight.hashCode());
		result = prime * result + ((vehicleId == null) ? 0 : vehicleId.hashCode());
		result = prime * result + ((vehicleModelId == null) ? 0 : vehicleModelId.hashCode());
		result = prime * result + ((vehicleWeight == null) ? 0 : vehicleWeight.hashCode());
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
		ZcTask other = (ZcTask) obj;
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
		if (vehicleFullWeight == null) {
			if (other.vehicleFullWeight != null)
				return false;
		} else if (!vehicleFullWeight.equals(other.vehicleFullWeight))
			return false;
		if (vehicleId == null) {
			if (other.vehicleId != null)
				return false;
		} else if (!vehicleId.equals(other.vehicleId))
			return false;
		if (vehicleModelId == null) {
			if (other.vehicleModelId != null)
				return false;
		} else if (!vehicleModelId.equals(other.vehicleModelId))
			return false;
		if (vehicleWeight == null) {
			if (other.vehicleWeight != null)
				return false;
		} else if (!vehicleWeight.equals(other.vehicleWeight))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ZcTask [id=" + id + ", vehicleId=" + vehicleId + ", typeId=" + typeId + ", vehicleWeight="
				+ vehicleWeight + ", vehicleFullWeight=" + vehicleFullWeight + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", vehicleModelId=" + vehicleModelId + "]";
	}
}
