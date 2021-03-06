package wobbly.pigeons.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Manager findByEmail(String email);
}
