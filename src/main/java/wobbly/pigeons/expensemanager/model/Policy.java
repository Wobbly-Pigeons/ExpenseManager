package wobbly.pigeons.expensemanager.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "policy_generator")
    @SequenceGenerator(name = "policy_generator", sequenceName = "policy_generator", allocationSize = 1)
    protected Long id;


    protected Long budgetMonthly = 1000L;
    protected int numberOfDaysToSubmitAnExpense = 14;


    @ManyToOne
    @JoinColumn(name = "department_id")
    protected Department department;

    @ManyToMany
    @JoinColumn(name = "user_id")
    protected List<User> user;




}
