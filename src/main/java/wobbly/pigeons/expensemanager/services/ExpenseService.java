package wobbly.pigeons.expensemanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Expense;
import wobbly.pigeons.expensemanager.repositories.ExpenseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    public List<Expense> getExpensesList() {
        return expenseRepository.findAll();
    }
}
