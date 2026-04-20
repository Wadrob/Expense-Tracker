package service;

import domain.Expense;
import helper.FileHelper;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    private File file;
    private ExpenseService service;

    @BeforeEach
    void setup() throws Exception {
        file = File.createTempFile("test", ".csv");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("id,date,description,amount\n");
        }

        service = new ExpenseService(file);
    }

    @AfterEach
    void cleanup() {
        file.delete();
    }

    @Test
    void shouldAddExpense() throws Exception {
        Expense expense = new Expense("Coffee", 10);

        service.addExpenseToFile(expense);

        List<Expense> expenses = FileHelper.getExpensesFromFile(file);

        assertEquals(1, expenses.size());
        assertEquals("Coffee", expenses.get(0).getDescription());
    }

    @Test
    void shouldDeleteExpense() throws Exception {
        Expense e1 = new Expense("A", 10);
        e1.setId(1);

        Expense e2 = new Expense("B", 20);
        e2.setId(2);

        service.addExpenseToFile(e1);
        service.addExpenseToFile(e2);

        service.deleteExpense(1, file.getPath());

        List<Expense> expenses = FileHelper.getExpensesFromFile(file);

        assertEquals(1, expenses.size());
        assertEquals(1, expenses.get(0).getId());
    }
}