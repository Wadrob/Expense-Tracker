package service;

import domain.Expense;
import helper.FileHelper;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileWriter;

@AllArgsConstructor
public class ExpenseTracerService {
    private File file;

    public void addExpenseToFile(Expense expense) throws Exception {
        int nextID = generateNextId();

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(String.format("%d,%s,%d\n",
                    nextID,
                    expense.getDescription(),
                    expense.getAmount()));
        }

        System.out.printf("Expense added successfully (ID: %s)%n", nextID);
    }

    private int generateNextId() throws Exception {
        return FileHelper.getExpensesFromFile(file).size() + 1;
    }
}