package wobbly.pigeons.expensemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wobbly.pigeons.expensemanager.models.Expense;
import wobbly.pigeons.expensemanager.services.ExpenseService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path ="api/v1/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PutMapping("/{id}")
    public Expense updateExpenseById (@PathVariable long id, @RequestBody Expense newExpense){
        return expenseService.updateExpense(id, newExpense);
        }

    @PostMapping
    public Expense addExpense (@RequestBody Expense newExpense){
        return expenseService.addExpense(newExpense);
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
        public List<Expense> getExpensesByPurchaseDate (@PathVariable LocalDateTime purchaseDate){
        return expenseService.getByPurchaseDate(purchaseDate);
    }

    @GetMapping("/{submissionDate}")
        public List<Expense> getExpensesBySubmissionDate (@PathVariable LocalDateTime submissionDate){
        return expenseService.getBySubmissionDate(submissionDate);
    }

    @GetMapping("/{employeeId}")
        public Collection<Expense> getExpensesByEmployeeId (@PathVariable long employeeId){
        return expenseService.getByEmployeeId(employeeId);
    }

    @GetMapping("/{category}")
    public Collection<Expense> getExpensesByCategory (@PathVariable String category){
        return expenseService.getByCategory(category);
    }

        }
