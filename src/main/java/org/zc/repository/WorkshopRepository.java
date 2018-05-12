package org.zc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zc.domain.InspectionGroup;
import org.zc.domain.WorkShop;

/**
 * 车间
 * Created by ceek on 2018-05-05 0:02.
 */
public interface WorkshopRepository extends JpaRepository<WorkShop, Integer> {
}
