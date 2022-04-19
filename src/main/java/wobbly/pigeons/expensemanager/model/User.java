package wobbly.pigeons.expensemanager.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

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
    @Transient
    private Department department;

    @ManyToMany(fetch = EAGER)
    @Column(name = "roles")
    private Set<Role> roles;

    @OneToMany(fetch = EAGER, cascade = CascadeType.MERGE)
    @Column(name = "expenses")
    private Set<Expense> expenses = new HashSet<>();

    @Column(name="Violations")
    private int violations;


    @ManyToOne(fetch = EAGER)@Cascade(value= org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "policy_id")
    @Transient
    private Policy policy;

    public User(String email, String password, String name, LocalDate dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.roles = new HashSet<>();
        this.expenses = new HashSet<>();
        this.department = new Department();
        this.violations = 0;
       // this.policy = new IndividualPolicy();

    }

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }








}
