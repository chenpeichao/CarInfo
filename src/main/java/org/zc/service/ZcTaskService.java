package org.zc.service;

import org.zc.domain.ZcTask;
import org.zc.dto.TaskListVehicleDTO;


/**
 * 任务task
 * @author 
 * @create 2018-03-06 15:28
 **/
public interface ZcTaskService {
    /**
     * 保存zcTask对象
     * @param zcTask
     */
    public void saveZcTask(ZcTask zcTask);
    
    /**
     * 通过id获取任务信息
     * @param taskId
     * @return
     */
    public ZcTask findByTaskid(Integer taskId);

    /**
     * 通过id获取车牌号、车辆类型、车辆型号
     * @param taskId
     * @return
     */
    public TaskListVehicleDTO getVehiclePlateModelTypeByTaskid(Integer taskId);

    /**
     * 根据任务id删除指定任务
     * @param taskId    任务id
     */
    public void deleteByTaskId(Integer taskId);
}
