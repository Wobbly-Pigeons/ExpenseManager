package wobbly.pigeons.expensemanager.services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.models.Employees;
import wobbly.pigeons.expensemanager.models.Expense;
import wobbly.pigeons.expensemanager.repositories.EmployeesRepository;
import wobbly.pigeons.expensemanager.repositories.ExpenseRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseService<EmployeeRepository> {

    private final ExpenseRepository expenseRepository;

    private final EmployeesRepository employeesRepository;



    public Expense addExpense(Expense expense){
       return expenseRepository.save(expense);
    }
    
    public Expense updateExpense(Long id, Expense newExpenseDetails) {
        Expense oldExpense = expenseRepository.findById(id).orElseThrow();
        oldExpense.setDateOfPurchase(newExpenseDetails.getDateOfPurchase());
        oldExpense.setCurrentStatus(newExpenseDetails.getCurrentStatus());
     oldExpense.setCategory(newExpenseDetails.getCategory());
        oldExpense.setDateModified(LocalDateTime.now());
        oldExpense.setLocalCurrency(newExpenseDetails.getLocalCurrency());
       oldExpense.setAmount(newExpenseDetails.getAmount());
       oldExpense.setItemName(newExpenseDetails.getItemName());
       oldExpense.setItemDescription(newExpenseDetails.getItemDescription());
       oldExpense.setCompanyCC(newExpenseDetails.isCompanyCC());

        return oldExpense;
    }


    public List<Expense> getByPurchaseDate(LocalDateTime purchaseDate) {
        return expenseRepository.getExpenseByPurchaseDate(purchaseDate);

    }

    public List<Expense> getBySubmissionDate(LocalDateTime submissionDate) {
        return expenseRepository.getExpenseBySubmissionDate(submissionDate);
    }
    public Collection<Expense> getByEmployeeId(Long employeeId) {
        Employees employee = employeesRepository.findById(employeeId).orElseThrow();
        return employee.getExpenses();
    }

    public Collection<Expense> getByCategory (String categoryName) {
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
