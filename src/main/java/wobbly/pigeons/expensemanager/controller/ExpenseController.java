package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wobbly.pigeons.expensemanager.model.CurrenciesAllowed;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO2;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.ExpenseCategory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @GetMapping("/receipt/{Id}", produces = MediaType.IMAGE_JPEG_VALUE)
    //Resource is an interface in Spring used to load the resources i/e files
    Resource dowloadReceipt(@PathVariable long receiptId){
        byte[] receipt = expenseRepository.findById(receiptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getReceipt();
        // wrapping the stored file bytes in a ByteArrayResource which let us download
        return new ByteArrayResource(receipt);
    }

    @PostMapping ("/new_expense")
    public String addExpense (@ModelAttribute ExpenseDTO2 expenseDTO2, Principal principal) throws IOException {
           expenseService.addExpense(expenseDTO2, principal);
        //the above line made for a 500 error... will need to fix!
        return "thank_you_for_submitting";
    }


    //uploading receipt
    @PostMapping()
    Long uploadReceipt(@RequestParam MultipartFile file) throws Exception {
        return expenseService.save(file.getBytes(), file.getOriginalFilename());
    }
    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    FileSystemResource downloadImage(@PathVariable Long imageId) throws Exception {
        return expenseService.find(imageId);
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
