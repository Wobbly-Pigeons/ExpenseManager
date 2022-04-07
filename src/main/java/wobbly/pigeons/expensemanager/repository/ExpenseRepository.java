package wobbly.pigeons.expensemanager.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findExpensesByUser(User user, Pageable pageable);
}