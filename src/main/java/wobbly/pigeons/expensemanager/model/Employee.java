package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@Entity
public class Employee extends User {

    @ManyToOne
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "employee_role_id")
    private Role employeeRole;

    public Employee(String name, String email, String password, LocalDate dob) {
        super(name,email,password,dob);

    }
}
