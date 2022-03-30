package wobbly.pigeons.expensemanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Long> {

}
