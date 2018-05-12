package org.zc.vo;

/**
 * 车辆信息VO类
 * Created by ceek on 2018-04-17 21:05.
 */
public class VehicleInfoVO {
    private Integer vehicleTypeId; //类型
    private Integer vehicleModelId;//车辆型号
    private double weight;//自身重量（单位吨）
    private double fullWeight;//满载重量（单位吨）
    private String plate;//车辆牌照

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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleInfoVO that = (VehicleInfoVO) o;

        if (Double.compare(that.weight, weight) != 0) return false;
        if (Double.compare(that.fullWeight, fullWeight) != 0) return false;
        if (vehicleTypeId != null ? !vehicleTypeId.equals(that.vehicleTypeId) : that.vehicleTypeId != null)
            return false;
        if (vehicleModelId != null ? !vehicleModelId.equals(that.vehicleModelId) : that.vehicleModelId != null)
            return false;
        return plate != null ? plate.equals(that.plate) : that.plate == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = vehicleTypeId != null ? vehicleTypeId.hashCode() : 0;
        result = 31 * result + (vehicleModelId != null ? vehicleModelId.hashCode() : 0);
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fullWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (plate != null ? plate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VehicleInfoVO{" +
                "vehicleTypeId=" + vehicleTypeId +
                ", vehicleModelId=" + vehicleModelId +
                ", weight=" + weight +
                ", fullWeight=" + fullWeight +
                ", plate='" + plate + '\'' +
                '}';
    }
}
