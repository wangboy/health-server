package com.wb.repository;

import com.wb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangbo on 2017/4/1.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	
	User findByName(String name);
}
