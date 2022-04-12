package wobbly.pigeons.expensemanager.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@Entity
public class Employee extends User {

    @ManyToOne(fetch = EAGER)
    @Cascade(value= org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Employee(String email, String password, String name, LocalDate dob) {
        super(email, password, name, dob);
    }


}
