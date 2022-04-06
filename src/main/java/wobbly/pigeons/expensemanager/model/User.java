package wobbly.pigeons.expensemanager.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
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

//    @Transient
//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
    private String name; // We could use a concat String for FirstN + LastN ??

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer age;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @ManyToMany
    @Column(name = "roles")
    private Set<Role> roles;

    @ManyToMany
    @Column(name = "expenses")
    private Set<Expense> expenses;

    public User(String email, String password, String name, LocalDate dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }
//    @Column(name = "Reputation")
//    private String status;  later implementation

}
