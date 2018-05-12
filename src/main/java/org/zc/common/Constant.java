package org.zc.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类
 * Created by ceek on 2018-04-19 0:49.
 */
@Component
public class Constant {
    //入厂鉴定itemtype类型标识在zc_quality_inspection_group
    public static Integer TO_ENTRANCE_CHECKUP_ITEM_TYPE;
    //整车质检itemtype类型标识在zc_quality_inspection_group
    public static Integer ALL_CAR_ITEM_TYPE;
    //调度员质检类型为-1
    public static Integer DISPATCH_QUALITY_INSPECTION_TYPE;
    //自检processId类型 在zc_process
    public static Integer SELF_INSPECTION_PROCESS_ID;
    //互检processId类型 在zc_process
    public static Integer EACH_INSPECTION_PROCESS_ID;
    //专检员processId类型 在zc_process
    public static Integer SPECIAL_INSPECTION_PROCESS_ID;
    //监造员processId类型 在zc_process
    public static Integer BUILD_INSPECTION_PROCESS_ID;
    //调度员所对应的质检类型为-1，因其不参与质检
    public static Integer DISPATCH_TYPE_ID;

    //分页信息封装
    //默认显示第一页
    public static Integer DEFAULT_PAGE_NUMBER;
    //默认每页显示10条记录
    public static Integer DEFAULT_PAGE_SIZE;
    
    
    
    //权限列表：
    //调度员
    public static Integer DISPATCH_ROLE_ID;
    //入场鉴定
    public static Integer ENTRANCE_CHECKUP_ROLE_ID;
    //员工长
    public static Integer STAFF_ZHANG_ROLE_ID;
    //员工
    public static Integer STAFF_ROLE_ID;
	//专检
    public static Integer SPECIAL_INSPECTION_ROLE_ID;
    //整车质检
    public static Integer ALL_CAR_ROLE_ID;
    //监造
    public static Integer BUILD_INSPECTION_ROLE_ID;
    //入场鉴定企业代表
    public static Integer ENTRANCE_CHECKUP_COMPANY_ROLE_ID;
    //入场鉴定监造代表
    public static Integer ENTRANCE_CHECKUP_INSPECTION_ROLE_ID;
	//整车质检专检
	public static Integer ALL_CAR_SPECIAL_INSPECTION_ROLE_ID;
	//整车质检监造
	public static Integer ALL_CAR_BUILD_INSPECTION_ROLE_ID;

    
    @Value("${to_entrance_checkup_item_type}")
	public void setToEntranceCheckupItemType(Integer toEntranceCheckupItemType) {
		TO_ENTRANCE_CHECKUP_ITEM_TYPE = toEntranceCheckupItemType;
	}

    @Value("${all_car_item_type}")
	public void setAllCarItemType(Integer allCarItemType) {
		ALL_CAR_ITEM_TYPE = allCarItemType;
	}
    @Value("${dispatch_quality_inspection_type}")
    public void setDispatchQualityInspectionType(Integer dispatchQualityInspectionType) {
    	DISPATCH_QUALITY_INSPECTION_TYPE = dispatchQualityInspectionType;
    }
    @Value("${self_inspection_process_id}")
    public void setSelfInspectionProcessId(Integer selfInspectionProcessId) {
        SELF_INSPECTION_PROCESS_ID = selfInspectionProcessId;
    }
    @Value("${each_inspection_process_id}")
    public void setEachInspectionProcessId(Integer eachInspectionProcessId) {
        EACH_INSPECTION_PROCESS_ID = eachInspectionProcessId;
    }
    @Value("${special_inspection_process_id}")
    public void setSpecialInspectionProcessId(Integer specialInspectionProcessId) {
    	SPECIAL_INSPECTION_PROCESS_ID = specialInspectionProcessId;
    }
    @Value("${build_inspection_process_id}")
    public void setBuildInspectionProcessId(Integer buildInspectionProcessId) {
    	BUILD_INSPECTION_PROCESS_ID = buildInspectionProcessId;
    }
    @Value("${dispatch_role_id}")
	public void setDispatchRoleId(Integer dispatchRoleId) {
		DISPATCH_ROLE_ID = dispatchRoleId;
	}
    @Value("${entrance_checkup_role_id}")
	public void setEntranceCheckupRoleId(Integer entranceCheckupRoleId) {
		ENTRANCE_CHECKUP_ROLE_ID = entranceCheckupRoleId;
	}
    @Value("${staff_zhang_role_id}")
	public void setStaffZhangRoleId(Integer staffZhangRoleId) {
		STAFF_ZHANG_ROLE_ID = staffZhangRoleId;
	}
    @Value("${staff_role_id}")
    public void setStaffRoleId(Integer staffRoleId) {
		STAFF_ROLE_ID = staffRoleId;
	}
    @Value("${special_inspection_role_id}")
	public void setSpecialInspectionRoleId(Integer specialInspectionRoleId) {
		SPECIAL_INSPECTION_ROLE_ID = specialInspectionRoleId;
	}
    @Value("${all_car_role_id}")
	public void setAllCarRoleId(Integer allCarRoleId) {
		ALL_CAR_ROLE_ID = allCarRoleId;
	}
    @Value("${build_inspection_role_id}")
	public void setBuildInspectionRoleId(Integer buildInspectionRoleId) {
		BUILD_INSPECTION_ROLE_ID = buildInspectionRoleId;
	}
    @Value("${dispatch_type_id}")
	public void setDispatchTypeId(Integer dispatchTypeId) {
		DISPATCH_TYPE_ID = dispatchTypeId;
	}
    @Value("${entrance_checkup_company_role_id}")
	public void setEntranceCheckupCompanyRoleId(Integer entranceCheckupCompanyRoleId) {
		ENTRANCE_CHECKUP_COMPANY_ROLE_ID = entranceCheckupCompanyRoleId;
	}
    @Value("${entrance_checkup_inspection_role_id}")
	public void setEntranceCheckupInspectionRoleId(Integer entranceCheckupInspectionRoleId) {
		ENTRANCE_CHECKUP_INSPECTION_ROLE_ID = entranceCheckupInspectionRoleId;
	}
    @Value("${all_car_special_inspection_role_id}")
	public void setAllCarSpecialInspectionRoleId(Integer allCarSpecialInspectionRoleId) {
		ALL_CAR_SPECIAL_INSPECTION_ROLE_ID = allCarSpecialInspectionRoleId;
	}
    @Value("${all_car_build_inspection_role_id}")
	public void setAllCarBuildInspectionRoleId(Integer allCarBuildInspectionRoleId) {
		ALL_CAR_BUILD_INSPECTION_ROLE_ID = allCarBuildInspectionRoleId;
	}
    @Value("${default_page_number}")
	public void setDefaultPageNumber(Integer defaultPageNumber) {
		DEFAULT_PAGE_NUMBER = defaultPageNumber;
	}
    @Value("${default_page_size}")
	public void setDefaultPageSize(Integer defaultPageSize) {
		DEFAULT_PAGE_SIZE = defaultPageSize;
	}
    
}
