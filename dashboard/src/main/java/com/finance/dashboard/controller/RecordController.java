package com.finance.dashboard.controller;

import com.finance.dashboard.dto.RecordDTO;
import com.finance.dashboard.entity.enums.Type;
import com.finance.dashboard.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    // ✅ CREATE — ADMIN & ANALYST
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    public RecordDTO createRecord(@Valid @RequestBody RecordDTO dto) {
        return recordService.createRecord(dto);
    }

    // ✅ GET ALL — ALL ROLES
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST') or hasRole('VIEWER')")
    public Page<RecordDTO> getAllRecords(Pageable pageable) {
        return recordService.getAllRecords(pageable);
    }

    // ✅ GET BY TYPE — ALL ROLES
    @GetMapping("/type")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST') or hasRole('VIEWER')")
    public Page<RecordDTO> getByType(@RequestParam Type type, Pageable pageable) {
        return recordService.getByType(type, pageable);
    }

    // ✅ GET BY CATEGORY — ALL ROLES
    @GetMapping("/category")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST') or hasRole('VIEWER')")
    public Page<RecordDTO> getByCategory(@RequestParam String category, Pageable pageable) {
        return recordService.getByCategory(category, pageable);
    }

    // ✅ FILTER BY TYPE + CATEGORY — ALL ROLES
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST') or hasRole('VIEWER')")
    public Page<RecordDTO> filter(@RequestParam Type type,
                                  @RequestParam String category,
                                  Pageable pageable) {
        return recordService.getByTypeAndCategory(type, category, pageable);
    }

    // ✅ UPDATE — ADMIN & ANALYST
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    public RecordDTO updateRecord(@PathVariable Long id,
                                  @Valid @RequestBody RecordDTO dto) {
        return recordService.updateRecord(id, dto);
    }

    // ✅ DELETE — ADMIN ONLY
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return "Record deleted successfully";
    }
}