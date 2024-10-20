package com.management.book.dao.refreshTokenDao;

import com.management.book.entity.RefreshToken;
import com.management.book.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RefreshTokenDaoImpl implements RefreshTokenDao {
    private final EntityManager entityManager;

    @Autowired
    public RefreshTokenDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        TypedQuery<RefreshToken> query = entityManager.createQuery("from RefreshToken where token=:token",
                RefreshToken.class);
        query.setParameter("token", token);
        List<RefreshToken> refreshToken = query.getResultList();
        if(refreshToken.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(refreshToken.get(0));
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        entityManager.persist(refreshToken);
        return refreshToken;
    }

    @Override
    public void deleteByToken(String token) {
        Optional<RefreshToken> refreshToken = findByToken(token);
        if(refreshToken.isEmpty()) {
            System.out.print("Not found");
            return;
        }
        entityManager.createQuery("delete from RefreshToken rt where rt.token = :token")
                .setParameter("token", token)
                .executeUpdate();
    }

    @Override
    public void deleteTokenByUserId(User user) {
        Optional<RefreshToken> refreshToken = findByUser(user);
        if(refreshToken.isEmpty()) {
            return;
        }
        entityManager.createQuery("delete from RefreshToken rt where rt.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }

    @Override
    public Optional<RefreshToken> findByUser(User user) {
        TypedQuery<RefreshToken> refreshTokenTypedQuery = entityManager
                .createQuery("from RefreshToken rt where rt.user = :user", RefreshToken.class);
        refreshTokenTypedQuery.setParameter("user", user);
        List<RefreshToken> refreshTokens = refreshTokenTypedQuery.getResultList();
        if(refreshTokens.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(refreshTokens.get(0));
    }
}
