package org.zc.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 可选车间VO
 * Created by ceek on 2018-04-24 22:10.
 */
public class ChoseWorkshopVO {
	private Integer workshopId;		//车间id
    private String workAreaName;        //工区名称
    private Integer workAreaId;          //工区id
    private List<StationVO> stationList = new ArrayList<StationVO>();   //工区对应工位列表

    public String getWorkAreaName() {
        return workAreaName;
    }

    public void setWorkAreaName(String workAreaName) {
        this.workAreaName = workAreaName;
    }

    public Integer getWorkAreaId() {
        return workAreaId;
    }

    public void setWorkAreaId(Integer workAreaId) {
        this.workAreaId = workAreaId;
    }

    public List<StationVO> getStationList() {
        return stationList;
    }

    public void setStationList(List<StationVO> stationList) {
        this.stationList = stationList;
    }

    public Integer getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(Integer workshopId) {
		this.workshopId = workshopId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stationList == null) ? 0 : stationList.hashCode());
		result = prime * result + ((workAreaId == null) ? 0 : workAreaId.hashCode());
		result = prime * result + ((workAreaName == null) ? 0 : workAreaName.hashCode());
		result = prime * result + ((workshopId == null) ? 0 : workshopId.hashCode());
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
		ChoseWorkshopVO other = (ChoseWorkshopVO) obj;
		if (stationList == null) {
			if (other.stationList != null)
				return false;
		} else if (!stationList.equals(other.stationList))
			return false;
		if (workAreaId == null) {
			if (other.workAreaId != null)
				return false;
		} else if (!workAreaId.equals(other.workAreaId))
			return false;
		if (workAreaName == null) {
			if (other.workAreaName != null)
				return false;
		} else if (!workAreaName.equals(other.workAreaName))
			return false;
		if (workshopId == null) {
			if (other.workshopId != null)
				return false;
		} else if (!workshopId.equals(other.workshopId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChoseWorkshopVO [workshopId=" + workshopId + ", workAreaName=" + workAreaName + ", workAreaId="
				+ workAreaId + ", stationList=" + stationList + "]";
	}
}
