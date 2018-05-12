package org.zc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zc.domain.VehicleType;

/**
 * DAO层 
 * @author 
 * @create 2018-03-06 15:32
 **/
public interface VehicleTypeRepository  extends JpaRepository<VehicleType, Integer> {
	
	
}
