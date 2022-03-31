package wobbly.pigeons.expensemanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "Email")
    //@Email  Validator
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "Name")
    private String name; // We could use a concat String for FirstN + LastN ??

    @Column(name = "Date of Birth")
    private Date dob;

    @ManyToMany
    @Column(name = "Roles")
    private Set<Roles> roles;

    @ManyToMany
    @Column(name = "Expenses")
    private Set<Expense> expenses;

//    @Column(name = "Reputation")
//    private String status;  later implementation

}
