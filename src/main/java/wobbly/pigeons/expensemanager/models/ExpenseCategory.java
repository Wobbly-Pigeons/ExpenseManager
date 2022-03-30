package wobbly.pigeons.expensemanager.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpenseCategory {

    @Id
    private long id;

    private String name;

}
