package wobbly.pigeons.expensemanager.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_generator", allocationSize = 1)
    protected Long id;

    @Column(name = "email")
    //@Email  Validator
    private String email;

    @Column(name = "password")
    private String password;


//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
//    @Transient
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

    @ManyToOne
    private Department department;

    @ManyToMany(fetch = EAGER)
    @Column(name = "roles")
    private Set<Role> roles;

    @OneToMany(fetch = EAGER, cascade = CascadeType.MERGE)
    @Column(name = "expenses")
    private Set<Expense> expenses = new HashSet<>();

    public User(String email, String password, String name, LocalDate dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.roles = new HashSet<>();
        this.expenses = new HashSet<>();
    }

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }
//    @Column(name = "Reputation")
//    private String status;  later implementation

}
