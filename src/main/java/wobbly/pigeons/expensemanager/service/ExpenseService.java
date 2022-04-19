package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import wobbly.pigeons.expensemanager.model.CurrenciesAllowed;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseCommentFormDTO;
>>>>>>> apr12
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO2;
import wobbly.pigeons.expensemanager.model.*;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;
import wobbly.pigeons.expensemanager.repository.ManagerRepository;
import wobbly.pigeons.expensemanager.repository.PolicyRepository;
import wobbly.pigeons.expensemanager.util.ConverterRestClient;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public final class ExpenseService {

    private final PolicyRepository policyRepository;
    private final EmployeeService employeeService;
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
        newExpense.setAmount(converterRestClient.getConversionAmount(newExpense.getLocalCurrency().toString(), "EUR", newExpense.getAmount()));
    }
    return expenseRepository.save(newExpense);
    }




    public Expense getExpenseById (long id){
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

    //Bonus 1
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


    public Long sumOfAmountInMonthExpensesByCategory(String categoryName) {

        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));

        List<Expense> expensesByCategory = getExpensesByCategory(categoryName);
        long amountByCategory = 0;


        for (Expense expense : expensesByCategory) {
            if (expense.getDateOfSubmission().isBefore(end)
                    && expense.getDateOfSubmission().isAfter(start)) {
                amountByCategory += expense.getAmount();
            }
        }
        return amountByCategory;
    }

    public Long sumOfAmountInMonthExpensesByDepartment(String departmentName) {

        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));

        List<Expense> expensesByDepartment = expenseRepository.findAll();
        long amountByDepartment = 0L;

        for (Expense expense : expensesByDepartment) {
            if (expense.getUser().getDepartment().toString().equalsIgnoreCase(departmentName)) {
                if (expense.getDateOfSubmission().isBefore(start)
                        && expense.getDateOfSubmission().isAfter(end)) {
                    amountByDepartment += expense.getAmount();
                }
            }
        }
        return amountByDepartment;
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

    public User findUserByPrincipal(Principal principal) {
        Employee employee = employeesRepository.findByEmail(principal.getName());
        if (employee == null) {
            employee = managerRepository.findByEmail(principal.getName());
        }
        return employee;
    }

    public Long totalAmountOfExpensesCurrentMonthByPrincipal(Principal principal) {

        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));

        User userByPrincipal = findUserByPrincipal(principal);

        Set<Expense> expenses = userByPrincipal.getExpenses();

        long totalAmountOfExpensesCurrentMonth = 0;

        for (Expense expense : expenses) {
            if (expense.getDateOfSubmission().isBefore(end) &&
                    expense.getDateOfSubmission().isAfter(start)) {
                totalAmountOfExpensesCurrentMonth += expense.getAmount();
            }
        }
        return totalAmountOfExpensesCurrentMonth;
    }

    public Long amountAvailableForCurrentMonth(Principal principal) {

        User userByPrincipal = findUserByPrincipal(principal);

        Long departmentBudget = userByPrincipal.getDepartment().getDepartmentBudget();

        return departmentBudget - totalAmountOfExpensesCurrentMonthByPrincipal(principal);


    }

    public Long comparePurchaseDateWithSubmissionDate(ExpenseDTO2 expenseDTO2) {

        LocalDate dateOfPurchase = expenseDTO2.getDateOfPurchase();
        LocalDate submissionDate = LocalDate.now();

        return ChronoUnit.DAYS.between(dateOfPurchase, submissionDate);


    }

    public boolean purchaseTooExpensiveForCategory(ExpenseDTO2 expenseDTO2) {

        ExpenseCategory category = expenseDTO2.getCategory();

        switch (category) {
            case FOOD:
                if (expenseDTO2.getAmount() > 100) {
                    return true;
                }
                break;
            case COMMODITY:
                if (expenseDTO2.getAmount() > 500) {
                    return true;
                }
                break;
            case EVENT:
                if (expenseDTO2.getAmount() > 2000) {
                    return true;
                }
                break;
            case TRAVEL:
                if (expenseDTO2.getAmount() > 1000) {
                    return true;
                }
                break;
            case EXTRA:
                if (expenseDTO2.getAmount() > 300) {
                    return true;
                }
                break;
            default:
                System.out.println("Category not found!!");

        }

        return false;
    }

    public boolean purchaseDateAndSubmissionDateIsNotTheSameMonth(ExpenseDTO2 expenseDTO2){

        LocalDate dateOfPurchase = expenseDTO2.getDateOfPurchase();

        return dateOfPurchase.getMonth() != LocalDate.now().getMonth();
    }





    //this method will execute a combination of different methods
    public void analyzeExpense(ExpenseDTO2 expenseDTO2, Principal principal) {

        // first check: if the purchase date is >= 14 days; add violation TODO
        if (comparePurchaseDateWithSubmissionDate(expenseDTO2) >= //>= 14) {
        findUserByPrincipal(principal).getPolicy().getNumberOfDaysToSubmitAnExpense()){
            employeeService.addViolationToUser(findUserByPrincipal(principal));
        }

        // second check: if the purchase overcome the amount available for the current month
        if (expenseDTO2.getAmount() > amountAvailableForCurrentMonth(principal)) {

            // send notification for manager

            //testing
            employeeService.addViolationToUser(findUserByPrincipal(principal));
        }

        // third check: if the current month available is negative and the user perform another expense; add violation
        if (amountAvailableForCurrentMonth(principal) <= 0 && expenseDTO2.getAmount() > 0) {
            employeeService.addViolationToUser(findUserByPrincipal(principal));
        }

        // forth check: if the current purchase is too expensive for specific category; add violation
        if (purchaseTooExpensiveForCategory(expenseDTO2)) {
            employeeService.addViolationToUser(findUserByPrincipal(principal));
        }

        // fifth check: if the purchase was made by CC the time
        // limit of the submission should be in the exact current month
        if (expenseDTO2.isCompanyCC() && purchaseDateAndSubmissionDateIsNotTheSameMonth(expenseDTO2)){
            employeeService.addViolationToUser(findUserByPrincipal(principal));
        }


    }


}
