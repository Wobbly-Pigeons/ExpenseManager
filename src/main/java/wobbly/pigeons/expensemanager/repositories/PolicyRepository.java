package wobbly.pigeons.expensemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
