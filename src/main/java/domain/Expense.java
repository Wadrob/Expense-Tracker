package domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Expense {
    private int id;
    private String description;
    private BigDecimal amount;
    private String date;

    public Expense(String description, BigDecimal amount, String date) {
        this.id = 0;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
}