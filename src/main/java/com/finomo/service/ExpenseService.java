package com.finomo.service;

import com.finomo.entity.Expense;
import com.finomo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    // Create a new expense
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    
    // Get all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    
    // Get expense by ID
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
    
    // Update an expense
    public Expense updateExpense(Long id, Expense expenseDetails) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setDescription(expenseDetails.getDescription());
            expense.setAmount(expenseDetails.getAmount());
            expense.setCategory(expenseDetails.getCategory());
            expense.setDate(expenseDetails.getDate());
            expense.setNotes(expenseDetails.getNotes());
            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("Expense not found with id: " + id);
        }
    }
    
    // Delete an expense
    public void deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Expense not found with id: " + id);
        }
    }
    
    // Get expenses by category
    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }
    
    // Get expenses by date range
    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetween(startDate, endDate);
    }
    
    // Get expenses by category and date range
    public List<Expense> getExpensesByCategoryAndDateRange(String category, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByCategoryAndDateBetween(category, startDate, endDate);
    }
    
    // Get expenses greater than amount
    public List<Expense> getExpensesGreaterThanAmount(BigDecimal amount) {
        return expenseRepository.findByAmountGreaterThan(amount);
    }
    
    // Search expenses by keyword in description
    public List<Expense> searchExpensesByDescription(String keyword) {
        return expenseRepository.findByDescriptionContainingIgnoreCase(keyword);
    }
    
    // Get total amount by category
    public List<Object[]> getTotalAmountByCategory() {
        return expenseRepository.findTotalAmountByCategory();
    }
    
    // Get total amount in date range
    public BigDecimal getTotalAmountByDateRange(LocalDate startDate, LocalDate endDate) {
        BigDecimal total = expenseRepository.findTotalAmountByDateRange(startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
}