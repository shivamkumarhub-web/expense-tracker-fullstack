package com.shivam.expensetracker.controller;

import com.shivam.expensetracker.dto.ExpenseRequest;
import com.shivam.expensetracker.dto.ExpenseResponse;
import com.shivam.expensetracker.dto.SummaryResponse;
import com.shivam.expensetracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expenses")
@SecurityRequirement(name = "Bearer Authentication")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) { this.expenseService = expenseService; }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses(Authentication auth) {
        return ResponseEntity.ok(expenseService.getUserExpenses(auth.getName()));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request, Authentication auth) {
        return ResponseEntity.ok(expenseService.createExpense(request, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request, Authentication auth) {
        return ResponseEntity.ok(expenseService.updateExpense(id, request, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, Authentication auth) {
        expenseService.deleteExpense(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getMonthlySummary(
            @RequestParam int year, @RequestParam int month, Authentication auth) {
        return ResponseEntity.ok(expenseService.getMonthlySummary(auth.getName(), year, month));
    }
}