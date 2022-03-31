package wobbly.pigeons.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
