package repository;

import domain.Expense;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvExpenseRepositoryTest {

    private Path tempFile;
    private CsvExpenseRepository repo;

    @BeforeEach
    void setup() throws Exception {
        tempFile = Files.createTempFile("expense", ".csv");
        repo = new CsvExpenseRepository(tempFile);
    }

    @Test
    void shouldSaveAndReadExpense() {
        repo.save(new Expense("test", BigDecimal.TEN, "2026-05-06"));

        List<Expense> result = repo.findAll();

        assertFalse(result.isEmpty());
        assertEquals("test", result.get(0).getDescription());
    }

    @Test
    void shouldDeleteExpense() {
        repo.save(new Expense("a", BigDecimal.TEN, "2026-05-06"));
        repo.save(new Expense("b", BigDecimal.TEN, "2026-05-06"));

        List<Expense> all = repo.findAll();
        int idToDelete = all.get(0).getId();

        repo.delete(idToDelete);

        List<Expense> after = repo.findAll();

        assertEquals(1, after.size());
    }

    @AfterEach
    void cleanup() throws Exception {
        Files.deleteIfExists(tempFile);
    }
}