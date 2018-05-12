package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 车辆小类型表
 * @author xuly
 *
 */

@Entity(name = "zc_vehicle_model")
public class VehicleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;//车辆牌照
	@Column(name = "type_id")
	private int typeId;//所需质检类别
	@Column(name = "vehicle_type_id")
	private int vehicleTypeId; //所属车辆类别 此字段等于 VehicleType的id
	@Column(name = "is_deleted")
	private boolean isDeleted;//是否删除
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
	public int getVehicleTypeId() {
		return vehicleTypeId;
	}
	public void setVehicleTypeId(int vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + createTime;
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + typeId;
		result = prime * result + updateTime;
		result = prime * result + vehicleTypeId;
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
		VehicleModel other = (VehicleModel) obj;
		if (createTime != other.createTime)
			return false;
		if (id != other.id)
			return false;
		if (isDeleted != other.isDeleted)
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
		if (vehicleTypeId != other.vehicleTypeId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "VehicleModel [id=" + id + ", name=" + name + ", typeId=" + typeId + ", vehicleTypeId=" + vehicleTypeId
				+ ", isDeleted=" + isDeleted + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}
