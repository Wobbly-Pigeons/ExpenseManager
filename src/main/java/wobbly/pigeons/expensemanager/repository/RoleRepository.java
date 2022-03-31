package wobbly.pigeons.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
