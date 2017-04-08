package com.wb.service.impl;

import com.netflix.discovery.converters.Auto;
import com.wb.domain.User;
import com.wb.repository.UserRepository;
import com.wb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by wangbo on 2017/4/6.
 */
@Service
public class UserServiceImpl implements UserService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void createUser(User user) {
		
		User existing = userRepository.findOne(user.getUsername());
		Assert.isNull(existing, "user already exists: " + user.getUsername() + "_" + user.getPassword());
		
		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);
		
		userRepository.save(user);
		
		log.info("new user has been created: {}", user.getUsername());
	}
}
