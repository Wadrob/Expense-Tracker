package service;

import domain.Expense;
import helper.FileHelper;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Setter
@AllArgsConstructor
public class ExpenseService {
    private File file;

    public void addExpenseToFile(Expense expense) throws Exception {
        int nextID = addToFile(expense);

        System.out.printf("Expense has been added successfully (ID: %s)%n", nextID);
    }

    private int generateNextId() throws Exception {
        return FileHelper.getExpensesFromFile(file).size() + 1;
    }

    public void readFile() throws IOException {
        List<Expense> expensesFromFile = FileHelper.getExpensesFromFile(file);
        for (Expense expense : expensesFromFile) {
            System.out.printf("ID: %s DATE: %s DESCRIPTION: %s AMOUNT: %s%n", expense.getId(), expense.getDate(), expense.getDescription(), expense.getAmount());
        }
    }

    public void summaryAmount() throws IOException {
        int sumAmount = FileHelper.getExpensesFromFile(file)
                .stream()
                .mapToInt(Expense::getAmount)
                .sum();

        System.out.printf("Total expenses: %d%n", sumAmount);
    }

    public void deleteExpense(int id, String path) throws Exception {
        List<Expense> expensesFromFile = FileHelper.getExpensesFromFile(file);

        if (isRemoved(id, expensesFromFile)) {
            System.out.println("Expense deleted successfully");
        } else {
            System.out.println("Expense not found");
        }

        if (!file.delete()) {
            throw new IOException("Failed to delete file");
        }

        file = FileHelper.getFile(path);

        for (Expense expense : expensesFromFile) {
            addToFile(expense);
        }
    }

    private int addToFile(Expense expense) throws Exception {
        int nextID = generateNextId();

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(String.format("%d,%s,%s,%d\n",
                    nextID,
                    expense.getDate(),
                    escape(expense.getDescription()),
                    expense.getAmount()));
        }

        return nextID;
    }

    private String escape(String value) {
        return value.replace(",", " ");
    }

    private boolean isRemoved(int id, List<Expense> expensesFromFile) {
        return expensesFromFile.removeIf(e -> e.getId() == id);
    }

    public void handleMonth(int month) throws IOException {
        int sumMonth = FileHelper.getExpensesFromFile(file)
                .stream()
                .filter(expense -> LocalDate.parse(expense.getDate()).getMonthValue() == month)
                .mapToInt(Expense::getAmount)
                .sum();

        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        System.out.printf("Total expenses for %s: %d%n", monthName, sumMonth);
    }
}