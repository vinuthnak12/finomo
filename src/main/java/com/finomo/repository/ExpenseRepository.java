package com.finomo.repository;

import com.finomo.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // Find expenses by category
    List<Expense> findByCategory(String category);
    
    // Find expenses by date range
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find expenses by category and date range
    List<Expense> findByCategoryAndDateBetween(String category, LocalDate startDate, LocalDate endDate);
    
    // Find expenses greater than a certain amount
    List<Expense> findByAmountGreaterThan(BigDecimal amount);
    
    // Find expenses by description containing a keyword (case-insensitive)
    List<Expense> findByDescriptionContainingIgnoreCase(String keyword);
    
    // Custom query to get total amount by category
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e GROUP BY e.category")
    List<Object[]> findTotalAmountByCategory();
    
    // Custom query to get total amount in a date range
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
    BigDecimal findTotalAmountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}