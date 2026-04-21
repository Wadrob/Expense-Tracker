package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Expense {
    private int id;
    private LocalDate date;
    private String description;
    private BigDecimal amount;

    public Expense(String description, BigDecimal amount) {
        this.date = LocalDate.now();
        this.description = description;
        this.amount = amount;
    }
}
