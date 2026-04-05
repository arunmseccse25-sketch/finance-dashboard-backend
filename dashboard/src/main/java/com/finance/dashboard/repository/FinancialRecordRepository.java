package com.finance.dashboard.repository;

import com.finance.dashboard.entity.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    // ✅ FIND ALL RECORDS FOR A SPECIFIC USER
    // This will be very useful for your Dashboard!
    List<FinancialRecord> findByUserId(Long userId);

    // ✅ FIND BY TYPE (INCOME or EXPENSE)
    List<FinancialRecord> findByType(String type);
}