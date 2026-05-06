package service;

import domain.Expense;
import repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public void addExpense(String description, BigDecimal amount) {
        Expense expense = new Expense(
                description,
                amount,
                LocalDate.now().toString()
        );

        repository.save(expense);
    }

    public void getAllExpenses() {
        List<Expense> allEx = repository.findAll();

        for (Expense expense : allEx) {
            System.out.printf("ID: %d DESCRIPTION: %s AMOUNT: %s DATE: %s \n",
                    expense.getId(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getDate());
        }
    }

    public BigDecimal getTotalAmount() {
        return repository.findAll()
                .stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void deleteExpense(int id) {
        repository.delete(id);
    }

    public BigDecimal getMonthlyTotal(int month) {
        return repository.findAll()
                .stream()
                .filter(expense -> LocalDate.parse(expense.getDate()).getMonthValue() == month)
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}