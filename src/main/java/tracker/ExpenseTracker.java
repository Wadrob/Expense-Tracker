package tracker;

import domain.Expense;
import service.ExpenseService;

import java.io.IOException;
import java.util.Scanner;

public class ExpenseTracker {
    private final static String EXPENSE_FILE_CSV = "expenseFile.csv";

    private final ExpenseService service;
    private final Scanner scanner;

    public ExpenseTracker(ExpenseService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void run() {

        while (true) {
            System.out.println("Enter a command or type info for commands info");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "add" -> handleAdd();
                case "list" -> handleList();
                case "summary" -> handleSummary();
                case "info" -> handleInfo();
                case "delete" -> handleDelete();
                case "summary month" -> handleSummaryMonth();
                case "quit" -> {
                    System.out.println("Program shutdown");
                    return;
                }

                default -> System.out.println("Unknown command, please try again.");
            }
        }
    }

    private void handleSummaryMonth() {
        System.out.println("Summary month: ");

        String idInput = scanner.nextLine();
        int month;

        try {
            month = Integer.parseInt(idInput);
            if (month <= 0) {
                throw new IllegalArgumentException("Month must be positive");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Month must be a number");
        }

        try {
            service.handleMonth(month);
        } catch (Exception e) {
            System.out.println("Error when adding expenses for month: " + e.getMessage());
        }
    }

    private void handleDelete() {
        System.out.println("ID to remove: ");

        String idInput = scanner.nextLine();
        int id;

        try {
            id = Integer.parseInt(idInput);
            if (id <= 0) {
                throw new IllegalArgumentException("Id must be positive");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id must be a number");
        }

        try {
            service.deleteExpense(id, EXPENSE_FILE_CSV);
        } catch (Exception e) {
            System.out.println("Error when removing id: " + e.getMessage());
        }
    }

    private void handleInfo() {
        System.out.println("""
                Type add to add new record
                Type list to list all records in file
                Type summary to list summary of amount in file
                Type delete to delete record
                Type summary month to list expenses for month
                Type quit to quit program""");
    }

    private void handleSummary() {
        try {
            service.summaryAmount();
        } catch (IOException e) {
            System.out.println("Problem with amount adding: " + e.getMessage());
        }
    }

    private void handleList() {
        try {
            service.readFile();
        } catch (Exception e) {
            System.out.println("Error when reading file: " + e.getMessage());
        }
    }

    private void handleAdd() {
        try {
            Expense expense = createExpense();
            if (expense != null) {
                service.addExpenseToFile(expense);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Expense createExpense() {
        System.out.println("Please add description or type quit to come back");
        String description = scanner.nextLine();

        if (description.equalsIgnoreCase("quit")) {
            return null;
        }

        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        System.out.println("Amount:");

        String amountInput = scanner.nextLine();

        int amount;


        try {
            amount = Integer.parseInt(amountInput);
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a number");
        }

        return new Expense(description, amount);
    }
}
