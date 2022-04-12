package wobbly.pigeons.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO2;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.IndividualPolicy;
import wobbly.pigeons.expensemanager.model.ReceiptStatuses;
import wobbly.pigeons.expensemanager.repository.EmployeeRepository;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;
import wobbly.pigeons.expensemanager.util.ConverterRestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final EmployeeRepository employeesRepository;
    private final ConverterRestClient converterRestClient;



    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(ExpenseDTO2 expenseDTO2, MultipartFile file) {
        Employee employee = employeesRepository.findById(expenseDTO2.getUser_id()).orElseThrow();

        Expense newExpense = new Expense(expenseDTO2.getReceipt(), expenseDTO2.getAmount(), employee);
        Double convertedAmount = converterRestClient.getConversionAmount(newExpense.getLocalCurrency().toString(), "EUR", newExpense.getAmount());
        newExpense.setConvertedAmount(convertedAmount);
        //RECEIPT UpLOADING AGA :)

        return expenseRepository.save(newExpense);
    }
    public Expense addExpense(ExpenseDTO expenseDTO) {
        Employee employee = employeesRepository.findById(expenseDTO.getUser_id()).orElseThrow();
        Expense newExpense = new Expense(expenseDTO.getAmount(), employee);
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
            if (expense.getDateOfSubmission().isBefore(start)
                    && expense.getDateOfSubmission().isAfter(end)) {
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

    public Long totalAmountOfExpensesCurrentMonthByEmployeeId(Long employeeId) {

        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));

        Set<Expense> expenses = employeesRepository.getById(employeeId).getExpenses();
        long totalAmountOfExpensesCurrentMonth = 0;

        for (Expense expense : expenses) {
            if (expense.getDateOfSubmission().isBefore(end) &&
            expense.getDateOfSubmission().isAfter(start)){
                totalAmountOfExpensesCurrentMonth += expense.getAmount();
            }
        }
        return totalAmountOfExpensesCurrentMonth;
    }

    public Long controlBudgetLimitForCurrentMonth(Long employeeId){

        Long totalAmountOfExpensesCurrentMonth =
                totalAmountOfExpensesCurrentMonthByEmployeeId(employeeId);

        Employee getEmployeeById = employeesRepository.getById(employeeId);

        IndividualPolicy individualPolicy = new IndividualPolicy(employeesRepository);
        Long individualBudget = individualPolicy.getIndividualBudget(getEmployeeById.getDepartment().toString());

        return individualBudget - totalAmountOfExpensesCurrentMonth;
    }

    public void checkExpenseInPolicy(Expense newExpense) {

//        TemporalField field =
//        LocalDate currentMonth = LocalDate.now();
//        currentMonth.range()

        Long departmentBudget = newExpense.getUser().getDepartment().getDepartmentBudget();
//        Long departmentBudgetUsed = sumOfAmountInMonthExpensesByDepartment(
//                newExpense.getUser().getDepartment().toString());


        if (newExpense.getAmount() >= departmentBudget) {
            //TODO
        }
    }

    public void approveExpense(Long id) {
        expenseRepository.findById(id).orElseThrow().setCurrentStatus(ReceiptStatuses.APPROVED);
    }

    public void commentAndReturnExpenseToEmployee(Long id, String status) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        if(status.equals("deny")) {
            expense.setCurrentStatus(ReceiptStatuses.REJECTED);
        } else if (status.equals("nmi")) {
            expense.setCurrentStatus(ReceiptStatuses.NEEDSFURTHERINFO);
        }

    }
}
