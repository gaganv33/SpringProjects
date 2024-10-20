package com.management.book.controller;


import com.management.book.dto.*;
import com.management.book.entity.RefreshToken;
import com.management.book.entity.User;
import com.management.book.exception.InvalidRefreshToken;
import com.management.book.service.authService.JwtService;
import com.management.book.service.authService.RefreshTokenService;
import com.management.book.service.userService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @GetMapping("/test")
    public String TestFunction() {
        return "test function result";
    }

    @PostMapping("/register")
    public User Register(@RequestBody @Valid UserDto userDto) {
        User user = new User(userDto);
        return userService.save(user);
    }

    @PostMapping("/login")
    public JwtResponse Login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.getUsername()))
                    .refreshToken(refreshToken.getToken()).build();
        }  else {
            throw new UsernameNotFoundException("Invalid user request.");
        }
    }

    @GetMapping("/logout")
    public String Logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);
        refreshTokenService.deleteByTokenUsingUsername(jwtService.extractUsername(token));
        return "Logged out successfully.";
    }

    @PostMapping("refresh")
    public JwtResponse GetAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshToken {
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken());
        if(refreshToken.isEmpty()) {
            throw new InvalidRefreshToken("Invalid refresh token.");
        }

        RefreshToken token = refreshTokenService.verifyExpiration(refreshToken.get());
        User user = token.getUser();

        return JwtResponse
                .builder()
                .accessToken(jwtService.generateToken(user.getUsername()))
                .refreshToken(token.getToken())
                .build();
    }
}
