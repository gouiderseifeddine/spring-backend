package com.nour.camping.controller;

import com.nour.camping.dto.SignInRequest;
import com.nour.camping.entity.Camping;
import com.nour.camping.entity.Role;
import com.nour.camping.entity.Sexe;
import com.nour.camping.entity.User;
import com.nour.camping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.signUp(user);
            response.put("message", "User registered successfully. Password has been sent to " + user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "Registration successful, but failed to send email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody SignInRequest signInRequest) {
        Optional<User> user = userService.signInByEmail(signInRequest.getEmail(), signInRequest.getPassword());
        Map<String, Object> response = new HashMap<>();
        if (user.isPresent()) {
            if (user.get().isBanned()) {
                response.put("error", "User is banned");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            UserDetails userDetails = userService.loadUserByUsername(user.get().getUsername());
            String jwtToken = userService.generateJwtToken(userDetails);
            response.put("message", "User signed in successfully");
            response.put("token",jwtToken);
            response.put("user", user.get()); // Include the user object with the role
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllNonAdminUsers() {
        List<User> users = userService.getAllUsers()
                .stream()
                .filter(user -> user.getRole() != Role.ADMIN)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/ban/{id}")
    public ResponseEntity<Map<String, String>> banUser(@PathVariable Long id) {
        userService.banUserById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User banned successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/unban/{id}")
    public ResponseEntity<Map<String, String>> unbanUser(@PathVariable Long id) {
        userService.unbanUserById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User unbanned successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/gender-distribution")
    public ResponseEntity<Map<String, Long>> getGenderDistribution() {
        List<User> users = userService.getAllUsers();
        long menCount = users.stream()
                .filter(user -> user.getSexe() == Sexe.HOMME)
                .count();
        long womenCount = users.stream()
                .filter(user -> user.getSexe() == Sexe.FEMME)
                .count();

        Map<String, Long> genderDistribution = new HashMap<>();
        genderDistribution.put("men", menCount);
        genderDistribution.put("women", womenCount);

        return ResponseEntity.ok(genderDistribution);
    }
    @GetMapping("/age-distribution")
    public ResponseEntity<Map<String, Long>> getAgeDistribution() {
        List<User> users = userService.getAllUsers();

        long youngstersCount = users.stream()
                .filter(user -> user.getAge() >= 15 && user.getAge() <= 24)
                .count();

        long adultsCount = users.stream()
                .filter(user -> user.getAge() >= 25 && user.getAge() <= 45)
                .count();

        long geezersCount = users.stream()
                .filter(user -> user.getAge() > 45)
                .count();

        Map<String, Long> ageDistribution = new HashMap<>();
        ageDistribution.put("youngsters", youngstersCount);
        ageDistribution.put("adults", adultsCount);
        ageDistribution.put("geezers", geezersCount);

        return ResponseEntity.ok(ageDistribution);
    }

}