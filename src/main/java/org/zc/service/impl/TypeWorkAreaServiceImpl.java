package org.zc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.domain.TypeWorkArea;
import org.zc.dto.TypeWorkAreaJobStationDTO;
import org.zc.repository.TypeWorkAreaRepository;
import org.zc.service.TypeWorkAreaService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * 质检类型对应的车间、工区、产线
 * Created by ceek on 2018-04-23 3:47.
 */
@Service
public class TypeWorkAreaServiceImpl implements TypeWorkAreaService {
    /**
     * 原生sql调用工具
     */
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private TypeWorkAreaRepository typeWorkAreaRepository;
    /**
     * 根据质检类型id查询车间、工区、工位信息
     * @param typeId        质检类型id
     * @return
     */
    public List<TypeWorkArea> findByTypeId(Integer typeId) {
        if(typeId == null) {
            return null;
        }
        return typeWorkAreaRepository.findByTypeId(typeId);
    }


    /**
     * 根据质检类型id查询车间、工区信息
     * @param typeId        质检类型id
     * @return
     */
    public List<TypeWorkArea> findWorkAndAreaByTypeId(Integer typeId) {
        if(typeId == null) {
            return null;
        }
        return typeWorkAreaRepository.findWorkAndAreaByTypeId(typeId);
    }

    /**
     * 根据质检类型id查询车间、工区、职能工位信息
     * @param typeId        质检类型id
     * @return
     */
    public List<TypeWorkAreaJobStationDTO> findWorkShopAreaAndJobStationByTypeId(Integer typeId) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        List<TypeWorkAreaJobStationDTO> resultList = new ArrayList<TypeWorkAreaJobStationDTO>();
        try {
            if(typeId == null) {
                return null;
            }
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" SELECT                                               ");
            sqlBuilder.append(" zcTypeWorkArea.ztwaId AS qualityTypeStationId,       ");
            sqlBuilder.append(" zcTypeWorkArea.workShopId AS workShopId,             ");
            sqlBuilder.append(" zcTypeWorkArea.workAreaId AS workAreaId,             ");
            sqlBuilder.append(" zcTypeWorkArea.typeId,                               ");
            sqlBuilder.append(" zjs.id AS jobStationId,                              ");
            sqlBuilder.append(" zjs.name AS jobStationName,                          ");
            sqlBuilder.append(" zjs.sid AS jobStationSid,                            ");
            sqlBuilder.append(" zjs.pid AS jobStationPid,                            ");
            sqlBuilder.append(" zjs.is_deleted AS jobStationIsDeleted,               ");
            sqlBuilder.append(" zcTypeWorkArea.stationId                             ");
            sqlBuilder.append(" FROM (SELECT                                         ");
            sqlBuilder.append("         ztwa.id AS ztwaId,                           ");
            sqlBuilder.append("         ztwa.workshop_id AS workShopId,              ");
            sqlBuilder.append("         ztwa.work_area_id AS workAreaId,             ");
            sqlBuilder.append("         ztwa.type_id AS typeId,                      ");
            sqlBuilder.append("         ztwa.station_id AS stationId                       ");
            sqlBuilder.append("       FROM zc_type_work_area ztwa                    ");
            sqlBuilder.append("       GROUP BY ztwa.workshop_id) zcTypeWorkArea      ");
            sqlBuilder.append("   RIGHT JOIN zc_job_station zjs                      ");
            sqlBuilder.append("     ON zcTypeWorkArea.workShopId = zjs.workshop_id   ");
            sqlBuilder.append(" WHERE zcTypeWorkArea.typeId = "+typeId+"				");
            sqlBuilder.append(" ORDER BY zcTypeWorkArea.workShopId, zcTypeWorkArea.workAreaId, zjs.id				");

            Query queryDate = manager.createNativeQuery(sqlBuilder.toString());
            List objecArraytList = queryDate.getResultList();
            for(int i = 0; i < objecArraytList.size(); i++) {
                Object[] obj = (Object[]) objecArraytList.get(i);

                TypeWorkAreaJobStationDTO typeWorkAreaJobStationDTO = new TypeWorkAreaJobStationDTO();
                typeWorkAreaJobStationDTO.setQualityTypeStationId(obj[0] != null ? Integer.parseInt(obj[0].toString()) : null);
                typeWorkAreaJobStationDTO.setWorkShopId(obj[1] != null ? Integer.parseInt(obj[1].toString()) : null);
                typeWorkAreaJobStationDTO.setWorkAreaId(obj[2] != null ? Integer.parseInt(obj[2].toString()) : null);
                typeWorkAreaJobStationDTO.setTypeId(obj[3] != null ? Integer.parseInt(obj[3].toString()) : null);
                typeWorkAreaJobStationDTO.setJobStationId(obj[4] != null ? Integer.parseInt(obj[4].toString()) : null);
                typeWorkAreaJobStationDTO.setJobStationName(obj[5] != null ? obj[5].toString() : "");
                typeWorkAreaJobStationDTO.setJobStationSid(obj[6] != null ? Integer.parseInt(obj[6].toString()) : null);
                typeWorkAreaJobStationDTO.setJobStationPid(obj[7] != null ? Integer.parseInt(obj[7].toString()) : null);
                typeWorkAreaJobStationDTO.setJobStationIsDeleted(obj[8] != null ? Boolean.getBoolean(obj[8].toString()) : null);
                typeWorkAreaJobStationDTO.setStationId(obj[9] != null ? Integer.parseInt(obj[9].toString()) : null);

                resultList.add(typeWorkAreaJobStationDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return resultList;
    }

    /**
     * 根据质检类型id和车间id得到工区列表信息
     * @param workshopId        车间id
     * @param typeId            质检类型id
     * @return
     */
    public List<TypeWorkArea> findWorkAreaByTypeIdAndWorkshopId(Integer workshopId, Integer typeId) {
        return typeWorkAreaRepository.findWorkAndAreaByTypeIdAndWorkshopId(workshopId, typeId);
    }
}
