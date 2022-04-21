package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.CurrenciesAllowed;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseCommentFormDTO;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO2;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.ExpenseCategory;
import wobbly.pigeons.expensemanager.service.ExpenseService;

import java.io.IOException;
import java.security.Principal;
import java.util.Locale;


@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping(value = "/expenses/{id}/{status}")
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

    @PostMapping(value = "/expenses/{id}")
    public String updateExpenseById (@PathVariable long id, @ModelAttribute Expense newExpense) throws IOException {
        expenseService.updateExpense(id, newExpense);
        return "redirect:/index";
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
         //expenseService.updateExpenseAmounts(principal, expenseDTO2);
         //  expenseService.analyzeExpense(expenseDTO2,principal);
        return "thank_you_for_submitting";
    }

        }
