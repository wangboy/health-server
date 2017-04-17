package com.wb.auth.service.impl;

import com.wb.auth.domain.Role;
import com.wb.auth.domain.User;
import com.wb.auth.repository.UserRepository;
import com.wb.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * Created by wangbo on 2017/4/6.
 */
@Service
public class UserServiceImpl implements UserService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void init() {
		for (int i = 0; i < 5; i++) {
			String cell = "1352209" + i + i + i + i;
			User existing = userRepository.findOne(cell);
			if (existing != null) {
				continue;
			}
			User user = new User();
			String name = "user" + i;
			user.setCell(cell);
			user.setName(name);
			user.setPassword(encoder.encode("123456789"));
			user.setRole(Role.USER);
			userRepository.save(user);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(encoder.encode("123456"));
		System.out.println("$2a$10$2eGL/CNVMsDGxRH8bEUMQuAbhk/54wE0EYNA7aLbIUc7A.Od6m5ki".length());
	}
	
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
