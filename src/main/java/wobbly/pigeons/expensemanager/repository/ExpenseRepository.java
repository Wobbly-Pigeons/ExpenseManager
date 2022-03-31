package wobbly.pigeons.expensemanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.models.Expense;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Long> {

    @Query
            ("SELECT '*' FROM Expense e WHERE e.category = 'categoryName'")
    public Collection<Expense> getExpenseByCategory(@Param("categoryName") String categoryName);

    @Query
            ("SELECT '*' FROM Expense e WHERE e.dateOfSubmission = 'submissionDate'")
    public List<Expense> getExpenseBySubmissionDate(@Param("submissionDate") LocalDateTime submissionDate);

    @Query
            ("SELECT '*' FROM Expense e WHERE e.dateOfPurchase = 'purchaseDate'")
    public List<Expense> getExpenseByPurchaseDate(@Param("purchaseDate") LocalDateTime purchaseDate);

    @Query
            ("SELECT '*' FROM Expense u WHERE u.user_id = 'employeeId'")
    public List<Expense> getExpenseByEmployeeId(@Param("employeeId") Long employeeId);

}
