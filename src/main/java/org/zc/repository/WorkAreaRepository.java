package org.zc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zc.domain.WorkArea;

/**
 * 工区
 * Created by ceek on 2018-04-24 22:49.
 */
public interface WorkAreaRepository extends JpaRepository<WorkArea, Integer> {
}
