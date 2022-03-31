package wobbly.pigeons.expensemanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.model.Expense;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Long> {

    @Query
            ("SELECT '*' FROM Expense e WHERE e.category = 'categoryName'")
    public List<Object[]> getExpenseByCategory(@Param("categoryName") String categoryName);

    @Query
            ("SELECT '*' FROM Expense e WHERE e.dateOfSubmission = 'submissionDate'")
    public List<Object[]> getExpenseBySubmissionDate(@Param("submissionDate") LocalDateTime submissionDate);

    @Query
            ("SELECT '*' FROM Expense e WHERE e.dateOfPurchase = 'purchaseDate'")
    public List<Object[]> getExpenseByPurchaseDate(@Param("purchaseDate") LocalDateTime purchaseDate);

    @Query
            ("SELECT '*' FROM Employees u WHERE u.id = 'employeeId'")
    public List<Object[]> getExpenseByEmployeeId(@Param("employeeId") Long employeeId);

}
