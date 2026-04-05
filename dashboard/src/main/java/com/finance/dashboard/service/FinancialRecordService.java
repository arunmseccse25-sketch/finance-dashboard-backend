package com.finance.dashboard.service;

import com.finance.dashboard.dto.RecordDTO;
import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.repository.FinancialRecordRepository;
import com.finance.dashboard.repository.UserRepository;
import com.finance.dashboard.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public FinancialRecordService(FinancialRecordRepository recordRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    public RecordDTO createRecord(RecordDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));
        FinancialRecord record = MapperUtil.toEntity(dto, user);
        return MapperUtil.toDTO(recordRepository.save(record));
    }

    public List<RecordDTO> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ REQUIREMENT #3: Dashboard Summary Logic
    public Map<String, Object> getDashboardSummary() {
        List<FinancialRecord> records = recordRepository.findAll();

        BigDecimal totalIncome = records.stream()
                .filter(r -> "INCOME".equalsIgnoreCase(r.getType().name()))
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = records.stream()
                .filter(r -> "EXPENSE".equalsIgnoreCase(r.getType().name()))
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpenses", totalExpense);
        summary.put("netBalance", totalIncome.subtract(totalExpense));
        summary.put("totalTransactions", records.size());

        return summary;
    }
}