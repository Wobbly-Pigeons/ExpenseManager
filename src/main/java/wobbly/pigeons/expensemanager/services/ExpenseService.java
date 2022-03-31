package wobbly.pigeons.expensemanager.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Expense;
import wobbly.pigeons.expensemanager.repositories.ExpenseRepository;

import java.time.LocalDateTime;
import java.util.Date;
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
    public Expense updateExpense(Long id, Expense newExpenseDetails) {
        Expense oldExpense = expenseRepository.findById(id).orElseThrow();
//        oldExpense.setDateOfPurchase(newExpenseDetails.getDateOfPurchase());
//        oldExpense.setCurrentStatus(newExpenseDetails.getCurrentStatus());
//        oldExpense.setCategory(newExpenseDetails.getCategory());
//        oldExpense.setLocalCurrency(newExpenseDetails.getLocalCurrency());
//        oldExpense.setAmount(newExpenseDetails.getAmount());
//        oldExpense.setItemName(newExpenseDetails.getItemName());
//        oldExpense.setItemDescription(newExpenseDetails.getItemDescription()));
//        oldExpense.setCompanyCC(newExpenseDetails.isCompanyCC());

        return oldExpense;
    }


    public List<Object[]> getByPurchaseDate(LocalDateTime purchaseDate) {
        return expenseRepository.getExpenseByPurchaseDate(purchaseDate);

    }

    public List<Object[]> getBySubmissionDate(LocalDateTime submissionDate) {
        return expenseRepository.getExpenseBySubmissionDate(submissionDate);
    }
    public List<Object[]> getByEmployeeId(Long employeeId) {
        return expenseRepository.getExpenseByEmployeeId(employeeId);
    }

    public List<Object[]> getbyCategory(String categoryName) {
        //int categoryId = categoryRepository.getIdForCategory(category);

        return expenseRepository.getExpenseByCategory(categoryName);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(long id) {
        return expenseRepository.findById(id).orElseThrow();
    }

    public void deleteExpense(Long id) {
        this.expenseRepository.deleteById(id);
    }


}
