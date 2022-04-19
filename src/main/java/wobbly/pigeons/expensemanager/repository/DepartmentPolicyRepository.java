package wobbly.pigeons.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.model.DepartmentPolicy;

@Repository
public interface DepartmentPolicyRepository extends JpaRepository<DepartmentPolicy, Long> {
}