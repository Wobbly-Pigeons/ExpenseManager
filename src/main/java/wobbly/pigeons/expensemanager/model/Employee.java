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

    public Employee(String email, String password, String name, LocalDate dob) {
        super(email, password, name, dob);
    }
}
