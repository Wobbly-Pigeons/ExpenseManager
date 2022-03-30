package wobbly.pigeons.expensemanager.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Expense;
import wobbly.pigeons.expensemanager.repositories.ExpenseRepository;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;


    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void addExpense(Expense expense){
        expenseRepository.save(expense);
    }

    //public List<Object[]> getByPurchaseDate(Date fromDate, Date tillDate) {
        //return expenseRepository.getExpenseByDate(fromDate, tillDate);

    //}

    public List<Object[]> getbyCategory(String category) {
        int categoryId = categoryRepository.getIdForCategory(category);

        return expenseRepository.getExpenseByCategory(categoryId);

    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(long id) {
        return expenseRepository.findById(id).orElseThrow();


    }
}
