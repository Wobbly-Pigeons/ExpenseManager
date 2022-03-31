package wobbly.pigeons.expensemanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Inheritance
@Entity
public class Employee extends UserModel {

    @ManyToOne
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "employee_role_id")
    private Role employeeRole;
}
