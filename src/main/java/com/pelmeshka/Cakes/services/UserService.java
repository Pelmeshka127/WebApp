package com.pelmeshka.Cakes.services;

import com.pelmeshka.Cakes.models.User;
import com.pelmeshka.Cakes.models.enums.Role;
import com.pelmeshka.Cakes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new user with email: {}.", user.getEmail());
        userRepository.save(user);
        return true;
    }
}
