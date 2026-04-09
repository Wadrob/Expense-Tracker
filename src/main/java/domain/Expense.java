package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private int ID;
    private String description;
    private int amount;

    public Expense(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }
}
