package wobbly.pigeons.expensemanager.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Inheritance
@Entity
public class Managers extends Employees {

    @OneToMany
    private List<Employees> employees;

    @ManyToOne
    @JoinColumn(name = "manager_role_id")
    private Roles managerRole ;
}
