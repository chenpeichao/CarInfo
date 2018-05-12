package org.zc.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:问题
 * Create by @author cpc
 * 2018年4月19日 上午8:38:12
 */
public class QualityInspectionItemVO {
	private Integer id;
	private String title;		//问题描述
	private Integer type;		//0为判断题，1为选择题，2为填空题
	private List<String> itemList = new ArrayList<String>();	//标准答案列表
	
	public QualityInspectionItemVO() {
		super();
	}
	public QualityInspectionItemVO(Integer id, String title, Integer type) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<String> getItemList() {
		return itemList;
	}
	public void setItemList(List<String> itemList) {
		this.itemList = itemList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemList == null) ? 0 : itemList.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		QualityInspectionItemVO other = (QualityInspectionItemVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemList == null) {
			if (other.itemList != null)
				return false;
		} else if (!itemList.equals(other.itemList))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "QualityInspectionItemVO [id=" + id + ", title=" + title + ", type=" + type + ", itemList=" + itemList + "]";
	}
}
