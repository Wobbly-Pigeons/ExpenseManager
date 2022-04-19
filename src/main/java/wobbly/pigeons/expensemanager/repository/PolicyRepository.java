package wobbly.pigeons.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.model.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
