package com.shivam.expensetracker.repository;

import com.shivam.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserIdOrderByExpenseDateDesc(Long userId);

    List<Expense> findByUserIdAndTypeOrderByExpenseDateDesc(Long userId, String type);

    @Query("SELECT e FROM Expense e WHERE e.userId = :userId AND e.expenseDate BETWEEN :start AND :end ORDER BY e.expenseDate DESC")
    List<Expense> findByUserIdAndDateRange(@Param("userId") Long userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.userId = :userId AND e.type = :type AND e.expenseDate BETWEEN :start AND :end")
    BigDecimal sumByTypeAndDateRange(@Param("userId") Long userId, @Param("type") String type, @Param("start") LocalDate start, @Param("end") LocalDate end);
}