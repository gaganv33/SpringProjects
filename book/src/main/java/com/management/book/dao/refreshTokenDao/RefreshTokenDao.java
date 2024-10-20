package com.management.book.dao.refreshTokenDao;

import com.management.book.entity.RefreshToken;
import com.management.book.entity.User;

import java.util.Optional;

public interface RefreshTokenDao {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken save(RefreshToken refreshToken);
    void deleteByToken(String token);
    void deleteTokenByUserId(User user);
    Optional<RefreshToken> findByUser(User user);
}
