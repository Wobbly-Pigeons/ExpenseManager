package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.CurrenciesAllowed;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO2;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.ExpenseCategory;
import wobbly.pigeons.expensemanager.repository.ExpenseRepository;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ExpenseService;
import wobbly.pigeons.expensemanager.service.ManagerService;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Controller(value = "/expenses")

@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;
    private final EmployeeService employeeService;
    private final ManagerService managerService;


    @PutMapping(value = "/{id}/{status}")
    public String managerUpdateExpenseStatus(@PathVariable("id") Long id, @PathVariable("status") String status) {
        expenseService.commentAndReturnExpenseToEmployee(id, status);
        return "redirect:/expense_management";
    }

    @GetMapping(value = "/{id}/{status}")
    public String managerUpdateExpenseStatus(@PathVariable("id") Long id, @PathVariable("status") String status, Model model) {
        model.addAttribute("status", status);
        return "comment_expense_management_form";
    }

    @PutMapping("/{id}")
    public Expense updateExpenseById (@PathVariable long id, @RequestBody Expense newExpense){
        return expenseService.updateExpense(id, newExpense);
        }


    @GetMapping(value = "/new_expense")
    public String newExpenseForm(Model model) {

        model.addAttribute("ExpenseDTO2", new ExpenseDTO2());
        model.addAttribute("expenseCategoryList", ExpenseCategory.values());
        model.addAttribute("currenciesAllowedList", CurrenciesAllowed.values());
        //AGA I THINK U PUT THE VISUALS/GOOGLE THING HERE
        return "expense_submission";
    }

    @GetMapping(value = "/receipt/{expenseId}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable Long expenseId){
        return expenseService.getExpenseById(expenseId).getReceipt();
    }

    @PostMapping ("/new_expense")
    public String addExpense (@ModelAttribute ExpenseDTO2 expenseDTO2, Principal principal) throws IOException {
           expenseService.addExpense(expenseDTO2, principal);
        //the above line made for a 500 error... will need to fix!
        return "thank_you_for_submitting";
    }


//    @GetMapping("/submitted")
//    public String thankYouForSubmitting(@ModelAttribute ExpenseDTO2 expenseDTO2, Model model) {
//        return "thank_you_for_submitting";
//    }
    // ______________________________________________________________________________


    @DeleteMapping("/{id}")
    public void deleteExpenseById(@PathVariable long id){
        expenseService.deleteExpense(id);
        }

    @GetMapping()
    public List<Expense> getAllExpenses(){
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
        public Expense getExpensesById (@PathVariable long id){
        return expenseService.getExpenseById(id);
    }

    @GetMapping("/{purchaseDate}")
        public List<Expense> getExpensesByPurchaseDate (@PathVariable LocalDate purchaseDate){
        return expenseService.getExpensesByPurchaseDate(purchaseDate);
    }

    @GetMapping("/{submissionDate}")
        public List<Expense> getExpensesBySubmissionDate (@PathVariable LocalDate submissionDate){
        return expenseService.getExpensesBySubmissionDate(submissionDate);
    }

    @GetMapping("/{employeeId}")
        public Collection<Expense> getExpensesByEmployeeId (@PathVariable long employeeId){
        return expenseService.getExpensesByEmployeeId(employeeId);
    }

    @GetMapping("/{category}")
    public Collection<Expense> getExpensesByCategory (@PathVariable String category){
        return expenseService.getExpensesByCategory(category);
    }

        }
