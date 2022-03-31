package wobbly.pigeons.expensemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Department;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository <Department, Long>  {

    Optional<Department> findByName(String name);


}
