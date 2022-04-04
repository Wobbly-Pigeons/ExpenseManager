package wobbly.pigeons.expensemanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wobbly.pigeons.expensemanager.service.EmployeeService;
import wobbly.pigeons.expensemanager.service.ManagerService;

@RestController(value = "/api/v1/")
@AllArgsConstructor
public class UserController {

    private final EmployeeService employeeService;
    private final ManagerService managerService;

    @GetMapping(value = "/registration")
    public String registration() {
        return "register";
    }

}
