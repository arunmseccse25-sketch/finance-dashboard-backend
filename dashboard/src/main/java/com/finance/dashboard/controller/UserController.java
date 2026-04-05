package com.finance.dashboard.controller;

import com.finance.dashboard.dto.UserDTO;
import com.finance.dashboard.entity.enums.Role;
import com.finance.dashboard.entity.enums.Status;
import com.finance.dashboard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ CREATE
    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }

    // ✅ GET ALL
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // ✅ UPDATE ROLE
    @PutMapping("/{id}/role")
    public UserDTO updateRole(@PathVariable Long id, @RequestParam Role role) {
        return userService.updateRole(id, role);
    }

    // ✅ UPDATE STATUS
    @PutMapping("/{id}/status")
    public UserDTO updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return userService.updateStatus(id, status);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}