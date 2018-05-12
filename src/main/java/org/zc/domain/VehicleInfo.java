package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity(name = "zc_vehicle_info")
public class VehicleInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String plate;//车辆牌照
	@Column
	private double weight;//自身重量（单位吨）
	@Column(name = "full_weight")
	private double fullWeight;//满载重量（单位吨）
	@Column(name = "vehicle_type_id")
	private Integer vehicleTypeId; //类型
	@Column(name = "vehicle_model_id")
	private Integer vehicleModelId;//车辆型号
	@Column(name = "is_deleted")
	private boolean isDeleted;//是否删除
	@Column(name = "create_time")
	private Long createTime;
	@Column(name = "update_time")
	private Long updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getFullWeight() {
		return fullWeight;
	}
	public void setFullWeight(double fullWeight) {
		this.fullWeight = fullWeight;
	}
	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}
	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}
	public Integer getVehicleModelId() {
		return vehicleModelId;
	}
	public void setVehicleModelId(Integer vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		VehicleInfo that = (VehicleInfo) o;

		if (Double.compare(that.weight, weight) != 0) return false;
		if (Double.compare(that.fullWeight, fullWeight) != 0) return false;
		if (isDeleted != that.isDeleted) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (plate != null ? !plate.equals(that.plate) : that.plate != null) return false;
		if (vehicleTypeId != null ? !vehicleTypeId.equals(that.vehicleTypeId) : that.vehicleTypeId != null)
			return false;
		if (vehicleModelId != null ? !vehicleModelId.equals(that.vehicleModelId) : that.vehicleModelId != null)
			return false;
		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
		return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = id != null ? id.hashCode() : 0;
		result = 31 * result + (plate != null ? plate.hashCode() : 0);
		temp = Double.doubleToLongBits(weight);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fullWeight);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (vehicleTypeId != null ? vehicleTypeId.hashCode() : 0);
		result = 31 * result + (vehicleModelId != null ? vehicleModelId.hashCode() : 0);
		result = 31 * result + (isDeleted ? 1 : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		return result;
	}
	@Override
	public String toString() {
		return "VehicleInfo{" +
				"id=" + id +
				", plate='" + plate + '\'' +
				", weight=" + weight +
				", fullWeight=" + fullWeight +
				", vehicleTypeId=" + vehicleTypeId +
				", vehicleModelId=" + vehicleModelId +
				", isDeleted=" + isDeleted +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
