package com.shivam.expensetracker.dto;

import java.math.BigDecimal;

public class SummaryResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private int transactionCount;

    public SummaryResponse(BigDecimal totalIncome, BigDecimal totalExpense, int transactionCount) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = totalIncome.subtract(totalExpense);
        this.transactionCount = transactionCount;
    }
    public BigDecimal getTotalIncome() { return totalIncome; }
    public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }
    public BigDecimal getTotalExpense() { return totalExpense; }
    public void setTotalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public int getTransactionCount() { return transactionCount; }
    public void setTransactionCount(int transactionCount) { this.transactionCount = transactionCount; }
}