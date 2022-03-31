package wobbly.pigeons.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.IndividualPolicy;

@Repository
public interface IndividualPolicyRepository extends JpaRepository<IndividualPolicy, Long> {

}
