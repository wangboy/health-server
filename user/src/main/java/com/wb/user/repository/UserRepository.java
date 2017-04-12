package com.wb.user.repository;

import com.wb.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangbo on 2017/4/1.
 */
public interface UserRepository extends JpaRepository<User, String> {
	User findByName(String name);
}
