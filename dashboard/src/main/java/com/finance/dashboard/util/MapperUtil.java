package com.finance.dashboard.util;

import com.finance.dashboard.dto.RecordDTO;
import com.finance.dashboard.dto.UserDTO;
import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.entity.enums.*;

public class MapperUtil {

    // Record → DTO
    public static RecordDTO toDTO(FinancialRecord record) {
        return new RecordDTO(
            record.getId(), 
            record.getAmount(), 
            record.getType().name(), 
            record.getCategory(),
            record.getDate(), 
            record.getDescription(), 
            record.getUser().getId()
        );
    }

    // DTO → Record
    public static FinancialRecord toEntity(RecordDTO dto, User user) {
        FinancialRecord record = new FinancialRecord();
        record.setId(dto.getId());
        record.setAmount(dto.getAmount());
        record.setType(Type.valueOf(dto.getType().toUpperCase()));
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setDescription(dto.getDescription());
        record.setUser(user);
        return record;
    }

    // User → DTO (Updated to use username)
    public static UserDTO toDTO(User user) {
        return new UserDTO(
            user.getId(), 
            user.getUsername(), // Using getUsername() from Entity
            user.getEmail(), 
            user.getRole().name(), 
            user.getStatus().name(),
            user.getPassword()
        );
    }

    // DTO → User (Updated to use username)
    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername()); // Using setUsername() in Entity
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        
        // Safety check for Enums
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        user.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        return user;
    }
}