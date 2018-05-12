package org.zc.service;

import java.util.List;

import org.zc.domain.ZcTaskExplorer;


/**
 * @author 
 * @create 2018-03-06 15:28
 **/
public interface ZcTaskExplorerService {
	/**
	 * 根据任务id查询整个任务的业务流程
	 * @param task_id
	 * @return
	 */
	 public ZcTaskExplorer findZcTaskExplorerByTaskid(int task_id); 
	 
	 public List<ZcTaskExplorer> findAllZcTaskExplorer();
    
	 //获取总条数
	 public long CountNum();
}
