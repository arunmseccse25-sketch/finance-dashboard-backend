package com.finance.dashboard.repository;

import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.entity.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

public interface RecordRepository extends JpaRepository<FinancialRecord, Long> {

    Page<FinancialRecord> findByType(Type type, Pageable pageable);

    Page<FinancialRecord> findByCategory(String category, Pageable pageable);

    Page<FinancialRecord> findByTypeAndCategory(Type type, String category, Pageable pageable);

    List<FinancialRecord> findTop10ByOrderByDateDesc();

    @Query("SELECT MONTH(r.date) as month, SUM(r.amount) as total " +
           "FROM FinancialRecord r WHERE r.type = :type AND YEAR(r.date) = :year " +
           "GROUP BY MONTH(r.date) ORDER BY MONTH(r.date)")
    List<Map<String, Object>> getMonthlyTrend(@Param("type") Type type, @Param("year") int year);

    @Query("SELECT WEEK(r.date) as week, SUM(r.amount) as total " +
           "FROM FinancialRecord r WHERE r.type = :type AND YEAR(r.date) = :year " +
           "GROUP BY WEEK(r.date) ORDER BY WEEK(r.date)")
    List<Map<String, Object>> getWeeklyTrend(@Param("type") Type type, @Param("year") int year);
}