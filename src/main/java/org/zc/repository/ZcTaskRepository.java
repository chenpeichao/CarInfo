package org.zc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zc.domain.ZcTask;

/**
 * DAO层 
 * @author 
 * @create 2018-03-06 15:32
 **/
public interface ZcTaskRepository  extends JpaRepository<ZcTask, Integer> {
	
	
	  @Query("select typeId from org.zc.domain.ZcTask o where o.id = ?1") 
	  public int findTypeIdByTaskid(int taskId); //根据taskid查找typeid
	  
	  
	
}
