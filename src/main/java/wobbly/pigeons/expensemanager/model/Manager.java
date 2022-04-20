package wobbly.pigeons.expensemanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@RequiredArgsConstructor
@Inheritance
@Entity
@Table(name = "manager")
public class Manager extends Employee {

    @OneToMany(fetch = EAGER, mappedBy = "manager", cascade = CascadeType.MERGE)
    private List<Employee> employees = new ArrayList<>();

    public Manager(String email, String password, String name, LocalDate dob) {
        super(email, password, name, dob);
    }
}
