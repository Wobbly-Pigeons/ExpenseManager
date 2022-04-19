package wobbly.pigeons.expensemanager.model.DTO;

import lombok.Data;

@Data
public class ExpenseCommentFormDTO {
    private Long id;
    private String comment;

    public ExpenseCommentFormDTO(Long id) {
        this.id = id;
    }
}
