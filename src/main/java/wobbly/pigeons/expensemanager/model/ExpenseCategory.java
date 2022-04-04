package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "expenseCategory_generator")
    @SequenceGenerator(name = "expenseCategory_generator", sequenceName = "expenseCategory_generator", allocationSize = 1)
    private long id;

    private String name;

}
