package org.zc.vo;

/**
 * 任务项VO
 * Created by ceek on 2018-04-23 1:29.
 */
public class TaskVO {
    private Integer taskRecordId;         //任务流转记录id
    private String vehiclePlate;    //车牌号
    private String vehicleModel;    //车辆型号
    private String vehicleType;     //车辆类 型
    private String msg;             //任务步骤msg：您现在需要进行入厂鉴定(针对入厂鉴定员)
    private Integer status;         //执行任务标识0：无需操作、1：入厂鉴定。。。

    public TaskVO() {
    }

    public TaskVO(Integer taskRecordId, String vehiclePlate, String vehicleModel, String vehicleType) {
        this.taskRecordId = taskRecordId;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
    }

    public Integer getTaskRecordId() {
        return taskRecordId;
    }

    public void setTaskRecordId(Integer taskRecordId) {
        this.taskRecordId = taskRecordId;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskVO taskVO = (TaskVO) o;

        if (taskRecordId != null ? !taskRecordId.equals(taskVO.taskRecordId) : taskVO.taskRecordId != null) return false;
        if (vehiclePlate != null ? !vehiclePlate.equals(taskVO.vehiclePlate) : taskVO.vehiclePlate != null)
            return false;
        if (vehicleModel != null ? !vehicleModel.equals(taskVO.vehicleModel) : taskVO.vehicleModel != null)
            return false;
        if (vehicleType != null ? !vehicleType.equals(taskVO.vehicleType) : taskVO.vehicleType != null) return false;
        if (msg != null ? !msg.equals(taskVO.msg) : taskVO.msg != null) return false;
        return status != null ? status.equals(taskVO.status) : taskVO.status == null;

    }

    @Override
    public int hashCode() {
        int result = taskRecordId != null ? taskRecordId.hashCode() : 0;
        result = 31 * result + (vehiclePlate != null ? vehiclePlate.hashCode() : 0);
        result = 31 * result + (vehicleModel != null ? vehicleModel.hashCode() : 0);
        result = 31 * result + (vehicleType != null ? vehicleType.hashCode() : 0);
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskVO{" +
                "taskId=" + taskRecordId +
                ", vehiclePlate='" + vehiclePlate + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
