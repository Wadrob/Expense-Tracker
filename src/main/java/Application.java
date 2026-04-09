import helper.FileHelper;
import service.ExpenseTracerService;
import tracker.ExpenseTracker;

public class Application {

    public static void main(String[] args) {
        new Application().start();
    }

    private void start() {
        try {
            var file = FileHelper.getFile();
            var expenseTracerService = new ExpenseTracerService(file);
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