package com.student.management.dao.userDao;

import com.student.management.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where username=:username",
                User.class);
        query.setParameter("username", username);
        List<User> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
}
