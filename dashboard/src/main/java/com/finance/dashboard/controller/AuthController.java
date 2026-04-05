package com.finance.dashboard.controller; // This must be the first line

import com.finance.dashboard.dto.RegisterDTO;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.entity.enums.Role;
import com.finance.dashboard.entity.enums.Status;
import com.finance.dashboard.exception.CustomException;
import com.finance.dashboard.repository.UserRepository;
import com.finance.dashboard.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterDTO registerDTO) {

        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new CustomException("Email already exists");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setFullName(registerDTO.getFullName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setStatus(Status.ACTIVE);

        // Convert String from DTO to Role Enum
        try {
            if (registerDTO.getRole() != null) {
                // This will try to match ADMIN, ANALYST, or VIEWER
                user.setRole(Role.valueOf(registerDTO.getRole().toUpperCase()));
            } else {
                user.setRole(Role.VIEWER); // Default fallback
            }
        } catch (Exception e) {
            // If the input String doesn't match an Enum value, default to VIEWER
            user.setRole(Role.VIEWER); 
        }

        userRepository.save(user);
        return "User Registered Successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody RegisterDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}