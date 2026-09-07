package com.shivam.expensetracker.service;

import com.shivam.expensetracker.dto.ExpenseRequest;
import com.shivam.expensetracker.dto.ExpenseResponse;
import com.shivam.expensetracker.dto.SummaryResponse;
import com.shivam.expensetracker.entity.Category;
import com.shivam.expensetracker.entity.Expense;
import com.shivam.expensetracker.exception.ResourceNotFoundException;
import com.shivam.expensetracker.repository.CategoryRepository;
import com.shivam.expensetracker.repository.ExpenseRepository;
import com.shivam.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository,
                          UserRepository userRepository) {
        this.expenseRepository = expenseRepository; this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest request, String username) {
        Long userId = userRepository.findByUsername(username).orElseThrow().getId();
        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setType(request.getType());
        expense.setExpenseDate(request.getExpenseDate() != null ? request.getExpenseDate() : LocalDate.now());
        expense.setUserId(userId);
        if (request.getCategoryId() != null) {
            Category cat = categoryRepository.findById(request.getCategoryId()).orElse(null);
            expense.setCategory(cat);
        }
        expenseRepository.save(expense);
        return toResponse(expense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getUserExpenses(String username) {
        Long userId = userRepository.findByUsername(username).orElseThrow().getId();
        return expenseRepository.findByUserIdOrderByExpenseDateDesc(userId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request, String username) {
        Long userId = userRepository.findByUsername(username).orElseThrow().getId();
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        if (!expense.getUserId().equals(userId)) throw new ResourceNotFoundException("Expense not found");
        if (request.getDescription() != null) expense.setDescription(request.getDescription());
        if (request.getAmount() != null) expense.setAmount(request.getAmount());
        if (request.getType() != null) expense.setType(request.getType());
        if (request.getExpenseDate() != null) expense.setExpenseDate(request.getExpenseDate());
        if (request.getCategoryId() != null) {
            expense.setCategory(categoryRepository.findById(request.getCategoryId()).orElse(null));
        }
        expenseRepository.save(expense);
        return toResponse(expense);
    }

    @Transactional
    public void deleteExpense(Long id, String username) {
        Long userId = userRepository.findByUsername(username).orElseThrow().getId();
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        if (!expense.getUserId().equals(userId)) throw new ResourceNotFoundException("Expense not found");
        expenseRepository.delete(expense);
    }

    @Transactional(readOnly = true)
    public SummaryResponse getMonthlySummary(String username, int year, int month) {
        Long userId = userRepository.findByUsername(username).orElseThrow().getId();
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        List<Expense> expenses = expenseRepository.findByUserIdAndDateRange(userId, start, end);
        BigDecimal totalIncome = expenseRepository.sumByTypeAndDateRange(userId, "INCOME", start, end);
        BigDecimal totalExpense = expenseRepository.sumByTypeAndDateRange(userId, "EXPENSE", start, end);
        return new SummaryResponse(totalIncome, totalExpense, expenses.size());
    }

    private ExpenseResponse toResponse(Expense e) {
        ExpenseResponse r = new ExpenseResponse();
        r.setId(e.getId()); r.setDescription(e.getDescription()); r.setAmount(e.getAmount());
        r.setType(e.getType()); r.setExpenseDate(e.getExpenseDate());
        r.setCategoryName(e.getCategory() != null ? e.getCategory().getName() : null);
        r.setCreatedAt(e.getCreatedAt());
        return r;
    }
}