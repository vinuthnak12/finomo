package com.finomo.config;

import com.finomo.entity.Expense;
import com.finomo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Load sample data if the database is empty
        if (expenseRepository.count() == 0) {
            loadSampleData();
        }
    }
    
    private void loadSampleData() {
        // Create sample expenses without setting IDs (let them be auto-generated)
        Expense expense1 = new Expense();
        expense1.setDescription("Grocery shopping at Walmart");
        expense1.setAmount(new BigDecimal("125.50"));
        expense1.setCategory("Groceries");
        expense1.setDate(LocalDate.now().minusDays(5));
        expense1.setNotes("Weekly grocery shopping");
        
        Expense expense2 = new Expense();
        expense2.setDescription("Gas station fill-up");
        expense2.setAmount(new BigDecimal("45.75"));
        expense2.setCategory("Transportation");
        expense2.setDate(LocalDate.now().minusDays(3));
        expense2.setNotes("Full tank for the week");
        
        Expense expense3 = new Expense();
        expense3.setDescription("Electric bill payment");
        expense3.setAmount(new BigDecimal("89.30"));
        expense3.setCategory("Utilities");
        expense3.setDate(LocalDate.now().minusDays(10));
        expense3.setNotes("Monthly electricity bill");
        
        Expense expense4 = new Expense();
        expense4.setDescription("Lunch at restaurant");
        expense4.setAmount(new BigDecimal("25.00"));
        expense4.setCategory("Dining");
        expense4.setDate(LocalDate.now().minusDays(2));
        expense4.setNotes("Business lunch meeting");
        
        Expense expense5 = new Expense();
        expense5.setDescription("Netflix subscription");
        expense5.setAmount(new BigDecimal("15.99"));
        expense5.setCategory("Entertainment");
        expense5.setDate(LocalDate.now().minusDays(1));
        expense5.setNotes("Monthly subscription");
        
        // Save sample data
        expenseRepository.save(expense1);
        expenseRepository.save(expense2);
        expenseRepository.save(expense3);
        expenseRepository.save(expense4);
        expenseRepository.save(expense5);
        
        System.out.println("Sample expense data loaded successfully!");
    }
}