package org.zc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户主界面上显示的列表 用于流程控制
 * @author Administrator
 *
 */

@Entity(name = "zc_task_explorer")
public class ZcTaskExplorer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "vehicle_info_id")
	private int vehicleInfoId;//车辆信息ID 数据在 zc_vehicle_info表中
	@Column(name = "task_id")
	private int taskId;//任务ID  在zc_task表中
	@Column
	private String process;//整个质检流程 
	@Column(name = "createTime")
	private long create_time; 
	@Column(name = "updateTime")
	private long update_time;
	@Column(name = "is_deleted")
	private boolean isDeleted;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVehicleInfoId() {
		return vehicleInfoId;
	}
	public void setVehicleInfoId(int vehicleInfoId) {
		this.vehicleInfoId = vehicleInfoId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (create_time ^ (create_time >>> 32));
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + ((process == null) ? 0 : process.hashCode());
		result = prime * result + taskId;
		result = prime * result + (int) (update_time ^ (update_time >>> 32));
		result = prime * result + vehicleInfoId;
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
		ZcTaskExplorer other = (ZcTaskExplorer) obj;
		if (create_time != other.create_time)
			return false;
		if (id != other.id)
			return false;
		if (isDeleted != other.isDeleted)
			return false;
		if (process == null) {
			if (other.process != null)
				return false;
		} else if (!process.equals(other.process))
			return false;
		if (taskId != other.taskId)
			return false;
		if (update_time != other.update_time)
			return false;
		if (vehicleInfoId != other.vehicleInfoId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ZcTaskExplorer [id=" + id + ", vehicleInfoId=" + vehicleInfoId + ", taskId=" + taskId + ", process="
				+ process + ", create_time=" + create_time + ", update_time=" + update_time + ", isDeleted=" + isDeleted
				+ "]";
	}
}
