package org.zc.service;

import java.util.List;

import org.zc.domain.TypeWorkArea;
import org.zc.dto.TypeWorkAreaJobStationDTO;

/**
 * 质检类型对应的车间、工区、产线
 * Created by ceek on 2018-04-23 3:47.
 */
public interface TypeWorkAreaService {
    /**
     * 根据质检类型id查询车间、工区、工位信息
     * @param typeId        质检类型id
     * @return
     */
    public List<TypeWorkArea> findByTypeId(Integer typeId);

    /**
     * 根据质检类型id查询车间、工区信息
     * @param typeId        质检类型id
     * @return
     */
    public List<TypeWorkArea> findWorkAndAreaByTypeId(Integer typeId);

    /**
     * 根据质检类型id查询车间、工区、职能工位信息
     * @param typeId        质检类型id
     * @return
     */
    public List<TypeWorkAreaJobStationDTO> findWorkShopAreaAndJobStationByTypeId(Integer typeId);

    /**
     * 根据质检类型id和车间id得到工区列表信息
     * @param workshopId        车间id
     * @param typeId            质检类型id
     * @return
     */
    public List<TypeWorkArea> findWorkAreaByTypeIdAndWorkshopId(Integer workshopId, Integer typeId);
}
