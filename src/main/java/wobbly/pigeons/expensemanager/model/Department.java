package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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
    @OneToOne
    private DepartmentPolicy policy;

    public Department(String name, List<Employee> employeesList, DepartmentPolicy departmentPolicy){
        this.name = name;
        this.employee = employeesList;
        this.policy = departmentPolicy;
    }

}
