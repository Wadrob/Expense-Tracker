import cli.ExpenseTrackerCLI;
import repository.CsvExpenseRepository;
import repository.ExpenseRepository;
import service.ExpenseService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {

    Path path = Paths.get("expenseFile.csv");

    public static void main(String[] args) {
        new Application().start();
    }

    private void start() {
        try {
            ExpenseRepository expenseRepository = new CsvExpenseRepository(path);
            ExpenseService expenseService = new ExpenseService(expenseRepository);
            ExpenseTrackerCLI expenseTracker = new ExpenseTrackerCLI(expenseService);
            expenseTracker.run();
        } catch (RuntimeException | IOException exception) {
            exception.printStackTrace();
        }
    }
}