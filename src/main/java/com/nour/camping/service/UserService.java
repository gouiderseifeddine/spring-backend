package com.nour.camping.service;

import com.nour.camping.entity.Camping;
import com.nour.camping.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User signUp(User user);
    Optional<User> signInByEmail(String email, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> getAllUsers();
    void banUserById(Long userId);
    void unbanUserById(Long userId);

    UserDetails loadUserByUsername(String username);

    String generateJwtToken(UserDetails userDetails);
}
