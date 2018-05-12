package org.zc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zc.domain.ZcTaskExplorer;

/**
 * DAO层 
 * @author 
 * @create 2018-03-06 15:32
 **/
public interface ZcTaskExplorerRepository  extends JpaRepository<ZcTaskExplorer, Integer> {
	
	
	  @Query("select o from org.zc.domain.ZcTaskExplorer o where o.taskId = ?1") 
	  public ZcTaskExplorer findZcTaskExplorerByTaskid(int task_id); //根据taskid获取质检流程
	
}
