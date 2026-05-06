package repository;

import domain.Expense;

import java.util.List;

public interface ExpenseRepository {
    List<Expense> findAll();

    void save(Expense expense);

    void delete(int id);
}
