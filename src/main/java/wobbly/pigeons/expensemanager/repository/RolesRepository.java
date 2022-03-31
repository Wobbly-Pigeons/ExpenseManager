package wobbly.pigeons.expensemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
}
