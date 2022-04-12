package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wobbly.pigeons.expensemanager.model.DTO.ExpenseDTO;
import wobbly.pigeons.expensemanager.model.DTO.UserDTO;
import wobbly.pigeons.expensemanager.model.Employee;
import wobbly.pigeons.expensemanager.model.Expense;
import wobbly.pigeons.expensemanager.model.Manager;
import wobbly.pigeons.expensemanager.model.User;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ExpenseService;
import wobbly.pigeons.expensemanager.service.ManagerService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final EmployeeService employeeService;
    private final ManagerService managerService;
    private final ExpenseService expenseService;

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("UserDTO", new UserDTO());
        return "registration";
    }

    @GetMapping(value = "/username")
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    /**
     * This is the landing page for our users after they log in.
     * It will show a short page of their 5 most recently updated (by themselves or otherwise) expenses,
     * and will show a quick summary showing their remaining budget
     * @param model
     * @return department list page
     */
    @GetMapping(path = "/index")
    public String listExpensesForUser(Model model, Principal principal){
        String manager = "index";
        return findPaginatedUserIndex(1, "index", "dateModified", "asc", model, principal);
    }

  /**
   * @param pageNo
   * @param sortField
   * @param sortDir
   * @param model
   * @return expenses list
   */
  @GetMapping(path = "/{index}/page/{pageNo}")
  public String findPaginatedUserIndex(
      @PathVariable(value = "pageNo") int pageNo,
      @PathVariable(value = "index") String index,
      @RequestParam("sortField") String sortField,
      @RequestParam("sortDir") String sortDir,
      Model model,
      Principal principal) {
        int pageSize = 5;
        User currentUser = null;
        Page<Expense> page = null;
        List<Expense> listExpenses = null;

        if(index.equals("index")) {
            currentUser = employeeService.findByEmail(principal.getName());

            page = employeeService.findPaginatedExpensesByUser(pageNo, pageSize, sortField, sortDir, (Employee) currentUser);
            listExpenses = page.getContent();

        } else if (index.equals("expense_management")) {
            currentUser = managerService.findByEmail(principal.getName());
            model.addAttribute("manager", false);
            model.addAttribute("typeOfDash", "expense_management");

            page = managerService.findPaginatedExpensesByUser(pageNo, pageSize, sortField, sortDir, (Manager) currentUser);
            listExpenses = page.getContent();
        }


      assert currentUser != null;
      model.addAttribute("currentUsername", currentUser.getName());
//        model.addAttribute("currentUserDepartment",currentUser.getDepartment());
//        model.addAttribute("currentUserId",currentUser.getId());

//        model.addAttribute("currentMonthAmountExpense",
//                expenseService.totalAmountOfExpensesCurrentMonthByEmployeeId(currentUser.getId()));
//
//        model.addAttribute("currentBudgetLimit",
//                expenseService.controlBudgetLimitForCurrentMonth(currentUser.getId()));



        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listExpenses", currentUser.getExpenses());
        return "index";
    }

    @GetMapping(value = "/new_expense")
    public String newExpenseForm(Model model) {
        model.addAttribute("ExpenseDTO", new ExpenseDTO());
        return "expense_form";
    }

    @GetMapping(value = "/expense_management")
    public String listExpensesForManager(Model model, Principal principal) {
        return findPaginatedUserIndex(1, "expense_management", "dateModified", "asc", model, principal);
    }
}