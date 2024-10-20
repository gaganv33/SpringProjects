package com.management.book.dao.userDao;

import com.management.book.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);
    User save(User user);
}
