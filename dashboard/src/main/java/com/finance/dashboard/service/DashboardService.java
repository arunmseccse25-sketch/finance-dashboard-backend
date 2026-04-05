package com.finance.dashboard.service;

import com.finance.dashboard.dto.RecordDTO;
import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.entity.enums.Type;
import com.finance.dashboard.exception.CustomException;
import com.finance.dashboard.repository.RecordRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final RecordRepository recordRepository;

    public DashboardService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    // ✅ TOTAL INCOME
    public BigDecimal getTotalIncome() {
        List<FinancialRecord> records = recordRepository.findAll();
        if (records.isEmpty()) {
            throw new CustomException("No financial records found");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (FinancialRecord record : records) {
            if (record.getType() == Type.INCOME) {
                if (record.getAmount() == null) {
                    throw new CustomException("Invalid income record: amount is missing");
                }
                total = total.add(record.getAmount());
            }
        }
        return total;
    }

    // ✅ TOTAL EXPENSE
    public BigDecimal getTotalExpense() {
        List<FinancialRecord> records = recordRepository.findAll();
        if (records.isEmpty()) {
            throw new CustomException("No financial records found");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (FinancialRecord record : records) {
            if (record.getType() == Type.EXPENSE) {
                if (record.getAmount() == null) {
                    throw new CustomException("Invalid expense record: amount is missing");
                }
                total = total.add(record.getAmount());
            }
        }
        return total;
    }

    // ✅ BALANCE
    public BigDecimal getBalance() {
        return getTotalIncome().subtract(getTotalExpense());
    }

    // ✅ CATEGORY WISE TOTALS
    public Map<String, BigDecimal> getCategoryTotals() {
        List<FinancialRecord> records = recordRepository.findAll();
        if (records.isEmpty()) {
            throw new CustomException("No financial records found");
        }
        Map<String, BigDecimal> categoryTotals = new HashMap<>();
        for (FinancialRecord record : records) {
            String category = record.getCategory();
            BigDecimal amount = record.getAmount() != null ? record.getAmount() : BigDecimal.ZERO;
            categoryTotals.put(category, categoryTotals.getOrDefault(category, BigDecimal.ZERO).add(amount));
        }
        return categoryTotals;
    }

    // ✅ RECENT TRANSACTIONS (LAST 10)
    public List<RecordDTO> getRecentTransactions() {
        List<FinancialRecord> records = recordRepository.findTop10ByOrderByDateDesc();
        if (records.isEmpty()) {
            throw new CustomException("No recent transactions found");
        }
        return records.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ DELETE ALL
    public void deleteAllRecords() {
        if (recordRepository.count() == 0) {
            throw new CustomException("No records available to delete");
        }
        recordRepository.deleteAll();
    }

    // ✅ MONTHLY TREND
    public List<Map<String, Object>> getMonthlyTrend(Type type, int year) {
        List<Map<String, Object>> result = recordRepository.getMonthlyTrend(type, year);
        if (result.isEmpty()) {
            throw new CustomException("No monthly data found for type: " + type + " and year: " + year);
        }
        return result;
    }

    // ✅ WEEKLY TREND
    public List<Map<String, Object>> getWeeklyTrend(Type type, int year) {
        List<Map<String, Object>> result = recordRepository.getWeeklyTrend(type, year);
        if (result.isEmpty()) {
            throw new CustomException("No weekly data found for type: " + type + " and year: " + year);
        }
        return result;
    }

    // 🔁 MAPPER
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