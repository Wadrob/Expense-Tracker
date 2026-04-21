import helper.FileHelper;
import service.ExpenseService;
import tracker.ExpenseTracker;

import java.io.File;
import java.io.IOException;

public class Application {
    private static final String EXPENSE_FILE_CSV = "expenseFile.csv";

    public static void main(String[] args) {
        new Application().start();
    }

    private void start() {
        try {
            File file = FileHelper.getFile(EXPENSE_FILE_CSV);
            ExpenseService expenseService = new ExpenseService(file);
            ExpenseTracker expenseTracker = new ExpenseTracker(expenseService);
            expenseTracker.run();
        } catch (IOException | RuntimeException exception) {
            exception.printStackTrace();
        }
    }
}