package com.mohamednasser.sadaqa.security;

import com.mohamednasser.sadaqa.dto.UserLoginData;
import com.mohamednasser.sadaqa.model.Role;
import com.mohamednasser.sadaqa.model.User;
import com.mohamednasser.sadaqa.repository.UserRepository;
import com.mohamednasser.sadaqa.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public String authenticate(UserLoginData credentials) throws BadCredentialsException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.email(),
                        credentials.password()
                )
        );
        if (authentication != null) return onSuccessfulAuthentication(authentication);
        throw new BadCredentialsException("Invalid credentials");
    }

    private String onSuccessfulAuthentication(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities()
                .stream()
                .limit(1)
                .toList()
                .get(0)
                .getAuthority();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);
        return jwtService.generateJwtToken(username, extraClaims);
    }


    public User getAuthenticatedUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("No such user.");
        return user.get();
    }
}
