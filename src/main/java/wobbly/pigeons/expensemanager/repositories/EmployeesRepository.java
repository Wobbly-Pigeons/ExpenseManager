package wobbly.pigeons.expensemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Long> {

}
