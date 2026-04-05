package com.finance.dashboard.controller;

import com.finance.dashboard.dto.RecordDTO;
import com.finance.dashboard.service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/records")
public class FinancialRecordController {

    private final FinancialRecordService recordService;

    public FinancialRecordController(FinancialRecordService recordService) {
        this.recordService = recordService;
    }

    // 🔴 ADMIN ONLY (Protected by SecurityConfig)
    @PostMapping
    public RecordDTO createRecord(@Valid @RequestBody RecordDTO dto) {
        return recordService.createRecord(dto);
    }

    // 🟢 ALL ROLES
    @GetMapping
    public List<RecordDTO> getAllRecords() {
        return recordService.getAllRecords();
    }

    // 🟡 ADMIN & ANALYST ONLY
    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return recordService.getDashboardSummary();
    }
}