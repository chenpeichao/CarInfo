package org.zc.vo;

/**
 * 任务流转记录VO
 * Created by ceek on 2018-05-05 0:22.
 */
public class TaskExecuteRecordVO {
    private Integer taskRecordId;					        //任务流转记录id
    private Integer taskId;                               //任务id
    private Integer userId;                               //员工id
    private Integer status;                               //任务列表status
    private String mark;                                  //标识记录

    public Integer getTaskRecordId() {
        return taskRecordId;
    }

    public void setTaskRecordId(Integer taskRecordId) {
        this.taskRecordId = taskRecordId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskExecuteRecordVO that = (TaskExecuteRecordVO) o;

        if (taskRecordId != null ? !taskRecordId.equals(that.taskRecordId) : that.taskRecordId != null) return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return mark != null ? mark.equals(that.mark) : that.mark == null;

    }

    @Override
    public int hashCode() {
        int result = taskRecordId != null ? taskRecordId.hashCode() : 0;
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskExecuteRecordVO{" +
                "taskRecordId=" + taskRecordId +
                ", taskId=" + taskId +
                ", userId=" + userId +
                ", status=" + status +
                ", mark='" + mark + '\'' +
                '}';
    }
}
