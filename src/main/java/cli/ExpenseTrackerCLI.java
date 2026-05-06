package cli;

import service.ExpenseService;

import java.math.BigDecimal;
import java.util.Scanner;

public class ExpenseTrackerCLI {
    private final ExpenseService service;
    private final Scanner scanner;

    public ExpenseTrackerCLI(ExpenseService service) {
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
        while (true) {
            System.out.println("Type number value of month or quit to come back");

            String monthInput = scanner.nextLine();

            if (monthInput.equalsIgnoreCase("quit")) {
                return;
            }

            int month;

            try {
                month = Integer.parseInt(monthInput);
                if (month <= 0) {
                    System.out.println("Month must be positive");
                    continue;
                }

                if (month > 12) {
                    System.out.println("There is no month");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Month must be a number");
                continue;
            }

            try {
                BigDecimal monthlyTotal = service.getMonthlyTotal(month);
                System.out.println("Amount for month " + month + ": " + monthlyTotal);
                break;
            } catch (Exception e) {
                System.out.println("Error when adding expenses for month: " + e.getMessage());
            }
        }
    }

    private void handleDelete() {
        while (true) {
            System.out.println("Type ID to remove or quit to come back");

            String idInput = scanner.nextLine();

            if (idInput.equalsIgnoreCase("quit")) {
                return;
            }

            int id;

            try {
                id = Integer.parseInt(idInput);
                if (id <= 0) {
                    System.out.println("Id must be positive");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Id must be a number");
                continue;
            }

            try {
                service.deleteExpense(id);
                break;
            } catch (Exception e) {
                System.out.println("Error when removing id: " + e.getMessage());
            }
        }
    }

    private void handleInfo() {
        System.out.println("""
                                
                Type add to add new record
                Type list to list all records in file
                Type summary to list summary of amount in file
                Type delete to delete record
                Type summary month to list expenses for month
                Type quit to quit program
                """);
    }

    private void handleSummary() {
        BigDecimal totalAmount = service.getTotalAmount();
        System.out.println(totalAmount);
    }

    private void handleList() {
        try {
            service.getAllExpenses();
        } catch (Exception e) {
            System.out.println("Error when reading file: " + e.getMessage());
        }
    }

    private void handleAdd() {
        String description;

        while (true) {
            System.out.println("Please add description or type quit to come back");
            description = scanner.nextLine();

            if (description.equalsIgnoreCase("quit")) {
                return;
            }

            if (description.isBlank()) {
                System.out.println("Description cannot be empty");
                continue;
            }

            System.out.println("Amount:");

            String amountInput = scanner.nextLine();
            BigDecimal amount;


            try {
                amount = new BigDecimal(amountInput);
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Amount must be positive");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Amount must be a number");
                continue;
            }

            try {
                service.addExpense(description, amount);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
