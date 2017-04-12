package com.wb.user.service;

import com.wb.user.domain.User;

/**
 * Created by wangbo on 2017/4/1.
 */
public interface UserService {
	User findByName(String name);
	
	User createUser(User user);
	
	void saveUserChange(String name, User user);
}
