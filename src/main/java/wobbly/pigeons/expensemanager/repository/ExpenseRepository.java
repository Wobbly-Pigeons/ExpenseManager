package wobbly.pigeons.expensemanager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.model.Expense;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query
            ("SELECT '*' FROM Expense e WHERE e.category = 'categoryName'")
    Collection<Expense> getExpenseByCategory(@Param("categoryName") String categoryName);

    @Query
            ("SELECT '*' FROM Expense e WHERE e.dateOfSubmission = 'submissionDate'")
    List<Expense> getExpenseBySubmissionDate(@Param("submissionDate") LocalDate submissionDate);

    @Query
            ("SELECT '*' FROM Expense e WHERE e.dateOfPurchase = 'purchaseDate'")
    List<Expense> getExpenseByPurchaseDate(@Param("purchaseDate") LocalDateTime purchaseDate);

    @Query
            ("FROM Expense u WHERE u.user = 'employeeId'")
    List<Expense> getExpenseByEmployeeId(@Param("employeeId") Long employeeId);

}
