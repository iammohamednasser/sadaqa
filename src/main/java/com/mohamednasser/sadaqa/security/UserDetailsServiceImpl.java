package com.mohamednasser.sadaqa.security;

import com.mohamednasser.sadaqa.model.Role;
import com.mohamednasser.sadaqa.model.User;
import com.mohamednasser.sadaqa.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByEmail(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("No such user.");
        String password = user.get().getPassword();
        Role role = user.get().getRole();
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role.getName()));
        return new org.springframework.security.core.userdetails.User(username, password, roles);
    }
}
