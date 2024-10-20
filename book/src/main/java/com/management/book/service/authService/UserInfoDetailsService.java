package com.management.book.service.authService;

import com.management.book.dao.userDao.UserDao;
import com.management.book.dto.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import com.management.book.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserInfoDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userDao.findByUsername(username);
        if(result.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found in the database.");
        }
        return new UserInfoDetails(result.get());
    }
}
