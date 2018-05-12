package org.zc.dto;

/**
 * 质检类型对应的职能工位、工区、车间信息
 * Created by ceek on 2018-04-24 0:15.
 */
public class TypeWorkAreaJobStationDTO {
    private Integer qualityTypeStationId;   //质检种类所拥有的产线id（zc_type_work_area）
    private Integer workShopId;              //车间id
    private Integer workAreaId;             //工区id
    private Integer typeId;                 //质检类型id
    private Integer stationId;                 //物理工位id
    private Integer jobStationId;           //职能工位id
    private String jobStationName;          //职能工位名称
    private Integer jobStationSid;          //下一职能工位id
    private Integer jobStationPid;          //上一职能工位id
    private Boolean jobStationIsDeleted;   //职能工位是否失效

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getQualityTypeStationId() {
        return qualityTypeStationId;
    }

    public void setQualityTypeStationId(Integer qualityTypeStationId) {
        this.qualityTypeStationId = qualityTypeStationId;
    }

    public Integer getWorkShopId() {
        return workShopId;
    }

    public void setWorkShopId(Integer workShopId) {
        this.workShopId = workShopId;
    }

    public Integer getWorkAreaId() {
        return workAreaId;
    }

    public void setWorkAreaId(Integer workAreaId) {
        this.workAreaId = workAreaId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getJobStationId() {
        return jobStationId;
    }

    public void setJobStationId(Integer jobStationId) {
        this.jobStationId = jobStationId;
    }

    public String getJobStationName() {
        return jobStationName;
    }

    public void setJobStationName(String jobStationName) {
        this.jobStationName = jobStationName;
    }

    public Integer getJobStationSid() {
        return jobStationSid;
    }

    public void setJobStationSid(Integer jobStationSid) {
        this.jobStationSid = jobStationSid;
    }

    public Integer getJobStationPid() {
        return jobStationPid;
    }

    public void setJobStationPid(Integer jobStationPid) {
        this.jobStationPid = jobStationPid;
    }

    public Boolean getJobStationIsDeleted() {
        return jobStationIsDeleted;
    }

    public void setJobStationIsDeleted(Boolean jobStationIsDeleted) {
        this.jobStationIsDeleted = jobStationIsDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeWorkAreaJobStationDTO that = (TypeWorkAreaJobStationDTO) o;

        if (qualityTypeStationId != null ? !qualityTypeStationId.equals(that.qualityTypeStationId) : that.qualityTypeStationId != null)
            return false;
        if (workShopId != null ? !workShopId.equals(that.workShopId) : that.workShopId != null) return false;
        if (workAreaId != null ? !workAreaId.equals(that.workAreaId) : that.workAreaId != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (jobStationId != null ? !jobStationId.equals(that.jobStationId) : that.jobStationId != null) return false;
        if (jobStationName != null ? !jobStationName.equals(that.jobStationName) : that.jobStationName != null)
            return false;
        if (jobStationSid != null ? !jobStationSid.equals(that.jobStationSid) : that.jobStationSid != null)
            return false;
        if (jobStationPid != null ? !jobStationPid.equals(that.jobStationPid) : that.jobStationPid != null)
            return false;
        return jobStationIsDeleted != null ? jobStationIsDeleted.equals(that.jobStationIsDeleted) : that.jobStationIsDeleted == null;

    }

    @Override
    public int hashCode() {
        int result = qualityTypeStationId != null ? qualityTypeStationId.hashCode() : 0;
        result = 31 * result + (workShopId != null ? workShopId.hashCode() : 0);
        result = 31 * result + (workAreaId != null ? workAreaId.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (jobStationId != null ? jobStationId.hashCode() : 0);
        result = 31 * result + (jobStationName != null ? jobStationName.hashCode() : 0);
        result = 31 * result + (jobStationSid != null ? jobStationSid.hashCode() : 0);
        result = 31 * result + (jobStationPid != null ? jobStationPid.hashCode() : 0);
        result = 31 * result + (jobStationIsDeleted != null ? jobStationIsDeleted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TypeWorkAreaJobStationDTO{" +
                "qualityTypeStationId=" + qualityTypeStationId +
                ", workShopId=" + workShopId +
                ", workAreaId=" + workAreaId +
                ", typeId=" + typeId +
                ", stationId=" + stationId +
                ", jobStationId=" + jobStationId +
                ", jobStationName='" + jobStationName + '\'' +
                ", jobStationSid=" + jobStationSid +
                ", jobStationPid=" + jobStationPid +
                ", jobStationIsDeleted=" + jobStationIsDeleted +
                '}';
    }
}
