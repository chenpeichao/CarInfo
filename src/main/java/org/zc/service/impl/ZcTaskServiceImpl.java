package org.zc.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.ZcTask;
import org.zc.dto.TaskListVehicleDTO;
import org.zc.repository.ZcTaskRepository;
import org.zc.service.ZcTaskService;

/**
 * 任务task
 *
 * @author xly
 * @create 2018-03-06 15:28
 **/
@Service
public class ZcTaskServiceImpl implements ZcTaskService {
	@Autowired
	private ZcTaskRepository zcTaskRepository;
	/**
	 * 原生sql调用工具
	 */
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	/**
     * 保存zcTask对象
     * @param zcTask
     */
	@Override
	public void saveZcTask(ZcTask zcTask) {
		zcTaskRepository.saveAndFlush(zcTask);
	}

	/**
     * 通过id获取任务信息
     * @param taskId		任务id
     * @return
     */
    public ZcTask findByTaskid(Integer taskId) {
    	return zcTaskRepository.findOne(taskId);
    }

	/**
	 * 通过id获取车牌号、车辆类型、车辆型号
	 * @param taskId
	 * @return
	 */
	public TaskListVehicleDTO getVehiclePlateModelTypeByTaskid(Integer taskId) {
		EntityManager manager = entityManagerFactory.createEntityManager();
		TaskListVehicleDTO taskListVehicleDTO = new TaskListVehicleDTO();

		try {
			if(taskId == null) {
				return null;
			}
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT                                   ");
			sqlBuilder.append("  zt.id,                                 ");
			sqlBuilder.append("  zvi.plate,                             ");
			sqlBuilder.append("  zvm.name AS vehicleModelName,          ");
			sqlBuilder.append("  zvt.name AS vehicleTypeName            ");
			sqlBuilder.append("FROM zc_task zt                          ");
			sqlBuilder.append("  INNER JOIN zc_vehicle_info zvi         ");
			sqlBuilder.append("    ON zt.vehicle_id = zvi.id               ");
			sqlBuilder.append("  INNER JOIN zc_vehicle_model zvm        ");
			sqlBuilder.append("    ON zvi.vehicle_model_id = zvm.id     ");
			sqlBuilder.append("  INNER JOIN zc_vehicle_type zvt         ");
			sqlBuilder.append("    ON zvi.vehicle_type_id = zvt.id      ");
			sqlBuilder.append("WHERE zt.id = "+taskId+"					");

			Query queryDate = manager.createNativeQuery(sqlBuilder.toString());
			List objecArraytList = queryDate.getResultList();
			if(objecArraytList != null && objecArraytList.size() > 0) {
				Object[] obj = (Object[]) objecArraytList.get(0);
				if(obj[0] == null) {
					return null;        //有数据情况下，任务id不可能为null
				}
				taskListVehicleDTO.setTaskId(Integer.parseInt(obj[0].toString()));
				taskListVehicleDTO.setVehiclePlate(obj[1] != null ? obj[1].toString() : "");
				taskListVehicleDTO.setVehicleModel(obj[2] != null ? obj[2].toString() : "");
				taskListVehicleDTO.setVehicleType(obj[3] != null ? obj[3].toString() : "");
			}
			return taskListVehicleDTO;
		} finally {
			manager.close();
		}
	}

	/**
	 * 根据任务id删除指定任务
	 * @param taskId    任务id
	 */
	public void deleteByTaskId(Integer taskId) {
		zcTaskRepository.delete(taskId);
	}
}
