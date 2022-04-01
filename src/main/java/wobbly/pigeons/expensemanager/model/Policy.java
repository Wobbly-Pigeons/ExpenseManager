package wobbly.pigeons.expensemanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public abstract class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "policy_generator")
    @SequenceGenerator(name = "policy_generator", sequenceName = "policy_generator", allocationSize = 1)
    protected Long id;

    protected Long budgetMonthly;
}
