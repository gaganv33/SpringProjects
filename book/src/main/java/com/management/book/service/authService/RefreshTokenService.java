package com.management.book.service.authService;

import com.management.book.dao.refreshTokenDao.RefreshTokenDao;
import com.management.book.dao.userDao.UserDao;
import com.management.book.entity.RefreshToken;
import com.management.book.entity.User;
import com.management.book.exception.InvalidRefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenDao refreshTokenDao;
    private final UserDao userDao;

    @Autowired
    public RefreshTokenService(RefreshTokenDao refreshTokenDao, UserDao userDao) {
        this.refreshTokenDao = refreshTokenDao;
        this.userDao = userDao;
    }

    @Transactional
    public RefreshToken createRefreshToken(String username) {
        Optional<User> user = userDao.findByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found in database.");
        }
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user.get())
                .token(UUID.randomUUID().toString())
                .expiry(Instant.now().plusMillis(600000))
                .build();

        return refreshTokenDao.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenDao.findByToken(token);
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) throws InvalidRefreshToken {
        if(token.getExpiry().compareTo(Instant.now()) < 0) {
            Optional<RefreshToken> refreshToken = refreshTokenDao.findByToken(token.getToken());
            if(refreshToken.isPresent()) {
                refreshTokenDao.deleteByToken(token.getToken());
                throw new InvalidRefreshToken("Invalid refresh token.");
            }
         }
        return token;
    }

    @Transactional
    public void deleteByTokenUsingUsername(String username) {
        Optional<User> user = userDao.findByUsername(username);
        if(user.isEmpty()) {
            return;
        }
        refreshTokenDao.deleteTokenByUserId(user.get());
    }
}
