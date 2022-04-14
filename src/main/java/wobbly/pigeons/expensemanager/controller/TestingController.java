package wobbly.pigeons.expensemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ExpenseService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class TestingController {

    private final ExpenseService expenseService;
    private final EmployeeService employeeService;

    @GetMapping("/test")
    String testingValues(Principal principal, Model model){

        model.addAttribute("currentUsername", principal.getName());

        model.addAttribute("totalAmount",
                expenseService.totalAmountOfExpensesCurrentMonthByPrincipal(principal));

        model.addAttribute("amountAvailable",
                expenseService.amountAvailableForCurrentMonth(principal));

        model.addAttribute("violations",
                employeeService.getUserViolations(principal));


        return "testing";
    }
}
