package service;

import domain.Expense;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ExpenseRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository repository;

    @InjectMocks
    private ExpenseService service;

    @Test
    void shouldCalculateTotalAmount() {
        when(repository.findAll()).thenReturn(List.of(
                new Expense("a", BigDecimal.valueOf(10), "2026-05-01"),
                new Expense("b", BigDecimal.valueOf(20), "2026-05-02")
        ));

        BigDecimal result = service.getTotalAmount();

        assertEquals(BigDecimal.valueOf(30), result);
    }

    @Test
    void shouldCalculateMonthlyTotal() {
        when(repository.findAll()).thenReturn(List.of(
                new Expense("a", BigDecimal.valueOf(10), "2026-05-01"),
                new Expense("b", BigDecimal.valueOf(20), "2026-04-01")
        ));

        BigDecimal result = service.getMonthlyTotal(5);

        assertEquals(BigDecimal.valueOf(10), result);
    }

    @Test
    void shouldCallRepositoryDelete() {
        service.deleteExpense(5);

        verify(repository, times(1)).delete(5);
    }

    @Test
    void shouldSaveExpense() {
        service.addExpense("test", BigDecimal.valueOf(100));

        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);

        verify(repository).save(captor.capture());

        Expense saved = captor.getValue();

        assertEquals("test", saved.getDescription());
        assertEquals(BigDecimal.valueOf(100), saved.getAmount());
        assertNotNull(saved.getDate());
    }
}