package com.finance.dashboard.controller;

import com.finance.dashboard.entity.enums.Type;
import com.finance.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // ✅ SUMMARY
    @GetMapping("/summary")
    public ResponseEntity<Map<String, BigDecimal>> getSummary() {
        Map<String, BigDecimal> result = new HashMap<>();
        result.put("income", dashboardService.getTotalIncome());
        result.put("expense", dashboardService.getTotalExpense());
        result.put("balance", dashboardService.getBalance());
        return ResponseEntity.ok(result);
    }

    // ✅ MONTHLY TREND
    // GET /api/dashboard/trends/monthly?type=INCOME&year=2024
    @GetMapping("/trends/monthly")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyTrend(
            @RequestParam Type type,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getMonthlyTrend(type, year));
    }

    // ✅ WEEKLY TREND
    // GET /api/dashboard/trends/weekly?type=EXPENSE&year=2024
    @GetMapping("/trends/weekly")
    public ResponseEntity<List<Map<String, Object>>> getWeeklyTrend(
            @RequestParam Type type,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getWeeklyTrend(type, year));
    }
}