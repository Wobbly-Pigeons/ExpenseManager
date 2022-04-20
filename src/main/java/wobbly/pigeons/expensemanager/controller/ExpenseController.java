package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.CurrenciesAllowed;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseCommentFormDTO;
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
import java.util.Locale;


@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PutMapping(value = "/expenses/{id}/{status}")
    public String managerUpdateExpenseStatus(@PathVariable("id") Long id, @PathVariable("status") String status,
                                             @ModelAttribute ExpenseCommentFormDTO comment) {
        expenseService.commentAndReturnExpenseToEmployee(id, status, comment);
        return "redirect:/expense_management";
    }

    @GetMapping(value = "/expenses/{id}/{status}")
    public String managerUpdateExpenseStatus(@PathVariable("id") Long id, @PathVariable("status") String status, Model model) {
        switch(status.toLowerCase(Locale.ROOT)) {
            case "approve":
                expenseService.approveExpense(id);
                return "redirect:/expense_management";
            case "update":
                Expense expenseToBeUpdated = expenseService.getExpenseById(id);
                model.addAttribute("Expense", expenseToBeUpdated);
                model.addAttribute("expenseCategoryList", ExpenseCategory.values());
                model.addAttribute("currenciesAllowedList", CurrenciesAllowed.values());
                return "expense_edit";
            case "deny": case "needs-info":
                Expense expenseToBeDenied = expenseService.getExpenseById(id);
                model.addAttribute("expense", expenseToBeDenied);
                model.addAttribute("status", status.toLowerCase(Locale.ROOT));
                model.addAttribute("expenseId", id);
                model.addAttribute("expenseCommentForm", new ExpenseCommentFormDTO(id));
                return "expense_comment_form";
            default:
                return "malformed_url";
        }
    }

    @PutMapping("/expenses/{id}")
    public String updateExpenseById (@PathVariable long id, @RequestBody Expense newExpense){
        expenseService.updateExpense(id, newExpense);
        return "index";
        }


    @GetMapping(value = "/expenses/new_expense")
    public String newExpenseForm(Model model) {

        model.addAttribute("ExpenseDTO2", new ExpenseDTO2());
        model.addAttribute("expenseCategoryList", ExpenseCategory.values());
        model.addAttribute("currenciesAllowedList", CurrenciesAllowed.values());
        //AGA I THINK U PUT THE VISUALS/GOOGLE THING HERE
        return "expense_submission";
    }


    @PostMapping ("/expenses/new_expense")
    public String addExpense (@ModelAttribute ExpenseDTO2 expenseDTO2, Principal principal) throws IOException {
         expenseService.addExpense(expenseDTO2, principal);
         //  expenseService.analyzeExpense(expenseDTO2,principal);
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

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(){
        return expenseService.getAllExpenses();
    }

    @GetMapping("/expenses/{id}")
        public Expense getExpensesById (@PathVariable long id){
        return expenseService.getExpenseById(id);
    }

    @GetMapping("/expenses/{purchaseDate}")
        public List<Expense> getExpensesByPurchaseDate (@PathVariable LocalDate purchaseDate){
        return expenseService.getExpensesByPurchaseDate(purchaseDate);
    }

    @GetMapping("/expenses/{submissionDate}")
        public List<Expense> getExpensesBySubmissionDate (@PathVariable LocalDate submissionDate){
        return expenseService.getExpensesBySubmissionDate(submissionDate);
    }

    @GetMapping("/expenses/{employeeId}")
        public Collection<Expense> getExpensesByEmployeeId (@PathVariable long employeeId){
        return expenseService.getExpensesByEmployeeId(employeeId);
    }

    @GetMapping("/expenses/{category}")
    public Collection<Expense> getExpensesByCategory (@PathVariable String category){
        return expenseService.getExpensesByCategory(category);
    }

        }
