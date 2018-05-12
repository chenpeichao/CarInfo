package org.zc.common.cache;

import org.zc.dto.UserLoginInfoDTO;

/**
 * Description: 用于当前线程userLoginInfoDTO信息绑定
 * Create by @author cpc 
 * 2018年4月21日 下午7:05:33
 */
public class CacheCurrentUserThreadLocal {
	// ①使用ThreadLocal保存userLoginInfoDTO信息
	private static ThreadLocal<UserLoginInfoDTO> threadLocalUser = new ThreadLocal<UserLoginInfoDTO>();

	public static UserLoginInfoDTO getCurrentUser() {
		// ②如果connThreadLocal没有本线程对应的Connection创建一个新的Connection，
		// 并将其保存到线程本地变量中。
		if (threadLocalUser.get() == null) {
			return null;
		} else {
			// ③直接返回线程本地变量
			return threadLocalUser.get();
		}
	}

	public static void putUser(UserLoginInfoDTO userLoginInfoDTO) {
		// ④从ThreadLocal中获取线程对应的
		threadLocalUser.set(userLoginInfoDTO);
	}
}
