package com.finance.dashboard.service;

import com.finance.dashboard.dto.UserDTO;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.entity.enums.Role;
import com.finance.dashboard.entity.enums.Status;
import com.finance.dashboard.exception.CustomException;
import com.finance.dashboard.repository.UserRepository;
import com.finance.dashboard.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ CREATE
    public UserDTO createUser(UserDTO dto) {

        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new CustomException("Email is required");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomException("User already exists with this email");
        }

        User user = MapperUtil.toEntity(dto);
        user.setStatus(Status.ACTIVE);

        User saved = userRepository.save(user);

        return MapperUtil.toDTO(saved);
    }

    // ✅ GET ALL
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new CustomException("No users found");
        }

        List<UserDTO> dtoList = new ArrayList<>();

        for (User user : users) {
            dtoList.add(MapperUtil.toDTO(user));
        }

        return dtoList;
    }

    // ✅ GET BY ID
    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with id: " + id));

        return MapperUtil.toDTO(user);
    }

    // ✅ UPDATE ROLE
    public UserDTO updateRole(Long id, Role role) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with id: " + id));

        user.setRole(role);

        User saved = userRepository.save(user);

        return MapperUtil.toDTO(saved);
    }

    // ✅ UPDATE STATUS
    public UserDTO updateStatus(Long id, Status status) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with id: " + id));

        user.setStatus(status);

        User saved = userRepository.save(user);

        return MapperUtil.toDTO(saved);
    }

    // ✅ DELETE
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new CustomException("Cannot delete: User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }
}