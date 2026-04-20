import helper.FileHelper;
import service.ExpenseService;
import tracker.ExpenseTracker;

public class Application {
    private final static String EXPENSE_FILE_CSV = "expenseFile.csv";

    public static void main(String[] args) {
        new Application().start();
    }

    private void start() {
        try {
            var file = FileHelper.getFile(EXPENSE_FILE_CSV);
            var expenseTracerService = new ExpenseService(file);
            var expenseTracker = new ExpenseTracker(expenseTracerService);
            expenseTracker.run();
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void handleError(Exception e) {
        System.err.println("Error: " + e.getMessage());
    }
}