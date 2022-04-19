package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wobbly.pigeons.expensemanager.model.CurrenciesAllowed;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseCommentFormDTO;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO2;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;
import wobbly.pigeons.expensemanager.repository.ManagerRepository;
import wobbly.pigeons.expensemanager.util.ConverterRestClient;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final EmployeeRepository employeesRepository;
    private final ConverterRestClient converterRestClient;
    private final ManagerRepository managerRepository;
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

  public Expense addExpense(ExpenseDTO2 expenseDTO2, Principal principal) throws IOException {
    Employee employee = employeesRepository.findByEmail(principal.getName());
    if (employee == null) {
      employee = managerRepository.findByEmail(principal.getName());
    }

    Expense newExpense = new Expense(expenseDTO2.getReceipt().getBytes(), expenseDTO2.getAmount(), employee);
    newExpense.setCompanyCC(expenseDTO2.isCompanyCC());
    newExpense.setItemName(expenseDTO2.getItemName());
    newExpense.setItemDescription(expenseDTO2.getItemDescription());
    newExpense.setComment(expenseDTO2.getComment());
    newExpense.setCategory(expenseDTO2.getCategory());
    newExpense.setCurrentStatus(ReceiptStatuses.SUBMITTED);
    newExpense.setLocalCurrency(expenseDTO2.getLocalCurrency());
    newExpense.setDateModified(LocalDateTime.now());
    if(expenseDTO2.getLocalCurrency() != CurrenciesAllowed.EUR) {
        newExpense.setAmount(converterRestClient.getConversionAmount(
                newExpense.getLocalCurrency().toString(), "EUR", newExpense.getAmount()));
    }
    return expenseRepository.save(newExpense);
    }

    public Expense getExpenseById(long id) {
        return expenseRepository.findById(id).orElseThrow();
    }

    public Expense updateExpense(Long id, Expense newExpenseDetails) {
        Expense oldExpense = expenseRepository.findById(id).orElseThrow();
        oldExpense.setDateOfPurchase(newExpenseDetails.getDateOfPurchase());
        oldExpense.setCategory(newExpenseDetails.getCategory());
        oldExpense.setDateModified(LocalDateTime.now());
        oldExpense.setLocalCurrency(newExpenseDetails.getLocalCurrency());
        oldExpense.setAmount(newExpenseDetails.getAmount());
        oldExpense.setItemName(newExpenseDetails.getItemName());
        oldExpense.setItemDescription(newExpenseDetails.getItemDescription());
        oldExpense.setCompanyCC(newExpenseDetails.isCompanyCC());
        expenseRepository.flush();
        return oldExpense;
    }

    public void deleteExpense(Long id) {
        this.expenseRepository.deleteById(id);
    }

    public Collection<Expense> getExpensesByEmployeeId(Long employeeId) {
        Employee employee = employeesRepository.findById(employeeId).orElseThrow();
        return employee.getExpenses();
    }

    public List<Expense> getExpensesBySubmissionDate(LocalDate submissionDate) {

        List<Expense> all = expenseRepository.findAll();

        List<Expense> orderBySubmissionDate = new ArrayList<>();

        for (Expense expense : all) {

            if (expense.getDateOfSubmission().equals(submissionDate)) {
                orderBySubmissionDate.add(expense);
            }

        }

        return orderBySubmissionDate;
    }

    public List<Expense> getExpensesByPurchaseDate(LocalDate purchaseDate) {

        List<Expense> all = expenseRepository.findAll();
        List<Expense> purchaseDateList = new ArrayList<>();

        for (Expense expense : all) {
            if (expense.getDateOfPurchase().isEqual(purchaseDate)) {
                purchaseDateList.add(expense);
            }
        }
        return purchaseDateList;
    }

    public List<Expense> getExpensesByCategory(String categoryName) {

        List<Expense> all = expenseRepository.findAll();
        List<Expense> expensesByCategory = new ArrayList<Expense>();

        for (Expense expense : all) {
            if (expense.getCategory().toString().equalsIgnoreCase(categoryName)) {
                expensesByCategory.add(expense);
            }
        }
        return expensesByCategory;
    }


    public void approveExpense(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        expense.setCurrentStatus(ReceiptStatuses.APPROVED);
        expense.setDateModified(LocalDateTime.now());
        expense.setDateOfStatusChange(LocalDateTime.now());
        expenseRepository.flush();
    }

    public void commentAndReturnExpenseToEmployee(Long id, String status, ExpenseCommentFormDTO comment) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        expense.setComment(comment.getComment());
        expense.setDateModified(LocalDateTime.now());
        expense.setDateOfStatusChange(LocalDateTime.now());
        if(status.equals("deny")) {
            expense.setCurrentStatus(ReceiptStatuses.REJECTED);
        } else if (status.equals("needs-info")) {
            expense.setCurrentStatus(ReceiptStatuses.NEEDSFURTHERINFO);
        }
        expenseRepository.flush();
    }
}
