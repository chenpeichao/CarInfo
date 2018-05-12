package org.zc.dto;

/**
 * 任务列表中车辆查询结果信息-车牌号、车辆型号、车辆类型
 * Created by ceek on 2018-04-23 1:49.
 */
public class TaskListVehicleDTO {
    private Integer taskId;
    private String vehiclePlate;    //车牌号
    private String vehicleModel;    //车辆型号
    private String vehicleType;     //车辆类型

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskListVehicleDTO that = (TaskListVehicleDTO) o;

        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (vehiclePlate != null ? !vehiclePlate.equals(that.vehiclePlate) : that.vehiclePlate != null) return false;
        if (vehicleModel != null ? !vehicleModel.equals(that.vehicleModel) : that.vehicleModel != null) return false;
        return vehicleType != null ? vehicleType.equals(that.vehicleType) : that.vehicleType == null;

    }

    @Override
    public int hashCode() {
        int result = taskId != null ? taskId.hashCode() : 0;
        result = 31 * result + (vehiclePlate != null ? vehiclePlate.hashCode() : 0);
        result = 31 * result + (vehicleModel != null ? vehicleModel.hashCode() : 0);
        result = 31 * result + (vehicleType != null ? vehicleType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskListVehicleDTO{" +
                "taskId=" + taskId +
                ", vehiclePlate='" + vehiclePlate + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
