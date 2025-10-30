package com.finomo.controller;

import com.finomo.entity.Expense;
import com.finomo.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
@Tag(name = "Expense Management", description = "APIs for managing personal finance expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    // Create a new expense
    @Operation(
        summary = "Create a new expense",
        description = "Creates a new expense record with the provided details. All required fields must be provided."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Expense created successfully",
                    content = @Content(schema = @Schema(implementation = Expense.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        try {
            Expense savedExpense = expenseService.createExpense(expense);
            return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get all expenses
    @Operation(
        summary = "Get all expenses",
        description = "Retrieves a list of all expense records in the system."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all expenses"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        try {
            List<Expense> expenses = expenseService.getAllExpenses();
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get expense by ID
    @Operation(
        summary = "Get expense by ID",
        description = "Retrieves a specific expense record by its unique identifier."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Expense found and returned successfully",
                    content = @Content(schema = @Schema(implementation = Expense.class))),
        @ApiResponse(responseCode = "404", description = "Expense not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(
            @Parameter(description = "Unique identifier of the expense", required = true)
            @PathVariable Long id) {
        try {
            Optional<Expense> expense = expenseService.getExpenseById(id);
            if (expense.isPresent()) {
                return new ResponseEntity<>(expense.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update an expense
    @Operation(
        summary = "Update an existing expense",
        description = "Updates an existing expense record with new details. The expense ID must exist."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Expense updated successfully",
                    content = @Content(schema = @Schema(implementation = Expense.class))),
        @ApiResponse(responseCode = "404", description = "Expense not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @Parameter(description = "Unique identifier of the expense to update", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody Expense expenseDetails) {
        try {
            Expense updatedExpense = expenseService.updateExpense(id, expenseDetails);
            return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Delete an expense
    @Operation(
        summary = "Delete an expense",
        description = "Permanently deletes an expense record from the system."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Expense deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Expense not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(
            @Parameter(description = "Unique identifier of the expense to delete", required = true)
            @PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Expense deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get expenses by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable String category) {
        try {
            List<Expense> expenses = expenseService.getExpensesByCategory(category);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get expenses by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<Expense>> getExpensesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            List<Expense> expenses = expenseService.getExpensesByDateRange(startDate, endDate);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get expenses by category and date range
    @GetMapping("/category/{category}/date-range")
    public ResponseEntity<List<Expense>> getExpensesByCategoryAndDateRange(
            @PathVariable String category,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            List<Expense> expenses = expenseService.getExpensesByCategoryAndDateRange(category, startDate, endDate);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get expenses greater than amount
    @GetMapping("/amount-greater-than/{amount}")
    public ResponseEntity<List<Expense>> getExpensesGreaterThanAmount(@PathVariable BigDecimal amount) {
        try {
            List<Expense> expenses = expenseService.getExpensesGreaterThanAmount(amount);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Search expenses by keyword in description
    @Operation(
        summary = "Search expenses by keyword",
        description = "Searches for expenses containing the specified keyword in their description (case-insensitive)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Expense>> searchExpensesByDescription(
            @Parameter(description = "Keyword to search for in expense descriptions", required = true)
            @RequestParam String keyword) {
        try {
            List<Expense> expenses = expenseService.searchExpensesByDescription(keyword);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get total amount by category
    @GetMapping("/analytics/total-by-category")
    public ResponseEntity<Map<String, BigDecimal>> getTotalAmountByCategory() {
        try {
            List<Object[]> results = expenseService.getTotalAmountByCategory();
            Map<String, BigDecimal> categoryTotals = new HashMap<>();
            
            for (Object[] result : results) {
                String category = (String) result[0];
                BigDecimal total = (BigDecimal) result[1];
                categoryTotals.put(category, total);
            }
            
            return new ResponseEntity<>(categoryTotals, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get total amount in date range
    @GetMapping("/analytics/total-by-date-range")
    public ResponseEntity<Map<String, BigDecimal>> getTotalAmountByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            BigDecimal total = expenseService.getTotalAmountByDateRange(startDate, endDate);
            Map<String, BigDecimal> response = new HashMap<>();
            response.put("total", total);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}