package org.zc.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zc.common.Constant;
import org.zc.common.cache.CacheCurrentUserThreadLocal;
import org.zc.domain.JobStation;
import org.zc.domain.User;
import org.zc.dto.UserLoginInfoDTO;
import org.zc.repository.UserRepository;
import org.zc.service.UserService;
import org.zc.vo.LoginUserVO;
import org.zc.vo.StaffVO;

/**
 * Description:用户service实现类
 * Create by @author cpc
 * 2018年4月22日 下午1:23:48
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 原生sql调用工具
     */
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private UserRepository userRepository;
    
    
    /**
     * 查询登录用户的登录信息--用户/权限/用户组
     * @param userId    用户id
     * @return
     */
    public UserLoginInfoDTO getUserLoginInfoDTO(String userId) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        UserLoginInfoDTO userLoginInfoDTO = new UserLoginInfoDTO();
        try {
            if(StringUtils.isBlank(userId)) {
                return null;
            }
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT								");
            sqlBuilder.append("  zu.id       	AS userId,          ");
            sqlBuilder.append("  zu.name	AS NAME,                ");
            sqlBuilder.append("  zu.username	AS userName,        ");
            sqlBuilder.append("  zr.id         AS roleId,           ");
            sqlBuilder.append("  zr.name       AS roleName,         ");
            sqlBuilder.append("  zu.group_id   AS userGroupId,      ");
            sqlBuilder.append("  zug.name      AS groupName,         ");
            sqlBuilder.append("  zu.workshop_id      AS workshopId         ");
            sqlBuilder.append("FROM zc_user zu                      ");
            sqlBuilder.append("  LEFT JOIN zc_user_group zug       ");
            sqlBuilder.append("    ON zu.group_id = zug.id          ");
            sqlBuilder.append("  INNER JOIN zc_user_role zur        ");
            sqlBuilder.append("    ON zu.id = zur.user_id           ");
            sqlBuilder.append("  INNER JOIN zc_role zr              ");
            sqlBuilder.append("    ON zur.role_id = zr.id           ");
            sqlBuilder.append("WHERE zu.is_admin = 0                ");     //只查询前台操作用户
            sqlBuilder.append("    AND zu.id = "+userId+"                   ");

            Query queryDate = manager.createNativeQuery(sqlBuilder.toString());
            List objecArraytList = queryDate.getResultList();
            for (int i = 0; i < objecArraytList.size(); i++) {
                Object[] obj = (Object[]) objecArraytList.get(i);
                //用户基本信息和组信息只有一份
                if(i == 0) {
                    if(obj[0] == null) {
                        return null;        //有数据情况下，用户id不可能为null
                    }
                    userLoginInfoDTO.setUserId(Integer.parseInt(obj[0].toString()));
                    userLoginInfoDTO.setName(obj[1] != null ? obj[1].toString() : "");
                    userLoginInfoDTO.setUserName(obj[2] != null ? obj[2].toString() : "");
                    userLoginInfoDTO.setUserGroupId(obj[5] != null ? Integer.parseInt(obj[5].toString()) : null);
                    userLoginInfoDTO.setUserGroupName(obj[6] != null ? obj[6].toString() : null);
                    userLoginInfoDTO.setWorkShopId(obj[7] != null ? Integer.parseInt(obj[7].toString()) : 0);
                }
                //用户的权限和权限名可能不为空
                if(obj[3] != null) {
                    userLoginInfoDTO.getRoleMap().put(Integer.parseInt(obj[3].toString()), obj[4].toString());
                }
            }
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
            manager.close();
        }

        return userLoginInfoDTO;
    }
    
    /**
	 * 根据用户组id获取用户对应职能工位信息
	 * @return
	 */
	public List<JobStation> getJobStationByUserGroupId(Integer userGroupId) {
		return userRepository.getJobStationByUserGroupId(userGroupId);
	}
	
	/**
	 * 根据用户id查询用户信息
	 * @param userId		用户id
	 * @return
	 */
	public User findOneByUserId(Integer userId) {
		return userRepository.findOne(userId);
	}
	
	/**
	 * 根据任务流转id获取工位普通员工列表
	 * @param taskRecordId	任务流转id
	 * @return
	 */
	public Map<String, Object> getStaffListInStationByTaskRecordId(Integer taskRecordId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//1、查询任务流转记录详细信息-得到职能工位以及其对应的质检类型
//		TaskExecuteRecord taskExecuteRecord = taskExecuteRecordService.findOneById(taskRecordId);
//		if(taskExecuteRecord == null) {
//			resultMap.put("code", 0);
//			resultMap.put("msg", "未查找到任务流转记录信息!");
//			return resultMap;
//		}
		
		//2、根据流转记录中的职能工位、质检类型、车间、工区确定唯一的物理工位---得到物理工位对应的员工列表
//		List<User> userList = userRepository.getStaffListByJobStationIdAndTypeId(taskExecuteRecord.getJobStationId(), taskExecuteRecord.getTypeId(), taskExecuteRecord.getWorkshopId(), taskExecuteRecord.getWorkAreaId());

//		//2.1、得到用户id列表
//		List<Integer> userIdList = new ArrayList<Integer>();
//		for(User user : userList) {
//			userIdList.add(user.getId());
//		}
//		//3、得到需要过滤的员工权限列表--员工长
//		List<Integer> roleIdList = new ArrayList<Integer>();
//		roleIdList.add(Constant.STAFF_ZHANG_ROLE_ID);
//
//		//4、根据员工id列表信息查询具有指定权限的员工信息
//		List<User> staffZhangByUserIdList = userRepository.findStaffZhangByUserIdList(userIdList, roleIdList);
//
//		//5、第2步中查询出来的所有员工信息去掉有员工长权限的用户--得到普通员工的列表
//		userList.removeAll(staffZhangByUserIdList);

		List<User> userList = userRepository.getStaffListByStaffZhangGroupId(CacheCurrentUserThreadLocal.getCurrentUser().getUserGroupId());
		//6、界面返回信息进行VO封装
		List<StaffVO> staffVOList = new ArrayList<StaffVO>();
		for(User user : userList) {
			StaffVO staffVO = new StaffVO();
			staffVO.setUserId(user.getId());
			staffVO.setName(user.getName());
			staffVO.setUsername(user.getUsername());
			staffVOList.add(staffVO);
		}
		
		resultMap.put("code", 1);
		resultMap.put("datas", staffVOList);
		return resultMap;
	}
	
	/**
	 * 用户登录接口
	 * @param username		登录用户名
	 * @param password		密码
	 * @return
	 */
	public Map<String, Object> userLogin(String username, String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String md5Password = getMD5(password);
		
		if(StringUtils.isBlank(md5Password)) {
			resultMap.put("msg", "密码错误，请联系管理员！");
            resultMap.put("code", 0);
            return resultMap;
		}
		
		List<User> userByUsername = userRepository.getUserByUsername(username);
		if(userByUsername == null || userByUsername.size() == 0) {
			resultMap.put("msg", "用户名不存在！");
	        resultMap.put("code", -102);
	        return resultMap;
		}
		
		List<User> loginUserList = userRepository.userLogin(username, md5Password);
		
		if(loginUserList == null || loginUserList.size() == 0) {
			resultMap.put("msg", "用户名或密码错误！");
	        resultMap.put("code", -103);
	        return resultMap;
		}
		
		User loginUser = loginUserList.get(0);
        UserLoginInfoDTO userLoginInfoDTO = getUserLoginInfoDTO(loginUser.getId() + "");
		LoginUserVO loginUserVO = new LoginUserVO(loginUser.getId(), loginUser.getUsername(), loginUser.getName());

		resultMap.put("msg", "用户登录成功！");
        //判断用户是否有入厂鉴定权限---用于前台是否显示录入车辆
        if(userLoginInfoDTO.getRoleMap().containsKey(Constant.ENTRANCE_CHECKUP_ROLE_ID)) {
            loginUserVO.setHaveSaveVehicle(1);
        } else {
            loginUserVO.setHaveSaveVehicle(0);
        }
        resultMap.put("datas", loginUserVO);
        resultMap.put("code", 101);
        return resultMap;
	}
	
	/** 
     * 对字符串md5加密(小写+字母) 
     * 
     * @param str 传入要加密的字符串 
     * @return  MD5加密后的字符串 
     */  
    public static String getMD5(String str) {  
        try {  
            // 生成一个MD5加密计算摘要  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            // 计算md5函数  
            md.update(str.getBytes());  
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
            return new BigInteger(1, md.digest()).toString(16);  
        } catch (Exception e) {  
        	e.printStackTrace();
        	return null;
        }  
    }  
}
