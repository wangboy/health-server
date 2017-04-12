package com.wb.user.service.impl;

import com.wb.user.client.AuthClient;
import com.wb.user.domain.User;
import com.wb.user.service.UserService;
import com.wb.user.domain.Role;
import com.wb.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * Created by wangbo on 2017/4/1.
 */
@Service
public class UserServiceImpl implements UserService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthClient authClient;
	
	@PostConstruct
	public void init() {
		for (int i = 0; i < 5; i++) {
			String name = "user" + i;
			User existing = userRepository.findByName(name);
			if (existing != null) {
				continue;
			}
			User user = new User();
			user.setCell("1352209" + i + i + i + i);
			user.setName(name);
			user.setPassword("pass" + i);
			user.setRole(Role.USER);
			userRepository.save(user);
			
		}
		
	}
	
	@Override
	public User findByName(String name) {
		Assert.hasLength(name);
		return userRepository.findByName(name);
//		return userRepository.findOne()
	}
	
	@Override
	public User createUser(User user) {
		User existing = userRepository.findByName(user.getName());
		Assert.isNull(existing, "user already exists: " + user.getName());

		authClient.createUser(user);
		
//		String hash = encoder.encode(user.getPassword());
//		user.setPassword(hash);
		user.setCell(user.getCell() == null ? "" : user.getCell());
		
		userRepository.save(user);
		
		log.info("new user has been created: {}", user.getName());
		
		return user;
	}
	
	@Override
	public void saveUserChange(String name, User userChange) {
		User user = userRepository.findByName(name);
		Assert.notNull(user, "can't find user with name " + name);
		
		user.setCell(userChange.getCell());
		
		//		String hash = encoder.encode(user.getPassword());
		//		user.setPassword(hash);
		userRepository.save(user);
		
		log.debug("user {} changes has been saved", name);
		
	}
}
