package com.finance.dashboard.service;

import com.finance.dashboard.dto.RecordDTO;
import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.entity.enums.Type;
import com.finance.dashboard.exception.CustomException;
import com.finance.dashboard.repository.RecordRepository;
import com.finance.dashboard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    public RecordService(RecordRepository recordRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    // ✅ CREATE
    public RecordDTO createRecord(RecordDTO dto) {
        if (dto.getUserId() == null) {
            throw new CustomException("User ID is required");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found with id: " + dto.getUserId()));

        FinancialRecord record = new FinancialRecord();

        try {
            record.setAmount(dto.getAmount());
            record.setType(Type.valueOf(dto.getType()));
        } catch (Exception e) {
            throw new CustomException("Invalid record type. Use INCOME or EXPENSE");
        }

        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setDescription(dto.getDescription());
        record.setUser(user);

        FinancialRecord saved = recordRepository.save(record);

        return mapToDTO(saved);
    }

    // ✅ GET ALL
    public Page<RecordDTO> getAllRecords(Pageable pageable) {
        Page<FinancialRecord> records = recordRepository.findAll(pageable);
        if (records.isEmpty()) {
            throw new CustomException("No records found");
        }
        return records.map(this::mapToDTO);
    }

    // ✅ FILTER TYPE
    public Page<RecordDTO> getByType(Type type, Pageable pageable) {
        Page<FinancialRecord> records = recordRepository.findByType(type, pageable);
        if (records.isEmpty()) {
            throw new CustomException("No records found for type: " + type);
        }
        return records.map(this::mapToDTO);
    }

    // ✅ FILTER CATEGORY
    public Page<RecordDTO> getByCategory(String category, Pageable pageable) {
        Page<FinancialRecord> records = recordRepository.findByCategory(category, pageable);
        if (records.isEmpty()) {
            throw new CustomException("No records found for category: " + category);
        }
        return records.map(this::mapToDTO);
    }

    // ✅ FILTER TYPE + CATEGORY
    public Page<RecordDTO> getByTypeAndCategory(Type type, String category, Pageable pageable) {
        Page<FinancialRecord> records = recordRepository.findByTypeAndCategory(type, category, pageable);
        if (records.isEmpty()) {
            throw new CustomException("No records found for given filters");
        }
        return records.map(this::mapToDTO);
    }

    // ✅ UPDATE
    public RecordDTO updateRecord(Long id, RecordDTO dto) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new CustomException("Record not found with id: " + id));

        try {
            record.setAmount(dto.getAmount());
            record.setType(Type.valueOf(dto.getType()));
        } catch (Exception e) {
            throw new CustomException("Invalid record type. Use INCOME or EXPENSE");
        }

        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setDescription(dto.getDescription());

        FinancialRecord saved = recordRepository.save(record);

        return mapToDTO(saved);
    }

    // ✅ DELETE
    public void deleteRecord(Long id) {
        if (!recordRepository.existsById(id)) {
            throw new CustomException("Record not found with id: " + id);
        }
        recordRepository.deleteById(id);
    }

    // 🔁 COMMON MAPPER (CLEAN CODE)
    private RecordDTO mapToDTO(FinancialRecord record) {
        RecordDTO dto = new RecordDTO();
        dto.setId(record.getId());
        dto.setAmount(record.getAmount());
        dto.setType(record.getType().name());
        dto.setCategory(record.getCategory());
        dto.setDate(record.getDate());
        dto.setDescription(record.getDescription());
        dto.setUserId(record.getUser().getId());
        return dto;
    }
}