package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final EmployeeRepository employeesRepository;

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
        Employee employee = employeesRepository.findById(employeeId).orElseThrow();
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
