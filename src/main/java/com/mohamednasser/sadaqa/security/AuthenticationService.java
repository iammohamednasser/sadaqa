package com.mohamednasser.sadaqa.security;

import com.mohamednasser.sadaqa.dto.UserLoginData;
import com.mohamednasser.sadaqa.model.User;
import com.mohamednasser.sadaqa.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public AuthenticationService(
            AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public String authenticate(UserLoginData credentials) throws BadCredentialsException {
        String email = credentials.email();
        String password = credentials.password();
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (authentication != null)
            return onSuccessfulAuthentication(authentication);
        throw new BadCredentialsException("Invalid credentials.");
    }

    private String onSuccessfulAuthentication(Authentication authentication) {
        return generateJwtToken(authentication);
    }

    private String generateJwtToken(Authentication authentication) {
        String username = authentication.getName();
        String role = getRole(authentication);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return jwtService.generateJwtToken(username, claims);
    }

    private static String getRole(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .limit(1)
                .toList()
                .get(0)
                .getAuthority();
    }

    public User getAuthenticatedUser() {
        String email = getAuthenticatedUserEmail();
        return getUserOrThrowException(email);
    }

    private User getUserOrThrowException(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            throw new UsernameNotFoundException("No such user.");
        return user.get();
    }

    private static String getAuthenticatedUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
