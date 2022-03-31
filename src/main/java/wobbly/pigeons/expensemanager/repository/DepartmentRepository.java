package wobbly.pigeons.expensemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.model.Department;


import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository <Department, Long>  {

    Optional<Department> findByName(String name);

}
