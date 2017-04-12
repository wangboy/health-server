package com.wb.auth.repository;

import com.wb.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangbo on 2017/4/6.
 */
public interface UserRepository extends JpaRepository<User, String> {
}
