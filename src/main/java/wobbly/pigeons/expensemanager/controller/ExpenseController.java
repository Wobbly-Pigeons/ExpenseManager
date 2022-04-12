package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.service.ExpenseService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Controller(value = "/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

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

    @PostMapping
    public Expense addExpense (@ModelAttribute ExpenseDTO expenseDTO){
        return expenseService.addExpense(expenseDTO);
        }

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
