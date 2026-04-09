package tracker;

import domain.Expense;
import service.ExpenseTracerService;

import java.util.Scanner;

public class ExpenseTracker {

    private final ExpenseTracerService service;
    private final Scanner scanner;

    public ExpenseTracker(ExpenseTracerService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void run() {

        while (true) {
            System.out.println("Use command add/quit");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "add" -> handleAdd();
                case "quit" -> {
                    System.out.println("Program shutdown");
                    return;
                }

                default -> System.out.println("Unknown command, please try again.");
            }
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
