package com.student.management.dao.userDao;

import com.student.management.entity.User;

import java.util.Optional;

public interface UserDAO {
    User findByUsername(String username);
    User save(User user);
}
