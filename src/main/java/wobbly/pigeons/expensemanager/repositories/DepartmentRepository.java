package wobbly.pigeons.expensemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
