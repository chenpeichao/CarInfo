package org.zc.service;

import org.zc.domain.WorkShop;

import java.util.Map;

/**
 * 车间
 * Created by ceek on 2018-04-24 21:54.
 */
public interface WorkshopService {
    /**
     * 根据流转记录id获取可以选择的工区以及对应的工位列表
     * @param taskRecordId
     * @return
     */
    public Map<String, Object> workAreaChoseList(Integer taskRecordId);
    
    /**
     * 流转记录中保存车间对应多个工区，选择指定工区
     * @param taskRecordId		任务流转记录id
     * @param taskRecordId		工区id
     * @return
     */
    public Map<String, Object> commitWorkAreaChoseByTaskRecordIdAndWorkAreaId(Integer taskRecordId, Integer workAreaId);

    /**
     * 根据车间id查询车间信息
     * @param workshopId    车间id
     * @return
     */
    public WorkShop findWorkshopById(Integer workshopId);
}
