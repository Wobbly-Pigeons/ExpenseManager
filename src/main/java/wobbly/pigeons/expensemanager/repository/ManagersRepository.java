package wobbly.pigeons.expensemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Managers;

@Repository
public interface ManagersRepository extends JpaRepository<Managers,Long> {
}
