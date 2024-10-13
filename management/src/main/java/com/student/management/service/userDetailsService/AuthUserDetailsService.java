package com.student.management.service.userDetailsService;

import com.student.management.dao.userDao.UserDAO;
import com.student.management.entity.User;
import com.student.management.entity.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    @Autowired
    public AuthUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found in the database.");
        }
        return new UserPrincipal(user);
    }
}
