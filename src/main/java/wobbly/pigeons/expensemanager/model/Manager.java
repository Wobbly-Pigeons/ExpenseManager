package wobbly.pigeons.expensemanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Inheritance
@Entity
public class Manager extends Employee {

    @OneToMany
    private List<Employee> employees;

    public Manager(String email, String password, String name, LocalDate dob) {
        super(email, password, name, dob);
    }
}
