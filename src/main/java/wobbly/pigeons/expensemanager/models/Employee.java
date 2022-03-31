package wobbly.pigeons.expensemanager.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Inheritance
@Entity
public class Employees extends UserModel {

    @ManyToOne
    private Managers managers;

    @ManyToOne
    @JoinColumn(name = "employee_role_id")
    private Roles employeeRole;
}
