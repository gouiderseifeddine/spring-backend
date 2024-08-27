package com.nour.camping.service;

import com.nour.camping.entity.Camping;
import com.nour.camping.entity.User;
import com.nour.camping.repository.UserRepository;
import com.nour.camping.service.UserService;
import com.nour.camping.utils.JwtUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Autowired
    private EmailSenderService emailUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User signUp(User user) {
        if (existsByUsername(user.getUsername()) || existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Username or Email already in use");
        }

        User savedUser = userRepository.save(user);

        String body = "Dear " + savedUser.getUsername() + ",\n\n" +
                "Welcome to Camping! Your account has been successfully created. " +
                "Please find your login credentials below:\n\n" +
                "Email: " + savedUser.getEmail() + "\n" +
                "Password: " + savedUser.getPassword() + "\n\n" +
                "Please keep this information secure.\n\n" +
                "Best regards,\nThe Camping Team";

        try {
            emailUtil.send(savedUser.getEmail(), "Welcome to Camping!", body);
        } catch (MessagingException e) {
            // Log the exception or handle it as necessary
            System.out.println("Failed to send email: " + e.getMessage());
        }

        return savedUser;
    }

    @Override
    public Optional<User> signInByEmail(String email, String password) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent() && password.equals(user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void banUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBanned(true);  // Set the user as banned
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public void unbanUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBanned(false);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }
    @Override
    public String generateJwtToken(UserDetails userDetails) {
        return jwtUtil.generateToken(userDetails);
    }

}