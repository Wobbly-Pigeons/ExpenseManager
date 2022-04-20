package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "department_generator")
    @SequenceGenerator(name = "department_generator", sequenceName = "department_generator", allocationSize = 1)
    private long id;
    private String name;

    @OneToMany
    private Collection<Employee> employee;

    private Long departmentBudget;

    public Department(String name){
        this.name = name;
        this.departmentBudget = getBudgetFromPolicy();
    }

    public Long getBudgetFromPolicy(){

        DepartmentPolicy departmentPolicy = new DepartmentPolicy();

        return departmentPolicy.getBudgetMonthly();
    }

}
