package org.zc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zc.domain.TypeWorkArea;

/**
 * 质检类型对应的车间、工区、产线
 * Created by ceek on 2018-04-23 3:49.
 */
public interface TypeWorkAreaRepository extends JpaRepository<TypeWorkArea, Integer> {
    /**
     * 根据质检类型id查询车间、工区信息
     * @param typeId        质检类型id
     * @return
     */
    @Query(" select typeWorkArea from org.zc.domain.TypeWorkArea typeWorkArea  where typeWorkArea.typeId = :typeId group by typeWorkArea.workAreaId ")
    public List<TypeWorkArea> findWorkAndAreaByTypeId(@Param("typeId")Integer typeId);

    /**
     * 根据质检类型id查询车间、工区、工位信息
     * @param typeId        质检类型id
     * @return
     */
    @Query(" select typeWorkArea from org.zc.domain.TypeWorkArea typeWorkArea  where typeWorkArea.typeId = :typeId ")
    public List<TypeWorkArea> findByTypeId(@Param("typeId")Integer typeId);

    /**
     * 根据质检类型id和车间id得到工区列表信息
     * @param workshopId        车间id
     * @param typeId            质检类型id
     * @return
     */
    @Query(" select typeWorkArea from org.zc.domain.TypeWorkArea typeWorkArea  where typeWorkArea.workshopId = :workshopId and typeWorkArea.typeId = :typeId order by typeWorkArea.workshopId")
    public List<TypeWorkArea> findWorkAndAreaByTypeIdAndWorkshopId(@Param("workshopId")Integer workshopId, @Param("typeId")Integer typeId);
}
