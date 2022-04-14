package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final PolicyRepository policyRepository;

    private final ExpenseRepository expenseRepository;
    private final EmployeeRepository employeesRepository;
    private final ConverterRestClient converterRestClient;
    private final ManagerRepository managerRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

//    public Expense addExpense(ExpenseDTO2 expenseDTO2, MultipartFile file) {
//        Employee employee = employeesRepository.findById(expenseDTO2.getUser_id()).orElseThrow();
//
//        Expense newExpense = new Expense(expenseDTO2.getReceipt(), expenseDTO2.getAmount(), employee);
//        Long convertedAmount = converterRestClient.getConversionAmount(newExpense.getLocalCurrency().toString(), "EUR", newExpense.getAmount());
////        newExpense.setConvertedAmount(convertedAmount);
//        //RECEIPT UpLOADING AGA :)
//
//        return expenseRepository.save(newExpense);
//    }

//    public Expense addExpense(ExpenseDTO expenseDTO) {
//        Employee employee = employeesRepository.findById(expenseDTO.getUser_id()).orElseThrow();
//        Expense newExpense = new Expense(expenseDTO.getAmount(), employee);
//        return expenseRepository.save(newExpense);
//    }

//    public Expense addExpense(ExpenseDTO2 expenseDTO2, Employee employee){
//    Expense newExpense = new Expense(expenseDTO2.getReceipt(), expenseDTO2.getAmount(), employee);
//    Long convertedAmount =
//        converterRestClient.getConversionAmount(
//            newExpense.getLocalCurrency().toString(), "EUR", newExpense.getAmount());
//    newExpense.setConvertedAmount(convertedAmount);
//    return expenseRepository.save(newExpense);
//        }

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
        newExpense.setCurrentStatus(ReceiptStatuses.SUBMITTEDANDPENDING);
        newExpense.setLocalCurrency(expenseDTO2.getLocalCurrency());
        newExpense.setDateModified(LocalDateTime.now());
        newExpense.setAmount(converterRestClient.getConversionAmount(newExpense.getLocalCurrency().toString(), "EUR", newExpense.getAmount()).longValue());

        return expenseRepository.save(newExpense);
    }

    public Expense getExpenseById(long id) {
        return expenseRepository.findById(id).orElseThrow();
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
        List<Expense> purchaseDateList = new ArrayList<Expense>();

        for (Expense expense : all) {
            if (expense.getDateOfPurchase().toLocalDate().isEqual(purchaseDate)) {
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
        expenseRepository.findById(id).orElseThrow().setCurrentStatus(ReceiptStatuses.APPROVED);
    }

    public void commentAndReturnExpenseToEmployee(Long id, String status) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        if (status.equals("deny")) {
            expense.setCurrentStatus(ReceiptStatuses.REJECTED);
        } else if (status.equals("nmi")) {
            expense.setCurrentStatus(ReceiptStatuses.NEEDSFURTHERINFO);
        }

    }





    public User findUserByPrincipal(Principal principal){
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


    public Long analyzeExpense(ExpenseDTO2 expenseDTO2, Principal principal) {

        Long totalAmount = totalAmountOfExpensesCurrentMonthByPrincipal(principal);

//        DepartmentPolicy departmentPolicy = new DepartmentPolicy();
        //  Long departmentPolicyBudget = departmentPolicy.getDepartmentPolicyBudget();
        // TODO all values are good... only the division by all departments dont work

        Long sumOfTotalWithExpenseAmount = totalAmount + expenseDTO2.getAmount();

        return sumOfTotalWithExpenseAmount;
    }
}
