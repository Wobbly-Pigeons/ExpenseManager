package wobbly.pigeons.expensemanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_generator")
    @SequenceGenerator(name = "role_generator", sequenceName = "role_generator", allocationSize = 1)
    private Long id;

    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }
}
