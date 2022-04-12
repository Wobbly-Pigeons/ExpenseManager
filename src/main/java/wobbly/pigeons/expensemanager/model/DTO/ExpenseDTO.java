package wobbly.pigeons.expensemanager.model.DTO;

import lombok.Data;

@Data
public class ExpenseDTO {
    private long amount;
    private long user_id;
    private byte[] receipt;
}
