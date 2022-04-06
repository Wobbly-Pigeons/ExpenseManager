package wobbly.pigeons.expensemanager.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    protected Long id;

    @Column(name = "email")
    //@Email  Validator
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name; // We could use a concat String for FirstN + LastN ??

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @ManyToMany
    @Column(name = "roles")
    private Set<Role> roles;

    @ManyToMany
    @Column(name = "expenses")
    private Set<Expense> expenses;

    public User(String name, String email, String password, LocalDate dob) {
        this.name =name;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

//    @Column(name = "Reputation")
//    private String status;  later implementation

}
