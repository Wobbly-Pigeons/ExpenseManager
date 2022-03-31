package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Department {

    @Id
    private long id;
    private String name;
    @OneToMany
    private Employees employee;
    @OneToOne
    private Policy policy;

}
